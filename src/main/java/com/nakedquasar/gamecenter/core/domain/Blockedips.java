package com.nakedquasar.gamecenter.core.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Cacheable
@Entity(name = "Blockedips")
@Table(name="blockedips")
public class Blockedips {
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "IpsIdSequence", sequenceName = "ips_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IpsIdSequence")
	private Long ipsId;
	
	@Column(name = "ip", nullable = false)
	private String ip;

	public Long getIpsId() {
		return ipsId;
	}

	public void setIpsId(Long ipsId) {
		this.ipsId = ipsId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
