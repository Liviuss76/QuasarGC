package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.PlayerScore;
import com.nakedquasar.gamecenter.core.domain.PlayerScoreKey;

public interface PlayersScoresRepository extends JpaRepository<PlayerScore, PlayerScoreKey> {
	@Query("SELECT ps FROM PlayerScore ps WHERE ps.id.leaderboard.leaderboardId =:leaderboardId AND ps.id.player.playerId =:playerId)")
	public PlayerScore findByPlayerScoreKey(@Param("leaderboardId")UUID leaderboardId, @Param("playerId") UUID playerId);
	
	@Query("SELECT ps FROM PlayerScore ps WHERE ps.id.leaderboard.leaderboardId =:leaderboardId ORDER BY ps.score DESC)")
	public List<PlayerScore> findByLeaderboardId(@Param("leaderboardId") UUID leaderboardId,  Pageable pageable);
}