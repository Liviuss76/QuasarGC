package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerAchievementUnlockSubmit {
	@JsonProperty("AchievementCode")
	private String achievementCode;
	
	public PlayerAchievementUnlockSubmit() {
	}

	public String getAchievementCode() {
		return achievementCode;
	}

	public void setAchievementCode(String achievementCode) {
		this.achievementCode = achievementCode;
	}
}
