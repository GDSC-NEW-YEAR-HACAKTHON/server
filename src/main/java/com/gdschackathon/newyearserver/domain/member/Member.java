package com.gdschackathon.newyearserver.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdschackathon.newyearserver.domain.challenge.Challenge;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "person_token", length = 1000)
	private String personToken;

	@Column(name = "email", length = 100)
	private String email;

	@OneToMany(mappedBy = "member")
	private List<Challenge> challenges = new ArrayList<>();
}