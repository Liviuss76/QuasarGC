package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nakedquasar.gamecenter.core.domain.PlayerScoreRank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerScoreResponse {
	@JsonProperty("PlayerDisplayName")
	private String playerDisplayName;

	@JsonProperty("PlayerScore")
	private int playerScore;

	@JsonProperty("PlayerRank")
	private int playerRank;
	
	@JsonProperty("PlayerPlatform")
	private String platform;
	
	@JsonProperty("ScoresCount")
	private int scoresCount;
	
	public PlayerScoreResponse() {
	}
	
	public PlayerScoreResponse(PlayerScoreRank dbPlayerScore) {
		setPlayerDisplayName(dbPlayerScore.getPlayer().getPlayerDisplayName());
		setPlayerScore(dbPlayerScore.getScore());
		setPlayerRank(dbPlayerScore.getRank());
		setPlatform(dbPlayerScore.getPlatform());
	}
	
	public PlayerScoreResponse(String displayname, int score, int rank, String platform) {
		setPlayerDisplayName(displayname);
		setPlayerScore(score);
		setPlayerRank(rank);
		setPlatform(platform);
	}

	public String getPlayerUsername() {
		return playerDisplayName;
	}

	public void setPlayerDisplayName(String playerDisplayName) {
		this.playerDisplayName = playerDisplayName;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getPlayerRank() {
		return playerRank;
	}

	public void setPlayerRank(int playerRank) {
		this.playerRank = playerRank;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public int getScoresCount() {
		return scoresCount;
	}

	public void setScoresCount(int scoresCount) {
		this.scoresCount = scoresCount;
	}

}
