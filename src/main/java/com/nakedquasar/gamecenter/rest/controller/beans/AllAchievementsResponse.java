package com.nakedquasar.gamecenter.rest.controller.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllAchievementsResponse {
	public AllAchievementsResponse() {
	}
	
	public AllAchievementsResponse(List<PlayerAchievementResponse> playerAchievements) {
		this.playerAchievements = playerAchievements;
	}
	
	@JsonProperty("PlayerAchievements")
	private List<PlayerAchievementResponse> playerAchievements;
	
	public List<PlayerAchievementResponse> getPlayerAchievements() {
		return playerAchievements;
	}

	public void setPlayerAchievements(List<PlayerAchievementResponse> playerAchievements) {
		this.playerAchievements = playerAchievements;
	}
}
