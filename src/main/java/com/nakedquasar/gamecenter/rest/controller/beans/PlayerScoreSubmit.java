package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerScoreSubmit {
	@JsonProperty("LeaderboardId")
	private String leaderboardId;

	@JsonProperty("PlayerScore")
	private int playerScore;

	public PlayerScoreSubmit() {
	}

	public String getLeaderboardId() {
		return leaderboardId;
	}

	public void setLeaderboardId(String leaderboardId) {
		this.leaderboardId = leaderboardId;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

}
