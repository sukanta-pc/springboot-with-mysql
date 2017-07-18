package com.user.app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sukanta
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Config {

    
    //	Access Cloud-specific information in a cloud-neutral way:
    @Bean
    public Cloud cloudFactory() {

    	try {
    		return new CloudFactory().getCloud();
    	} catch ( CloudException ce ) {
    		//	Not running in cloud environment, return null
    		return null;
    	}
    }
}