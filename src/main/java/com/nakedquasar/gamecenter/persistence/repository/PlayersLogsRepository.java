package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.PlayerLog;

public interface PlayersLogsRepository extends JpaRepository<PlayerLog, Long> {
	@Query("SELECT pl FROM PlayerLog pl WHERE pl.player.playerId=:playerId ORDER BY pl.actionDate DESC)")
	public Page<PlayerLog> findAllPlayerLogsByPlayerId(Pageable pageable, @Param("playerId")UUID playerId);
	
	@Query("SELECT pl FROM PlayerLog pl WHERE pl.player.playerId=:playerId ORDER BY pl.actionDate DESC)")
	public List<PlayerLog> findAllPlayerLogsByPlayerId(@Param("playerId")UUID playerId);
	
	@Query("SELECT COUNT(pl.logId) FROM PlayerLog pl WHERE pl.player.playerId =:playerId)")
	public int getCountLogsForPlayerId(@Param("playerId") UUID playerId);
}
