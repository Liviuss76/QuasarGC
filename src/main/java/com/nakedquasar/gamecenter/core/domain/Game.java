package com.nakedquasar.gamecenter.core.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Cacheable
@Entity(name = "Game")
@Table(name="game")
public class Game {
	@Id
	@Column(name = "gameid")
	@GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
	@Type(type="pg-uuid")
	private UUID gameId;
	
	@Column(name = "gamename", nullable = false, unique = true)
	private String gameName;
	
	@Column(name = "gameimage", nullable = true)
	private String gameImage;
	
	@Column(name = "gamecreationdate", nullable = false)
	private Date gameCreationDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private List<LeaderBoard> leaderboards;
	
	@Transient
	private int playersCount;
	
	@Transient
	private int leaderboardsCount;
	
	@Transient
	private int achievementsCount;
	
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

	public List<LeaderBoard> getLeaderboards() {
		return leaderboards;
	}

	public void setLeaderboards(List<LeaderBoard> leaderboards) {
		this.leaderboards = leaderboards;
	}

	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}

	public int getLeaderboardsCount() {
		return leaderboardsCount;
	}

	public void setLeaderboardsCount(int leaderboardsCount) {
		this.leaderboardsCount = leaderboardsCount;
	}

	public String getGameImage() {
		return gameImage;
	}

	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}

	public Date getGameCreationDate() {
		return gameCreationDate;
	}

	public void setGameCreationDate(Date gameCreationDate) {
		this.gameCreationDate = gameCreationDate;
	}

	public int getAchievementsCount() {
		return achievementsCount;
	}

	public void setAchievementsCount(int achievementsCount) {
		this.achievementsCount = achievementsCount;
	}

}
