package Controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import App.Project2BackEndApplication;
import Models.Books;
import Services.UserService;

@SpringBootTest(classes=Project2BackEndApplication.class)
public class BooksController {
	@MockBean
	private UserService userServ;
	@Autowired
	private BooksController booksController;
	@Autowired
	private WebApplicationContext context;
	
	// to have more thorough testing, i'm going to have
	// a Jackson objectmapper to map objects to JSON
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	// sets up a mock of the spring mvc infrastructure
	// and allows us to mock http requests
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void getBooksSuccessfully() throws JsonProcessingException, Exception {
		List<Books> mockBooksList = Collections.emptyList();
		when(userServ.viewAvailableBooks()).thenReturn(mockBooksList);
		
		// perform sets up the HTTP request
		// and the expect methods expect things from the HTTP response
		mockMvc.perform(get("/books"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMapper.writeValueAsString(mockBooksList)));
		// this sends a mock GET request to /pets
		// and expects a 200 (OK) status code and the body to be
		// the JSON of the mockPetsList
	}

}
