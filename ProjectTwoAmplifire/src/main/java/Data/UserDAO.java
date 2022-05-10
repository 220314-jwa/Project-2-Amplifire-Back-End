package Data;

import java.sql.SQLException;

import App.Model.User;

//we can set the generic's type here since we are inheriting it
//i've set it to User so a class that implements this interface will
//have the types as User

public interface UserDAO extends GenericDAO<User>{
	// here we can add any other methods that are not covered by basic CRUD
		// (we inherited the CRUD method from the generic DAO
		public User getByUsername(String username);
		public void updateBooks(int bookId, int userId) throws SQLException;

}
