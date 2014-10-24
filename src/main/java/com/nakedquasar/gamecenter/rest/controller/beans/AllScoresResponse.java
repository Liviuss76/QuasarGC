package com.nakedquasar.gamecenter.rest.controller.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllScoresResponse {
	public AllScoresResponse() {
	}

	@JsonProperty("PlayerScores")
	private List<PlayerScoreResponse> playerScores;

	public List<PlayerScoreResponse> getPlayerScores() {
		return playerScores;
	}

	public void setPlayerScores(List<PlayerScoreResponse> playerScores) {
		this.playerScores = playerScores;
	}
}
