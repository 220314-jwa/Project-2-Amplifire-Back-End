package dev.amplifire.app.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.amplifire.app.Project2BackEndApplication;
import dev.amplifire.app.models.Users;
import dev.amplifire.app.services.UserService;

@SpringBootTest(classes=Project2BackEndApplication.class)
public class UsersControllerTest {
	@MockBean
	private UserService userServ;
	
	@Autowired
	private UsersControllerTest usersController;
	
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	public void getUsersByIdSuccessfully() throws JsonProcessingException, Exception {
		Users mockUser = userServ.getUserById(0);
		when(userServ.getUserById(0)).thenReturn(mockUser);
		
		mockMvc.perform(get("users/{id}"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMapper.writeValueAsString(mockUser)));
	}
}
