package com.nakedquasar.gamecenter.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.PlayerProfile;
import com.nakedquasar.gamecenter.core.domain.PlayerProfileKey;

public interface PlayersProfilesRepository extends JpaRepository<PlayerProfile, PlayerProfileKey> {
	@Query("SELECT profile FROM PlayerProfile pp WHERE pp.id.player.playerId =:playerId AND pp.id.game.gameId =:gameId)")
	public String findByPlayerGameKey(@Param("playerId")UUID playerId, @Param("gameId") UUID gameId);
	
	@Query("SELECT pp FROM PlayerProfile pp WHERE pp.id.player.playerId =:playerId AND pp.id.game.gameId =:gameId)")
	public PlayerProfile findByKeyValues(@Param("playerId")UUID playerId, @Param("gameId") UUID gameId);
	
}