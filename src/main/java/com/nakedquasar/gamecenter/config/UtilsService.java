package com.nakedquasar.gamecenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("utilsService")
public class UtilsService {
	@Value("${app.version}") private String appVersion;
	
	public String getAppVersion(){
		return appVersion;
	}
}
