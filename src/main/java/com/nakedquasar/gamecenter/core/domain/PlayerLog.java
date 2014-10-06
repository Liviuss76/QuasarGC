package com.nakedquasar.gamecenter.core.domain;

import java.util.Date;

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

@Entity(name = "PlayerLog")
@Table(name="playerlog")
public class PlayerLog {
	@Id
	@Column(name = "logid")
	@SequenceGenerator(name = "PlayerLogSequence", sequenceName = "playerlog_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PlayerLogSequence")
	private Long logId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playerid", nullable = false)
	private Player player;
	
	@Column(name = "actiontype", nullable = false)
	private String actionType;
	
	@Column(name = "action", nullable = false)
	private String action;
	
	@Column(name = "actiondate", nullable = false)
	private Date actionDate;
	
	@Column(name = "ip", nullable = false)
	private String ip;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
}
