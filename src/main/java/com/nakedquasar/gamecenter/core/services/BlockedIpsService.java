package com.nakedquasar.gamecenter.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nakedquasar.gamecenter.mvc.dto.BlockedIpDto;

public interface BlockedIpsService {
	public Page<BlockedIpDto> getAllBlockedIps(Pageable pageable);

	public void blockIp(String blockedIp);
	
	public void deleteIp(long id);
}
