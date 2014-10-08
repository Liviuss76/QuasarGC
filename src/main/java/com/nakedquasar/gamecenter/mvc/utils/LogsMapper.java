package com.nakedquasar.gamecenter.mvc.utils;

import java.util.ArrayList;
import java.util.List;

import com.nakedquasar.gamecenter.core.domain.PlayerLog;
import com.nakedquasar.gamecenter.mvc.dto.PlayerLogDto;

public class LogsMapper {
	public static PlayerLogDto map(PlayerLog playerLog) {
		PlayerLogDto plDto = new PlayerLogDto();
		plDto.setAction(playerLog.getAction());
		plDto.setActionType(playerLog.getActionType());
		plDto.setActionDate(playerLog.getActionDate());
		plDto.setIp(playerLog.getIp());
		return plDto;
	}

	public static List<PlayerLogDto> map(List<PlayerLog> playerLogs) {
		List<PlayerLogDto> plogDtos = new ArrayList<PlayerLogDto>();
		for (PlayerLog playerLog : playerLogs) {
			plogDtos.add(map(playerLog));
		}
		return plogDtos;
	}
}
