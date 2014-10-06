package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerAchievementResponse {
	@JsonProperty("AchievementName")
	private String achievementName;

	@JsonProperty("AchievementDesc")
	private String achievementDesc;
	
	@JsonProperty("AchievementImage")
	private String achievementImage;
	
	@JsonProperty("AchievementCode")
	private String achievementCode;
	
	@JsonProperty("AchievementUnlockPoints")
	private double achievementUnlockPoints;
	
	@JsonProperty("AchievementBonusPoints")
	private Long achievementBonusPoints;
	
	@JsonProperty("AchievementProgress")
	private double achievementProgress;
	
	@JsonProperty("AchievementUnlocked")
	private boolean achievementUnlocked;
	
	@JsonProperty("AchievementRepeatable")
	private boolean achievementRepeatable;
	
	public PlayerAchievementResponse() {
	}

	public String getAchievementName() {
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}

	public String getAchievementDesc() {
		return achievementDesc;
	}

	public void setAchievementDesc(String achievementDesc) {
		this.achievementDesc = achievementDesc;
	}

	public String getAchievementImage() {
		return achievementImage;
	}

	public void setAchievementImage(String achievementImage) {
		this.achievementImage = achievementImage;
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

	public boolean isAchievementUnlocked() {
		return achievementUnlocked;
	}

	public void setAchievementUnlocked(boolean achievementUnlocked) {
		this.achievementUnlocked = achievementUnlocked;
	}

	public boolean isAchievementRepeatable() {
		return achievementRepeatable;
	}

	public void setAchievementRepeatable(boolean achievementRepeatable) {
		this.achievementRepeatable = achievementRepeatable;
	}

	public double getAchievementUnlockPoints() {
		return achievementUnlockPoints;
	}

	public void setAchievementUnlockPoints(double achievementUnlockPoints) {
		this.achievementUnlockPoints = achievementUnlockPoints;
	}

	public Long getAchievementBonusPoints() {
		return achievementBonusPoints;
	}

	public void setAchievementBonusPoints(Long achievementBonusPoints) {
		this.achievementBonusPoints = achievementBonusPoints;
	}
	
	

}
