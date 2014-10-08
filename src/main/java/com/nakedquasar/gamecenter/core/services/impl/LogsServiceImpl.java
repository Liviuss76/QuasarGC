package com.nakedquasar.gamecenter.core.services.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nakedquasar.gamecenter.core.domain.PlayerLog;
import com.nakedquasar.gamecenter.core.services.LogsService;
import com.nakedquasar.gamecenter.persistence.repository.PlayersLogsRepository;

@Repository
@Transactional
public class LogsServiceImpl implements LogsService {

	@Autowired
	public PlayersLogsRepository logRepository;

	@Override
	public Page<PlayerLog> getAllLogsByPlayerId(UUID playerId, Pageable pageable) {
		return logRepository.findAllPlayerLogsByPlayerId(pageable, playerId);
	}
	
	@Override
	public int getPlayerLogsCount(UUID playerId) {
		return logRepository.getCountLogsForPlayerId(playerId);
	}

}
