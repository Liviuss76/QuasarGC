package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.Game;
import com.nakedquasar.gamecenter.core.domain.LeaderBoard;

public interface LeaderboardsRepository extends JpaRepository<LeaderBoard, UUID> {
	public LeaderBoard findByLeaderboardId(UUID leaderboardId);
	public List<LeaderBoard> findByGame(Game game);
	
	@Query("SELECT lb FROM Leaderboard lb ORDER BY lb.leaderboardCreationDate ASC)")
	public Page<LeaderBoard> findAllLeaderboards(Pageable pageable);
	
	@Query("SELECT lb FROM Leaderboard lb WHERE lb.game.gameId=:gameId ORDER BY lb.leaderboardCreationDate ASC)")
	public Page<LeaderBoard> findAllLeaderboardsByGameId(Pageable pageable, @Param("gameId")UUID gameId);
	
	@Query("SELECT COUNT(lb) FROM Leaderboard lb WHERE lb.game.gameId=:gameId ")
	public int getLeaderboardsCountByGameId(@Param("gameId")UUID gameId);
}