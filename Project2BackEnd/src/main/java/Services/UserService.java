package Services;

import java.util.List;

import org.apache.catalina.User;

import Exceptions.AlreadyIssuedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;
import Models.Books;
import Models.Users;



public interface UserService {
	public Users logIn(String username, String password) throws IncorrectCredentialsException;
	
	public Users register(Users user) throws UsernameAlreadyExistsException;
	
	public List<Books> viewAvailableBooks();
	
	public List<Books> searchBookByTitle(String title);
	
	public Users checkoutBook(Users user, Books bookToCheckout) throws AlreadyIssuedException, Exception ;

	public Books getBookById(int id);
	
	public Users getUserById(int id);
}
