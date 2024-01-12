package com.gdschackathon.newyearserver.domain.watcher;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdschackathon.newyearserver.domain.challenge.dto.GetChallengeRes;
import com.gdschackathon.newyearserver.domain.challenge.dto.PutChallengeCheckRes;
import com.gdschackathon.newyearserver.domain.challengestep.ChallengeStep;
import com.gdschackathon.newyearserver.domain.watcher.dto.PostWatcherDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/watcher")
public class WatcherController {
	private final WatcherService watcherService;
	@PostMapping("")
	@Transactional()
	public GetChallengeRes postChallengeWatcher(
		@RequestBody PostWatcherDto postWatcherDto
	) {
		Long userId = 2L;
		return watcherService.postChallengeWatcher(userId, postWatcherDto);
	}
}