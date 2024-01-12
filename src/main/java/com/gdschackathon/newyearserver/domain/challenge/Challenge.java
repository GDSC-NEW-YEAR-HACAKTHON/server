package com.gdschackathon.newyearserver.domain.challenge;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdschackathon.newyearserver.domain.challengestep.ChallengeStep;
import com.gdschackathon.newyearserver.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenge")
public class Challenge {
	@Id
	@Column(name = "challenge_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "goal", length = 1000)
	private String goal;

	@Column(name = "deadline")
	private LocalDateTime deadline;

	@Column(name = "code", length = 5000)
	private String code;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "challenge")
	private List<ChallengeStep> challengeSteps = new ArrayList<>();

}
