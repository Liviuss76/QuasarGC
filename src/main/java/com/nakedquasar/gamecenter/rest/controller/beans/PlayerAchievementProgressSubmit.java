package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerAchievementProgressSubmit {
	@JsonProperty("AchievementCode")
	private String achievementCode;
	
	@JsonProperty("AchievementUnlockPoints")
	private double achievementUnlockPoints;
	
	public PlayerAchievementProgressSubmit() {
	}

	public String getAchievementCode() {
		return achievementCode;
	}

	public void setAchievementCode(String achievementCode) {
		this.achievementCode = achievementCode;
	}

	public double getAchievementUnlockPoints() {
		return achievementUnlockPoints;
	}

	public void setAchievementUnlockPoints(double achievementUnlockPoints) {
		this.achievementUnlockPoints = achievementUnlockPoints;
	}
}
