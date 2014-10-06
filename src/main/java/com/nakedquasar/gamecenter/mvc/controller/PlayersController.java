package com.nakedquasar.gamecenter.mvc.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.core.services.AchievementService;
import com.nakedquasar.gamecenter.core.services.GameService;
import com.nakedquasar.gamecenter.core.services.LeaderboardService;
import com.nakedquasar.gamecenter.core.services.LogsService;
import com.nakedquasar.gamecenter.core.services.PlayerService;
import com.nakedquasar.gamecenter.mvc.dto.ChangepasswdDto;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;
import com.nakedquasar.gamecenter.mvc.dto.PlayerAchievementDto;
import com.nakedquasar.gamecenter.mvc.dto.PlayerDto;
import com.nakedquasar.gamecenter.mvc.dto.PlayerLeaderboardDto;
import com.nakedquasar.gamecenter.mvc.dto.PlayerLogDto;
import com.nakedquasar.gamecenter.mvc.dto.SearchFormDto;
import com.nakedquasar.gamecenter.mvc.utils.GameMapper;
import com.nakedquasar.gamecenter.mvc.utils.PageWrapper;
import com.nakedquasar.gamecenter.mvc.utils.PlayerMapper;

@Controller
@RequestMapping("/players")
public class PlayersController {
	@Autowired
	public PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Autowired
	private LeaderboardService leaderboardService;

	@Autowired
	private AchievementService achievementService;

	@Autowired
	private LogsService logsService;

	@ModelAttribute("gamesCount")
	public int getGamesCount() {
		return gameService.getGamesCount();
	}

	@ModelAttribute("playersCount")
	public int getPlayersCount() {
		return playerService.getPlayersCount();
	}

