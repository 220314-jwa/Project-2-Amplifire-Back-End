package dev.amplifire.app.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import dev.amplifire.app.Project2BackEndApplication;
import dev.amplifire.app.controllers.BooksController;
import dev.amplifire.app.models.Books;
import dev.amplifire.app.models.Status;
import dev.amplifire.app.models.Users;
import dev.amplifire.app.services.UserService;

@SpringBootTest(classes=Project2BackEndApplication.class)
public class BooksControllerTest {
	@MockBean
	private UserService userServ;
	@Autowired
	private BooksControllerTest booksController;
	@Autowired// tells the object is a dependency 
	// IOC container responsible for instantiation, assembly, and mgmt of beans
	private WebApplicationContext context;
	
	// to have more thorough testing, i'm going to have
	// a Jackson objectmapper to map objects to JSON
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	// sets up a mock of the spring mvc infrastructure
	// and allows us to mock http requests
	// Model-View_Controller framework is based on the use of a DispatcherServlet, 
	// which uses a FrontContoller design pattern.
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
	
	@Test
	public void getBooksByIdSuccessfully() throws JsonProcessingException, Exception {
		List<Books> mockBooksList = Collections.emptyList();
		Books mockId = userServ.getBookById(0);
		when(userServ.viewAvailableBooks().contains(mockId)).thenReturn(mockBooksList.contains(mockId));
		
		mockMvc.perform(get("/books/{id}"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMapper.writeValueAsString(mockBooksList.contains(mockId))));
		
	}
	
	@Test
	public void getByTitleSuccessfully() throws JsonProcessingException, Exception {
		String testTitle = "";
		List<Books> mockBooksList = Collections.emptyList();
		when(userServ.searchBookByTitle(testTitle)).thenReturn(mockBooksList);
		
		mockMvc.perform(get("/books/title"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMapper.writeValueAsString(mockBooksList.contains(testTitle))));
		
	}
	
	@Test
	public void putCheckoutBookSuccessfully() throws JsonProcessingException, Exception {
		List<Books> mockBookList = Collections.emptyList();
		Books mockCheckoutBook = userServ.getBookById(0);
		Users mockUser = userServ.getUserById(0);
		when(userServ.checkoutBook(mockUser, mockCheckoutBook)).thenReturn(mockUser);
		
		mockMvc.perform(put("/books/checkout"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMapper.writeValueAsString(mockUser.getBooks().equals(mockBookList.contains(mockCheckoutBook)))));
	}
	
	

}
