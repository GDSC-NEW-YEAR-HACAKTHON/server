package com.gdschackathon.newyearserver.domain.challenge.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetChallengeRes {
	private String goal;
	private String deadline;
	private String code;
	private List<ChallengeStepResDto> challengeSteps;
}
