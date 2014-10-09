package com.nakedquasar.gamecenter.mvc.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
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

import com.nakedquasar.gamecenter.config.UtilsService;
import com.nakedquasar.gamecenter.core.domain.LeaderBoard;
import com.nakedquasar.gamecenter.core.services.GameService;
import com.nakedquasar.gamecenter.core.services.LeaderboardService;
import com.nakedquasar.gamecenter.mvc.dto.ChangepasswdDto;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;
import com.nakedquasar.gamecenter.mvc.dto.LeaderboardDto;
import com.nakedquasar.gamecenter.mvc.utils.GameMapper;
import com.nakedquasar.gamecenter.mvc.utils.LeaderboardMapper;
import com.nakedquasar.gamecenter.mvc.utils.PageWrapper;

@Controller
@RequestMapping("/leaderboards")
public class LeaderboardsController {

	@Autowired
	private LeaderboardService leaderboardService;
	
	@Autowired
	private GameService gameService;

	@Autowired
	UtilsService utilsService;
	
	@ModelAttribute("appversion")
	public String getAppVersion() {
		return utilsService.getAppVersion();
	}
	
	@ModelAttribute("passwordForm")
	public ChangepasswdDto getPasswordForm() {
		return new ChangepasswdDto();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getLeaderboardsPage(ModelMap model, Pageable pageable,
			HttpSession session) {

		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");
		model.addAttribute("gameDto", gt);

		PageWrapper<LeaderBoard> page = new PageWrapper<LeaderBoard>(
				leaderboardService.getAllLeaderboardsByGameId(pageable,
						gt.getGameId()), "/leaderboards");
		model.addAttribute("leaderboards",
				LeaderboardMapper.map(page.getContent()));
		model.addAttribute("page", page);

		return "leaderboards";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createLeaderboard(ModelMap model, HttpSession session) {

		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");
		model.addAttribute("gameDto", gt);
		model.addAttribute("leaderboardForm", new LeaderboardDto());

		return "leaderboard";
	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveLeaderboardRedir() {
		return "redirect:/leaderboards";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveLeaderboardPage(
			@Valid @ModelAttribute("leaderboardForm") LeaderboardDto leaderboardDto,
			BindingResult result,
			ModelMap model,
			HttpSession session, RedirectAttributes redirectAttrs) {

		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");

		try {
			if (result.hasErrors()) {
				if (leaderboardDto.getLeaderboardId() != null) {
					redirectAttrs.addAttribute("id",
							leaderboardDto.getLeaderboardId());
				}
				model.addAttribute("gameDto", gt);
				model.addAttribute("leaderboardForm", leaderboardDto);
				return "/leaderboard";
			}

			if (leaderboardDto.getLeaderBoardImageRaw() != null && !leaderboardDto.getLeaderBoardImageRaw().isEmpty()) {
				validateImage(leaderboardDto.getLeaderBoardImageRaw());
				String imageString = Base64.encodeBase64String(leaderboardDto.getLeaderBoardImageRaw()
						.getBytes());
				leaderboardDto.setLeaderBoardImage(imageString);
			} else if (leaderboardDto.getLeaderboardId() != null) {
				leaderboardDto.setLeaderBoardImage(leaderboardService
						.getLeaderBoardById(leaderboardDto.getLeaderboardId())
						.getLeaderBoardImage());
			}

			if (leaderboardDto.getLeaderboardId() != null) {
				leaderboardDto.setGameId(gt.getGameId());
				leaderboardService.saveLeaderboard(leaderboardDto);
			} else {
				leaderboardDto.setGameId(gt.getGameId());
				leaderboardService.createLeaderboard(leaderboardDto);
				gt.setLeaderboardsCount(gt.getLeaderboardsCount() + 1);
				session.setAttribute("gameDto", gt);
			}
		} catch (Exception e) {
			redirectAttrs.addAttribute("error", e.getMessage());

			if (leaderboardDto.getLeaderboardId() != null) {
				redirectAttrs.addAttribute("id",
						leaderboardDto.getLeaderboardId());
				return "redirect:/leaderboards/edit";
			}

			return "redirect:/leaderboards/create";
		}
		redirectAttrs.addAttribute("msg",
				"Leaderbord " + leaderboardDto.getLeaderboardName()
						+ " sucessfuly saved!");
		return "redirect:/leaderboards";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editLeaderboard(@RequestParam("id") String leaderboardId,
			ModelMap model, HttpSession session) {
		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");

		LeaderboardDto lbdto = LeaderboardMapper.map(leaderboardService
				.getLeaderBoardById(UUID.fromString(leaderboardId)));
		model.addAttribute("gameDto", gt);
		model.addAttribute("leaderboardForm", lbdto);
		return "leaderboard";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteLeaderboard(@RequestParam("id") String leaderboardId,
			ModelMap map, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");

		try {
			leaderboardService
					.deleteLeaderboard(UUID.fromString(leaderboardId));
			gt.setLeaderboardsCount(gt.getLeaderboardsCount() - 1);
			session.setAttribute("gameDto", gt);
		} catch (Exception e) {
			map.put("error", e.getMessage());
			return "redirect:/leaderboards";
		}
		map.put("msg", "Leaderboard was sucessfuly deleted!");
		return "redirect:/leaderboards";
	}
	
	@RequestMapping(value = "/goldb", method = RequestMethod.GET)
	public String goGameRedir(@RequestParam("gameId") String gameId, @RequestParam("ldbId") String achievementId,
			HttpSession session, RedirectAttributes redirectAttrs) {
		GameDto gm = GameMapper.map(gameService.getGameById(UUID.fromString(gameId)));
		session.setAttribute("gameDto", gm);

		redirectAttrs.addAttribute("id", achievementId);
		return "redirect:/leaderboards/edit";
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
