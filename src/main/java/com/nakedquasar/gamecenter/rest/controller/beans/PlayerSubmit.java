package com.nakedquasar.gamecenter.rest.controller.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerSubmit {
	@JsonProperty("PlayerUsername")
	private String playerUsername;
	
	@JsonProperty("PlayerPassword")
	private String playerPassword;
	
	@JsonProperty("PlayerDisplayName")
	private String playerDisplayName;
	
	@JsonProperty("PlayerFirstname")
	private String playerFirstName;
	
	@JsonProperty("PlayerLastname")
	private String playerLastName;
	
	@JsonProperty("PlayerEmail")
	private String playerEmail;
	
	@JsonProperty("PlayerBirthdate")
	private Date playerBirthdate;
	
	@JsonProperty("PlayerSex")
	private String playerSex;
	
	@JsonProperty("PlayerPlatform")
	private String playerPlatform;
	
	@JsonProperty("PlayerPicture")
	private String playerPicture;
	private String playerPictureDefault="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";
	
	@JsonProperty("PlayerProfile")
	private String playerProfile;
	
	public PlayerSubmit() {
	}

	public String getPlayerUsername() {
		return playerUsername;
	}

	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}

	public String getPlayerPassword() {
		return playerPassword;
	}

	public void setPlayerPassword(String playerPassword) {
		this.playerPassword = playerPassword;
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

	public String getPlayerPlatform() {
		return playerPlatform;
	}

	public void setPlayerPlatform(String playerPlatform) {
		this.playerPlatform = playerPlatform;
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

	public Date getPlayerBirthdate() {
		return playerBirthdate;
	}

	public void setPlayerBirthdate(Date playerBirthdate) {
		this.playerBirthdate = playerBirthdate;
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

	public String getPlayerPictureDefault() {
		return playerPictureDefault;
	}

	public void setPlayerPictureDefault(String playerPictureDefault) {
		this.playerPictureDefault = playerPictureDefault;
	}
	
	public String getPlayerProfile() {
		return playerProfile;
	}

	public void setPlayerProfile(String playerProfile) {
		this.playerProfile = playerProfile;
	}

}
