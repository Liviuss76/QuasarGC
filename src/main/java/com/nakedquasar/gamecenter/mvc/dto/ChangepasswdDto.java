package com.nakedquasar.gamecenter.mvc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangepasswdDto {
	@NotNull
	@Size(min = 1, max = 255)
	private String playerPassword;
	@NotNull
	@Size(min = 1, max = 255)
	private String playerPasswordConfirm;

	public String getPlayerPassword() {
		return playerPassword;
	}

	public void setPlayerPassword(String playerPassword) {
		this.playerPassword = playerPassword;
	}

	public String getPlayerPasswordConfirm() {
		return playerPasswordConfirm;
	}

	public void setPlayerPasswordConfirm(String playerPasswordConfirm) {
		this.playerPasswordConfirm = playerPasswordConfirm;
	}

}
