package com.user.app.controller;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.user.app.entity.User;
import com.user.app.model.ResponseVO;
import com.user.app.model.UserVO;
import com.user.app.repository.UserRepository;
import com.user.app.util.AppLogger;
import com.user.app.util.InputValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * @author Sukanta
 * 
 * UserController exposed service for user data CRUD operation
 *
 */
@Controller    
@RequestMapping(path="/users") 
@Api(value="usermanagement", description="Operations pertaining to users data management")
public class UserController {
	@Autowired 
	private UserRepository userRepository;
	
	@ApiOperation(value = "View a list of available Users", response = Iterable.class)
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Iterable<User> getAllUsers() {
		AppLogger.getLogger().info("Get all users..");
		return userRepository.findAll();
	}
	
	@ApiOperation(value = "View of available User based on Id", response = User.class)
	@RequestMapping(path="/{id}", method=RequestMethod.GET) 
	public @ResponseBody User getUser(@ApiParam @PathVariable long id) {
		AppLogger.getLogger().info("Get user by Id:"+id);
		return userRepository.findOne(id);
	}
	
	@ApiOperation(value = "Create a new user", response = ResponseVO.class)
	@RequestMapping(method=RequestMethod.PUT) 
	public @ResponseBody ResponseVO addNewUser (@ApiParam @RequestBody UserVO userVo) {
		AppLogger.getLogger().info("input data: "+ toStringInput(userVo));
		//input validation
		if(!InputValidator.isValidateInput(userVo)){
			AppLogger.getLogger().info("Input data not valid.. ");
			return new ResponseVO(false,"Input data not valid.. ");
		}
		userRepository.save(setUserData(userVo));
		AppLogger.getLogger().info("New user Created successfully");
		return new ResponseVO(true,"New user Created successfully");
	}

	
	@ApiOperation(value = "Update user", response = ResponseVO.class)
	@RequestMapping(method=RequestMethod.POST) 
	public @ResponseBody ResponseVO updateUser (@ApiParam @RequestBody UserVO userVo) {
		AppLogger.getLogger().info("input data: "+ toStringInput(userVo));
		//input validation
		if(isValidId(userVo.getId()) || !InputValidator.isValidateInput(userVo)){
			AppLogger.getLogger().info("Input data not valid.. ");
			return new ResponseVO(false,"Input data not valid.. ");
		}
		userRepository.save(setUserData(userVo));
		AppLogger.getLogger().info("User data updated successfully..");
		return new ResponseVO(true,"User data updated successfully");
	}

	@ApiOperation(value = "Delete user", response = ResponseVO.class)
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody ResponseVO deleteUser (@ApiParam @PathVariable Long id) {
		//input validation
		if(isValidId(id)){
			AppLogger.getLogger().info("User Id Invalid..");
			return new ResponseVO(false,"User Id Invalid..");
		}
		User n = new User();
		n.setId(id);
		userRepository.delete(n);
		AppLogger.getLogger().info("User{id:"+id+"} deleted successfully..");
		return new ResponseVO(true,"User{id:"+id+"} deleted successfully");
	}

	private boolean isValidId(Long id) {
		boolean isValidUserId = null==userRepository.findOne(id);
		AppLogger.getLogger().info("isValidUser"+isValidUserId);
		return isValidUserId;
	}
	
	private User setUserData(UserVO userVo) {
		User n = new User();
		n.setId(userVo.getId());
		n.setName(userVo.getName());
		n.setEmail(userVo.getEmail());
		n.setProfession(userVo.getProfession());
		return n;
	}

	private String toStringInput(UserVO userVo) {
		return new ReflectionToStringBuilder(userVo, new RecursiveToStringStyle()).toString();
	}
}
