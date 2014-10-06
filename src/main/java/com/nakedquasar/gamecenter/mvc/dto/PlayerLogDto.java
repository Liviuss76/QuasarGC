package com.nakedquasar.gamecenter.mvc.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PlayerLogDto implements Serializable {
	private static final long serialVersionUID = -7629881426596215366L;
	private String actionType;
	private String action;
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date actionDate;
	private String ip;

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