	@ModelAttribute("passwordForm")
	public ChangepasswdDto getPasswordForm() {
		return new ChangepasswdDto();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getPlayerPage(ModelMap model, Pageable pageable, HttpSession session) {
		session.removeAttribute("playerDto");
		PageWrapper<Player> page = new PageWrapper<Player>(playerService.getAllPlayers(pageable), "/players");
		model.addAttribute("players", PlayerMapper.map(page.getContent()));
		model.addAttribute("searchForm", new SearchFormDto());
		model.addAttribute("page", page);

		return "players";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String getSearchPlayerPage(@ModelAttribute("searchFrom") SearchFormDto searchFormDto, ModelMap model,
			Pageable pageable, HttpSession session) {
		session.removeAttribute("playerDto");
		PageWrapper<Player> page;

		if (searchFormDto.getPlayerUsername() != null && !searchFormDto.getPlayerUsername().trim().isEmpty()) {
			page = new PageWrapper<Player>(playerService.getAllPlayers(pageable, searchFormDto.getPlayerUsername()),
					"/players");
		} else {
			page = new PageWrapper<Player>(playerService.getAllPlayers(pageable), "/players");
		}

		model.addAttribute("players", PlayerMapper.map(page.getContent()));
		model.addAttribute("searchForm", searchFormDto);
		model.addAttribute("page", page);

		return "players";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editPlayer(@RequestParam("id") String playerId, ModelMap model, HttpSession session) {
		if (playerId == null) {
			return "redirect:/players";
		}

		try {
			PlayerDto playerDto = PlayerMapper.map(playerService.getPlayer(playerId));
			playerDto.setLeaderboardsCount(leaderboardService.getPlayerLeaderboardsCount(UUID.fromString(playerId)));
			playerDto.setAchievementsCount(achievementService.getPlayerAchievementsCount(UUID.fromString(playerId)));
			playerDto.setLogsCount(logsService.getPlayerLogsCount(UUID.fromString(playerId)));
			model.addAttribute("playerForm", playerDto);
			session.setAttribute("playerForm", playerDto);
			model.addAttribute("editMode", true);
			return "player";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/players";
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createPlayer(@ModelAttribute("playerForm") PlayerDto playerDto, ModelMap model, HttpSession session) {
		if (playerDto == null) {
			model.addAttribute("playerForm", new PlayerDto());
		} else {
			model.addAttribute("playerForm", playerDto);
		}
		model.addAttribute("editMode", false);
		return "player";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savePlayer(@Valid @ModelAttribute("playerForm") PlayerDto playerDto, BindingResult result,
			ModelMap model, RedirectAttributes redirectAttrs) {

		try {
			if (result.hasErrors()) {
				model.addAttribute("playerForm", playerDto);
				if (playerDto.getPlayerId() != null) {
					model.addAttribute("editMode", true);
				} else {
					model.addAttribute("editMode", false);
				}
				return "player";
			}

			if (playerDto.getPlayerPictureRaw() != null && !playerDto.getPlayerPictureRaw().isEmpty()) {
				validateImage(playerDto.getPlayerPictureRaw());
				String imageString = Base64.encodeBase64String(playerDto.getPlayerPictureRaw().getBytes());
				playerDto.setPlayerPicture(imageString);
			} else if (playerDto.getPlayerId() != null) {
				playerDto.setPlayerPicture(playerService.getPlayer(playerDto.getPlayerId()).getPlayerPicture());
			}

			if (playerDto.getPlayerId() != null) {
				playerService.savePlayer(playerDto);
			} else {
				playerService.createPlayer(playerDto);
			}
		} catch (Exception e) {
			redirectAttrs.addAttribute("error", e.getMessage());

			if (playerDto.getPlayerId() != null) {
				redirectAttrs.addAttribute("id", playerDto.getPlayerId().toString());
				redirectAttrs.addAttribute("editMode", true);
				return "redirect:/players/edit";
			} else {
				redirectAttrs.addAttribute("playerForm", playerDto);
				redirectAttrs.addAttribute("editMode", false);
				return "redirect:/players/create";
			}

		}
		redirectAttrs.addAttribute("msg", "Player " + playerDto.getPlayerUsername() + " sucessfuly saved!");
		return "redirect:/players";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteAchievement(@RequestParam("id") String playerId, ModelMap map, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		try {
			playerService.deletePlayer(UUID.fromString(playerId));
		} catch (Exception e) {
			redirectAttrs.addAttribute("error", e.getMessage());
			return "redirect:/players";
		}
		redirectAttrs.addAttribute("msg", "Player was sucessfuly deleted!");
		return "redirect:/players";
	}

	@RequestMapping(value = "/playerleaderboards", method = RequestMethod.GET)
	public String gePlayerLeaderboards(ModelMap model, Pageable pageable, HttpSession session) {
		if (session.getAttribute("playerForm") == null) {
			return "redirect:/players";
		}

		PlayerDto playerDto = (PlayerDto) session.getAttribute("playerForm");

		PageWrapper<PlayerLeaderboardDto> page = new PageWrapper<PlayerLeaderboardDto>(
				leaderboardService.getPlayerLeaderboards(pageable, playerDto.getPlayerId()),
				"/players/playerleaderboards");
		model.addAttribute("leaderboards", (List<PlayerLeaderboardDto>) page.getContent());
		model.addAttribute("playerForm", playerDto);
		model.addAttribute("page", page);

		return "playerleaderboards";
	}

	@RequestMapping(value = "/playerachievements", method = RequestMethod.GET)
	public String gePlayerAchievements(ModelMap model, Pageable pageable, HttpSession session) {
		if (session.getAttribute("playerForm") == null) {
			return "redirect:/players";
		}

		PlayerDto playerDto = (PlayerDto) session.getAttribute("playerForm");

		PageWrapper<PlayerAchievementDto> page = new PageWrapper<PlayerAchievementDto>(
				achievementService.getPlayerAchievements(pageable, playerDto.getPlayerId()),
				"/player/playerachievements");
		model.addAttribute("achievements", (List<PlayerAchievementDto>) page.getContent());
		model.addAttribute("playerForm", playerDto);
		model.addAttribute("page", page);

		return "playerachievements";
	}

	@RequestMapping(value = "/playerlogs", method = RequestMethod.GET)
	public String gePlayerLogs(ModelMap model, Pageable pageable, HttpSession session) {
		if (session.getAttribute("playerForm") == null) {
			return "redirect:/players";
		}

		PlayerDto playerDto = (PlayerDto) session.getAttribute("playerForm");

		PageWrapper<PlayerLogDto> page = new PageWrapper<PlayerLogDto>(logsService.getAllLogsByPlayerId(pageable,
				playerDto.getPlayerId()), "/player/playerlogs");
		model.addAttribute("logs", (List<PlayerLogDto>) page.getContent());
		model.addAttribute("playerForm", playerDto);
		model.addAttribute("page", page);

		return "playerlogs";
	}

	@RequestMapping(value = "/goach", method = RequestMethod.GET)
	public String goGameRedir(@RequestParam("gameId") String gameId, @RequestParam("achId") String achievementId,
			HttpSession session, RedirectAttributes redirectAttrs) {
		GameDto gm = GameMapper.map(gameService.getGameById(UUID.fromString(gameId)));
		session.setAttribute("gameDto", gm);

		redirectAttrs.addAttribute("id", achievementId);
		return "redirect:/achievements/edit";
	}

	private void validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/png")) {
			throw new RuntimeException("Only PNG images are accepted");
		}

		BufferedImage imageTmp;
		try {
			imageTmp = ImageIO.read(image.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException("Failed to read image");
		}

		if (imageTmp == null)
			throw new RuntimeException("Failed to read image");

		Integer width = imageTmp.getWidth();
		Integer height = imageTmp.getHeight();

		if (width > 512 || height > 512) {
			throw new RuntimeException("Only images with max resolution 512x512 are accepted");
		}
	}

}
