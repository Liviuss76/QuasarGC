package com.nakedquasar.gamecenter.config;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class CoreConfig {
	@Value("${keystore.file}") private String keystoreFile;
	@Value("${keystore.pass}") private String keystorePass;
	@Value("${keystore.type}") private String keystoreType;
	@Value("${keystore.alias}") private String keystoreAlias;
	@Value("${server.port}") private int serverPort;
	
	@Bean
	   public MultipartResolver multipartResolver() {
	   CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	   multipartResolver.setMaxUploadSize(100000000);
	   return multipartResolver;
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer()  throws FileNotFoundException {
		final String absoluteKeystoreFile = ResourceUtils.getFile(keystoreFile).getAbsolutePath();

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(	ConfigurableEmbeddedServletContainer factory) {
				if (factory instanceof TomcatEmbeddedServletContainerFactory) {
					TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
					containerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
							@Override
							public void customize(Connector connector) {
								connector.setPort(serverPort);
								connector.setSecure(true);
								connector.setScheme("https");
								Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
					            proto.setSSLEnabled(true);
					            proto.setKeystoreFile(absoluteKeystoreFile);
					            proto.setKeystorePass(keystorePass);
					            proto.setKeystoreType(keystoreType);
					            proto.setKeyAlias(keystoreAlias);
							}
						});
					containerFactory.setSessionTimeout(10, TimeUnit.MINUTES);
					ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/errors/unauthorised");
					ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/errors/unauthorised");
		            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/errors/resourcenotfound");
		            containerFactory.addErrorPages(error401Page, error403Page, error404Page);
				}
			}
		};
	}
}
