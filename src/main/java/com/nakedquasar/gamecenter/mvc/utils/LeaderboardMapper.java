package com.nakedquasar.gamecenter.mvc.utils;

import java.util.ArrayList;
import java.util.List;

import com.nakedquasar.gamecenter.core.domain.LeaderBoard;
import com.nakedquasar.gamecenter.mvc.dto.LeaderboardDto;

public class LeaderboardMapper {
	public static LeaderboardDto map(LeaderBoard leaderboard) {
		LeaderboardDto dto = new LeaderboardDto();
		dto.setLeaderboardId(leaderboard.getLeaderboardId());
		dto.setGameId(leaderboard.getGame().getGameId());
		dto.setLeaderboardName(leaderboard.getLeaderboardName());
		dto.setLeaderBoardImage(leaderboard.getLeaderBoardImage());
		dto.setLeaderboardMinSubmitValue(leaderboard.getLeaderboardMinSubmitValue());
		dto.setLeaderboardMaxSubmitValue(leaderboard.getLeaderboardMaxSubmitValue());
		dto.setLeaderboardScoreIncrement(leaderboard.isLeaderboardScoreIncrement());
		dto.setLeaderboardCreationDate(leaderboard.getLeaderboardCreationDate());
		dto.setScoresCount(leaderboard.getScoresCount());
		return dto;
	}

	public static List<LeaderboardDto> map(List<LeaderBoard> leaderboards) {
		List<LeaderboardDto> dtos = new ArrayList<LeaderboardDto>();
		for (LeaderBoard leaderboard : leaderboards) {
			dtos.add(map(leaderboard));
		}
		return dtos;
	}
}
