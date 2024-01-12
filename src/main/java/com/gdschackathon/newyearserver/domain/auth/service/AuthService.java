package com.gdschackathon.newyearserver.domain.auth.service;

import com.gdschackathon.newyearserver.domain.member.Member;
import com.gdschackathon.newyearserver.domain.auth.principal.CustomUserPrincipal;
import com.gdschackathon.newyearserver.domain.auth.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Service
public class AuthService {

    private static final long ACCESS_TOKEN_EXPIRED_DATE = 604800000L;

    private final MemberRepository memberRepository;

    @Value("${app.access-token-secret-key}")
    private String accessTokenSecretKey;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void register(String personalToken, String email) {
        Member member = new Member(personalToken, email);
        memberRepository.save(member);
    }

    public String login(String personalToken) {
        Member member = memberRepository.findByPersonalToken(personalToken)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        return generateAccessToken(member);
    }

    private String generateAccessToken(Member member) {
        Claims claims = Jwts.claims()
                .subject(member.getEmail())
                .issuedAt(new Date(currentTimeMillis()))
                .expiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRED_DATE))
                .add("memberId", member.getId())
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Authentication createAuthentication(String accessToken) {
        Claims tokenClaims = getTokenClaims(accessToken);
        UserDetails userDetails = new CustomUserPrincipal((long) (int) tokenClaims.get("memberId"), tokenClaims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, accessToken, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private Claims getTokenClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }
}
