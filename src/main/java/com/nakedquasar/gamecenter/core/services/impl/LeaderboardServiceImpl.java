package com.nakedquasar.gamecenter.core.services.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.nakedquasar.gamecenter.core.domain.Game;
import com.nakedquasar.gamecenter.core.domain.LeaderBoard;
import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.core.domain.PlayerLog;
import com.nakedquasar.gamecenter.core.domain.PlayerScore;
import com.nakedquasar.gamecenter.core.domain.PlayerScoreKey;
import com.nakedquasar.gamecenter.core.services.LeaderboardService;
import com.nakedquasar.gamecenter.mvc.dto.LeaderboardDto;
import com.nakedquasar.gamecenter.persistence.repository.GamesRepository;
import com.nakedquasar.gamecenter.persistence.repository.LeaderboardsRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersLogsRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersScoresRepository;
import com.nakedquasar.gamecenter.rest.controller.beans.AllScoresResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerScoreResponse;

@Repository
@Transactional
public class LeaderboardServiceImpl implements LeaderboardService {
	@Autowired
	public PlayersScoresRepository scoresRepository;
	@Autowired
	public LeaderboardsRepository leaderboardRepository;
	@Autowired
	public PlayersRepository playerRepository;
	@Autowired
	public GamesRepository gameRepository;
	@Autowired
	public PlayersLogsRepository playerLogRepository;

	@Transactional
	public AllScoresResponse getAllScores(UUID leaderboardId, int page, int size) {
		Pageable topTen = new PageRequest(page, size);

		AllScoresResponse apsr = new AllScoresResponse();

		int scoresCount = getScoresCount(leaderboardId);

		if (scoresCount > 0) {
			List<PlayerScoreResponse> playerScoreResponses = new ArrayList<PlayerScoreResponse>();
			List<PlayerScore> playerScores = scoresRepository.findByLeaderboardId(leaderboardId, topTen);

			int i = 1;
			for (PlayerScore playerScore : playerScores) {
				PlayerScoreResponse playerScoreResponse = new PlayerScoreResponse();
				playerScoreResponse.setPlayerDisplayName(playerScore.getId().getPlayer().getPlayerDisplayName());
				playerScoreResponse.setPlatform(playerScore.getId().getPlayer().getPlayerPlatform());
				playerScoreResponse.setPlayerScore(playerScore.getScore());
				playerScoreResponse.setPlayerRank((page * size) + i);
				playerScoreResponse.setScoresCount(scoresCount);
				playerScoreResponse.setPlayerPicture(playerScore.getId().getPlayer().getPlayerPicture());
				i++;
				playerScoreResponses.add(playerScoreResponse);
			}

			apsr.setPlayerScores(playerScoreResponses);
		}

		return apsr;
	}

	@Transactional
	public int getScoresCount(UUID leaderboardId) {
		return scoresRepository.getCountScoresForLeaderboardId(leaderboardId);
	}

