package com.nakedquasar.gamecenter.security;

import java.util.Date;

public class BlockedUser {
	private Date blockTime;
	private int loginAttempts;

	public BlockedUser(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public Date getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Date blockTime) {
		this.blockTime = blockTime;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
		this.blockTime = new Date();
	}

	public void incrementLoginAttempts(int size) {
		loginAttempts = loginAttempts + size;
	}

	public boolean isIpBlocked() {
		return blockTime != null;
	}

	public double getRemainingTime(Date currTime, int timeToBlock) {
		return (double) (getBlockTime().getTime() + ((double)timeToBlock * 60000.0)
				- currTime.getTime() ) / 60000.0;
	}
}
