package com.gdschackathon.newyearserver.domain.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutChallengeCheckRes {
	private Long challengeStepId;
	private boolean isCompleted;
}