	@Transactional
	public PlayerScoreResponse getPlayerScore(UUID leaderboardId) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Player player;
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			player = playerRepository.findByPlayerUsernamePass(userDetails.getUsername(), userDetails.getPassword());
		} else {
			throw new Exception("Player does not exist.");
		}

		int scoresCount = getScoresCount(leaderboardId);
		PlayerScore playerScore = scoresRepository.findByPlayerScoreKey(leaderboardId, player.getPlayerId());
		if (playerScore != null) {
			int playerRank = getPlayerRank(leaderboardId, player.getPlayerId());
			return new PlayerScoreResponse(player.getPlayerDisplayName(), playerScore.getScore(), playerRank,
					player.getPlayerPlatform(), scoresCount, player.getPlayerPicture());
		} else {
			return new PlayerScoreResponse(player.getPlayerDisplayName(), 0, 0, player.getPlayerPlatform(), scoresCount, player.getPlayerPicture());
		}
	}

	@Transactional
	public PlayerScoreResponse submitPlayerScore(UUID leaderboardId, int playerScore, String ip) throws Exception {
		PlayerScoreResponse psr;
		Player player;
		LeaderBoard lb = leaderboardRepository.findByLeaderboardId(leaderboardId);
		if (lb == null) {
			throw new Exception("No leaderboard:" + leaderboardId + " found");
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			player = playerRepository.findByPlayerUsernamePass(userDetails.getUsername(), userDetails.getPassword());
		} else {
			throw new Exception("Player does not exist.");
		}

		PlayerLog plog = new PlayerLog();
		plog.setPlayer(player);
		plog.setActionType("SUBMIT SCORE");
		plog.setAction("Leaderboard:" + lb.getLeaderboardId() + " Score:" + playerScore);
		plog.setActionDate(new Date(new java.util.Date().getTime()));
		plog.setIp(ip);
		playerLogRepository.saveAndFlush(plog);

		int scoresCount = getScoresCount(leaderboardId)+1;
		PlayerScore ps = scoresRepository.findByPlayerScoreKey(leaderboardId, player.getPlayerId());

		if (ps == null) {
			ps = new PlayerScore();
			PlayerScoreKey psk = new PlayerScoreKey();
			psk.setLeaderboard(lb);
			psk.setPlayer(player);
			ps.setId(psk);
			ps.setScore(playerScore);
			scoresRepository.saveAndFlush(ps);

			PlayerScore playerScoreNew = scoresRepository.findByPlayerScoreKey(leaderboardId, player.getPlayerId());
			int playerRank = getPlayerRank(leaderboardId, player.getPlayerId());
			psr = new PlayerScoreResponse(player.getPlayerDisplayName(), playerScoreNew.getScore(), playerRank,
					player.getPlayerPlatform(), scoresCount, player.getPlayerPicture());

		} else {
			if (!ps.getId().getPlayer().isPlayerEnabled()) {
				// If player blocked, return existing score but rank to 0
				psr = new PlayerScoreResponse(ps.getId().getPlayer().getPlayerDisplayName(), ps.getScore(), 0, ps
						.getId().getPlayer().getPlayerPlatform(), scoresCount, ps.getId().getPlayer().getPlayerPicture());
			} else {
				if (playerScore < ps.getId().getLeaderboard().getLeaderboardMinSubmitValue()
						|| playerScore > ps.getId().getLeaderboard().getLeaderboardMaxSubmitValue()) {
					// Hack atempt? Do not increment score, just return existing
					// value.
					PlayerScore playerScoreNew = scoresRepository.findByPlayerScoreKey(leaderboardId,
							player.getPlayerId());
					int playerRank = getPlayerRank(leaderboardId, player.getPlayerId());
					psr = new PlayerScoreResponse(player.getPlayerDisplayName(), playerScoreNew.getScore(), playerRank,
							player.getPlayerPlatform(), scoresCount, player.getPlayerPicture());

				} else {
					if (ps.getId().getLeaderboard().isLeaderboardScoreIncrement()) {
						// Get existing score and increment it with new score
						ps.setScore(ps.getScore() + playerScore);
					} else {
						// Overwrite existing score only if newest score is
						// greater
						if (playerScore > ps.getScore())
							ps.setScore(playerScore);
					}
					scoresRepository.saveAndFlush(ps);
				}

				PlayerScore playerScoreNew = scoresRepository.findByPlayerScoreKey(leaderboardId, player.getPlayerId());
				int playerRank = getPlayerRank(leaderboardId, player.getPlayerId());
				psr = new PlayerScoreResponse(player.getPlayerDisplayName(), playerScoreNew.getScore(), playerRank,
						player.getPlayerPlatform(), scoresCount, player.getPlayerPicture());
			}
		}

		return psr;
	}

	@Override
	public Page<LeaderBoard> getAllLeaderboards(Pageable pageable) {
		Page<LeaderBoard> leaderboardsList = leaderboardRepository.findAllLeaderboards(pageable);
		for (LeaderBoard lb : leaderboardsList) {
			lb.setScoresCount(getScoresCount(lb.getLeaderboardId()));
		}

		return leaderboardsList;
	}

	@Override
	public Page<LeaderBoard> getAllLeaderboardsByGameId(Pageable pageable, UUID gameId) {
		Page<LeaderBoard> leaderboardsList = leaderboardRepository.findAllLeaderboardsByGameId(pageable, gameId);
		for (LeaderBoard lb : leaderboardsList) {
			lb.setScoresCount(getScoresCount(lb.getLeaderboardId()));
		}

		return leaderboardsList;
	}

	@Override
	public LeaderBoard createLeaderboard(LeaderboardDto leaderboardDto) throws Exception {

		Game gm = gameRepository.findOne(leaderboardDto.getGameId());

		LeaderBoard newLeaderboard = new LeaderBoard();
		newLeaderboard.setGameId(gm);
		newLeaderboard.setLeaderboardName(leaderboardDto.getLeaderboardName());
		newLeaderboard.setLeaderBoardImage(leaderboardDto.getLeaderBoardImage());
		newLeaderboard.setLeaderboardScoreIncrement(leaderboardDto.isLeaderboardScoreIncrement());
		newLeaderboard.setLeaderboardMinSubmitValue(leaderboardDto.getLeaderboardMinSubmitValue());
		newLeaderboard.setLeaderboardMaxSubmitValue(leaderboardDto.getLeaderboardMaxSubmitValue());
		newLeaderboard.setLeaderboardCreationDate(new Date(new java.util.Date().getTime()));

		return leaderboardRepository.saveAndFlush(newLeaderboard);
	}

	@Override
	public LeaderBoard saveLeaderboard(LeaderboardDto leaderboardDto) throws Exception {
		LeaderBoard newLeaderboard = leaderboardRepository.findOne(leaderboardDto.getLeaderboardId());
		newLeaderboard.setLeaderboardName(leaderboardDto.getLeaderboardName());
		newLeaderboard.setLeaderBoardImage(leaderboardDto.getLeaderBoardImage());
		newLeaderboard.setLeaderboardScoreIncrement(leaderboardDto.isLeaderboardScoreIncrement());
		newLeaderboard.setLeaderboardMinSubmitValue(leaderboardDto.getLeaderboardMinSubmitValue());
		newLeaderboard.setLeaderboardMaxSubmitValue(leaderboardDto.getLeaderboardMaxSubmitValue());

		return leaderboardRepository.saveAndFlush(newLeaderboard);
	}

	@Override
	public void deleteLeaderboard(UUID leaderboardId) throws Exception {
		leaderboardRepository.delete(leaderboardId);
	}

	@Override
	public LeaderBoard getLeaderBoardById(UUID leaderBoardId) {
		return leaderboardRepository.findOne(leaderBoardId);
	}

	@Override
	public Page<PlayerScore> getPlayerLeaderboards(Pageable pageable, UUID playerId) {
		Player player = playerRepository.findOne(playerId);
		return scoresRepository.findByPlayerId(player.getPlayerId(), pageable);
	}

	@Override
	public int getPlayerLeaderboardsCount(UUID playerId) {
		return scoresRepository.getCountLeaderboardsForPlayerId(playerId);
	}

	public int getPlayerRank(UUID leaderboardId, UUID playerId) {
		return scoresRepository.getPlayerRank(leaderboardId.toString(), playerId.toString());
	}
}
