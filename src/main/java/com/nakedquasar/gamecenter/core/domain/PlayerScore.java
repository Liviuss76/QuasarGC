package com.nakedquasar.gamecenter.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "PlayerScore")
@Table(name = "playerscore")
public class PlayerScore extends BaseEntity<PlayerScoreKey> {
	private static final long serialVersionUID = 1L;

	@Column(name = "score")
	private int score;

	@Transient
	private int rank;
	
	public int getScore() {
		return score;
	}

	public void setScore(int playerScore) {
		this.score = playerScore;
	}

	@Override
	public void setId(PlayerScoreKey id) {
		super.setId(id);
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
