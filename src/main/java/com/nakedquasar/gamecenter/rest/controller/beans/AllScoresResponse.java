package com.nakedquasar.gamecenter.rest.controller.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nakedquasar.gamecenter.core.domain.PlayerScoreRank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllScoresResponse {
	public AllScoresResponse() {
	}

	public AllScoresResponse(List<PlayerScoreRank> dbPlayerScores) {
		playerScores = new ArrayList<PlayerScoreResponse>();
		for (PlayerScoreRank dbps : dbPlayerScores) {
			playerScores.add(new PlayerScoreResponse(dbps));
		}
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
