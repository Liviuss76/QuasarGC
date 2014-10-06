package com.nakedquasar.gamecenter.mvc.dto;

import java.io.Serializable;
import java.util.UUID;

public class PlayerLeaderboardDto implements Serializable {
	private static final long serialVersionUID = -5269575692919067346L;
	private UUID leaderboardId;
	private UUID gameId;
	private String gameName;
	private String gameImage;
	private String leaderboardName;
	private String leaderboardImage;
	private int currentScore;
	private int currentRank;

	public UUID getLeaderboardId() {
		return leaderboardId;
	}

	public void setLeaderboardId(UUID leaderboardId) {
		this.leaderboardId = leaderboardId;
	}

	public UUID getGameId() {
		return gameId;
	}

	public void setGameId(UUID gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameImage() {
		return gameImage;
	}

	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}

	public String getLeaderboardName() {
		return leaderboardName;
	}

	public void setLeaderboardName(String leaderboardName) {
		this.leaderboardName = leaderboardName;
	}

	public String getLeaderBoardImage() {
		return leaderboardImage;
	}

	public void setLeaderBoardImage(String leaderBoardImage) {
		this.leaderboardImage = leaderBoardImage;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getCurrentRank() {
		return currentRank;
	}

	public void setCurrentRank(int currentRank) {
		this.currentRank = currentRank;
	}

}
