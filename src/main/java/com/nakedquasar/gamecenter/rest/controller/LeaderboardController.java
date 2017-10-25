package com.nakedquasar.gamecenter.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nakedquasar.gamecenter.core.domain.Player;
import com.nakedquasar.gamecenter.core.services.AchievementService;
import com.nakedquasar.gamecenter.core.services.LeaderboardService;
import com.nakedquasar.gamecenter.core.services.PlayerService;
import com.nakedquasar.gamecenter.rest.controller.beans.AllAchievementsResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.AllScoresResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.IapSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementProgressResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementProgressSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerAchievementUnlockSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerProfileResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerProfileSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerScoreResponse;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerScoreSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.PlayerSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.RestResponse;
import com.nakedquasar.gamecenter.rest.controller.errors.ErrorInfo;
import com.nakedquasar.gamecenter.security.Security;

@Controller
@RequestMapping("/api")
public class LeaderboardController {
	@Autowired
	public LeaderboardService scoreService;

	@Autowired
	public AchievementService achievementService;

	@Autowired
	public PlayerService playerService;
	
	final static Logger logger = Logger.getLogger(LeaderboardController.class);
	@Value("${key1}")
	private String key1;
	@Value("${key2}")
	private String key2;
	@Value("${key3}")
	private String key3;
	@Value("${key4}")
	private String key4;
	@Value("${key5}")
	private String key5;
	@Value("${key6}")
	private String key6;
	@Value("${key7}")
	private String key7;
	@Value("${key8}")
	private String key8;
	@Value("${key9}")
	private String key9;
	@Value("${key10}")
	private String key10;

	private List<String> keys;

