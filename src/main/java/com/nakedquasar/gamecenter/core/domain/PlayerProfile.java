package com.nakedquasar.gamecenter.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PlayerProfile")
@Table(name = "playerprofile")
public class PlayerProfile extends BaseEntity<PlayerProfileKey> {
	private static final long serialVersionUID = 1L;

	@Column(name = "profile")
	private String profile;

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public void setId(PlayerProfileKey id) {
		super.setId(id);
	}

}
