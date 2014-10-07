package com.nakedquasar.gamecenter.core.domain;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Cacheable
@Entity(name = "Achievement")
@Table(name="achievement")
public class Achievement {
	@Id
	@Column(name = "achievementid")
	@SequenceGenerator(name = "AchIdSequence", sequenceName = "achievement_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AchIdSequence")
	private Long achievementId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gameid")
	private Game game;
	
	@Column(name = "achievementcode", nullable = false, unique = true)
	private String achievementCode;
	
	@Column(name = "achievementname", nullable = false)
	private String achievementName;
	
	@Column(name = "achievementdesc", nullable = false, unique = false)
	private String achievementDesc;
	
	@Column(name = "achievementimage", nullable = true, unique = false)
	private String achievementImage;
	
	@Column(name = "achievementunlockpoints", nullable = false, unique = false)
	private double achievementUnlockPoints;
	
	@Column(name = "achievementincrementpoints", nullable = false)
	private boolean achievementIncrementPoints;
	
	@Column(name = "achievementgivepoints", nullable = false, unique = false)
	private Long achievementGivePoints;
	
	@Column(name = "achievementhidden", nullable = false, unique = false)
	private boolean achievementHidden;
	
	@Column(name = "achievementrepeatable", nullable = false, unique = false)
	private boolean achievementRepeatable;
	
	@Column(name = "achievementcreationdate", nullable = false)
	private Date achievementCreationDate;

	public Long getAchievementId() {
		return achievementId;
	}

	public void setAchievementId(Long achievemntId) {
		this.achievementId = achievemntId;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getAchievementCode() {
		return achievementCode;
	}

	public void setAchievementCode(String achievementCode) {
		this.achievementCode = achievementCode;
	}

	public String getAchievementName() {
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}

	public String getAchievementDesc() {
		return achievementDesc;
	}

	public void setAchievementDesc(String achievementDesc) {
		this.achievementDesc = achievementDesc;
	}

	public String getAchievementImage() {
		return achievementImage;
	}

	public void setAchievementImage(String achievementImage) {
		this.achievementImage = achievementImage;
	}

	public double getAchievementUnlockPoints() {
		return achievementUnlockPoints;
	}

	public void setAchievementUnlockPoints(double achievementUnlockPoints) {
		this.achievementUnlockPoints = achievementUnlockPoints;
	}

	public Long getAchievementGivePoints() {
		return achievementGivePoints;
	}

	public void setAchievementGivePoints(Long achievementGivePoints) {
		this.achievementGivePoints = achievementGivePoints;
	}

	public boolean isAchievementHidden() {
		return achievementHidden;
	}

	public void setAchievementHidden(boolean achievementHidden) {
		this.achievementHidden = achievementHidden;
	}

	public boolean isAchievementRepeatable() {
		return achievementRepeatable;
	}

	public void setAchievementRepeatable(boolean achievementRepeatable) {
		this.achievementRepeatable = achievementRepeatable;
	}

	public Date getAchievementCreationDate() {
		return achievementCreationDate;
	}

	public void setAchievementCreationDate(Date achievementCreationDate) {
		this.achievementCreationDate = achievementCreationDate;
	}

	public boolean isAchievementIncrementPoints() {
		return achievementIncrementPoints;
	}

	public void setAchievementIncrementPoints(boolean achievementIncrementPoints) {
		this.achievementIncrementPoints = achievementIncrementPoints;
	}
}
