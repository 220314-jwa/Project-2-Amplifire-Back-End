package ServiceTest;

import java.util.List;

import org.junit.Test;

import App.Model.Book;
import App.Model.User;
import Exceptions.AlreadyIssuedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;


public interface UserServiceTests {
	
	@Test
	public void logInSuccessfully() throws IncorrectCredentialsException;
	@Test
    public void register() throws UsernameAlreadyExistsException;
	@Test
	public List<Book> viewAvailableBooks();
	@Test
	public List<Book> searchBooksByTitle(String title);
	@Test
	public void checkoutBook() throws AlreadyIssuedException, Exception;
	public Book getBookById(int id);
	public User getUserById(int id);
}

