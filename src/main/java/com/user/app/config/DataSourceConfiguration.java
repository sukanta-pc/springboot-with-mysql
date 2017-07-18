/*package com.user.app.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.Cloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class DataSourceConfiguration {

	@Autowired(required = false)
	Cloud cloud;

	@Bean
	@ConfigurationProperties("spring.jpa")
	public DataSource dataSource() {
		return cloud.getSingletonServiceConnector(DataSource.class, null);
	}

}*/