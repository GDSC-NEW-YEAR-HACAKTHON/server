package com.gdschackathon.newyearserver.domain.watcher;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.gdschackathon.newyearserver.domain.challenge.Challenge;
import com.gdschackathon.newyearserver.domain.challenge.ChallengeRepository;
import com.gdschackathon.newyearserver.domain.challenge.dto.ChallengeStepReqDto;
import com.gdschackathon.newyearserver.domain.challenge.dto.ChallengeStepResDto;
import com.gdschackathon.newyearserver.domain.challenge.dto.GetChallengeRes;
import com.gdschackathon.newyearserver.domain.member.Member;
import com.gdschackathon.newyearserver.domain.member.MemberRepository;
import com.gdschackathon.newyearserver.domain.watcher.dto.PostWatcherDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WatcherService {

	private final WatcherRepository watcherRepository;
	private final ChallengeRepository challengeRepository;
	private final MemberRepository memberRepository;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public GetChallengeRes postChallengeWatcher(Long userId, PostWatcherDto postWatcherDto){
		Member member =  memberRepository.findById(userId).orElseThrow(
			RuntimeException::new
		);
		Challenge challenge = challengeRepository.findByCode(postWatcherDto.getChallengeCode()).orElseThrow(
			RuntimeException::new
		);

		Watcher newWatcher = Watcher.builder()
			.member(member)
			.challenge(challenge)
			.build();

		watcherRepository.save(newWatcher);

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
}
