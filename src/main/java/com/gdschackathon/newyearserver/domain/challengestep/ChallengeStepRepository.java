package com.gdschackathon.newyearserver.domain.challengestep;

import com.gdschackathon.newyearserver.domain.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeStepRepository extends JpaRepository<ChallengeStep, Long> {

    void deleteAllByChallenge(Challenge challenge);
}
