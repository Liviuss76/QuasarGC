package com.nakedquasar.gamecenter.core.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PlayerScoreKey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "playerid", referencedColumnName = "playerid")
	private Player player;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "leaderboardid", referencedColumnName = "leaderboardid")
	private LeaderBoard leaderboard;

	@Override
	public boolean equals(Object obj) {
		final boolean returner;
		if (obj instanceof PlayerScoreKey) {
			return getLeaderboard().equals(
					((PlayerScoreKey) obj).getLeaderboard())
					&& getPlayer().equals(((PlayerScoreKey) obj).getPlayer());
		} else {
			returner = false;
		}
		return returner;
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}

	public LeaderBoard getLeaderboard() {
		return leaderboard;
	}

	public void setLeaderboard(LeaderBoard leaderboard) {
		this.leaderboard = leaderboard;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
