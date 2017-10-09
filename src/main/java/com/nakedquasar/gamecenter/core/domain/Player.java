package com.nakedquasar.gamecenter.core.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity(name="Player")
@Table(name="player")
public class Player {
	@Id
	@Column(name = "playerid")
	@GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
	@Type(type="pg-uuid")
	private UUID playerId;
	
	@Column(name = "playerusername", nullable = false, unique = true)
	private String playerUsername;
	
	@Column(name = "playerpassword", nullable = false)
	private String playerPassword;
	
	@Column(name = "playerdisplayname", nullable = false)
	private String playerDisplayName;
	
	@Column(name = "playerfirstname", nullable = true)
	private String playerFirstName;
	
	@Column(name = "playerlastname", nullable = true)
	private String playerLastName;
	
	@Column(name = "playeremail", nullable = true)
	private String playerEmail;
	
	@Column(name = "playerbirthdate", nullable = true)
	private Date playerBirthdate;
	
	@Column(name = "playersex", nullable = true)
	private String playerSex;
	
	@Column(name = "playerrole", nullable = true)
	private String playerRole;
	
	@Column(name = "playerplatform", nullable = false)
	private String playerPlatform;
	
	@Column(name = "playerpicture", nullable = true)
	private String playerPicture;
	
	@Column(name = "playerenabled", nullable = false )
	private boolean playerEnabled;
	
	@Column(name = "datetimeofcreation", nullable = false)
	private Date dateTimeOfCreation;
	
	@Column(name = "ip", nullable = true)
	private String ip;
	
	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID PlayerId) {
		this.playerId = PlayerId;
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

	public void setPlayerPassword(String PlayerPassword) {
		this.playerPassword = PlayerPassword;
	}

	public String getPlayerFirstName() {
		return playerFirstName;
	}

	public void setPlayerFirstName(String PlayerFirstName) {
		this.playerFirstName = PlayerFirstName;
	}

	public String getPlayerLastName() {
		return playerLastName;
	}

	public void setPlayerLastName(String PlayerLastName) {
		this.playerLastName = PlayerLastName;
	}

	public Date getDateTimeOfCreation() {
		return dateTimeOfCreation;
	}

	public void setDateTimeOfCreation(Date dateTimeOfCreation) {
		this.dateTimeOfCreation = dateTimeOfCreation;
	}

	public boolean isPlayerEnabled() {
		return playerEnabled;
	}

	public void setPlayerEnabled(boolean playerEnabled) {
		this.playerEnabled = playerEnabled;
	}

	public String getPlayerPlatform() {
		return playerPlatform;
	}

	public void setPlayerPlatform(String playerPlatform) {
		this.playerPlatform = playerPlatform;
	}

	public String getPlayerRole() {
		return playerRole;
	}

	public void setPlayerRole(String playerRole) {
		this.playerRole = playerRole;
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
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
