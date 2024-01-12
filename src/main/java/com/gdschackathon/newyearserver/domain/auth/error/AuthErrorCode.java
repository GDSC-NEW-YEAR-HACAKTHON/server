package com.gdschackathon.newyearserver.domain.auth.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public enum AuthErrorCode {

    REQUEST_TOKEN_NOT_FOUND(BAD_REQUEST, "요청에 토큰이 존재하지 않습니다."),
    AUTHENTICATION_ERROR(UNAUTHORIZED, "Authentication exception.");

    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(
            HttpStatus httpStatus,
            String message
    ) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
