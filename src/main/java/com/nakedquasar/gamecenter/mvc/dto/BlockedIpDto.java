package com.nakedquasar.gamecenter.mvc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BlockedIpDto implements Serializable {
	private static final long serialVersionUID = -1363693805130027279L;
	private long id;
	@NotNull
	@Size(min = 7, max = 255)
	private String blockedIp;

	public String getBlockedIp() {
		return blockedIp;
	}

	public void setBlockedIp(String blockedIp) {
		this.blockedIp = blockedIp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
