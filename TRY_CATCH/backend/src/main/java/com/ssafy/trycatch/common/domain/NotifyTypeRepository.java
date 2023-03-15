package com.ssafy.trycatch.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyTypeRepository extends JpaRepository<NotifyType, Long> {
}
