package com.ssafy.trycatch.common.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.controller.dto.BookmarkRequestDto;
import com.ssafy.trycatch.common.controller.dto.FindBookmarkedFeedDto;
import com.ssafy.trycatch.common.controller.dto.FindBookmarkedQuestionDto;
import com.ssafy.trycatch.common.controller.dto.FindBookmarkedRoadmapDto;
import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.exceptions.BookmarkDuplicatedException;
import com.ssafy.trycatch.common.service.exceptions.LikesDuplicatedException;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.feed.service.ESFeedService;
import com.ssafy.trycatch.feed.service.FeedService;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.service.RoadmapService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.trycatch.common.domain.TargetType.*;

@RestController
@RequestMapping("/${apiPrefix}/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final QuestionService questionService;
    private final RoadmapService roadmapService;
    private final FeedService feedService;
    private final UserService userService;
    private final ESFeedService esFeedService;

    @Autowired
    public BookmarkController(
            BookmarkService bookmarkService,
            QuestionService questionService,
            RoadmapService roadmapService,
            FeedService feedService,
            UserService userService,
            ESFeedService esFeedService
    ) {
        this.bookmarkService = bookmarkService;
        this.questionService = questionService;
        this.roadmapService = roadmapService;
        this.feedService = feedService;
        this.userService = userService;
        this.esFeedService = esFeedService;
    }

    /**
     * @param requestUser 로그인된 유저
     * @param bookmarkRequestDto 북마크 요청 dto
     * @return 생성 성공 시 201 Created 응답
     */
    @PostMapping
    public ResponseEntity<Void> bookmarkTarget(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        // 게스트 요청 방지
        final Long userId = requestUser.getId();
        userService.findUserById(userId);


        // 중복 요청 방지
        final TargetType type = TargetType
                .valueOf(bookmarkRequestDto.getType());

        final Bookmark lastBookmark = bookmarkService
                .getLastBookmarkOrFalse(userId, bookmarkRequestDto.getId(), type);

        if (lastBookmark.getActivated()) {
            throw new BookmarkDuplicatedException();
        }

        // 북마크 생성과 저장
        final Bookmark newBookmark = bookmarkRequestDto.newBookmark(requestUser);
        bookmarkService.register(newBookmark);

        return ResponseEntity.status(201)
                             .build();
    }

    /**
     * @param requestUser 로그인된 유저
     * @param bookmarkRequestDto 북마크 요청 dto
     * @return 수정 성공 시 204 No Content 응답
     */
    @PutMapping
    public ResponseEntity<Void> removeBookmark(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        // 게스트 요청 방지
        final Long userId = requestUser.getId();
        userService.findUserById(userId);

        // 중복 요청 방지
        final TargetType type = TargetType
                .valueOf(bookmarkRequestDto.getType());

        final Bookmark lastBookmark = bookmarkService
                .getLastBookmarkOrThrow(userId, bookmarkRequestDto.getId(), type);

        if (!lastBookmark.getActivated()) {
            throw new LikesDuplicatedException();
        }

        // 마지막 likes를 가져와서 활성화 상태를 false로 변경
        lastBookmark.setActivated(false);
        bookmarkService.register(lastBookmark);

        return ResponseEntity.status(204)
                             .build();
    }

    /**
     * @param requestUser 요청자
     * @return 유저가 북마크한 질문 리스트를 FindBookmarkedQuestionDto로 반환
     */
    @GetMapping("/question")
    public ResponseEntity<List<FindBookmarkedQuestionDto>> findBookmarkedQuestions(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        // 게스트 요청 방지
        final Long userId = requestUser.getId();
        userService.findUserById(userId);

        // 북마크 서비스에서 userId, targetType, activated 로 활성화된 질문 List<Bookmark> 반환
        List<Bookmark> activatedBookmarks = bookmarkService
                .getActivatedBookmarks(userId, QUESTION);

        // List<Bookmark>을 List<Question>으로 변환
        // 삭제된 콘텐츠에 기록된 북마크 정보이면 그 기록을 찾아 activated를 false로 변경
        final List<Question> bookmarkedQuestions = new ArrayList<>();

        for (Bookmark bookmark : activatedBookmarks) {
            final Long questionId = bookmark.getTargetId();
            final Long foundId = questionService.findQuestionIdByIdOrNull(questionId);

              if (null == foundId) {
                final Bookmark lastBookmark = bookmarkService
                        .getLastBookmarkOrThrow(userId, questionId, QUESTION);
                lastBookmark.setActivated(false);
                bookmarkService.register(lastBookmark);
            }
            else {
                final Question question = questionService.findQuestionById(questionId);
                bookmarkedQuestions.add(question);
            }
        }

        // List<Question>을 List<FindBookmarkedQuestionDto>로 변환
        List<FindBookmarkedQuestionDto> bookmarkedQuestionsResponse = bookmarkedQuestions
                .stream()
                .map(FindBookmarkedQuestionDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookmarkedQuestionsResponse);
    }

    /**
     * @param requestUser 요청자
     * @return 유저가 북마크한 로드맵 리스트를 FindBookmarkedRoadmapDto 로 반환
     */
    @GetMapping("/roadmap")
    public ResponseEntity<List<FindBookmarkedRoadmapDto>> findBookmarkedRoadmaps(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        // 게스트 요청 방지
        final Long userId = requestUser.getId();
        userService.findUserById(userId);

        // 북마크 서비스에서 userId, targetType, activated 로 활성화된 로드맵 북마크 리스트 List<Bookmark> 반환
        List<Bookmark> activatedBookmarks = bookmarkService
                .getActivatedBookmarks(userId, ROADMAP);

        // List<Bookmark>을 List<Roadmap>으로 변환
        // 삭제된 콘텐츠에 기록된 북마크 정보이면 그 기록을 찾아 activated를 false로 변경
        final List<Roadmap> bookmarkedRoadmaps = new ArrayList<>();

        for (Bookmark bookmark : activatedBookmarks) {
            final Long roadmapId = bookmark.getTargetId();
            final Long foundId = roadmapService.findRoadmapIdByIdOrNull(roadmapId);

            if (null == foundId) {
                final Bookmark lastBookmark = bookmarkService
                        .getLastBookmarkOrThrow(userId, roadmapId, ROADMAP);
                lastBookmark.setActivated(false);
                bookmarkService.register(lastBookmark);
            }
            else {
                final Roadmap roadmap = roadmapService.findByRoadmapId(roadmapId);
                bookmarkedRoadmaps.add(roadmap);
            }
        }

        // List<Roadmap>을 List<FindBookmarkedRoadmapDto>로 변환
        List<FindBookmarkedRoadmapDto> bookmarkedRoadmapsResponse = bookmarkedRoadmaps
                .stream()
                .map(FindBookmarkedRoadmapDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookmarkedRoadmapsResponse);
    }


    @GetMapping("/feed")
    public ResponseEntity<List<FindBookmarkedFeedDto>> findBookmarkedFeeds(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        // 게스트 요청 방지
        final Long userId = requestUser.getId();
        userService.findUserById(userId);

        // 북마크 서비스에서 userId, targetType, activated 로 활성화된 로드맵 피드 리스트 List<Bookmark> 반환
        List<Bookmark> activatedBookmarks = bookmarkService
                .getActivatedBookmarks(userId, FEED);

        // List<Bookmark>을 List<Feed>으로 변환
        // 삭제된 콘텐츠에 기록된 북마크 정보이면 그 기록을 찾아 activated를 false로 변경
        final List<Feed> bookmarkedFeeds = new ArrayList<>();

        for (Bookmark bookmark : activatedBookmarks) {
            final Long feedId = bookmark.getTargetId();
            final Long foundId = feedService.findFeedIdByIdOrNull(feedId);

            if (null == foundId) {
                final Bookmark lastBookmark = bookmarkService
                        .getLastBookmarkOrThrow(userId, feedId, FEED);
                lastBookmark.setActivated(false);
                bookmarkService.register(lastBookmark);
            }
            else {
                final Feed feed = feedService.findById(feedId);
                bookmarkedFeeds.add(feed);
            }
        }

        // List<Feed>를 List<FindBookmarkedFeedDto>로 변환
        final List<FindBookmarkedFeedDto> responseDtoList = new ArrayList<>();

        for (Feed bookmarkedFeed : bookmarkedFeeds) {
            final String stringId = bookmarkedFeed.getEsId();
            final ESFeed esFeed = esFeedService.findById(stringId);
            final String logoSrc = feedService.findIconByCompany(esFeed.getPk());

            final FindBookmarkedFeedDto responseDto = FindBookmarkedFeedDto
                    .from(bookmarkedFeed, esFeed, logoSrc);

            responseDtoList.add(responseDto);
        }

        return ResponseEntity.ok(responseDtoList);
    }
}
