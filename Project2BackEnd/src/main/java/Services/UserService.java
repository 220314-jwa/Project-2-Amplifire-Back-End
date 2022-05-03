package Services;

import java.util.List;

import org.apache.catalina.User;

import Exceptions.AlreadyIssuedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;
import Models.Books;
import Models.Users;

/*- here we can lay out all of the behaviors that we want
 * users to be able to do. services are just about the
 * services, or tasks, that we want to provide to users,
 * and the Java that makes those methods work.
 * 
 * it also allows for a separation between database code (DAOs),
 * HTTP handling code (Javalin), and the "business logic" or actual
 * functionality that users are doing (services). this idea is called
 * "separation of concerns" and allows you to have cleaner, more
 * organized, and more maintainable code.
 */

public interface UserService {
	public Users logIn(String username, String password) throws IncorrectCredentialsException;
	
	public Users register(Users user) throws UsernameAlreadyExistsException;
	
	public List<Books> viewAvailableBooks();
	
	public List<Books> searchBookByTitle(String title);
	
	public Users checkoutBook(Users user, Books bookToCheckout) throws AlreadyIssuedException, Exception ;

	public Books getBookById(int id);
	
	public Users getUserById(int id);
}
