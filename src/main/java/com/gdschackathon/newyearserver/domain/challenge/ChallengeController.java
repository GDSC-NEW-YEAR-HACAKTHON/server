package com.gdschackathon.newyearserver.domain.challenge;

import com.gdschackathon.newyearserver.domain.auth.principal.CustomUserPrincipal;
import com.gdschackathon.newyearserver.domain.challenge.dto.GetChallengeRes;
import com.gdschackathon.newyearserver.domain.challenge.dto.PostChallengeReq;
import com.gdschackathon.newyearserver.domain.challenge.dto.PutChallengeCheckReq;
import com.gdschackathon.newyearserver.domain.challenge.dto.PutChallengeCheckRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/success")
    public String challengeSuccess(@AuthenticationPrincipal CustomUserPrincipal principal) throws Exception {
        challengeService.challengeSuccess(principal.memberId());

        return "send success email";
    }

    @DeleteMapping("/fail")
    public String challengeFail(@AuthenticationPrincipal CustomUserPrincipal principal) throws Exception {
        challengeService.challengeFail(principal.memberId());

        return "send fail email";
    }
}
