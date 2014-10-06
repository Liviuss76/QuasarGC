package com.nakedquasar.gamecenter.mvc.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.nakedquasar.gamecenter.core.services.GameService;
import com.nakedquasar.gamecenter.mvc.dto.ChangepasswdDto;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;

@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
	private GameService gameService;

	@ModelAttribute("passwordForm")
	public ChangepasswdDto getPasswordForm() {
		return new ChangepasswdDto();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminPage(Model model, HttpSession session) {
		if (session.getAttribute("gameDto") != null) {
			model.addAttribute("gameDto", session.getAttribute("gameDto"));
		} else {
			return "redirect:/games";
		}

		return "game";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createGame(@Valid @ModelAttribute("gameDto") GameDto gameDtoForm, BindingResult result, ModelMap model,
			HttpSession session) {
		
		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}
		
		GameDto gt = (GameDto) session.getAttribute("gameDto");
		
		if (result.hasErrors()) {
			session.setAttribute("gameDto", gameDtoForm);
			return "/game";
		}

		if (gameDtoForm.getGameImageRaw() != null && !gameDtoForm.getGameImageRaw().isEmpty()) {
			try {
				validateImage(gameDtoForm.getGameImageRaw());
				String imageString = Base64.encodeBase64String(gameDtoForm.getGameImageRaw().getBytes());
				gameDtoForm.setGameImage(imageString);
			} catch (Exception re) {
				model.put("error", re.getMessage());
				return "redirect:/game";
			}
		} else {
			gameDtoForm.setGameImage(gt.getGameImage());
		}

		try {

			gameDtoForm.setGameId(gt.getGameId());
			gameService.saveGame(gameDtoForm);
			session.setAttribute("gameDto", gameDtoForm);
		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "redirect:/game";
		}
		model.put("msg", "Game sucessfuly saved!");
		return "redirect:/game";
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
