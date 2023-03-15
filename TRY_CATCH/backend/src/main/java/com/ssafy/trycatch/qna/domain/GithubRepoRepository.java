package com.ssafy.trycatch.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GithubRepoRepository extends JpaRepository<GithubRepo, Long> {
    Optional<GithubRepo> findByUserId(Long userId);

}