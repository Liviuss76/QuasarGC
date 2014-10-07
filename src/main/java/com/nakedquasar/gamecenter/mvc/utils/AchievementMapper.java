package com.nakedquasar.gamecenter.mvc.utils;

import java.util.ArrayList;
import java.util.List;

import com.nakedquasar.gamecenter.core.domain.Achievement;
import com.nakedquasar.gamecenter.core.domain.PlayerAchievement;
import com.nakedquasar.gamecenter.mvc.dto.AchievementDto;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementResponse;

public class AchievementMapper {
	public static AchievementDto map(Achievement achievement) {
		AchievementDto dto = new AchievementDto();
		dto.setAchievementId(achievement.getAchievementId());
		dto.setGameId(achievement.getGame().getGameId());
		dto.setAchievementCode(achievement.getAchievementCode());
		dto.setAchievementName(achievement.getAchievementName());
		dto.setAchievementDesc(achievement.getAchievementDesc());
		dto.setAchievementImage(achievement.getAchievementImage());
		dto.setAchievementUnlockPoints(achievement.getAchievementUnlockPoints());
		dto.setAchievementBonusPoints(achievement.getAchievementGivePoints());
		dto.setAchievementHidden(achievement.isAchievementHidden());
		dto.setAchievementRepeatable(achievement.isAchievementRepeatable());
		dto.setAchievementIncrementPoints(achievement.isAchievementIncrementPoints());
		dto.setAchievementCreationDate(achievement.getAchievementCreationDate());
		return dto;
	}

	public static List<AchievementDto> map(List<Achievement> achievements) {
		List<AchievementDto> dtos = new ArrayList<AchievementDto>();
		for (Achievement achievement : achievements) {
			dtos.add(map(achievement));
		}
		return dtos;
	}

	public static PlayerAchievementResponse mapJson(Achievement achievement) {
		PlayerAchievementResponse dto = new PlayerAchievementResponse();
		dto.setAchievementName(achievement.getAchievementName());
		dto.setAchievementDesc(achievement.getAchievementDesc());
		dto.setAchievementImage(achievement.getAchievementImage());
		dto.setAchievementCode(achievement.getAchievementCode());
		dto.setAchievementUnlockPoints(achievement.getAchievementUnlockPoints());
		dto.setAchievementBonusPoints(achievement.getAchievementGivePoints());
		dto.setAchievementRepeatable(achievement.isAchievementRepeatable());
		dto.setAchievementProgress(0);
		return dto;
	}

	public static PlayerAchievementResponse mapJson(Achievement achievement, PlayerAchievement playerAchievement) {
		PlayerAchievementResponse dto = new PlayerAchievementResponse();
		dto.setAchievementName(achievement.getAchievementName());
		dto.setAchievementDesc(achievement.getAchievementDesc());
		dto.setAchievementImage(achievement.getAchievementImage());
		dto.setAchievementCode(achievement.getAchievementCode());
		dto.setAchievementUnlockPoints(achievement.getAchievementUnlockPoints());
		dto.setAchievementBonusPoints(achievement.getAchievementGivePoints());
		dto.setAchievementRepeatable(achievement.isAchievementRepeatable());
		dto.setAchievementProgress(0);

		if (playerAchievement.getUnlockpoints() >= achievement.getAchievementUnlockPoints()) {
			dto.setAchievementProgress(100.0);
			dto.setAchievementUnlocked(playerAchievement.getUnlockedcount() > 0);
		} else {
			double progress = (achievement.getAchievementUnlockPoints() / 100.0) * playerAchievement.getUnlockpoints();
			dto.setAchievementProgress(progress);
			dto.setAchievementUnlocked(playerAchievement.getUnlockedcount() > 0);
		}

		return dto;
	}

	public static List<PlayerAchievementResponse> mapJson(List<Achievement> achievements) {
		List<PlayerAchievementResponse> dtos = new ArrayList<PlayerAchievementResponse>();
		for (Achievement achievement : achievements) {
			if (!achievement.isAchievementHidden()) {
				dtos.add(mapJson(achievement));
			}
		}
		return dtos;
	}

	public static List<PlayerAchievementResponse> mapJson(List<Achievement> achievements,
			List<PlayerAchievement> playerAchievements) {
		List<PlayerAchievementResponse> dtos = new ArrayList<PlayerAchievementResponse>();

		for (Achievement achievement : achievements) {
			if (!achievement.isAchievementHidden()) {
				PlayerAchievementResponse playerAchievementResponse = mapJson(achievement);

				for (PlayerAchievement playerAchievement : playerAchievements) {
					if (playerAchievement.getId().getAchievement().getAchievementCode()
							.equals(playerAchievementResponse.getAchievementCode())) {
						if (playerAchievement.getUnlockpoints() >= playerAchievementResponse
								.getAchievementUnlockPoints()) {
							playerAchievementResponse.setAchievementProgress(100.0);
							playerAchievementResponse.setAchievementUnlocked(playerAchievement.getUnlockedcount() > 0);
						} else {
							double progress = (playerAchievementResponse.getAchievementUnlockPoints() / 100.0)
									* playerAchievement.getUnlockpoints();
							playerAchievementResponse.setAchievementProgress(progress);
							playerAchievementResponse.setAchievementUnlocked(playerAchievement.getUnlockedcount() > 0);
						}
						break;
					}
				}

				dtos.add(playerAchievementResponse);
			}
		}
		return dtos;
	}
}
