package com.nakedquasar.gamecenter.core.services.impl;

import java.sql.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nakedquasar.gamecenter.core.domain.Game;
import com.nakedquasar.gamecenter.core.services.GameService;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;
import com.nakedquasar.gamecenter.persistence.repository.AchievementsRepository;
import com.nakedquasar.gamecenter.persistence.repository.GamesRepository;
import com.nakedquasar.gamecenter.persistence.repository.LeaderboardsRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersRepository;

@Repository
@Transactional
public class GameServiceImpl implements GameService {
	@Autowired
	public GamesRepository gameRepository;

	@Autowired
	public PlayersRepository playerRepository;
	@Autowired
	public LeaderboardsRepository leaderboardRepository;
	@Autowired
	public AchievementsRepository achievementRepository;

	@Override
	public Page<Game> getAllGames(Pageable pageable) {
		Page<Game> gamesList = gameRepository.findAllGames(pageable);
		for (Game gm : gamesList) {
			gm.setPlayersCount(playerRepository.getPlayersCountByGameName(gm.getGameName()));
			gm.setLeaderboardsCount(leaderboardRepository.getLeaderboardsCountByGameId(gm.getGameId()));
			gm.setAchievementsCount(achievementRepository.getAchievementsCountByGameId(gm.getGameId()));
		}

		return gamesList;
	}

	@Override
	public Game createGame(GameDto gameDto) throws Exception {
		Game newGame;

		newGame = gameRepository.findByGameName(gameDto.getGameName());

		if (newGame != null) {
			throw new Exception("Game name " + gameDto.getGameName() + " already in use!");
		} else {
			newGame = new Game();
			newGame.setGameName(gameDto.getGameName());
			newGame.setGameImage(gameDto.getGameImage());
			newGame.setGameCreationDate(new Date(new java.util.Date().getTime()));
		}

		newGame = gameRepository.saveAndFlush(newGame);
		newGame.setPlayersCount(0);
		newGame.setLeaderboardsCount(0);
		newGame.setAchievementsCount(0);
		return newGame;
	}

	@Override
	public void deleteGame(UUID gameId) throws Exception {
		Game gm = gameRepository.findOne(gameId);

		if (gm == null)
			throw new Exception("Game ID:" + gameId + " not found");

		if (gm.getLeaderboards().size() > 0)
			throw new Exception("Game " + gm.getGameName() + " could not be deleted because it contains "
					+ gm.getLeaderboards().size() + " Leaderboards. Delete Leaderboards first.");

		gameRepository.delete(gameId);
	}

	@Override
	public Game getGameById(UUID gameId) {
		Game newGame = gameRepository.findOne(gameId);
		newGame.setPlayersCount(playerRepository.getPlayersCountByGameName(newGame.getGameName()));
		newGame.setLeaderboardsCount(leaderboardRepository.getLeaderboardsCountByGameId(newGame.getGameId()));
		newGame.setAchievementsCount(achievementRepository.getAchievementsCountByGameId(newGame.getGameId()));
		return newGame;
	}

	@Override
	public Game saveGame(GameDto gameDto) throws Exception {
		Game gm = gameRepository.findByGameName(gameDto.getGameName());

		if (gm != null && !gm.getGameId().equals(gameDto.getGameId())) {
			throw new Exception("Game name " + gameDto.getGameName() + " already in use!");
		} else {
			Game game = gameRepository.findOne(gameDto.getGameId());
			game.setGameName(gameDto.getGameName());
			game.setGameImage(gameDto.getGameImage());
			game = gameRepository.saveAndFlush(game);
			game.setPlayersCount(playerRepository.getPlayersCountByGameName(game.getGameName()));
			game.setLeaderboardsCount(leaderboardRepository.getLeaderboardsCountByGameId(game.getGameId()));
			game.setAchievementsCount(achievementRepository.getAchievementsCountByGameId(game.getGameId()));

			return game;
		}
	}

	@Override
	public int getGamesCount() {
		return gameRepository.getGamesCount();
	}

}
