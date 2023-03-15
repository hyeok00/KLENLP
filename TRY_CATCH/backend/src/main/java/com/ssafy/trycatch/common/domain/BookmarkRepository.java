package com.ssafy.trycatch.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserIdAndTargetTypeAndActivatedIsTrue(Long userId, TargetType targetType);

    Optional<Bookmark> findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(Long userId, Long targetId, TargetType targetType);

    boolean existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(
            Long userId, Long targetId,
            TargetType targetType
    );



}
