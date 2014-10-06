package com.nakedquasar.gamecenter.core.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nakedquasar.gamecenter.mvc.dto.PlayerLogDto;

public interface LogsService {
	public Page<PlayerLogDto> getAllLogsByPlayerId(Pageable pageable, UUID playerId);

	public int getPlayerLogsCount(UUID playerId);
}
