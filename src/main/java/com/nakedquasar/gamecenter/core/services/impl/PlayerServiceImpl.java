package com.nakedquasar.gamecenter.core.services.impl;

import java.sql.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.core.domain.PlayerLog;
import com.nakedquasar.gamecenter.core.services.PlayerService;
import com.nakedquasar.gamecenter.mvc.dto.PlayerDto;
import com.nakedquasar.gamecenter.persistence.repository.PlayersLogsRepository;
import com.nakedquasar.gamecenter.persistence.repository.PlayersRepository;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerSubmit;

@Repository
@Transactional
public class PlayerServiceImpl implements PlayerService {
	@Autowired
	public PlayersRepository playerRepository;
	@Autowired
	public PlayersLogsRepository playerLogRepository;

	@Override
	public PlayerResponse getPlayer(String username, String password) throws Exception {
		Player pl = playerRepository.findByPlayerUsernamePass(username, password);
		if (pl == null)
			throw new Exception("Wrong username or password");
		return new PlayerResponse(pl);
	}

	@Override
	public Player getDbPlayer(String username, String password) throws Exception {
		Player pl = playerRepository.findByPlayerUsernamePass(username, password);
		if (pl == null)
			throw new Exception("Wrong username or password");
		return pl;
	}

	@Override
	public PlayerResponse submitPlayer(PlayerSubmit player, String ip) throws Exception {

		Player pl = playerRepository.findByPlayerUsername(player.getPlayerUsername());

		if (pl == null) {
			pl = new Player();
			pl.setPlayerUsername(player.getPlayerUsername());

			String password = player.getPlayerPassword();
			StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(password);
			pl.setPlayerPassword(encodedPassword);
			pl.setPlayerDisplayName(player.getPlayerDisplayName());
			pl.setPlayerFirstName(player.getPlayerFirstName());
			pl.setPlayerLastName(player.getPlayerLastName());
			pl.setPlayerEmail(player.getPlayerEmail());
			pl.setPlayerBirthdate(player.getPlayerBirthdate());
			pl.setPlayerSex(player.getPlayerSex());
			pl.setPlayerPlatform(player.getPlayerPlatform());
			pl.setPlayerRole("ROLE_USER");
			pl.setPlayerEnabled(true);
			pl.setDateTimeOfCreation(new Date(new java.util.Date().getTime()));
			pl.setIp(ip);

			if (player.getPlayerPicture() != null && !player.getPlayerPicture().trim().isEmpty()) {
				pl.setPlayerPicture(player.getPlayerPicture());
			} else {
				pl.setPlayerPicture(player.getPlayerPictureDefault());
			}

			pl.setPlayerprofile(player.getPlayerProfile());
			
			pl = playerRepository.saveAndFlush(pl);

			PlayerLog plog = new PlayerLog();
			plog.setPlayer(pl);
			plog.setActionType("REGISTER PLAYER");
			plog.setAction("Username:" + pl.getPlayerUsername());
			plog.setActionDate(new Date(new java.util.Date().getTime()));
			plog.setIp(ip);
			playerLogRepository.saveAndFlush(plog);

			return new PlayerResponse(pl);
		} else {
			throw new Exception("Username in use");
		}
	}

	@Override
	public PlayerResponse updatePlayer(PlayerSubmit playerSubmit, String ip) throws Exception {

		Player player = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			player = getDbPlayer(userDetails.getUsername(), userDetails.getPassword());
		} else {
			throw new Exception("No credentials found for player.");
		}

