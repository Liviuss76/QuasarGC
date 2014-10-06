package com.nakedquasar.gamecenter.mvc.dto;

import java.io.Serializable;

public class SearchFormDto implements Serializable {
	private static final long serialVersionUID = 5921984711783573914L;
	private String playerUsername;

	public String getPlayerUsername() {
		return playerUsername;
	}

	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}

}
