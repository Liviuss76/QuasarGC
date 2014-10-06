package com.nakedquasar.gamecenter.mvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nakedquasar.gamecenter.core.services.BlockedIpsService;
import com.nakedquasar.gamecenter.core.services.GameService;
import com.nakedquasar.gamecenter.core.services.PlayerService;
import com.nakedquasar.gamecenter.mvc.dto.BlockedIpDto;
import com.nakedquasar.gamecenter.mvc.dto.ChangepasswdDto;
import com.nakedquasar.gamecenter.mvc.utils.PageWrapper;

@Controller
@RequestMapping("/settings")
public class SettingsController {
	@Autowired
	public PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Autowired
	private BlockedIpsService blockedipService;

	@ModelAttribute("gamesCount")
	public int getGamesCount() {
		return gameService.getGamesCount();
	}

	@ModelAttribute("playersCount")
	public int getPlayersCount() {
		return playerService.getPlayersCount();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getSettingsPage(ModelMap model) {
		model.addAttribute("passwordForm", new ChangepasswdDto());
		return "settings";
	}

	@RequestMapping(value = "/blockedips", method = RequestMethod.GET)
	public String getBlockedipsPage(ModelMap model, Pageable pageable) {
		PageWrapper<BlockedIpDto> page = new PageWrapper<BlockedIpDto>(blockedipService.getAllBlockedIps(pageable),
				"/settings/blockedips");
		model.addAttribute("ips", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("passwordForm", new ChangepasswdDto());
		model.addAttribute("blockedIpForm", new BlockedIpDto());

		return "blockedips";
	}

	@RequestMapping(value = "/addblockip", method = RequestMethod.POST)
	public String blockIp(@Valid @ModelAttribute("blockedIpForm") BlockedIpDto blockedIpDto, BindingResult result,
			ModelMap model, Pageable pageable, RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			PageWrapper<BlockedIpDto> page = new PageWrapper<BlockedIpDto>(blockedipService.getAllBlockedIps(pageable),
					"/settings/blockedips");
			model.addAttribute("ips", page.getContent());
			model.addAttribute("page", page);
			model.addAttribute("passwordForm", new ChangepasswdDto());
			model.addAttribute("blockedIpForm", blockedIpDto);
			return "blockedips";
		}

		blockedipService.blockIp(blockedIpDto.getBlockedIp());
		redirectAttrs.addAttribute("msg", "IP was sucessfuly blocked");
		return "redirect:/settings/blockedips";

	}

	@RequestMapping(value = "/deleteip", method = RequestMethod.GET)
	public String deleteIp(@RequestParam("id") String ipId, ModelMap model, RedirectAttributes redirectAttrs) {

		blockedipService.deleteIp(Long.parseLong(ipId));
		redirectAttrs.addAttribute("msg", "IP was sucessfuly deleted from blocklist");
		return "redirect:/settings/blockedips";
	}

	@RequestMapping(value = "/changepasswd", method = RequestMethod.POST)
	public String changePlayerPassword(@Valid @ModelAttribute("passwordForm") ChangepasswdDto changepasswdDto,
			BindingResult result, ModelMap model, RedirectAttributes redirectAttrs) {
		if (result.hasErrors()) {
			model.addAttribute("passwordForm", changepasswdDto);
			return "/settings";
		}

		if (!changepasswdDto.getPlayerPassword().equals(changepasswdDto.getPlayerPasswordConfirm())) {
			redirectAttrs.addAttribute("error", "'Password' and 'Password confirm' does not match");
			return "redirect:/settings";
		}

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
				User userDetails = (User) auth.getPrincipal();
				playerService.changePassword(userDetails.getUsername(), changepasswdDto.getPlayerPassword());
			} else {
				throw new Exception("No credentials found for player.");
			}
			redirectAttrs.addAttribute("msg", "Password was sucessfuly changed");
			return "redirect:/settings";

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/settings";
		}
	}

}
