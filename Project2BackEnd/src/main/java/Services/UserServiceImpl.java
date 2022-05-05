package Services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Data.BookRepository;
import Data.StatusRepository;
import Data.UserRepository;
import Exceptions.AlreadyIssuedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;
import Models.Books;
import Models.Status;
import Models.Users;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private StatusRepository statusRepo;

	//Constructor injection
	//@Autowired // can skip the annotation if you only have one constructor
	public UserServiceImpl(UserRepository userRepo, BookRepository bookRepo, StatusRepository statusRepo) {
		this.userRepo = userRepo;
		this.bookRepo = bookRepo;
		this.statusRepo = statusRepo;
	}
	
	@Override
	public Users logIn(String username, String password) throws IncorrectCredentialsException {
		Users user = userRepo.findByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			throw new IncorrectCredentialsException();
		}

	}
	
	@Override
	public Users register(Users newUser) throws UsernameAlreadyExistsException {
		int id = userRepo.save(newUser).getId();
		if (id != 0) {
			newUser.setId(id);
			return newUser;
		} else {
			throw new UsernameAlreadyExistsException();
		}
	}

	@Override
	public List<Books> viewAvailableBooks() {
		return bookRepo.findByStatusName("Available");
	}

	@Override
	public List<Books> searchBookByTitle(String title) {
		List<Books> books = bookRepo.findAll();
		List<Books> booksWithTitle = new LinkedList<>();
		for (int i=0; i<books.size(); i++) {
			if (books.get(i).getTitle().equals(title)) {
				booksWithTitle.add(books.get(i));
			}
		}
		return booksWithTitle;
	}

	@Override
	// tells Spring Data that the multiple DML statements in this method
		// are part of a single transaction to enforce ACID properties
	@Transactional
	public Users checkoutBook(Users user, Books bookToCheckout) throws AlreadyIssuedException, Exception {
		bookToCheckout = bookRepo.findById(bookToCheckout.getBookId()).get();
		
		if (bookToCheckout.getStatus().equals("Issued")) {
			throw new AlreadyIssuedException();
		} else {
			user = getUserById(user.getId());
			if (user != null) {
				// proceed with checkout
				Status issuedStatus = statusRepo.findByName("Issued");
				bookToCheckout.setStatus(issuedStatus);
				user.getBooks().add(bookToCheckout);
				bookRepo.save(bookToCheckout);
				userRepo.save(user);
			}
			return user;
		}
	}

	@Override
	public Books getBookById(int id) {
		return bookRepo.findById(id).get();
	}

	@Override
	public Users getUserById(int id) {
		Users user = userRepo.findById(id).get();
		user.setFullName(user.getFullName());
		return user;
	}

	



}
