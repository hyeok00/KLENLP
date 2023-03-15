package com.ssafy.trycatch.gamification.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.gamification.domain.Badge;
import com.ssafy.trycatch.gamification.domain.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BadgeService extends CrudService<Badge, Long, BadgeRepository> {

    @Autowired
    public BadgeService(BadgeRepository repository) {
        super(repository);
    }

    public Optional<Badge> findById(Long badgeId) { return repository.findById(badgeId); }
}
