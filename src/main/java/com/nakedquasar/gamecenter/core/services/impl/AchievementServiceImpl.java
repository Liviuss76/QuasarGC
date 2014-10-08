package com.nakedquasar.gamecenter.core.services.impl;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nakedquasar.gamecenter.core.domain.Achievement;
import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.core.domain.PlayerAchievement;
import com.nakedquasar.gamecenter.core.domain.PlayerAchievementKey;
import com.nakedquasar.gamecenter.core.domain.PlayerLog;
import com.nakedquasar.gamecenter.core.services.AchievementService;
import com.nakedquasar.gamecenter.mvc.dto.AchievementDto;
import com.nakedquasar.gamecenter.mvc.utils.AchievementMapper;
import com.nakedquasar.gamecenter.persistence.repository.AchievementsRepository;
import com.nakedquasar.gamecenter.persistence.repository.GamesRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersAchievementsRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersLogsRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersRepository;
import com.nakedquasar.gamecenter.rest.controller.beans.AllAchievementsResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementProgressResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementProgressSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementResponse;

@Repository
@Transactional
public class AchievementServiceImpl implements AchievementService {
	@Autowired
	public AchievementsRepository achievementsRepository;
	@Autowired
	public PlayersAchievementsRepository playerAchievementsRepository;
	@Autowired
	public GamesRepository gamesRepository;
	@Autowired
	public PlayersRepository playerRepository;
	@Autowired
	public PlayersLogsRepository playerLogRepository;

	@Override
	public Achievement createAchievement(AchievementDto achievement) throws Exception {
		Achievement newAchievement;

		newAchievement = achievementsRepository.findByAchievementCode(achievement.getAchievementCode());

		if (newAchievement != null) {
			throw new Exception("Achievement code " + achievement.getAchievementCode() + " already in use!");
		} else {
			newAchievement = new Achievement();
			newAchievement.setAchievementId(achievement.getAchievementId());
			newAchievement.setGame(gamesRepository.findOne(achievement.getGameId()));
			newAchievement.setAchievementCode(achievement.getAchievementCode());
			newAchievement.setAchievementName(achievement.getAchievementName());
			newAchievement.setAchievementDesc(achievement.getAchievementDesc());
			newAchievement.setAchievementImage(achievement.getAchievementImage());
			newAchievement.setAchievementUnlockPoints(achievement.getAchievementUnlockPoints());
			newAchievement.setAchievementGivePoints(achievement.getAchievementBonusPoints());
			newAchievement.setAchievementHidden(achievement.isAchievementHidden());
			newAchievement.setAchievementRepeatable(achievement.isAchievementRepeatable());
			newAchievement.setAchievementIncrementPoints(achievement.isAchievementIncrementPoints());
			newAchievement.setAchievementCreationDate(new Date(new java.util.Date().getTime()));
		}
		return achievementsRepository.saveAndFlush(newAchievement);
	}

	@Override
	public void deleteAchievement(Long achievementId) throws Exception {
		Achievement gm = achievementsRepository.findOne(achievementId);

		if (gm == null)
			throw new Exception("Achievemement ID:" + achievementId + " not found");

		achievementsRepository.delete(achievementId);
	}

	@Override
	public Achievement getAchievementById(Long achievementId) {
		return achievementsRepository.findOne(achievementId);

	}

	@Override
	public Achievement saveAchievement(AchievementDto achievementDto) throws Exception {
		Achievement ach = achievementsRepository.findByAchievementCode(achievementDto.getAchievementCode());

		if (ach != null && !ach.getAchievementId().equals(achievementDto.getAchievementId())) {
			throw new Exception("Achievement code " + achievementDto.getAchievementCode() + " already in use!");
		} else {
			Achievement achievement = achievementsRepository.findOne(achievementDto.getAchievementId());
			achievement.setGame(gamesRepository.findOne(achievementDto.getGameId()));
			achievement.setAchievementCode(achievementDto.getAchievementCode());
			achievement.setAchievementName(achievementDto.getAchievementName());
			achievement.setAchievementDesc(achievementDto.getAchievementDesc());
			achievement.setAchievementImage(achievementDto.getAchievementImage());
			achievement.setAchievementUnlockPoints(achievementDto.getAchievementUnlockPoints());
			achievement.setAchievementGivePoints(achievementDto.getAchievementBonusPoints());
			achievement.setAchievementHidden(achievementDto.isAchievementHidden());
			achievement.setAchievementRepeatable(achievementDto.isAchievementRepeatable());
			achievement.setAchievementIncrementPoints(achievementDto.isAchievementIncrementPoints());
			return achievementsRepository.saveAndFlush(achievement);
		}
	}

