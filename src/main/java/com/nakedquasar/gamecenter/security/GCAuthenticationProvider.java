package com.nakedquasar.gamecenter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class GCAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setPasswordEncoder(new StandardPasswordEncoder() );
		super.setUserDetailsService(userDetailsService);
	}
	
	@Autowired
	IpFilteringService ipFiltering;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		WebAuthenticationDetails wad = (WebAuthenticationDetails)authentication.getDetails();
		
		try {
			if(ipFiltering.isIpBlocked(wad.getRemoteAddress())){
				throw new InsufficientAuthenticationException("IP Blacklisted");	
			}
			Authentication auth = super.authenticate(authentication);
			return auth;

		} catch (BadCredentialsException e) {
			ipFiltering.addFailedLogin(wad.getRemoteAddress());
			throw e;
		}catch (AuthenticationException e){
			throw e;
		}

	}

}