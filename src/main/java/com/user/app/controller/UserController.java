package com.user.app.controller;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * @author Sukanta
 *
 */
@Controller    // This means that this class is a Controller
@RequestMapping(path="/users") // This means URL's start with /users (after Application path)
@Api(value="usermanagement", description="Operations pertaining to users data management")
public class UserController {
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	@ApiOperation(value = "View a list of available Users", response = Iterable.class)
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.GET) 
	@ApiOperation(value = "View of available User based on Id", response = User.class)
	public @ResponseBody User getUser(@ApiParam @PathVariable long id) {
		// This returns a JSON or XML with the users
		
		return userRepository.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT) 
	@ApiOperation(value = "Create a new user", response = ResponseVO.class)
	public @ResponseBody ResponseVO addNewUser (@ApiParam @RequestBody UserVO userVo) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		User n = new User();
		n.setName(userVo.getName());
		n.setEmail(userVo.getEmail());
		n.setProfession(userVo.getProfession());
		userRepository.save(n);
		return new ResponseVO(true,"New user Created successfully");
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	@ApiOperation(value = "Update user", response = ResponseVO.class)
	public @ResponseBody ResponseVO updateUser (@ApiParam @RequestBody UserVO userVo) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		User n = new User();
		n.setId(userVo.getId());
		n.setName(userVo.getName());
		n.setEmail(userVo.getEmail());
		n.setProfession(userVo.getProfession());
		userRepository.save(n);
		return new ResponseVO(true,"User data updated successfully");
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	@ApiOperation(value = "Delete user", response = ResponseVO.class)
	public @ResponseBody ResponseVO deleteUser (@ApiParam @PathVariable Long id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		User n = new User();
		n.setId(id);
		userRepository.delete(n);
		return new ResponseVO(true,"User deleted successfully");
	}
}