	@Override
	public int getAchievementsCount() {
		return achievementsRepository.getAchievementsCount();
	}

	@Override
	public Page<Achievement> getAllAchievements(Pageable pageable) {
		return achievementsRepository.findAll(pageable);
	}

	@Override
	public Page<Achievement> getAllAchievementsByGameId(Pageable pageable, UUID gameId) {
		return achievementsRepository.findAllAchievementsByGameId(pageable, gameId);
	}

	@Override
	public AllAchievementsResponse getAllAchievementsByGameId(UUID gameId) {
		List<Achievement> achievements = achievementsRepository.findAllAchievementsByGameId(gameId);
		List<PlayerAchievementResponse> mapedAchievements = AchievementMapper.mapJson(achievements);
		return new AllAchievementsResponse(mapedAchievements);
	}

	@Override
	public AllAchievementsResponse getAllAchievementsByGameAndPlayer(UUID gameId, UUID playerId) {
		List<PlayerAchievement> playerachievements = playerAchievementsRepository.findByPlayerId(playerId);
		List<Achievement> achievements = achievementsRepository.findAllAchievementsByGameId(gameId);
		List<PlayerAchievementResponse> mapedAchievements = AchievementMapper.mapJson(achievements, playerachievements);

		return new AllAchievementsResponse(mapedAchievements);
	}

	@Override
	public PlayerAchievementResponse getAchievementByCodeAndPlayer(String achievementCode, UUID playerId)
			throws Exception {
		Achievement ach = achievementsRepository.findByAchievementCode(achievementCode);
		if (ach == null || ach.isAchievementHidden()) {
			throw new Exception("Achievement not found or is hidden");
		}

		PlayerAchievement playerachievement = playerAchievementsRepository.findByPlayerAchievementKey(
				ach.getAchievementId(), playerId);

		if (playerachievement != null) {
			return AchievementMapper.mapJson(ach, playerachievement);
		}

		return AchievementMapper.mapJson(ach);
	}

	@Override
	public PlayerAchievementResponse unlockAchievementByCodeAndPlayer(String achievementCode, Player player, String ip)
			throws Exception {

		Achievement ach = achievementsRepository.findByAchievementCode(achievementCode);
		if (ach == null) {
			throw new Exception("Achievement not found");
		}

		PlayerLog plog = new PlayerLog();
		plog.setPlayer(player);
		plog.setActionType("SUBMIT ACHIEVEMENT");
		plog.setAction("Achievement:" + ach.getAchievementCode() + " UnlockAchievement");
		plog.setActionDate(new Date(new java.util.Date().getTime()));
		plog.setIp(ip);
		playerLogRepository.saveAndFlush(plog);

		PlayerAchievement playerachievement = playerAchievementsRepository.findByPlayerAchievementKey(
				ach.getAchievementId(), player.getPlayerId());

		if (!ach.isAchievementRepeatable() && playerachievement.getUnlockedcount() > 0) {
			throw new Exception("Achievement already unlocked");
		}

		if (playerachievement == null) {
			PlayerAchievementKey pak = new PlayerAchievementKey();
			pak.setAchievement(ach);
			pak.setPlayer(player);

			playerachievement = new PlayerAchievement();
			playerachievement.setId(pak);
			if (ach.isAchievementRepeatable()) {
				playerachievement.setUnlockpoints(0);
			} else {
				playerachievement.setUnlockpoints(ach.getAchievementUnlockPoints());
			}
			playerachievement.setUnlockedcount(playerachievement.getUnlockedcount() + 1);
			playerachievement = playerAchievementsRepository.saveAndFlush(playerachievement);
		} else {
			if (ach.isAchievementRepeatable()) {
				playerachievement.setUnlockpoints(0);
			} else {
				playerachievement.setUnlockpoints(ach.getAchievementUnlockPoints());
			}
			playerachievement.setUnlockedcount(playerachievement.getUnlockedcount() + 1);
			playerachievement = playerAchievementsRepository.saveAndFlush(playerachievement);
		}

		return AchievementMapper.mapJson(ach, playerachievement);
	}

