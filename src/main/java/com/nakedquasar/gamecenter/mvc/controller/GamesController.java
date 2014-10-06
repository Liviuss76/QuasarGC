package com.nakedquasar.gamecenter.mvc.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.nakedquasar.gamecenter.core.domain.Game;
import com.nakedquasar.gamecenter.core.services.AchievementService;
import com.nakedquasar.gamecenter.core.services.GameService;
import com.nakedquasar.gamecenter.core.services.PlayerService;
import com.nakedquasar.gamecenter.mvc.dto.ChangepasswdDto;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;
import com.nakedquasar.gamecenter.mvc.utils.GameMapper;
import com.nakedquasar.gamecenter.mvc.utils.PageWrapper;

@Controller
public class GamesController {

	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private AchievementService achievementService;
	
	@ModelAttribute("gamesCount")
	public int getGamesCount(){
	    return gameService.getGamesCount();
	}
	
	@ModelAttribute("playersCount")
	public int getPlayersCount(){
	    return playerService.getPlayersCount();
	}
	
	@ModelAttribute("passwordForm")
	public ChangepasswdDto getPasswordForm() {
		return new ChangepasswdDto();
	}

	@RequestMapping(value = "/games", method = RequestMethod.GET)
	public String getAdminPage(ModelMap model, Pageable pageable, HttpSession session) {
		session.removeAttribute("gameDto");
		PageWrapper<Game> page = new PageWrapper<Game>(
				gameService.getAllGames(pageable), "/games");
		model.addAttribute("games", GameMapper.map(page.getContent()));
		model.addAttribute("page", page);
		model.addAttribute("gameDto", new GameDto());

		return "games";
	}
	
	@RequestMapping(value = "/games/create", method = RequestMethod.GET)
	public String createGameRedir(){
		return "redirect:/games";
	}
	
	@RequestMapping(value = "/games/gogame", method = RequestMethod.GET)
	public String goGameRedir(@RequestParam("id") String gameId, HttpSession session){
		GameDto gm = GameMapper.map(gameService.getGameById(UUID.fromString(gameId)));
		session.setAttribute("gameDto", gm);
		return "redirect:/game";
	}

	@RequestMapping(value = "/games/create", method = RequestMethod.POST)
	public String createGame(
			@ModelAttribute("gameDto") GameDto gameDto,BindingResult result,
			ModelMap map,
			@ModelAttribute(value = "gameImage")
			 MultipartFile gameImage) {

		if (gameImage != null && !gameImage.isEmpty()) {
			try {
				validateImage(gameImage);
				String imageString = Base64.encodeBase64String(gameImage
						.getBytes());
				gameDto.setGameImage(imageString);
			} catch (Exception re) {
				map.put("error", re.getMessage());
				return "redirect:/games";
			}
		}

		try {
			gameService.createGame(gameDto);
		} catch (Exception e) {
			map.put("error", e.getMessage());
			return "redirect:/games";
		}
		map.put("msg", "Game " + gameDto.getGameName() + " sucessfuly saved!");
		return "redirect:/games";
	}

	@RequestMapping(value = "/games/delete", method = RequestMethod.GET)
	public String deleteGame(@RequestParam("id") String gameId, ModelMap map,
			HttpServletRequest request) {
		try {
			gameService.deleteGame(UUID.fromString(gameId));
		} catch (Exception e) {
			map.put("error", e.getMessage());
			return "redirect:/games";
		}
		map.put("msg", "Game was sucessfuly deleted!");
		return "redirect:/games";
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
			throw new RuntimeException(
					"Only images with max resolution 512x512 are accepted");
		}
	}
}
