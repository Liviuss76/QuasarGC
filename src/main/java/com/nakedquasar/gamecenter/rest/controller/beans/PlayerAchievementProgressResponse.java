package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerAchievementProgressResponse {
	@JsonProperty("AchievementCode")
	private String achievementCode;

	@JsonProperty("AchievementProgress")
	private double achievementProgress;
	
	@JsonProperty("Achievement")
	private PlayerAchievementResponse playerAchievement;
	
	public PlayerAchievementProgressResponse() {
	}

	public String getAchievementCode() {
		return achievementCode;
	}

	public void setAchievementCode(String achievementCode) {
		this.achievementCode = achievementCode;
	}

	public double getAchievementProgress() {
		return achievementProgress;
	}

	public void setAchievementProgress(double achievementProgress) {
		this.achievementProgress = achievementProgress;
	}

	public PlayerAchievementResponse getPlayerAchievement() {
		return playerAchievement;
	}

	public void setPlayerAchievement(PlayerAchievementResponse playerAchievement) {
		this.playerAchievement = playerAchievement;
	}
}
