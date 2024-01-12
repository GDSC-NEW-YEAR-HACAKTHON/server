package com.gdschackathon.newyearserver.domain.auth.filter;

import com.gdschackathon.newyearserver.domain.auth.service.AuthService;
import com.gdschackathon.newyearserver.domain.auth.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gdschackathon.newyearserver.domain.auth.error.AuthErrorCode.REQUEST_TOKEN_NOT_FOUND;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public TokenAuthenticationFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String accessToken = JwtUtil.getAccessToken(request);

        // 요청에 토큰이 없는 경우
        if (accessToken == null) {
            request.setAttribute("exceptionCode", REQUEST_TOKEN_NOT_FOUND);
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = authService.createAuthentication(accessToken);
        System.out.println(authentication.getPrincipal().toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
