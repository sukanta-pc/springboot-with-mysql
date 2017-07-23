package com.user.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.user.app.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDataManagementApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void verifyCreateUserSucceeds() throws Exception {
		User user = new User();
		user.setName("Jhon Dao");
		user.setEmail("jhondao@test.com");
		user.setProfession("Software Developer");
		String json = new Gson().toJson(user);

		mockMvc.perform(
				post("/users").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.message").value("New user Created successfully"));
	}
	
	@Test
	public void verifyCreateUserFails() throws Exception {
		User user = new User();
		user.setName("");
		user.setEmail("jhondao@test.com");
		user.setProfession("Software Developer");
		String json = new Gson().toJson(user);

		mockMvc.perform(
				post("/users").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorCode").value("INPUT.DATA.INVALID"))
				.andExpect(jsonPath("$.message").value("Input data not valid.."));
	}
	
	@Test
	public void verifyCreateUserMalformedRequest() throws Exception {
		String json = "{\"name\": \"Jhon Dao\",\"email\": \"jhondao@test.com\",\"profession\": [\"Software Developer\"]}";

		mockMvc.perform(
				post("/users").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode").value("400"))
				.andExpect(jsonPath("$.message").value("The request could not be understood by the server due to malformed syntax."));
	}
	
	@Test
	public void verifyUserAllList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@Test
	public void verifyUserById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.name").exists())
		.andExpect(jsonPath("$.email").exists())
		.andExpect(jsonPath("$.profession").exists())
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.name").value("Jhon Dao"))
		.andDo(print());
	}
	
	@Test
	public void verifyUpdateUserSucceeds() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("Jhon Dao");
		user.setEmail("jhondao@test.com");
		user.setProfession("Software Developer");
		String json = new Gson().toJson(user);

		mockMvc.perform(
				put("/users").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.message").value("User data updated successfully"));
	}
	
	@Test
	public void verifyUpdateUserFails() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("");
		user.setEmail("jhondao@test.com");
		user.setProfession("Software Developer");
		String json = new Gson().toJson(user);

		mockMvc.perform(
				put("/users").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorCode").value("INPUT.DATA.INVALID"))
				.andExpect(jsonPath("$.message").value("Input data not valid.."));
	}
	
	@Test
	public void verifyUserInvalidIdToDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/100").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.success").value("false"))
		.andExpect(jsonPath("$.errorCode").value("USER.ID.INVALID"))
		.andExpect(jsonPath("$.message").value("User to delete doesn´t exist"))
		.andDo(print());
	}
	
	@Test
	public void verifyUserDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success").exists())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.success").value("true"))
		.andExpect(jsonPath("$.message").value("User has been deleted"))
		.andDo(print());
	}
	
	@Test
	public void verifyAppEnvironment() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.isCloudEnvironment").exists())
		.andExpect(jsonPath("$.serverTime").exists())
		.andDo(print());
	}
	
	@Test
	public void verifyEnvironmentDetails() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/env").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print());
	}
}
