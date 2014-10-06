package com.nakedquasar.gamecenter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.Blockedips;

public interface BlockedipsRepository extends JpaRepository<Blockedips, Long> {

	@Query("SELECT COUNT(bi) FROM Blockedips bi WHERE bi.ip=:Ip ")
	public int getIp(@Param("Ip")String Ip);
}
