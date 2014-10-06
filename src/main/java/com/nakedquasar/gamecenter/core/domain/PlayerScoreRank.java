package com.nakedquasar.gamecenter.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity(name = "PlayerScoreRank")
@Table(name = "playerscorerank")
@Immutable
@IdClass(PlayerScoreKey.class)
public class PlayerScoreRank {
	@Id
	@ManyToOne
    @JoinColumn(name="playerId",referencedColumnName="playerId")
	private Player player;
	
	@Id
	@ManyToOne
    @JoinColumn(name="leaderboardId",referencedColumnName="leaderboardId")
	private LeaderBoard leaderboard;
	@Column(name = "score")
	private int score;
	@Column(name = "rank")
	private int rank;
	@Column(name = "platform")
	private String platform;
	

	public int getScore() {
		return score;
	}

	public void setScore(int playerScore) {
		this.score = playerScore;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player playerid) {
		this.player = playerid;
	}

	public LeaderBoard getLeaderboard() {
		return leaderboard;
	}

	public void setLeaderboard(LeaderBoard leaderboardid) {
		this.leaderboard = leaderboardid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
