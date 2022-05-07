package Data;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import App.Model.Book;

public class BookDAOTest {
	private static BookDAO bookDao = DAOFactory.getBookDAO();
	private static Book testBook = new Book();
	private static Book testNewBook = new Book();
	
	@BeforeAll
	public static void setUp() {
		// this is the base test pet used for most tests
		testBook.setTitle("test");
		
		// this is the pet to test create and delete
		Random rand = new Random();
		testNewBook.setTitle("test_" + rand.nextLong());
		
		// TODO create pet in DB with name "test"
		// and set the pet's ID to the returned value
		testBook.setBookId(bookDao.create(testBook));
	}
	
	@AfterAll
	public static void cleanUp() throws SQLException {
		// TODO remove pets in DB with name containing "test"
		bookDao.delete(testBook);
	}
	
	@Test
	@Disabled
	public void getByRenterExists() {
		// TODO
	}
	
	@Test
	@Disabled
	public void getByRenterDoesNotExist() {
		// TODO
	}
	
	@Test
	public void getByStatus() {
		String testStatus = "Available";
		List<Book> books = bookDao.getByStatus(testStatus);
		
		boolean onlyCorrectStatus = true;
		for (Book book : books) {
			if (!(book.getStatus().equals(testStatus))) {
				onlyCorrectStatus = false;
				break;
			}
		}
		assertTrue(onlyCorrectStatus);
	}
	
	@Test
	@Order(1)
	public void createBookSuccessfully() {
		int id = bookDao.create(testNewBook);
		testNewBook.setBookId(id);
		
		assertNotEquals(0, id);
	}
	
	@Test
	public void getByIdExists() {
		int id = testBook.getBookId();
		
		Book book = bookDao.getById(id);
		
		assertEquals(testBook, book);
	}
	
	@Test
	public void getByIdDoesNotExist() {
		Book book = bookDao.getById(0);
		assertNull(book);
	}
	
	@Test
	public void getAll() {
		assertNotNull(bookDao.getAll());
	}
	
	@Test
	public void updateBookExists() {
		assertDoesNotThrow(() -> {
			bookDao.update(testBook);
		});
	}
	
	@Test
	public void updateBookDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			bookDao.update(new Book());
		});
	}
	
	@Test
	@Order(2)
	public void deleteBookExists() {
		assertDoesNotThrow(() -> {
			bookDao.delete(testNewBook);
		});
	}
	
	@Test
	public void deleteBookDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			bookDao.delete(new Book());
		});
	}

}
