package com.nakedquasar.gamecenter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Configuration
    @Order(1)                                                        
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		@Qualifier("authenticationProvider")
		AuthenticationProvider authenticationProvider;

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.eraseCredentials(false).authenticationProvider(
					authenticationProvider);
		}
		
		protected void configure(HttpSecurity http) throws Exception {
        	http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .csrf().disable().antMatcher("/api/**").authorizeRequests()
			.antMatchers("/api/register", "/api/ping","/errors/").permitAll()
			.antMatchers("/api/**").hasAnyRole("USER","ADMIN")
            .and()
            .httpBasic();
        }
    }
	
	 @Configuration                                                   
	    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
			@Autowired
			@Qualifier("authenticationProvider")
			AuthenticationProvider authenticationProvider;

			@Autowired
			public void configureGlobal(AuthenticationManagerBuilder auth)
					throws Exception {
				auth.eraseCredentials(false).authenticationProvider(
						authenticationProvider);
			}
		 
			@Override
            public void configure(WebSecurity web) throws Exception {
                web
                    .ignoring()
                        .antMatchers("/resources/**","/css/**","/js/**","/images/**");
            }
			
	        @Override
	        protected void configure(HttpSecurity http) throws Exception {
	        	http.csrf().disable().authorizeRequests().anyRequest()
				.hasRole("ADMIN")
				.and()
				.formLogin()
				.loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
	        }
	    }	
}