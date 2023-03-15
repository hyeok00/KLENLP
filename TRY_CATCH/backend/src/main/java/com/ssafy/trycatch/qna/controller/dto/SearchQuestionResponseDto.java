package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;

@Data
public class SearchQuestionResponseDto {
    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     *
     * @param question 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static SearchQuestionResponseDto from(Question question) {
        final User author = question.getUser();
        final Set<Long> answerIds = question.getAnswers()
                                            .stream()
                                            .map(Answer::getId)
                                            .collect(Collectors.toSet());

        return SearchQuestionResponseDto.builder()
                .categoryName(question.getCategoryName())
                .authorUsername(author.getUsername())
                .title(question.getTitle())
                .content(question.getTitle())
                .timestamp(question.getCreatedAt()
                                   .atZone(TZ_SEOUL)
                                   .toInstant()
                                   .toEpochMilli())
                .updatedAt(question.getUpdatedAt())
                .viewCount(question.getViewCount())
                .likes(question.getLikes())
                .hidden(question.getHidden())
                .answerIds(answerIds)
                .build();
    }

    @Size(max = 30)
    private final QuestionCategory categoryName;
    @Size(max = 50)
    private final String authorUsername;
    @Size(max = 50)
    private final String title;
    private final String content;
    private final Long timestamp;
    private final Instant updatedAt;
    private final Integer viewCount;
    private final Integer likes;
    private final Boolean hidden;
    private final Set<Long> answerIds;

    @Builder
    public SearchQuestionResponseDto(
            QuestionCategory categoryName,
            String authorUsername,
            String title,
            String content,
            Long timestamp,
            Instant updatedAt,
            Integer viewCount,
            Integer likes,
            Boolean hidden,
            Set<Long> answerIds
    ) {
        this.categoryName = categoryName;
        this.authorUsername = authorUsername;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
        this.likes = likes;
        this.hidden = hidden;
        this.answerIds = answerIds;
    }
}
