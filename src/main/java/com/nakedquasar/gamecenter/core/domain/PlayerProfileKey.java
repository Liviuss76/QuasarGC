package com.nakedquasar.gamecenter.core.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PlayerProfileKey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "playerid", referencedColumnName = "playerid")
	private Player player;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "gameid", referencedColumnName = "gameid")
	private Game game;

	@Override
	public boolean equals(Object obj) {
		final boolean returner;
		if (obj instanceof PlayerProfileKey) {
			return getGame().equals(
					((PlayerProfileKey) obj).getGame())
					&& getPlayer().equals(((PlayerProfileKey) obj).getPlayer());
		} else {
			returner = false;
		}
		return returner;
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
