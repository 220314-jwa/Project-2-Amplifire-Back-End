package Services;

import java.util.List;

import Beans.Book;
import Beans.User;
import Exceptions.AlreadyIssuedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

public class UserServiceImpl implements UserService{

	public User logIn(String username, String password) throws IncorrectCredentialsException {
		// TODO Auto-generated method stub
		return null;
	}

	public User register(User newUser) throws UsernameAlreadyExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Book> viewAvailableBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Book> searchBooksByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	public User checkoutBook(User user, Book bookToCheckout) throws AlreadyIssuedException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Book getBookById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
