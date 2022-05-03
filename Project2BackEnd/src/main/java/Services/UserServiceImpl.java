package Services;

public class UserServiceImpl implements UserService{
	
	private UserDAO userDao;
	private BookDAO bookDao;
	
	@Autowired
	public UserServiceImpl(UserDAO userDao, BookDAO bookDao) {
		this.userDao = userDao;
		this.bookDao = bookDao;
	}

	@Override
	public User register(User newUser) throws UsernameAlreadyExistsException {
		int id = userDao.create(newUser);
		if (id != 0) {
			newUser.setId(id);
			return newUser;
		} else {
			throw new UsernameAlreadyExistsException();
		}
	}

	@Override
	public List<Book> viewAvailableBooks() {
		return bookDao.getByStatus("Available");
	}

	@Override
	public List<Book> searchBookByTitle(String title) {
		List<Book> books = bookDao.getAll();
		List<Book> booksWithTitle = new LinkedList<>();
		for (int i=0; i<books.size(); i++) {
			i
		}
	}

	@Override
	public User checkoutBook(User user, Book bookToCheckout) throws AlreadyIssuedException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getBookById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
