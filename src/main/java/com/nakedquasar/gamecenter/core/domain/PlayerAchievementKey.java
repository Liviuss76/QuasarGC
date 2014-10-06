package com.nakedquasar.gamecenter.core.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PlayerAchievementKey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "playerid", referencedColumnName = "playerid")
	private Player player;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "achievementid", referencedColumnName = "achievementid")
	private Achievement achievement;

	@Override
	public boolean equals(Object obj) {
		final boolean returner;
		if (obj instanceof PlayerAchievementKey) {
			return getAchievement().equals(
					((PlayerAchievementKey) obj).getAchievement())
					&& getPlayer().equals(((PlayerAchievementKey) obj).getPlayer());
		} else {
			returner = false;
		}
		return returner;
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}

	

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Achievement getAchievement() {
		return achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}

}
