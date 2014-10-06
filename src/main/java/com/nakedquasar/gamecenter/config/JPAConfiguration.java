package com.nakedquasar.gamecenter.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.nakedquasar.gamecenter.persistence.repository")
@EnableTransactionManagement
public class JPAConfiguration {
	@Value("${database.url}")
	private String databaseUrl;
	@Value("${database.username}")
	private String databaseUsername;
	@Value("${database.password}")
	private String databasePassword;
	@Value("${database.debugsql}")
	private String databaseDebugsql;

	@Bean(destroyMethod="close")
	public DataSource dataSource() throws Exception {
		final ComboPooledDataSource ret = new ComboPooledDataSource();
		ret.setDriverClass("org.postgresql.Driver");
		ret.setUser(databaseUsername);
		ret.setPassword(databasePassword);
		ret.setJdbcUrl(databaseUrl);
		ret.setMinPoolSize(5);
		ret.setMaxPoolSize(20);
		ret.setCheckoutTimeout(300);
		ret.setUnreturnedConnectionTimeout(1000);
		return ret;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws Exception {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.nakedquasar.*");
		factory.setDataSource(dataSource());
		factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		factory.setJpaProperties(additionalProperties());
		factory.afterPropertiesSet();

		return factory;
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		// properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.show_sql", databaseDebugsql);
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return properties;
	}
}