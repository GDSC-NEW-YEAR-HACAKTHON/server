package com.gdschackathon.newyearserver.domain.auth.error;

import com.gdschackathon.newyearserver.domain.auth.util.GsonUtil;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorApiResponse {

    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorApiResponse(String message) {
        this.message = message;
    }

    public String convertToJson() {
        return GsonUtil.toJson(this);
    }
}