		if (player == null) {
			throw new Exception("Player not found. Please contact administrator.");
		} else {
			if (playerSubmit.getPlayerPassword() != null && !playerSubmit.getPlayerPassword().trim().isEmpty()) {
				String password = playerSubmit.getPlayerPassword();
				StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
				String encodedPassword = passwordEncoder.encode(password);
				player.setPlayerPassword(encodedPassword);
			}

			player.setPlayerDisplayName(playerSubmit.getPlayerDisplayName());
			player.setPlayerFirstName(playerSubmit.getPlayerFirstName());
			player.setPlayerLastName(playerSubmit.getPlayerLastName());
			player.setPlayerEmail(playerSubmit.getPlayerEmail());
			player.setPlayerBirthdate(playerSubmit.getPlayerBirthdate());
			player.setPlayerSex(playerSubmit.getPlayerSex());

			if (playerSubmit.getPlayerPicture() != null && !playerSubmit.getPlayerPicture().trim().isEmpty()) {
				player.setPlayerPicture(playerSubmit.getPlayerPicture());
			}
			
			player.setPlayerprofile(playerSubmit.getPlayerProfile());

			player = playerRepository.saveAndFlush(player);

			PlayerLog plog = new PlayerLog();
			plog.setPlayer(player);
			plog.setActionType("UPDATE PLAYER");
			plog.setAction("Username:" + player.getPlayerUsername());
			plog.setActionDate(new Date(new java.util.Date().getTime()));
			plog.setIp(ip);
			playerLogRepository.saveAndFlush(plog);

			return new PlayerResponse(player);
		}
	}

	@Override
	public int getPlayersCount() {
		return playerRepository.getPlayersCount();
	}

	@Override
	public Page<Player> getAllPlayers(Pageable pageable) {
		return playerRepository.findAllPlayers(pageable);
	}

	@Override
	public Player createPlayer(PlayerDto playerDto) throws Exception {
		Player pl = playerRepository.findByPlayerUsername(playerDto.getPlayerUsername());

		if (pl == null) {
			pl = new Player();
			pl.setPlayerUsername(playerDto.getPlayerUsername());
			String password = playerDto.getPlayerPassword();
			StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
			pl.setPlayerPassword(passwordEncoder.encode(password));
			pl.setPlayerPicture(playerDto.getPlayerPicture());
			pl.setPlayerDisplayName(playerDto.getPlayerDisplayName());
			pl.setPlayerFirstName(playerDto.getPlayerFirstName());
			pl.setPlayerLastName(playerDto.getPlayerLastName());
			pl.setPlayerEmail(playerDto.getPlayerEmail());
			pl.setPlayerBirthdate(playerDto.getPlayerBirthdate());
			pl.setPlayerSex(playerDto.getPlayerSex());
			pl.setPlayerPlatform(playerDto.getPlayerPlatform());
			pl.setPlayerRole("ROLE_USER");
			pl.setPlayerEnabled(playerDto.isPlayerEnabled());
			pl.setDateTimeOfCreation(new Date(new java.util.Date().getTime()));
			pl.setIp(playerDto.getIp());
			pl.setPlayerprofile(playerDto.getPlayerProfile());
		} else {
			throw new Exception("Username in use");
		}
		return playerRepository.saveAndFlush(pl);
	}

	@Override
	public Player savePlayer(PlayerDto playerDto) throws Exception {
		Player pl = playerRepository.findByPlayerId(playerDto.getPlayerId());
		Player pl2 = playerRepository.findByPlayerUsername(playerDto.getPlayerUsername());

		if (pl2 != null && !pl2.getPlayerId().equals(playerDto.getPlayerId())) {
			throw new Exception("Username in use");
		} else if (pl == null) {
			throw new Exception("Player not found");
		} else {
			pl.setPlayerUsername(playerDto.getPlayerUsername());
			String password = playerDto.getPlayerPassword();
			if (!password.trim().isEmpty()) {
				StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
				pl.setPlayerPassword(passwordEncoder.encode(password));
			}
			pl.setPlayerPicture(playerDto.getPlayerPicture());
			pl.setPlayerDisplayName(playerDto.getPlayerDisplayName());
			pl.setPlayerFirstName(playerDto.getPlayerFirstName());
			pl.setPlayerLastName(playerDto.getPlayerLastName());
			pl.setPlayerEmail(playerDto.getPlayerEmail());
			pl.setPlayerBirthdate(playerDto.getPlayerBirthdate());
			pl.setPlayerSex(playerDto.getPlayerSex());
			pl.setPlayerPlatform(playerDto.getPlayerPlatform());
			pl.setPlayerRole("ROLE_USER");
			pl.setPlayerEnabled(playerDto.isPlayerEnabled());
			pl.setPlayerprofile(playerDto.getPlayerProfile());
		}

		return playerRepository.saveAndFlush(pl);
	}

	@Override
	public void deletePlayer(UUID playerId) throws Exception {
		playerRepository.delete(playerId);

	}

	@Override
	public Player getPlayer(String playerId) throws Exception {
		return playerRepository.findByPlayerId(UUID.fromString(playerId));
	}

	@Override
	public Player getPlayer(UUID playerId) {
		return playerRepository.findByPlayerId(playerId);
	}

	@Override
	public void changePassword(String username, String password) throws Exception {
		Player pl = playerRepository.findByPlayerUsername(username);

		if (pl == null)
			throw new Exception("Player not found");

		StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
		pl.setPlayerPassword(passwordEncoder.encode(password));

		playerRepository.saveAndFlush(pl);
	}

	@Override
	public Page<Player> getAllPlayers(Pageable pageable, String username) {
		return playerRepository.findAllPlayers(pageable, username);
	}

}
