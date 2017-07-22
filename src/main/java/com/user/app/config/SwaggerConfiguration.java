package com.user.app.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import com.user.app.util.AppLogger;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Sukanta
 *
 */
/**
 * Springfox Swagger configuration.
 *
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use a specific Spring profile for this class, so that only front-end developers
 * have access to the Swagger view.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Swagger Springfox configuration.
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket usersApi() {
    	AppLogger.getLogger().debug("Starting Swagger");
    	StopWatch watch = new StopWatch();
        watch.start();
    	Docket docket =  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.user.app.controller"))
                .paths(regex("/users.*"))
                .build()
                .apiInfo(metaData());
    	watch.stop();
    	AppLogger.getLogger().debug("Started Swagger in {} ms:"+ watch.getTotalTimeMillis());
    	return docket;
    }
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for User Data Management",
                "1.0",
                "Terms of service",
                new Contact("Sukanta Mondal", "https://github.com/suku19/", "sukanta_mondal@syntelinc.com"),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}
