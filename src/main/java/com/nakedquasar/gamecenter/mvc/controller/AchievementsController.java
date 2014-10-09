package com.nakedquasar.gamecenter.mvc.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
import com.nakedquasar.gamecenter.core.domain.Achievement;
import com.nakedquasar.gamecenter.core.services.AchievementService;
import com.nakedquasar.gamecenter.mvc.dto.AchievementDto;
import com.nakedquasar.gamecenter.mvc.dto.ChangepasswdDto;
import com.nakedquasar.gamecenter.mvc.dto.GameDto;
import com.nakedquasar.gamecenter.mvc.utils.AchievementMapper;
import com.nakedquasar.gamecenter.mvc.utils.PageWrapper;

@Controller
@RequestMapping("/achievements")
public class AchievementsController {
	@Autowired
	private AchievementService achievementService;

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
	public String getAchievementsPage(ModelMap model, Pageable pageable, HttpSession session) {

		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");
		model.addAttribute("gameDto", gt);

		PageWrapper<Achievement> page = new PageWrapper<Achievement>(achievementService.getAllAchievementsByGameId(
				pageable, gt.getGameId()), "/achievements");
		model.addAttribute("achievements", AchievementMapper.map(page.getContent()));
		model.addAttribute("page", page);

		return "achievements";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createAchievement(ModelMap model, HttpSession session) {

		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");
		model.addAttribute("gameDto", gt);
		model.addAttribute("achievementForm", new AchievementDto());

		return "achievement";
	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveAchievementRedir() {
		return "redirect:/achievements";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveAchievement(@Valid @ModelAttribute("achievementForm") AchievementDto achievementDto,
			BindingResult result, ModelMap model, HttpSession session, RedirectAttributes redirectAttrs) {

		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");

		try {
			if (result.hasErrors()) {
				if (achievementDto.getAchievementId() != null) {
					redirectAttrs.addAttribute("id", achievementDto.getAchievementId());
				}
				model.addAttribute("gameDto", gt);
				model.addAttribute("achievementForm", achievementDto);
				return "achievement";
			}

			if (achievementDto.getAchievementImageRaw() != null && !achievementDto.getAchievementImageRaw().isEmpty()) {
				validateImage(achievementDto.getAchievementImageRaw());
				String imageString = Base64.encodeBase64String(achievementDto.getAchievementImageRaw().getBytes());
				achievementDto.setAchievementImage(imageString);
			} else if (achievementDto.getAchievementId() != null) {
				achievementDto.setAchievementImage(achievementService.getAchievementById(
						achievementDto.getAchievementId()).getAchievementImage());
			}

			if (achievementDto.getAchievementId() != null) {
				achievementDto.setGameId(gt.getGameId());
				achievementService.saveAchievement(achievementDto);
			} else {
				achievementDto.setGameId(gt.getGameId());
				achievementService.createAchievement(achievementDto);
				gt.setAchievementsCount(gt.getAchievementsCount() + 1);
				session.setAttribute("gameDto", gt);
			}
		} catch (Exception e) {
			redirectAttrs.addAttribute("error", e.getMessage());

			if (achievementDto.getAchievementId() != null) {
				redirectAttrs.addAttribute("id", achievementDto.getAchievementId());
				return "redirect:/achievements/edit";
			}

			return "redirect:/achievements/create";
		}
		redirectAttrs.addAttribute("msg", "Achievement " + achievementDto.getAchievementName() + " sucessfuly saved!");
		return "redirect:/achievements";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editAchievement(@RequestParam("id") String achievementId, ModelMap model, HttpSession session) {
		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");

		AchievementDto achievementDto = AchievementMapper.map(achievementService.getAchievementById(Long
				.parseLong(achievementId)));
		model.addAttribute("gameDto", gt);
		model.addAttribute("achievementForm", achievementDto);
		return "achievement";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteAchievement(@RequestParam("id") String achievementId, ModelMap map, HttpServletRequest request,
			HttpSession session) {

		if (session.getAttribute("gameDto") == null) {
			return "redirect:/games";
		}

		GameDto gt = (GameDto) session.getAttribute("gameDto");

		try {
			achievementService.deleteAchievement(Long.parseLong(achievementId));
			gt.setAchievementsCount(gt.getAchievementsCount() - 1);
			session.setAttribute("gameDto", gt);
		} catch (Exception e) {
			map.put("error", e.getMessage());
			return "redirect:/achievements";
		}
		map.put("msg", "Achievement was sucessfuly deleted!");
		return "redirect:/achievements";
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
