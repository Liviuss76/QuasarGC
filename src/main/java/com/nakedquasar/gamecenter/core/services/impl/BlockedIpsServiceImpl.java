package com.nakedquasar.gamecenter.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nakedquasar.gamecenter.core.domain.Blockedips;
import com.nakedquasar.gamecenter.core.services.BlockedIpsService;
import com.nakedquasar.gamecenter.mvc.dto.BlockedIpDto;
import com.nakedquasar.gamecenter.persistence.repository.BlockedipsRepository;

@Repository
@Transactional
public class BlockedIpsServiceImpl implements BlockedIpsService {

	@Autowired
	public BlockedipsRepository blockedipRepository;

	@Override
	public Page<BlockedIpDto> getAllBlockedIps(Pageable pageable) {
		List<BlockedIpDto> blockipsDtos = new ArrayList<BlockedIpDto>();

		List<Blockedips> blockedIps = blockedipRepository.findAll();

		for (Blockedips blockedIp : blockedIps) {
			BlockedIpDto blockedIpDto = new BlockedIpDto();
			blockedIpDto.setId(blockedIp.getIpsId());
			blockedIpDto.setBlockedIp(blockedIp.getIp());
			blockipsDtos.add(blockedIpDto);
		}

		return new PageImpl<BlockedIpDto>(blockipsDtos);
	}

	@Override
	public void blockIp(String ip) {
		Blockedips blockedIp = new Blockedips();
		blockedIp.setIp(ip);
		blockedipRepository.saveAndFlush(blockedIp);		
	}

	@Override
	public void deleteIp(long id) {
		blockedipRepository.delete(id);		
	}
}
