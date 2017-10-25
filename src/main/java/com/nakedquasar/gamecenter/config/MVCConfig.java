package com.nakedquasar.gamecenter.config;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan( "com.nakedquasar.gamecenter" )
public class MVCConfig extends WebMvcConfigurerAdapter {
final static Logger logger = Logger.getLogger(MVCConfig.class);
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/games").setViewName("games");
		registry.addViewController("/game").setViewName("game");
		registry.addViewController("/leaderboard").setViewName("leaderboard");
		registry.addViewController("/achievement").setViewName("achievement");
		registry.addViewController("/login").setViewName("login");
	}

	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	    PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
	    resolver.setFallbackPageable(new PageRequest(0, 10));
	    argumentResolvers.add(resolver);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/**");
	}
}