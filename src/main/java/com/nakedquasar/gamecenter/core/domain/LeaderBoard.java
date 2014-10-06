package com.nakedquasar.gamecenter.core.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Cacheable
@Entity(name = "Leaderboard")
@Table(name = "leaderboard")
public class LeaderBoard {
	@Id
	@Column(name = "leaderboardid")
	@GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
	@Type(type="pg-uuid")
	private UUID leaderboardId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gameid")
	public Game game;

	@Column(name = "leaderboardname", nullable = false)
	private String leaderboardName;
	
	@Column(name = "leaderboardscoreincrement", nullable = false)
	private boolean leaderboardScoreIncrement;

	@Column(name = "leaderboardimage")
	private String leaderBoardImage;

	@Column(name = "leaderboardminsubmitvalue", nullable = false)
	private int leaderboardMinSubmitValue;

	@Column(name = "leaderboardmaxsubmitvalue", nullable = false)
	private int leaderboardMaxSubmitValue;
	
	@Column(name = "leaderboardcreationdate", nullable = false)
	private Date leaderboardCreationDate;

	@Transient
	private int scoresCount;
	
	public String getLeaderboardName() {
		return leaderboardName;
	}

	public void setLeaderboardName(String leaderboardName) {
		this.leaderboardName = leaderboardName;
	}

	public UUID getLeaderboardId() {
		return leaderboardId;
	}

	public void setLeaderboardId(UUID leaderboardId) {
		this.leaderboardId = leaderboardId;
	}

	public String getLeaderBoardImage() {
		return leaderBoardImage;
	}

	public void setLeaderBoardImage(String leaderBoardImage) {
		this.leaderBoardImage = leaderBoardImage;
	}

	public int getLeaderboardMinSubmitValue() {
		return leaderboardMinSubmitValue;
	}

	public void setLeaderboardMinSubmitValue(int leaderboardMinSubmitValue) {
		this.leaderboardMinSubmitValue = leaderboardMinSubmitValue;
	}

	public int getLeaderboardMaxSubmitValue() {
		return leaderboardMaxSubmitValue;
	}

	public void setLeaderboardMaxSubmitValue(int leaderboardMaxSubmitValue) {
		this.leaderboardMaxSubmitValue = leaderboardMaxSubmitValue;
	}

	public Game getGame() {
		return game;
	}

	public void setGameId(Game game) {
		this.game = game;
	}

	public boolean isLeaderboardScoreIncrement() {
		return leaderboardScoreIncrement;
	}

	public void setLeaderboardScoreIncrement(boolean leaderboardScoreIncrement) {
		this.leaderboardScoreIncrement = leaderboardScoreIncrement;
	}

	public int getScoresCount() {
		return scoresCount;
	}

	public void setScoresCount(int scoresCount) {
		this.scoresCount = scoresCount;
	}

	public Date getLeaderboardCreationDate() {
		return leaderboardCreationDate;
	}

	public void setLeaderboardCreationDate(Date leaderboardCreationDate) {
		this.leaderboardCreationDate = leaderboardCreationDate;
	}
}
