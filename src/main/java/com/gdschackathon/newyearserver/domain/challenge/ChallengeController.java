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
        @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return challengeService.getChallenge(principal.memberId());
    }

    @PostMapping
    @Transactional()
    public GetChallengeRes postChallengeStep(
            @RequestBody PostChallengeReq postChallengeReq,
        @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return challengeService.postChallenge(principal.memberId(), postChallengeReq);
    }

    @PutMapping("/check")
    @Transactional()
    public PutChallengeCheckRes putChallengeStepCheck(
            @RequestBody PutChallengeCheckReq putChallengeCheckReq,
        @AuthenticationPrincipal CustomUserPrincipal principal

    ) {
        return challengeService.putChallengeCheck(principal.memberId(), putChallengeCheckReq);
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
