package com.gdschackathon.newyearserver.domain.challenge;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdschackathon.newyearserver.domain.challenge.dto.ChallengeStepReqDto;
import com.gdschackathon.newyearserver.domain.challenge.dto.ChallengeStepResDto;
import com.gdschackathon.newyearserver.domain.challenge.dto.GetChallengeRes;
import com.gdschackathon.newyearserver.domain.challenge.dto.PostChallengeReq;
import com.gdschackathon.newyearserver.domain.challenge.dto.PutChallengeCheckReq;
import com.gdschackathon.newyearserver.domain.challenge.dto.PutChallengeCheckRes;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {
	private final ChallengeService challengeService;
	@GetMapping
	@Transactional(readOnly = true)
	public GetChallengeRes getChallengeStepList(
	) {
		Long userId = 3L;
		return challengeService.getChallenge(userId);
	}

	@PostMapping
	@Transactional()
	public GetChallengeRes postChallengeStep(
		@RequestBody PostChallengeReq postChallengeReq
	) {
		Long userId = 3L;
		return challengeService.postChallenge(userId, postChallengeReq);
	}

	@PutMapping("/check")
	@Transactional()
	public PutChallengeCheckRes putChallengeStepCheck(
		@RequestBody PutChallengeCheckReq putChallengeCheckReq
	) {
		Long userId = 1L;
		return challengeService.putChallengeCheck(userId, putChallengeCheckReq);
	}

}
