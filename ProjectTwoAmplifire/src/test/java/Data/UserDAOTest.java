package Data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import Beans.Book;
import Beans.User;

public class UserDAOTest {
	private static UserDAO userDao = DAOFactory.getUserDAO();
	private static User testUser = new User();
	private static User testNewUser = new User();
	
	@BeforeAll
	public static void setUp() {
		// this is the base test user used for most tests
		testUser.setUsername("test");
		
		// this is the user to test create and delete
		Random rand = new Random();
		testNewUser.setUsername("test_" + rand.nextLong());
		
		// TODO create user in DB with username "test"
		// and set the user's ID to the returned value
		testUser.setUserId(userDao.create(testUser));
	}
	
	@AfterAll
	public static void cleanUp() {
		try {
			userDao.delete(testUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByUsernameExists() {
		User user = userDao.getByUsername("test");
		assertEquals(testUser, user);
	}
	
	@Test
	public void getByUsernameDoesNotExist() {
		User user = userDao.getByUsername("qwertyuiop");
		assertNull(user);
	}
	
	@Test
	@Order(1)
	public void createUserSuccessfully() {
		int id = userDao.create(testNewUser);
		testNewUser.setUserId(id);
		
		assertNotEquals(0, id);
	}
	
	@Test
	public void createUserDuplicateUsername() {
		int id = userDao.create(testUser);
		
		assertEquals(0, id);
	}
	
	@Test
	public void getByIdExists() {
		int id = testUser.getUserId();
		
		User user = userDao.getById(id);
		
		assertEquals(testUser, user);
	}
	
	@Test
	public void getByIdDoesNotExist() {
		User user = userDao.getById(0);
		assertNull(user);
	}
	
	@Test
	public void getAll() {
		assertNotNull(userDao.getAll());
	}
	
	@Test
	public void updateUserExists() {
		assertDoesNotThrow(() -> {
			userDao.update(testUser);
		});
	}
	
	@Test
	public void updateUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			userDao.update(new User());
		});
	}
	
	@Test
	@Order(2)
	public void deleteUserExists() {
		assertDoesNotThrow(() -> {
			userDao.delete(testNewUser);
		});
	}
	
	@Test
	public void deleteUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			userDao.delete(new User());
		});
	}


}
