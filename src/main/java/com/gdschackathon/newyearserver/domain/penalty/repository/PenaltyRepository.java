package com.gdschackathon.newyearserver.domain.penalty.repository;

import com.gdschackathon.newyearserver.domain.penalty.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
