package Services;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import App.Model.Book;
import App.Model.User;
import Data.BookDAO;
import Data.DAOFactory;
import Data.UserDAO;
import Exceptions.AlreadyIssuedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

public class UserServiceImpl implements UserService{
	private UserDAO userDao = DAOFactory.getUserDAO();
	private BookDAO bookDao = DAOFactory.getBookDAO();


	public User logIn(String username, String password) throws IncorrectCredentialsException {
		User user = userDao.getByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			throw new IncorrectCredentialsException();
		}
	}


	public User register(User newUser) throws UsernameAlreadyExistsException {
		int id = userDao.create(newUser);
		if (id != 0) {
			newUser.setUserId(id);
			return newUser;
		} else {
			throw new UsernameAlreadyExistsException();
		}
	}


	public List<Book> viewAvailableBooks() {
		return bookDao.getByStatus("Available");
	}

	public List<Book> searchBooksByTitle(String title) {
		List<Book> books = bookDao.getAll();
		List<Book> booksWithTitle = new LinkedList<>();
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getTitle().toLowerCase().equals(title.toLowerCase())) {
				booksWithTitle.add(books.get(i));
			}
		}
		return booksWithTitle;
	}

	public User checkoutBook(User user, Book bookToCheckout) throws AlreadyIssuedException, Exception {
		bookToCheckout = bookDao.getById(bookToCheckout.getBookId());
		if (bookToCheckout.getStatus().equals("Issued")) {
			throw new AlreadyIssuedException();
		} else {
			user = userDao.getById(user.getUserId());
			if (user != null) {
				bookToCheckout.setStatus("Issued");
				user.getBooks().add(bookToCheckout);
				try {
					bookDao.update(bookToCheckout);
					userDao.update(user);
					userDao.updateBooks(bookToCheckout.getBookId(), user.getUserId());
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception();
				}
			}
			return user;
		}
	}

	public Book getBookById(int id) {
		return bookDao.getById(id);
	}

	public User getUserById(int id) {
		return userDao.getById(id);
	}

}
