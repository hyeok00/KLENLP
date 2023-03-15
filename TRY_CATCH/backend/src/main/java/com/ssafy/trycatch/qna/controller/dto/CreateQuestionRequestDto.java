package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateQuestionRequestDto {
    private String category;
    private String title;
    private String content;
    private String errorCode;
    private List<String> tags;

    public Question newQuestion(User user) {
        final QuestionCategory categoryName = QuestionCategory.valueOf(category);

        return Question.builder()
                .categoryName(categoryName)
                .user(user)
                .title(title)
                .content(content)
                .errorCode(errorCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(Instant.now())
                .chosen(false)
                .viewCount(0)
                .likes(0)
                .hidden(false)
                .tags(String.join(",", tags))
                .build();
    }
}