	@RequestMapping(method = RequestMethod.GET, value = "/scores")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse getAllScores(@RequestParam(value = "leaderboard", required = true) String leaderboardid,
			@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size",
					required = false) Integer size) throws Exception {
		RestResponse rre = new RestResponse();

		if (leaderboardid != null) {
			if (page == null)
				page = 0;

			if (size == null)
				size = 10;

			AllScoresResponse ps = scoreService.getAllScores(UUID.fromString(leaderboardid), page, size);
			rre.setObjectContainer(ps.getPlayerScores());
		} else {
			throw new Exception("Scores table not found.");
		}

		return rre;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/score")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse getScore(@RequestParam(value = "leaderboard", required = false) String leaderboardid)
			throws Exception {
		RestResponse rre = new RestResponse();

		if (leaderboardid != null) {
			PlayerScoreResponse details = scoreService.getPlayerScore(UUID.fromString(leaderboardid));
			details.setScoresCount(scoreService.getScoresCount(UUID.fromString(leaderboardid)));
			rre.setObjectContainer(details);
		} else {
			throw new Exception("Score not found.");
		}

		return rre;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/score")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse submitScore(@RequestBody PlayerScoreSubmit playerScore, HttpServletRequest request) {
		RestResponse rre = new RestResponse();
		try {
			PlayerScoreResponse playerScoreCreated = scoreService.submitPlayerScore(
					UUID.fromString(playerScore.getLeaderboardId()), playerScore.getPlayerScore(),
					request.getRemoteAddr());

			if (playerScoreCreated != null) {
				rre.setObjectContainer(playerScoreCreated);
			} else {
				throw new Exception("Unable to submit score.");
			}
		} catch (Exception e) {
			rre.setObjectContainer(null);
			rre.setErrorInfo(new ErrorInfo(e));
		}

		return rre;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/player")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse getPlayer() throws Exception {
		RestResponse rre = new RestResponse();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			PlayerResponse ps = playerService.getPlayer(userDetails.getUsername(), userDetails.getPassword());
			rre.setObjectContainer(ps);
		} else {
			throw new Exception("No credentials found for player.");
		}

		return rre;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse registerPlayer(@RequestBody PlayerSubmit player, HttpServletRequest request) throws Exception {
		RestResponse rre = new RestResponse();
		PlayerResponse playerCreated = playerService.submitPlayer(player, request.getRemoteAddr());
		rre.setObjectContainer(playerCreated);

		return rre;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse updatePlayer(@RequestBody PlayerSubmit player, HttpServletRequest request) throws Exception {
		RestResponse rre = new RestResponse();
		PlayerResponse playerCreated = playerService.updatePlayer(player, request.getRemoteAddr());
		rre.setObjectContainer(playerCreated);

		return rre;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/achievements")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse getAchievements(@RequestParam(value = "game", required = true) String gameId) throws Exception {
		RestResponse rre = new RestResponse();

		if (gameId != null) {
			AllAchievementsResponse ps = achievementService.getAllAchievementsByGameId(UUID.fromString(gameId));
			rre.setObjectContainer(ps.getPlayerAchievements());
		} else {
			throw new Exception("Scores table not found.");
		}

		return rre;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/achievement")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse getAchievement(@RequestParam(value = "code", required = true) String achievementId)
			throws Exception {
		RestResponse rre = new RestResponse();
		Player pl = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			pl = playerService.getDbPlayer(userDetails.getUsername(), userDetails.getPassword());
		} else {
			throw new Exception("No credentials found for player.");
		}

		if (achievementId != null) {
			PlayerAchievementResponse ps = achievementService.getAchievementByCodeAndPlayer(achievementId,
					pl.getPlayerId());
			rre.setObjectContainer(ps);
		} else {
			throw new Exception("No achievement code specified");
		}

		return rre;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/achievement")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse submitAchievement(@RequestBody PlayerAchievementProgressSubmit playerAchievementProgress,
			HttpServletRequest request) throws Exception {
		RestResponse rre = new RestResponse();
		Player pl = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			pl = playerService.getDbPlayer(userDetails.getUsername(), userDetails.getPassword());
		} else {
			throw new Exception("No credentials found for player.");
		}

		if (playerAchievementProgress != null) {
			PlayerAchievementProgressResponse ps = achievementService.submitAchievementProgress(
					playerAchievementProgress, pl, request.getRemoteAddr());
			rre.setObjectContainer(ps);
		} else {
			throw new Exception("No achievement specified");
		}
		return rre;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/unlockachievement")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse unlockAchievement(@RequestBody PlayerAchievementUnlockSubmit playerAchievementUnlock,
			HttpServletRequest request) throws Exception {
		RestResponse rre = new RestResponse();
		Player pl = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			pl = playerService.getDbPlayer(userDetails.getUsername(), userDetails.getPassword());
		} else {
			throw new Exception("No credentials found for player.");
		}

		if (playerAchievementUnlock != null) {
			PlayerAchievementResponse ps = achievementService.unlockAchievementByCodeAndPlayer(
					playerAchievementUnlock.getAchievementCode(), pl, request.getRemoteAddr());
			rre.setObjectContainer(ps);
		} else {
			throw new Exception("No achievement code specified");
		}
		return rre;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/profile")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse getProfile(@RequestParam(value = "game", required = true) String gameId) throws Exception {
		RestResponse rre = new RestResponse();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			Player pl = playerService.getDbPlayer(userDetails.getUsername(), userDetails.getPassword());	
			PlayerProfileResponse pr = playerService.getPlayerProfile(pl.getPlayerId(), UUID.fromString(gameId));
			rre.setObjectContainer(pr);
		} else {
			throw new Exception("No credentials found for player.");
		}
		
		return rre;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/profile")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse updateProfile(@RequestBody PlayerProfileSubmit profile, HttpServletRequest request) throws Exception {
		RestResponse rre = new RestResponse();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			User userDetails = (User) auth.getPrincipal();
			Player pl = playerService.getDbPlayer(userDetails.getUsername(), userDetails.getPassword());
			
			PlayerProfileResponse pr = playerService.updatePlayerProfile(pl.getPlayerId(), UUID.fromString(profile.getGameId()), profile.getProfile());
			rre.setObjectContainer(pr);
		} else {
			throw new Exception("No credentials found for player.");
		}
		
		return rre;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/iap")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse submitScore(@RequestBody String iap, HttpServletRequest request) {
		keys = new ArrayList<String>();
		keys.add(key1);
		keys.add(key2);
		keys.add(key3);
		keys.add(key4);
		keys.add(key5);
		keys.add(key6);
		keys.add(key7);
		keys.add(key8);
		keys.add(key9);
		keys.add(key10);
		
		ObjectMapper mapper = new ObjectMapper();
		RestResponse rre = new RestResponse();
		boolean state = false;
		// byte[] bytesEncoded = Base64.encodeBase64(str .getBytes());
		// System.out.println("ecncoded value is " + new String(bytesEncoded ));

		try {
			byte[] valueDecoded = Base64.decodeBase64(iap.getBytes());

			String decoded = encryptDecrypt(new String(valueDecoded));
			IapSubmit iapSubmit = mapper.readValue(decoded, IapSubmit.class);
			
			for (String key : keys) {
				state = Security.verifyPurchase(key, iapSubmit.getData(), iapSubmit.getSignature());
				if (state)
					break;
			}

			logger.info("IAP valid => " + state);

			iapSubmit.setValid(state);
			
			String serializedObj = mapper.writeValueAsString(iapSubmit) ; 
			String encoded = encryptDecrypt(serializedObj);
			byte[] bytesEncoded = Base64.encodeBase64(encoded.getBytes());
			
			rre.setObjectContainer(new String(bytesEncoded));

		} catch (Exception e) {
			rre.setObjectContainer(null);
			rre.setErrorInfo(new ErrorInfo(e));
		}

		return rre;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	RestResponse handleBadRequest(HttpServletRequest req, Exception ex) {
		return new RestResponse(null, new ErrorInfo(ex));
	}
	
	private String encryptDecrypt(String input) {
		String keyS = "nqcheck";
		char[] key = keyS.toCharArray();
		StringBuilder output = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			output.append((char) (input.charAt(i) ^ key[i % key.length]));
		}

		return output.toString();
	}
}
