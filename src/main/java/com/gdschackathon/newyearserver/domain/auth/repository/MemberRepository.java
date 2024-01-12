package com.gdschackathon.newyearserver.domain.auth.repository;

import com.gdschackathon.newyearserver.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPersonalToken(String personalToken);

}
