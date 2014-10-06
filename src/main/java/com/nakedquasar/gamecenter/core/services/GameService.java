package com.nakedquasar.gamecenter.core.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nakedquasar.gamecenter.core.domain.Game;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;

public interface GameService {
	public Page<Game> getAllGames(Pageable pageable);
	public Game createGame(GameDto gameDto) throws Exception;
	public Game saveGame(GameDto gameDto) throws Exception;
	public void deleteGame(UUID gameId) throws Exception;
	public Game getGameById(UUID gameId);
	public int getGamesCount();
}
