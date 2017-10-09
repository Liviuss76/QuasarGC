package com.nakedquasar.gamecenter.core.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.mvc.dto.PlayerDto;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerProfileResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerSubmit;

public interface PlayerService {
	public PlayerResponse getPlayer(String username, String password) throws Exception;

	public PlayerResponse submitPlayer(PlayerSubmit player, String ip) throws Exception;
	
	public PlayerResponse updatePlayer(PlayerSubmit player, String ip) throws Exception;

	public int getPlayersCount();

	public Player getDbPlayer(String username, String password) throws Exception;
	
	public Player getPlayer(String playerId) throws Exception;
	
	public Player getPlayer(UUID playerId);

	public Page<Player> getAllPlayers(Pageable pageable);
	
	public Page<Player> getAllPlayers(Pageable pageable, String username);

	public Player createPlayer(PlayerDto playerDto) throws Exception;

	public Player savePlayer(PlayerDto playerDto) throws Exception;

	public void deletePlayer(UUID playerId) throws Exception;
	
	public void changePassword(String username, String password) throws Exception;
	
	public PlayerProfileResponse getPlayerProfile(UUID playerId, UUID gameId);
	
	public PlayerProfileResponse updatePlayerProfile(UUID playerId, UUID gameId, String profile) throws Exception;
}
