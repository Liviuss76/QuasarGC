package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
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
	
	@Query("SELECT ps FROM PlayerScore ps WHERE ps.id.leaderboard.leaderboardId =:leaderboardId ORDER BY ps.calculatedrank DESC)")
	public List<PlayerScore> findByLeaderboardIdWithCalcRanking(@Param("leaderboardId") UUID leaderboardId,  Pageable pageable);
	
	@Query("SELECT COUNT(ps.id.leaderboard.leaderboardId) FROM PlayerScore ps WHERE ps.id.leaderboard.leaderboardId =:leaderboardId)")
	public int getCountScoresForLeaderboardId(@Param("leaderboardId") UUID leaderboardId);
	
	@Query("SELECT COUNT(ps.id.leaderboard.leaderboardId) FROM PlayerScore ps WHERE ps.id.player.playerId =:playerId)")
	public int getCountLeaderboardsForPlayerId(@Param("playerId") UUID playerId);
	
	@Query("SELECT ps FROM PlayerScore ps WHERE ps.id.player.playerId =:playerId ORDER BY ps.score DESC)")
	public Page<PlayerScore> findByPlayerId(@Param("playerId") UUID playerId, Pageable pageable);
	
	@Query(
			value="SELECT ps.rank from (SELECT playerscore.playerid, playerscore.leaderboardid, playerscore.score, "+
					"row_number() OVER (ORDER BY playerscore.score DESC) AS rank, "+ 
					"player.playerplatform AS platform "+
					"FROM playerscore, player "+
					"WHERE playerscore.playerid = player.playerid "+ 
					"AND player.playerenabled = true "+
					"AND leaderboardid=CAST(:leaderboardId as uuid) "+
					"ORDER BY playerscore.score DESC) as ps "+
					"where ps.playerid= CAST(:playerId as uuid)", 
			nativeQuery=true)
	public int getPlayerRank(@Param("leaderboardId") String leaderboardId, @Param("playerId") String playerId);
	
	@Query(
			value="SELECT ps.rank from (SELECT playerscore.playerid, playerscore.leaderboardid, playerscore.score, "+
					"row_number() OVER (ORDER BY playerscore.calculatedrank DESC) AS rank, "+ 
					"player.playerplatform AS platform "+
					"FROM playerscore, player "+
					"WHERE playerscore.playerid = player.playerid "+ 
					"AND player.playerenabled = true "+
					"AND leaderboardid=CAST(:leaderboardId as uuid) "+
					"ORDER BY playerscore.calculatedrank DESC) as ps "+
					"where ps.playerid= CAST(:playerId as uuid)", 
			nativeQuery=true)
	public int getCalculatedPlayerRank(@Param("leaderboardId") String leaderboardId, @Param("playerId") String playerId);
}