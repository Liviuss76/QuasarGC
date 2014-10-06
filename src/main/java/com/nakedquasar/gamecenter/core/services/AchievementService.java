package com.nakedquasar.gamecenter.core.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nakedquasar.gamecenter.core.domain.Achievement;
import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.mvc.dto.AchievementDto;
import com.nakedquasar.gamecenter.mvc.dto.PlayerAchievementDto;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementProgressSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.AllAchievementsResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementProgressResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementResponse;

public interface AchievementService {
	public Page<Achievement> getAllAchievements(Pageable pageable);

	public Page<Achievement> getAllAchievementsByGameId(Pageable pageable, UUID gameId);

	public int getAchievementsCount();

	public AllAchievementsResponse getAllAchievementsByGameId(UUID gameId);

	public AllAchievementsResponse getAllAchievementsByGameAndPlayer(UUID gameId, UUID playerId);

	public Achievement createAchievement(AchievementDto achievementDto) throws Exception;

	public Achievement saveAchievement(AchievementDto achievementDto) throws Exception;

	public void deleteAchievement(Long achievementId) throws Exception;

	public Achievement getAchievementById(Long achievementId);

	public PlayerAchievementResponse getAchievementByCodeAndPlayer(String achievementId, UUID playerId)
			throws Exception;

	public PlayerAchievementResponse unlockAchievementByCodeAndPlayer(String achievementCode, Player player, String ip)
			throws Exception;

	public PlayerAchievementProgressResponse submitAchievementProgress(
			PlayerAchievementProgressSubmit playerAchievementProgress, Player pl, String ip) throws Exception;
	
	public int getPlayerAchievementsCount(UUID playerId);

	public Page<PlayerAchievementDto> getPlayerAchievements(Pageable pageable, UUID playerId);
}
