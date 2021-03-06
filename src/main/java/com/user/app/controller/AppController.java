package com.user.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.user.app.util.AppLogger;
/**
 * @author Sukanta
 *
 */
/**
 * Controller for the Cloud Foundry workshop - Spring MVC version.
 * 
 */

@Controller
public class AppController {
	
	@Autowired (required=false) Cloud cloud;

	/**
	 * Gets basic environment information.  This is the application's
	 * default action.
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> appIndex() throws Exception {
		Map<String, Object> appConfig = new HashMap<>();	
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		String serverTime = dateFormat.format(date);
		appConfig.put("serverTime", serverTime);
		
		String port = System.getenv("PORT");
		appConfig.put("port", port);

		String vcapServices = System.getenv("VCAP_SERVICES");
		appConfig.put("vcapServices", vcapServices);
		
		if (cloud == null ){
			appConfig.put("isCloudEnvironment", Boolean.FALSE);
		} else {
			appConfig.put("isCloudEnvironment",true);
			appConfig.put("vcapApplication", cloud.getApplicationInstanceInfo().getProperties());
			AppLogger.getLogger().info("VCAP_SERVICES [{}] "+ vcapServices);
			AppLogger.getLogger().info("VCAP_APPLICATION [{}] "+ System.getenv("VCAP_APPLICATION"));
		}
		
		AppLogger.getLogger().info("Current date and time = [{"+serverTime+"}], port = [{"+port+"}].");

		return appConfig;
	}
	

	@RequestMapping(value = "/env", method = RequestMethod.GET)
	public @ResponseBody TreeMap<String,String> environment() throws Exception {
			
		//	Dump the environment variables to the page.  
		//	The TreeMap produces alphabetical order:
		return new TreeMap<String,String>(System.getenv());
	}
	
	/**
	 * Action to initiate shutdown of the system.  In CF, the application 
	 * <em>should</em>f restart.  In other environments, the application
	 * runtime will be shut down.
	 */
	@RequestMapping(value = "/kill", method = RequestMethod.GET)
	public void kill() {
		
		AppLogger.getLogger().warn("*** The system is shutting down. ***");
		System.exit(-1);
		
	}

}
