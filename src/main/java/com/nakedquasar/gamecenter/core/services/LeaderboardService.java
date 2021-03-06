package com.nakedquasar.gamecenter.core.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nakedquasar.gamecenter.core.domain.LeaderBoard;
import com.nakedquasar.gamecenter.core.domain.PlayerScore;
import com.nakedquasar.gamecenter.mvc.dto.LeaderboardDto;
import com.nakedquasar.gamecenter.rest.controller.beans.AllScoresResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerScoreResponse;

public interface LeaderboardService {
	public AllScoresResponse getAllScores(UUID leaderboardId, int page, int size);

	public PlayerScoreResponse getPlayerScore(UUID leaderboardId) throws Exception;

	public PlayerScoreResponse submitPlayerScore(UUID leaderboardId, int playerScore, String ip) throws Exception;

	public int getScoresCount(UUID fromString);

	public Page<LeaderBoard> getAllLeaderboards(Pageable pageable);

	public Page<LeaderBoard> getAllLeaderboardsByGameId(Pageable pageable, UUID gameId);
	
	public Page<PlayerScore> getPlayerLeaderboards(Pageable pageable, UUID playerId);
	
	public int getPlayerLeaderboardsCount(UUID playerId);

	public LeaderBoard createLeaderboard(LeaderboardDto leaderboardDto) throws Exception;

	public LeaderBoard saveLeaderboard(LeaderboardDto leaderboardDto) throws Exception;

	public void deleteLeaderboard(UUID leaderboardId) throws Exception;

	public LeaderBoard getLeaderBoardById(UUID leaderBoardId);
}
