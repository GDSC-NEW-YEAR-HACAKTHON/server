package com.gdschackathon.newyearserver.domain.penalty.service;

import com.gdschackathon.newyearserver.domain.penalty.entity.Penalty;
import com.gdschackathon.newyearserver.domain.penalty.repository.PenaltyRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Random;

@Service
public class PenaltyService {

    private static final String PENALTY_BASE_URL = "http://localhost:8080";
    private static final String PENALTY_WEB_URL = "/penalty?";

    private final PenaltyRepository penaltyRepository;

    public PenaltyService(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }

    public String getPenaltyUrl() {
        // 패널티 내용 & 사진 추출
        Penalty penalty = pickPenalty();
        String description = penalty.getDescription();
        String imageUrl = penalty.getImageUrl();

        // 링크 반환
        return PENALTY_BASE_URL + PENALTY_WEB_URL + "description=" + description + "&image-url=" + imageUrl;
    }

    private Penalty pickPenalty() {
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;

        return penaltyRepository.findById((long) randomNumber)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 벌칙입니다."));
    }
}
