package com.gdschackathon.newyearserver.domain.challenge.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChallengeStepResDto {
	private String content;
	private String deadline;
	private boolean completed;
}
