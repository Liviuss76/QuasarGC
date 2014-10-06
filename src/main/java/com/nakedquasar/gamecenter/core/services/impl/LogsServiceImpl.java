package com.nakedquasar.gamecenter.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nakedquasar.gamecenter.core.domain.PlayerLog;
import com.nakedquasar.gamecenter.core.services.LogsService;
import com.nakedquasar.gamecenter.mvc.dto.PlayerLogDto;
import com.nakedquasar.gamecenter.persistence.repository.PlayersLogsRepository;

@Repository
@Transactional
public class LogsServiceImpl implements LogsService {

	@Autowired
	public PlayersLogsRepository logRepository;

	@Override
	public Page<PlayerLogDto> getAllLogsByPlayerId(Pageable pageable, UUID playerId) {
		List<PlayerLogDto> plogDtos = new ArrayList<PlayerLogDto>();

		List<PlayerLog> playerLogs = logRepository.findAllPlayerLogsByPlayerId(playerId);

		for (PlayerLog playerLog : playerLogs) {
			PlayerLogDto plDto = new PlayerLogDto();
			plDto.setAction(playerLog.getAction());
			plDto.setActionType(playerLog.getActionType());
			plDto.setActionDate(playerLog.getActionDate());
			plDto.setIp(playerLog.getIp());
			plogDtos.add(plDto);
		}

		return new PageImpl<PlayerLogDto>(plogDtos);
	}
	
	@Override
	public int getPlayerLogsCount(UUID playerId) {
		return logRepository.getCountLogsForPlayerId(playerId);
	}

}
