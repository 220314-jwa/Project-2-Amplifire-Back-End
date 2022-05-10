package Services;

import java.util.List;

import App.Model.Book;
import App.Model.User;
import Exceptions.AlreadyIssuedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

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
	public User logIn(String username, String password) throws IncorrectCredentialsException;
    public User register(User newUser) throws UsernameAlreadyExistsException;
	public List<Book> viewAvailableBooks();
	public List<Book> searchBooksByTitle(String title);
	public User checkoutBook(User user, Book bookToCheckout) throws AlreadyIssuedException, Exception;
	public Book getBookById(int id);
	public User getUserById(int id);
}
