package com.gdschackathon.newyearserver.domain.challenge;

import java.util.Optional;

import com.gdschackathon.newyearserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
	Optional<Challenge> findByCode(String code);

    Optional<Challenge> findByMember(Member member);
}
