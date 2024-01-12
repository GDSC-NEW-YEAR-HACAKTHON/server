package com.gdschackathon.newyearserver.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000, unique = true, updatable = false)
    private String personalToken;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    public Member(String personalToken, String email) {
        this.personalToken = personalToken;
        this.email = email;
    }
}
