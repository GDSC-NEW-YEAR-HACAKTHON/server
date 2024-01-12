package com.gdschackathon.newyearserver.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.gdschackathon.newyearserver.domain.challenge.Challenge;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@OneToMany(mappedBy = "member")
	private List<Challenge> challenges = new ArrayList<>();

	public Member(String personalToken, String email) {
		this.personalToken = personalToken;
		this.email = email;
	}
}
