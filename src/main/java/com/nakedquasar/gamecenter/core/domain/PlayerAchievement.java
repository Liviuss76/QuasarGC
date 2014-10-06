package com.nakedquasar.gamecenter.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PlayerAchievement")
@Table(name = "playerachievement")
public class PlayerAchievement extends BaseEntity<PlayerAchievementKey> {
	private static final long serialVersionUID = 1L;

	@Column(name = "unlockpoints")
	private double unlockpoints;

	@Column(name = "unlockedcount")
	private int unlockedcount;

	@Override
	public void setId(PlayerAchievementKey id) {
		super.setId(id);
	}

	public double getUnlockpoints() {
		return unlockpoints;
	}

	public void setUnlockpoints(double unlockpoints) {
		this.unlockpoints = unlockpoints;
	}

	public int getUnlockedcount() {
		return unlockedcount;
	}

	public void setUnlockedcount(int unlockedcount) {
		this.unlockedcount = unlockedcount;
	}
}
