package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.PlayerAchievement;
import com.nakedquasar.gamecenter.core.domain.PlayerAchievementKey;

public interface PlayersAchievementsRepository extends JpaRepository<PlayerAchievement, PlayerAchievementKey> {
	@Query("SELECT pa FROM PlayerAchievement pa WHERE pa.id.achievement.achievementId =:achievementId AND pa.id.player.playerId =:playerId)")
	public PlayerAchievement findByPlayerAchievementKey(@Param("achievementId")Long achievementId, @Param("playerId") UUID playerId);
	
	@Query("SELECT pa FROM PlayerAchievement pa WHERE pa.id.player.playerId =:playerId)")
	public Page<PlayerAchievement> findByPlayerId(@Param("playerId") UUID playerId,  Pageable pageable);
	
	@Query("SELECT pa FROM PlayerAchievement pa WHERE pa.id.player.playerId =:playerId)")
	public List<PlayerAchievement> findByPlayerId(@Param("playerId") UUID playerId);
	
	@Query("SELECT COUNT(pa.id.achievement.achievementId) FROM PlayerAchievement pa WHERE pa.id.player.playerId =:playerId)")
	public int getCountAchievementsForPlayerId(@Param("playerId") UUID playerId);
}