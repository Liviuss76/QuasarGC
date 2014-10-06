package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.Achievement;

public interface AchievementsRepository extends JpaRepository<Achievement, Long> {
	public Achievement findByAchievementName(String achievementName);
	public Achievement findByAchievementCode(String achievementCode);
	
	@Query("SELECT ac FROM Achievement ac ORDER BY ac.achievementCreationDate ASC)")
	public Page<Achievement> findAllGames(Pageable page);
	
	@Query("SELECT ac FROM Achievement ac WHERE ac.game.gameId=:gameId ORDER BY ac.achievementCreationDate ASC)")
	public Page<Achievement> findAllAchievementsByGameId(Pageable pageable, @Param("gameId")UUID gameId);
	
	@Query("SELECT ac FROM Achievement ac WHERE ac.game.gameId=:gameId ORDER BY ac.achievementCreationDate ASC)")
	public List<Achievement> findAllAchievementsByGameId(@Param("gameId")UUID gameId);
	
	@Query("SELECT COUNT(ac) FROM Achievement ac")
	public int getAchievementsCount();
	
	@Query("SELECT COUNT(ac) FROM Achievement ac WHERE ac.game.gameId=:gameId ")
	public int getAchievementsCountByGameId(@Param("gameId")UUID gameId);
}
