package com.nakedquasar.gamecenter.mvc.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class GameDto implements Serializable {
	private static final long serialVersionUID = -4477306111829387333L;
	private UUID gameId;
	@NotNull
	@Size(min = 1, max = 255)
	private String gameName;
	private String gameImage="iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAYAAABccqhmAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA2ZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpBMkVFQTZBQTA5MjA2ODExODIyQUI3NUQyQ0JEQjMwMCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo3RDExMTFFNjM3QjAxMUU0OEFBMkJFMTM1Qjk5MTNEMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo3RDExMTFFNTM3QjAxMUU0OEFBMkJFMTM1Qjk5MTNEMSIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M1IE1hY2ludG9zaCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjAxODAxMTc0MDcyMDY4MTE4RjYyRUUzMUFCN0ZDQTZCIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkEyRUVBNkFBMDkyMDY4MTE4MjJBQjc1RDJDQkRCMzAwIi8+IDwvcmRmOkRlc2NyaXB0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";
	private int leaderboardsCount;
	private int achievementsCount;
	private int playersCount;
	private MultipartFile gameImageRaw;
	
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
	public int getLeaderboardsCount() {
		return leaderboardsCount;
	}
	public void setLeaderboardsCount(int leaderboardsCount) {
		this.leaderboardsCount = leaderboardsCount;
	}
	public int getAchievementsCount() {
		return achievementsCount;
	}
	public void setAchievementsCount(int achievementsCount) {
		this.achievementsCount = achievementsCount;
	}
	public int getPlayersCount() {
		return playersCount;
	}
	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}
	public String getGameImage() {
		return gameImage;
	}
	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}
	public MultipartFile getGameImageRaw() {
		return gameImageRaw;
	}
	public void setGameImageRaw(MultipartFile gameImageRaw) {
		this.gameImageRaw = gameImageRaw;
	}
}
