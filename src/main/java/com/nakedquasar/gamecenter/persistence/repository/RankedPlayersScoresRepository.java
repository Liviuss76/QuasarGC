package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.PlayerScoreKey;
import com.nakedquasar.gamecenter.core.domain.PlayerScoreRank;

public interface RankedPlayersScoresRepository extends JpaRepository<PlayerScoreRank, PlayerScoreKey> {
	@Query("SELECT ps FROM PlayerScoreRank ps WHERE ps.leaderboard.leaderboardId =:leaderboardId AND ps.player.playerId =:playerId)")
	public PlayerScoreRank findByPlayerScoreKey(@Param("leaderboardId")UUID leaderboardId, @Param("playerId") UUID playerId);
	
	@Query("SELECT ps FROM PlayerScoreRank ps WHERE ps.leaderboard.leaderboardId =:leaderboardId ORDER BY ps.score DESC)")
	public List<PlayerScoreRank> findByLeaderboardId(@Param("leaderboardId") UUID leaderboardId,  Pageable pageable);
	
	@Query("SELECT COUNT(ps.leaderboard.leaderboardId) FROM PlayerScoreRank ps WHERE ps.leaderboard.leaderboardId =:leaderboardId)")
	public int getCountScoresForLeaderboardId(@Param("leaderboardId") UUID leaderboardId);
	
	@Query("SELECT COUNT(ps.leaderboard.leaderboardId) FROM PlayerScoreRank ps WHERE ps.player.playerId =:playerId)")
	public int getCountLeaderboardsForPlayerId(@Param("playerId") UUID playerId);
	
	@Query("SELECT ps FROM PlayerScoreRank ps WHERE ps.player.playerId =:playerId ORDER BY ps.score DESC)")
	public List<PlayerScoreRank> findByPlayerId(@Param("playerId") UUID playerId);
}