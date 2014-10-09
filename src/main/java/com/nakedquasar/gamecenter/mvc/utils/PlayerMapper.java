package com.nakedquasar.gamecenter.mvc.utils;

import java.util.ArrayList;
import java.util.List;

import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.mvc.dto.PlayerDto;

public class PlayerMapper {
	public static PlayerDto map(Player player) {
		PlayerDto dto = new PlayerDto();
		dto.setPlayerId(player.getPlayerId());
		dto.setPlayerUsername(player.getPlayerUsername());
		dto.setPlayerPassword("");
		if (player.getPlayerPicture() != null && !player.getPlayerPicture().trim().isEmpty())
			dto.setPlayerPicture(player.getPlayerPicture());
		dto.setPlayerDisplayName(player.getPlayerDisplayName());
		dto.setPlayerFirstName(player.getPlayerFirstName());
		dto.setPlayerLastName(player.getPlayerLastName());
		dto.setPlayerEmail(player.getPlayerEmail());
		dto.setPlayerBirthdate(player.getPlayerBirthdate());
		dto.setPlayerSex(player.getPlayerSex());
		dto.setPlayerPlatform(player.getPlayerPlatform());
		dto.setPlayerRole(player.getPlayerRole());
		dto.setPlayerEnabled(player.isPlayerEnabled());
		dto.setIp(player.getIp());
		return dto;
	}

	public static List<PlayerDto> map(List<Player> players) {
		List<PlayerDto> dtos = new ArrayList<PlayerDto>();
		for (Player player : players) {
			dtos.add(map(player));
		}
		return dtos;
	}
}
