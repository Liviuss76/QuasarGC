package com.nakedquasar.gamecenter.mvc.utils;

import java.util.ArrayList;
import java.util.List;

import com.nakedquasar.gamecenter.core.domain.LeaderBoard;
import com.nakedquasar.gamecenter.core.domain.PlayerScoreRank;
import com.nakedquasar.gamecenter.mvc.dto.LeaderboardDto;
import com.nakedquasar.gamecenter.mvc.dto.PlayerLeaderboardDto;

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
	
	public static PlayerLeaderboardDto map(PlayerScoreRank psRank) {
		PlayerLeaderboardDto plDto = new PlayerLeaderboardDto();
		plDto.setGameId(psRank.getLeaderboard().getGame().getGameId());
		plDto.setGameName(psRank.getLeaderboard().getGame().getGameName());
		plDto.setGameImage(psRank.getLeaderboard().getGame().getGameImage());
		plDto.setLeaderboardId(psRank.getLeaderboard().getLeaderboardId());
		plDto.setLeaderboardName(psRank.getLeaderboard().getLeaderboardName());
		plDto.setLeaderBoardImage(psRank.getLeaderboard().getLeaderBoardImage());
		plDto.setCurrentScore(psRank.getScore());
		plDto.setCurrentRank(psRank.getRank());
		return plDto;
	}

	public static List<LeaderboardDto> map(List<LeaderBoard> leaderboards) {
		List<LeaderboardDto> dtos = new ArrayList<LeaderboardDto>();
		for (LeaderBoard leaderboard : leaderboards) {
			dtos.add(map(leaderboard));
		}
		return dtos;
	}
	
	public static List<PlayerLeaderboardDto> mapPlayerScoreRanks(List<PlayerScoreRank> psRanks) {
		List<PlayerLeaderboardDto> plDtos = new ArrayList<PlayerLeaderboardDto>();
		for(PlayerScoreRank psRank : psRanks){
			
			plDtos.add(map(psRank));
		}
		return plDtos;
	}
	
	
}
