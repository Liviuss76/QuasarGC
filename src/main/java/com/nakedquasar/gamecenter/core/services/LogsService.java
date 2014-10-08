package com.nakedquasar.gamecenter.core.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nakedquasar.gamecenter.core.domain.PlayerLog;

public interface LogsService {
	public Page<PlayerLog> getAllLogsByPlayerId(UUID playerId, Pageable pageable);

	public int getPlayerLogsCount(UUID playerId);
}
