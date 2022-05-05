package Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import App.Project2BackEndApplication;
import Data.BookRepository;
import Data.StatusRepository;
import Data.UserRepository;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;
import Models.Books;
import Models.Status;
import Models.Users;
import Services.UserService;

@SpringBootTest(classes=Project2BackEndApplication.class)
public class UserServiceTest {
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private BookRepository bookRepo;
	@MockBean
	private StatusRepository statusRepo;
	@Autowired
	private UserService userServ;

	@Test
	public void logInSuccessfully() throws IncorrectCredentialsException {
		// setup (arguments, expected result, etc.)
		String username = "snicholes";
		String password = "pass";
		
		// mocking: we need to mock userDao.getByUsername(username)
		// we're expecting a user with matching username & password
		Users mockUser = new Users();
		mockUser.setUsername(username);
		mockUser.setPassword(password);
		when(userRepo.findByUsername(username)).thenReturn(mockUser);
		
		// call the method we're testing
		Users result = userServ.logIn(username, password);
		
		// assertion
		assertEquals(username, result.getUsername());
	}
	
	@Test
	public void logInWrongUsername() {
		String username = "abc123";
		String password = "1234567890";
		
		// we need to mock userDao.getByUsername(username)
		when(userRepo.findByUsername(username)).thenReturn(null);
		
		assertThrows(IncorrectCredentialsException.class, () -> {
			// put the code that we're expecting to throw the exception
			userServ.logIn(username, password);
		});
	}
	
	@Test
	public void logInWrongPassword() {
		String username = "snicholes";
		String password = "1234567890";
		
		Users mockUser = new Users();
		mockUser.setUsername(username);
		mockUser.setPassword("fake_password");
		when(userRepo.findByUsername(username)).thenReturn(mockUser);
		
		assertThrows(IncorrectCredentialsException.class, () -> {
			// put the code that we're expecting to throw the exception
			userServ.logIn(username, password);
		});
	}
	
	@Test
	public void registerSuccessfully() throws UsernameAlreadyExistsException {
		Users newUser = new Users();
		
		// mock userDao.create(newUser)
		Users mockUser = new Users();
		mockUser.setId(1);
		when(userRepo.save(newUser)).thenReturn(mockUser);
		
		Users result = userServ.register(newUser);
		
		// the behavior that i'm looking for is that the
		// method returns the User with their newly generated ID,
		// so i want to make sure the ID was generated (not the default)
		assertNotEquals(0, result.getId());
	}
	
	@Test
	public void registerUsernameTaken() {
		Users newUser = new Users();
		newUser.setUsername("snicholes");
		
		// mock userDao.create(newUser)
		when(userRepo.save(newUser)).thenReturn(newUser);
		
		assertThrows(UsernameAlreadyExistsException.class, () -> {
			userServ.register(newUser);
		});
	}
	
	@Test
	public void viewPetsSuccessfully() {
		// mock petDao.getByStatus("Available");
		when(bookRepo.findByStatusName("Available")).thenReturn(Collections.emptyList());
		
		List<Books> books = userServ.viewAvailableBooks();
		
		// i just want to make sure that the pets are returned -
		// i don't need to check that the pets are all available
		// because that filtering happens in the database. i just
		// need to check that the pets list isn't null
		assertNotNull(books);
	}
	
	@Test
	public void searchBooksByTitle() {
		String title = "Success";
		
		// mock petDao.getAll()
		// i'm making a list that contains a pet with the species
		// and a pet without the species to make sure the service
		// is filtering them out properly
		List<Books> mockBooks = new LinkedList<>();
		Books book = new Books();
		book.setTitle(title);
		Books notBook = new Books();
		notBook.setTitle("Fail, never give up");
		mockBooks.add(book);
		mockBooks.add(notBook);
		
		when(bookRepo.findAll()).thenReturn(mockBooks);
		
		List<Books> booksByTitle = userServ.searchBookByTitle(title);
		
		boolean onlyBooksInList = true;
		for (Books bookie : booksByTitle) {
			String bookTitle = bookie.getTitle().toLowerCase();
			// if the pet species doesn't contain the species passed in
			if (!bookTitle.contains(title)) {
				// then we'll set the boolean to false
				onlyBooksInList = false;
				// and stop the loop because we don't need to continue
				break;
			}
		}
		
		assertTrue(onlyBooksInList);
	}
	
	@Test
	public void checkoutBookSuccessfully() throws Exception {
		Users testUser = new Users();
		Books testBook = new Books();
		
		// petDao.getById: return testPet
		when(bookRepo.findById(testBook.getBookId())).thenReturn(Optional.of(testBook));
		// userDao.getById: return testUser
		when(userRepo.findById(testUser.getId())).thenReturn(Optional.of(testUser));
		
		Status mockIssuedStatus = new Status();
		mockIssuedStatus.setName("Issued");
		when(statusRepo.findByName("Issued")).thenReturn(mockIssuedStatus);
		// petDao.update: do nothing
		// when petDao update is called with any pet object, do nothing
		doNothing().when(bookRepo).save(any(Books.class));
		// userDao.update: do nothing
		doNothing().when(userRepo).save(any(Users.class));
		
		Users result = userServ.checkoutBook(testUser, testBook);
		
		// there are two behaviors i'm looking for:
		// that the user now has the pet in their list of pets,
		// and that the pet in the list has its status updated.
		// to check this, i'm checking that the pet with the Adopted
		// status is in the user's list.
		testBook.setStatus(mockIssuedStatus);
		List<Books> usersBooks = result.getBooks();
		assertTrue(usersBooks.contains(testBook));
		
		// Mockito.verify allows you to make sure that a particular mock method
		// was called (or that it was never called, or how many times, etc.)
		verify(bookRepo, times(1)).save(any(Books.class));
	}
	
	@Test
	public void bookAlreadyIssued() throws SQLException {
		Users testUser = new Users();
		Books testBook = new Books();
		Status mockIssuedStatus = new Status();
		mockIssuedStatus.setName("Adopted");
		testBook.setStatus(mockIssuedStatus);
		
		// petDao.getById: return testPet
		when(bookRepo.getById(testBook.getBookId())).thenReturn(testBook);
		
		assertThrows(Exception.class, () -> {
			userServ.checkoutBook(testUser, testBook);
		});
		
		verify(bookRepo, never()).save(any(Books.class));
		verify(userRepo, never()).save(any(Users.class));
	}


}
