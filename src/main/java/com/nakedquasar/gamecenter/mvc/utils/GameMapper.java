package com.nakedquasar.gamecenter.mvc.utils;

import java.util.ArrayList;
import java.util.List;

import com.nakedquasar.gamecenter.core.domain.Game;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;

public class GameMapper {
	public static GameDto map(Game game) {
		GameDto dto = new GameDto();
		dto.setGameId(game.getGameId());
		dto.setGameName(game.getGameName());
		dto.setGameImage(game.getGameImage());
		dto.setLeaderboardsCount(game.getLeaderboardsCount());
		dto.setAchievementsCount(game.getAchievementsCount());
		dto.setPlayersCount(game.getPlayersCount());
		return dto;
	}

	public static List<GameDto> map(List<Game> games) {
		List<GameDto> dtos = new ArrayList<GameDto>();
		for (Game game : games) {
			dtos.add(map(game));
		}
		return dtos;
	}
}
