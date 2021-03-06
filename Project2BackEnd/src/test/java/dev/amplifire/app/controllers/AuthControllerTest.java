package dev.amplifire.app.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.amplifire.app.Project2BackEndApplication;
import dev.amplifire.app.controllers.AuthControllers;
import dev.amplifire.app.models.Users;
import dev.amplifire.app.services.UserService;

@SpringBootTest(classes=Project2BackEndApplication.class)
public class AuthControllerTest {
	@MockBean
	private UserService userServ;
	@Autowired
	private AuthControllers authController;
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void logInSuccessfully() throws JsonProcessingException, Exception {
		String mockCred = "test";
		
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("username", mockCred);
		credentials.put("password", mockCred);
		String credentialsJSON = jsonMapper.writeValueAsString(credentials);
		
		Users mockUser = new Users();
		mockUser.setUsername(mockCred);
		mockUser.setPassword(mockCred);
		when(userServ.logIn(mockCred, mockCred)).thenReturn(mockUser);
		
		mockMvc.perform(
				post("/auth")
				.content(credentialsJSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMapper.writeValueAsString(mockUser)));
	}

}
