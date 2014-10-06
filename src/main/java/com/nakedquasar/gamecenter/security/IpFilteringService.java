package com.nakedquasar.gamecenter.security;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nakedquasar.gamecenter.persistence.repository.BlockedipsRepository;

@Service("ipFilteringService")
public class IpFilteringService {
	private static Logger LOG = LoggerFactory.getLogger(IpFilteringService.class);
	@Value("${login.retrybeforeblock}")
	private int loginRetry;
	@Value("${login.timetoblock}")
	private int timeToBlock;
	@Autowired
	public BlockedipsRepository blockedipsRepository;

	private HashMap<String, BlockedUser> loginAttempts;

	public IpFilteringService() {
		loginAttempts = new HashMap<String, BlockedUser>();
	}
	
	

	public boolean isIpBlocked(String address) {

		if(blockedipsRepository.getIp(address) > 0)
			return true;
		
		if (loginAttempts.containsKey(address) && loginAttempts.get(address).isIpBlocked()) {
			if (loginAttempts.get(address).getRemainingTime(new Date(), timeToBlock) <= 0) {
				loginAttempts.remove(address);
				return false;
			} else {
				return true;
			}

		}
		return false;
	}

	public void addFailedLogin(String address) {
		if (loginAttempts.containsKey(address)) {
			if (!loginAttempts.get(address).isIpBlocked()) {
				BlockedUser busr = loginAttempts.get(address);
				busr.incrementLoginAttempts(1);

				if (busr.getLoginAttempts() >= loginRetry) {
					busr.setBlockTime(new Date());
					loginAttempts.put(address, busr);
					LOG.info("Address " + address + " has been blocked for " + timeToBlock
							+ "min due to many failed login attempts");
				} else {
					busr.incrementLoginAttempts(1);
					loginAttempts.put(address, busr);
				}
			}
		} else {
			loginAttempts.put(address, new BlockedUser(1));
			if (loginRetry == 1)
				LOG.info("Address " + address + " has been blocked for " + timeToBlock
						+ "min due to multiple failed login attempts");
		}
	}
}
