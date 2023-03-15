package com.ssafy.trycatch.qna.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ssafy.trycatch.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESQuestionRepository;
import com.ssafy.trycatch.qna.controller.annotation.IncreaseViewCount;
import com.ssafy.trycatch.qna.controller.dto.CreateQuestionRequestDto;
import com.ssafy.trycatch.qna.service.exceptions.AnswerNotFoundException;
import com.ssafy.trycatch.qna.service.exceptions.QuestionNotFoundException;
import com.ssafy.trycatch.qna.service.exceptions.RequestUserNotValidException;
import com.ssafy.trycatch.user.domain.User;
import org.springframework.util.StringUtils;


@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ESQuestionRepository esQuestionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionService(
            QuestionRepository questionRepository,
            ESQuestionRepository esQuestionRepository,
            AnswerRepository answerRepository
    ) {
        this.questionRepository = questionRepository;
        this.esQuestionRepository = esQuestionRepository;
        this.answerRepository = answerRepository;
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public Question saveQuestion(User requestUser, CreateQuestionRequestDto requestDto) {

        final Question question = requestDto.newQuestion(requestUser);
        questionRepository.save(question);

        final ESQuestion esQuestion = ESQuestion.of(question.getId(), requestDto);
        esQuestionRepository.save(esQuestion);

        return question;
    }

    public Question acceptAnswer(Long questionId, Long answerId, User user) {

        final Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        final Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        // 요청 유저가 질문 작성자가 아닌 경우나 본인 답변을 채택하려는 경우 예외처리
        final User questionAuthor = question.getUser();
        final User answerAuthor = answer.getUser();

        if ((user != questionAuthor) || (questionAuthor == answerAuthor)) {
            throw new RequestUserNotValidException();
        }

        question.setChosen(true);
        questionRepository.save(question);

        answer.setChosen(true);
        answerRepository.save(answer);

        return question;
    }

    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                                 .orElseThrow(QuestionNotFoundException::new);
    }

    public Long findQuestionIdByIdOrNull(Long questionId) {
        return questionRepository.findById(questionId)
                    .orElse(new Question())
                    .getId();
    }

    @IncreaseViewCount
    public Question findQuestionByIdWithViewCount(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
    }

    public List<Question> findAllQuestionsByCategory(QuestionCategory questionCategory, Pageable pageable) {
        return questionRepository
                .findByCategoryNameAndHiddenIsFalseOrderByCreatedAtDesc(questionCategory, pageable);
    }

    @Transactional
    public void updateQuestion(
            Long userId,
            Long questionId,
            String category,
            String title,
            String content,
            String errorCode,
            List<String> tags,
            Boolean hidden
    ) {
        final Question question = questionRepository.findById(questionId)
                                                    .orElseThrow(QuestionNotFoundException::new);

        if (!Objects.equals(question.getUser().getId(), userId)) {
            throw new RequestUserNotValidException();
        }

        final QuestionCategory questionCategory = QuestionCategory.valueOf(category);

        question.setCategoryName(questionCategory);
        question.setTitle(title);
        question.setContent(content);
        question.setErrorCode(errorCode);
        question.setTags(String.join(",", tags));
        question.setHidden(hidden);
        questionRepository.save(question);
    }

    /**
     * @throws IllegalArgumentException questionId가 없는 경우
     */
    public void deleteQuestion(Long questionId, Long userId) {
        final Question question = questionRepository.findById(questionId)
                                                    .orElseThrow(QuestionNotFoundException::new);
        if (!Objects.equals(question.getUser().getId(), userId)) {
            throw new RequestUserNotValidException();
        }
        questionRepository.deleteById(questionId);
    }

    public Page<ESQuestion> search(String query, QuestionCategory category, Pageable pageable) {
        if (StringUtils.hasText(query)) {
            return esQuestionRepository.searchByTitleOrContentAndCategory(query, category.name(), pageable);
        }
        return esQuestionRepository.searchByCategory(category.name(), pageable);
    }

    public List<Question> findPopularQuestions(Optional<String> category, Pageable pageable) {
        final List<Question> popularQuestions;
        final QuestionCategory questionCategory = QuestionCategory.valueOf(category.orElse("DEFAULT"));
        if (QuestionCategory.DEFAULT == questionCategory) {
            popularQuestions = questionRepository.findAllByOrderByLikesDesc(pageable);
        } else {
            popularQuestions = questionRepository
                    .findByCategoryNameOrderByLikesDesc(questionCategory, pageable);
        }

        return popularQuestions;
    }
}
