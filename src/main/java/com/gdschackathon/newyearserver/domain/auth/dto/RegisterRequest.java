package com.gdschackathon.newyearserver.domain.auth.dto;

public record RegisterRequest(
        String personalToken,
        String email
) {
}