	@Override
	public PlayerAchievementProgressResponse submitAchievementProgress(
			PlayerAchievementProgressSubmit playerAchievementProgress, Player player, String ip) throws Exception {
		PlayerAchievementProgressResponse papr = new PlayerAchievementProgressResponse();

		Achievement ach = achievementsRepository.findByAchievementCode(playerAchievementProgress.getAchievementCode());
		if (ach == null) {
			throw new Exception("Achievement not found");
		}

		PlayerAchievement playerachievement = playerAchievementsRepository.findByPlayerAchievementKey(
				ach.getAchievementId(), player.getPlayerId());

		PlayerLog plog = new PlayerLog();
		plog.setPlayer(player);
		plog.setActionType("SUBMIT ACHIEVEMENT");
		plog.setAction("Achievement:" + ach.getAchievementCode() + " UnlockPoints:"
				+ playerAchievementProgress.getAchievementUnlockPoints());
		plog.setActionDate(new Date(new java.util.Date().getTime()));
		plog.setIp(ip);
		playerLogRepository.saveAndFlush(plog);

		if (playerachievement == null) {
			PlayerAchievementKey pak = new PlayerAchievementKey();
			pak.setAchievement(ach);
			pak.setPlayer(player);

			playerachievement = new PlayerAchievement();
			playerachievement.setId(pak);
			if (playerAchievementProgress.getAchievementUnlockPoints() >= ach.getAchievementUnlockPoints()) {
				if (ach.isAchievementRepeatable()) {
					playerachievement.setUnlockpoints(0);
					playerachievement.setUnlockedcount(playerachievement.getUnlockedcount() + 1);
					papr.setPlayerAchievement(AchievementMapper.mapJson(ach, playerachievement));
					papr.setAchievementProgress(100);
				} else {
					playerachievement.setUnlockpoints(ach.getAchievementUnlockPoints());
					playerachievement.setUnlockedcount(playerachievement.getUnlockedcount() + 1);
					papr.setPlayerAchievement(AchievementMapper.mapJson(ach, playerachievement));
					papr.setAchievementProgress(100);
				}
			} else {
				playerachievement.setUnlockpoints(playerAchievementProgress.getAchievementUnlockPoints());
				double progress = (ach.getAchievementUnlockPoints() / 100.0) * playerachievement.getUnlockpoints();

				papr.setAchievementProgress(progress);
			}

			playerachievement = playerAchievementsRepository.saveAndFlush(playerachievement);

		} else {
			if (!ach.isAchievementRepeatable() && playerachievement.getUnlockedcount() > 0) {
				throw new Exception("Achievement already unlocked");
			}

			if (ach.isAchievementIncrementPoints()) {
				if (playerachievement.getUnlockpoints() + playerAchievementProgress.getAchievementUnlockPoints() >= ach
						.getAchievementUnlockPoints()) {
					if (ach.isAchievementRepeatable()) {
						playerachievement.setUnlockpoints(0);
						playerachievement.setUnlockedcount(playerachievement.getUnlockedcount() + 1);
						papr.setPlayerAchievement(AchievementMapper.mapJson(ach, playerachievement));
						papr.setAchievementProgress(100);
					} else {
						playerachievement.setUnlockpoints(ach.getAchievementUnlockPoints());
						playerachievement.setUnlockedcount(1);
						papr.setPlayerAchievement(AchievementMapper.mapJson(ach, playerachievement));
						papr.setAchievementProgress(100);
					}
				} else {
					playerachievement.setUnlockpoints(playerachievement.getUnlockpoints()
							+ playerAchievementProgress.getAchievementUnlockPoints());
					double progress = (ach.getAchievementUnlockPoints() / 100.0) * playerachievement.getUnlockpoints();
					papr.setAchievementProgress(progress);
				}
			} else {
				if (playerAchievementProgress.getAchievementUnlockPoints() >= ach.getAchievementUnlockPoints()) {
					if (ach.isAchievementRepeatable()) {
						playerachievement.setUnlockpoints(0);
						playerachievement.setUnlockedcount(playerachievement.getUnlockedcount() + 1);
						papr.setPlayerAchievement(AchievementMapper.mapJson(ach, playerachievement));
						papr.setAchievementProgress(100);
					} else {
						playerachievement.setUnlockpoints(ach.getAchievementUnlockPoints());
						playerachievement.setUnlockedcount(1);
						papr.setPlayerAchievement(AchievementMapper.mapJson(ach, playerachievement));
						papr.setAchievementProgress(100);
					}
				} else {
					playerachievement.setUnlockpoints(playerAchievementProgress.getAchievementUnlockPoints());
					double progress = (ach.getAchievementUnlockPoints() / 100.0) * playerachievement.getUnlockpoints();
					papr.setAchievementProgress(progress);
				}
			}

			playerachievement = playerAchievementsRepository.saveAndFlush(playerachievement);
		}

		papr.setAchievementCode(ach.getAchievementCode());

		return papr;
	}

	@Override
	public int getPlayerAchievementsCount(UUID playerId) {
		return playerAchievementsRepository.getCountAchievementsForPlayerId(playerId);
	}

	@Override
	public Page<PlayerAchievement> getPlayerAchievements(Pageable pageable, UUID playerId) {
		Player player = playerRepository.findOne(playerId);
		return playerAchievementsRepository.findByPlayerId(player.getPlayerId(), pageable);
	}

}
