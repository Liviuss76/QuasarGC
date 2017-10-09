package com.nakedquasar.gamecenter.rest.controller.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nakedquasar.gamecenter.core.domain.Player;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerResponse {
	@JsonProperty("PlayerId")
	private String playerId;

	@JsonProperty("PlayerUsername")
	private String playerUsername;

	@JsonProperty("PlayerDisplayName")
	private String playerDisplayName;

	@JsonProperty("PlayerFirstname")
	private String playerFirstName;

	@JsonProperty("PlayerLastname")
	private String playerLastName;

	@JsonProperty("PlayerEmail")
	private String playerEmail;

	@JsonProperty("PlayerBirthdate")
	private String playerBirthdate;

	@JsonProperty("PlayerSex")
	private String playerSex;

	@JsonProperty("PlayerBlocked")
	private boolean playerBlocked;

	@JsonProperty("PlayerPicture")
	private String playerPicture;
	
	@JsonProperty("PlayerProfile")
	private String playerProfile;

	public PlayerResponse() {
	}

	public PlayerResponse(Player dbPlayer) {
		setPlayerId(dbPlayer.getPlayerId());
		setPlayerUsername(dbPlayer.getPlayerUsername());
		setPlayerDisplayName(dbPlayer.getPlayerDisplayName());
		setPlayerFirstName(dbPlayer.getPlayerFirstName());
		setPlayerLastName(dbPlayer.getPlayerLastName());
		setPlayerEmail(dbPlayer.getPlayerEmail());
		setPlayerBirthdate(dbPlayer.getPlayerBirthdate());
		setPlayerSex(dbPlayer.getPlayerSex());
		setPlayerBlocked(!dbPlayer.isPlayerEnabled());
		setPlayerPicture(dbPlayer.getPlayerPicture());
	}

	public UUID getPlayerId() {
		return UUID.fromString(playerId);
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId.toString();
	}

	public String getPlayerUsername() {
		return playerUsername;
	}

	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}

	public String getPlayerFirstName() {
		return playerFirstName;
	}

	public void setPlayerFirstName(String playerFirstName) {
		this.playerFirstName = playerFirstName;
	}

	public String getPlayerLastName() {
		return playerLastName;
	}

	public void setPlayerLastName(String playerLastName) {
		this.playerLastName = playerLastName;
	}

	public boolean isPlayerBlocked() {
		return playerBlocked;
	}

	public void setPlayerBlocked(boolean playerBlocked) {
		this.playerBlocked = playerBlocked;
	}

	public String getPlayerDisplayName() {
		return playerDisplayName;
	}

	public void setPlayerDisplayName(String playerDisplayName) {
		this.playerDisplayName = playerDisplayName;
	}

	public String getPlayerEmail() {
		return playerEmail;
	}

	public void setPlayerEmail(String playerEmail) {
		this.playerEmail = playerEmail;
	}

	public String getPlayerBirthdate() {
		return playerBirthdate;
	}

	public void setPlayerBirthdate(Date playerBirthdate) {
		if (playerBirthdate != null) {
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			String playerBirthdateTxt = df.format(playerBirthdate);
			this.playerBirthdate = playerBirthdateTxt;
		}
	}

	public String getPlayerSex() {
		return playerSex;
	}

	public void setPlayerSex(String playerSex) {
		this.playerSex = playerSex;
	}

	public String getPlayerPicture() {
		return playerPicture;
	}

	public void setPlayerPicture(String playerPicture) {
		this.playerPicture = playerPicture;
	}
	
	public String getPlayerProfile() {
		return playerProfile;
	}
	
	public void setPlayerProfile(String playerProfile) {
		this.playerProfile = playerProfile;
	}

}
