package com.gdschackathon.newyearserver.domain.auth.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gdschackathon.newyearserver.domain.auth.error.AuthErrorCode.AUTHENTICATION_ERROR;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        AuthErrorCode exceptionCode = (AuthErrorCode) request.getAttribute("exceptionCode");

        if (exceptionCode != null) {
            setResponse(response, exceptionCode);
            return;
        }

        log.error("Responding with unauthorized error. Message := {}", authException.getMessage());
        setResponse(response, AUTHENTICATION_ERROR);
    }

    private void setResponse(HttpServletResponse response, AuthErrorCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(new ErrorApiResponse(exceptionCode.getMessage()).convertToJson());
    }
}
