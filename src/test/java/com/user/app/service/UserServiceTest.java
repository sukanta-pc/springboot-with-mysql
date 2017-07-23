package com.user.app.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.app.entity.User;
import com.user.app.repository.UserRepository;
import com.user.app.service.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAlluser(){
		List<User> userList = new ArrayList<User>();
		userList.add(new User(1L,"Sukanta","sukata@gmail.com","Software Engineer"));
		userList.add(new User(2L,"Jhon","Jhon@gmail.com","Software Engineer"));
		userList.add(new User(3L,"Smith","smith@gmail.com","Software Engineer"));
		when(userRepository.findAll()).thenReturn(userList);
		
		List<User> result = userService.getAlluser();
		assertEquals(3, result.size());
	}
	
	@Test
	public void getUserById(){
		User user = new User(1L,"Sukanta","sukata@gmail.com","Software Engineer");
		when(userRepository.findOne(1L)).thenReturn(user);
		User result = userService.getUserById(1L);
		assertEquals("1", result.getId().toString());
		assertEquals("Sukanta", result.getName());
		assertEquals("sukata@gmail.com", result.getEmail());
		assertEquals("Software Engineer", result.getProfession());
	}
	
	@Test
	public void saveUser(){
		User user = new User(1L,"Sukanta","sukata@gmail.com","Software Engineer");
		when(userRepository.save(user)).thenReturn(user);
		User result = userService.saveUser(user);
		assertEquals("1", result.getId().toString());
		assertEquals("Sukanta", result.getName());
		assertEquals("sukata@gmail.com", result.getEmail());
		assertEquals("Software Engineer", result.getProfession());
	}
	
	@Test
	public void deleteUser(){
		User user = new User(1L,"Sukanta","sukata@gmail.com","Software Engineer");
		userService.deleteUser(user);
        verify(userRepository, times(1)).delete(user);
	}
}
