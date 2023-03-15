package com.ssafy.trycatch.user.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserAnswerDto {
	public final Long answerId;
	public final Long questionId;
	public final String questionTitle;
	public final String questionContent;
	public final Long timestamp;
	public final Integer likeCount;
	public final Boolean isLiked;
	public final String answerContent;

	@Builder
	public UserAnswerDto(
		Long answerId, Long questionId, String questionTitle, String questionContent, Long timestamp,
		Integer likeCount, Boolean isLiked, String answerContent
	) {
		this.answerId = answerId;
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.questionContent = questionContent;
		this.timestamp = timestamp;
		this.likeCount = likeCount;
		this.isLiked = isLiked;
		this.answerContent = answerContent;
	}
}
