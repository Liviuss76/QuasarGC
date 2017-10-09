package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerProfileResponse {
	@JsonProperty("PlayerProfile")
	private String playerProfile;
	
	public String getPlayerProfile() {
		return playerProfile;
	}

	public void setPlayerProfile(String playerProfile) {
		this.playerProfile = playerProfile;
	}

	public PlayerProfileResponse(String playerProfile) {
		setPlayerProfile(playerProfile);
	}
}
