package com.nakedquasar.gamecenter.mvc.dto;

import java.io.Serializable;
import java.util.UUID;

public class PlayerAchievementDto implements Serializable {
	private static final long serialVersionUID = 5044436380054731827L;
	private UUID gameId;
	private String gameName;
	private String gameImage;
	private Long achievementId;
	private String achievementCode;
	private String achievementName;
	private String achievementImage;
	private double achievementUnlockPoints;
	private double currentUnlockPoints;
	private double achievementProgress;
	private int unlockedcount;

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

	public String getAchievementCode() {
		return achievementCode;
	}

	public void setAchievementCode(String achievementCode) {
		this.achievementCode = achievementCode;
	}

	public Long getAchievementId() {
		return achievementId;
	}

	public void setAchievementId(Long achievementId) {
		this.achievementId = achievementId;
	}

	public String getAchievementName() {
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}

	public String getAchievementImage() {
		return achievementImage;
	}

	public void setAchievementImage(String achievementImage) {
		this.achievementImage = achievementImage;
	}

	public double getAchievementUnlockPoints() {
		return achievementUnlockPoints;
	}

	public void setAchievementUnlockPoints(double achievementUnlockPoints) {
		this.achievementUnlockPoints = achievementUnlockPoints;
	}

	public double getAchievementProgress() {
		return achievementProgress;
	}

	public void setAchievementProgress(double achievementProgress) {
		this.achievementProgress = achievementProgress;
	}

	public double getCurrentUnlockPoints() {
		return currentUnlockPoints;
	}

	public void setCurrentUnlockPoints(double currentUnlockPoints) {
		this.currentUnlockPoints = currentUnlockPoints;
	}

	public int getUnlockedcount() {
		return unlockedcount;
	}

	public void setUnlockedcount(int unlockedcount) {
		this.unlockedcount = unlockedcount;
	}

}
