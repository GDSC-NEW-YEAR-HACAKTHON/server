package com.gdschackathon.newyearserver.domain.challenge;

import com.gdschackathon.newyearserver.domain.challenge.dto.*;
import com.gdschackathon.newyearserver.domain.challengestep.ChallengeStep;
import com.gdschackathon.newyearserver.domain.challengestep.ChallengeStepRepository;
import com.gdschackathon.newyearserver.domain.email.service.MailService;
import com.gdschackathon.newyearserver.domain.member.entity.Member;
import com.gdschackathon.newyearserver.domain.member.repository.MemberRepository;
import com.gdschackathon.newyearserver.domain.watcher.WatcherRepository;
import com.gdschackathon.newyearserver.global.util.ChallengeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeStepRepository challengeStepRepository;
    private final MemberRepository memberRepository;
    private final WatcherRepository watcherRepository;
    private final MailService mailService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public GetChallengeRes getChallenge(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(
                RuntimeException::new
        );

        List<Challenge> challenges = member.getChallenges();

        if (challenges.isEmpty()) {
            return null;
        }
        Challenge challenge = challenges.get(0);

        GetChallengeRes getChallengeRes = new GetChallengeRes();
        getChallengeRes.setGoal(challenge.getGoal());
        getChallengeRes.setCode(challenge.getCode());
        getChallengeRes.setDeadline(challenge.getDeadline().format(formatter));
        getChallengeRes.setChallengeSteps(challenge.getChallengeSteps().stream().map(challengeStep -> ChallengeStepResDto.builder()
                .content(challengeStep.getContent())
                .deadline(challengeStep.getDeadline().format(formatter))
                .completed(challengeStep.isCompleted())
                .build()).toList());

        return getChallengeRes;
    }

    public GetChallengeRes postChallenge(Long userId, PostChallengeReq postChallengeReq) {
        Member member = memberRepository.findById(userId).orElseThrow(
                RuntimeException::new
        );
        Challenge challenge = Challenge.builder()
                .deadline(postChallengeReq.getDeadline())
                .goal(postChallengeReq.getGoal())
                .member(member)
                .code(ChallengeUtil.getChallengeUuidCode())
                .build();

        challengeRepository.save(challenge);

        GetChallengeRes getChallengeRes = new GetChallengeRes();
        getChallengeRes.setGoal(challenge.getGoal());
        getChallengeRes.setCode(challenge.getCode());
        getChallengeRes.setDeadline(challenge.getDeadline().format(formatter));
        getChallengeRes.setChallengeSteps(postChallengeReq.getPostChallengeSteps().stream()
                .map(postChallengeStepReq -> convertToChallengeStep(postChallengeStepReq, challenge)).collect(Collectors.toList()));

        return getChallengeRes;
    }

    public PutChallengeCheckRes putChallengeCheck(Long userId, PutChallengeCheckReq putChallengeCheckReq) {
        Member member = memberRepository.findById(userId).orElseThrow(
                RuntimeException::new
        );

        ChallengeStep challengeStep = challengeStepRepository.findById(putChallengeCheckReq.getChallengeStepId()).orElseThrow(
                RuntimeException::new
        );
        if (!challengeStep.isCompleted()) {
            challengeStep.setCompleted(true);
        } else {
            throw new RuntimeException();
        }
        PutChallengeCheckRes putChallengeCheckRes = new PutChallengeCheckRes();
        putChallengeCheckRes.setChallengeStepId(challengeStep.getId());
        putChallengeCheckRes.setCompleted(challengeStep.isCompleted());

        return putChallengeCheckRes;
    }

    public ChallengeStepResDto convertToChallengeStep(ChallengeStepReqDto postChallengeStepReq, Challenge challenge) {
        ChallengeStep challengeStep = ChallengeStep.builder()
                .content(postChallengeStepReq.getContent())
                .deadline(postChallengeStepReq.getDeadline())
                .challenge(challenge)
                .isCompleted(false)
                .build();

        challengeStepRepository.save(challengeStep);

        return ChallengeStepResDto.builder()
                .deadline(challengeStep.getDeadline().format(formatter))
                .content(challengeStep.getContent())
                .build();
    }

    public void challengeSuccess(Long memberId) throws Exception {
        Member member = getMember(memberId);
        Challenge challenge = challengeRepository.findByMember(member)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다."));
        List<String> emails = watcherRepository.findAllByChallenge(challenge).stream()
                .map(watcher -> watcher.getMember().getEmail())
                .toList();

        mailService.sendSuccessEmail(emails);

        deleteChallenge(challenge);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }

    private void deleteChallenge(Challenge challenge) {
        watcherRepository.deleteAllByChallenge(challenge);
        challengeStepRepository.deleteAllByChallenge(challenge);
        challengeRepository.delete(challenge);
    }

    public void challengeFail(Long memberId) throws Exception {
        Member member = getMember(memberId);
        Challenge challenge = challengeRepository.findByMember(member)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다."));
        List<String> emails = watcherRepository.findAllByChallenge(challenge).stream()
                .map(watcher -> watcher.getMember().getEmail())
                .toList();

        mailService.sendFailEmail(emails);

        deleteChallenge(challenge);
    }
}
