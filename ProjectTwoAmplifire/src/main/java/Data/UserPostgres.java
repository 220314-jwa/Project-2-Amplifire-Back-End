package Data;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Beans.User;
import Utility.ConnectionFactory;

public class UserPostgres implements UserDAO {
private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();
	
	@Override
	public int create(User newObj) {
		int generatedId = 0;
		Connection conn = connFactory.getConnection();
		try {
			String sql = "insert into users (id, full_name, username, pass_word)"
					+ " values (default,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, newObj.getFullName());
			pStmt.setString(2, newObj.getUsername());
			pStmt.setString(3, newObj.getPassword());
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			pStmt.executeUpdate();
			ResultSet resultSet = pStmt.getGeneratedKeys();
			
			if (resultSet.next()) {
				generatedId = resultSet.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return generatedId;
	}

	@Override
	public User getById(int id) {
		User user = null;
		try (Connection conn = connFactory.getConnection()) {
			String sql = "select * from users left join book_renter on user.id=book_renter.book_id"
					+ " where users.id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setUserId(id);
				String fullName = resultSet.getString("full_name");
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("passwd"));
				
				BookDAO bookDao = DAOFactory.getBookDAO();
				user.setBooks(bookDao.getByRenter(user));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new LinkedList<>();
		try (Connection conn = connFactory.getConnection()) {
			// left join because we want ALL the people even if they don't have any pets.
			// a full join would be fine too since everything in the pet_owner table
			// will have a user associated with it, but a left join makes more sense logically
			String sql = "select * from users left join book_renter on users.id=book_renter.users_id";
			Statement stmt = (Statement) conn.createStatement();
			
			ResultSet resultSet = ((java.sql.Statement) stmt).executeQuery(sql);
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("id"));
				String fullName = resultSet.getString("full_name");
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("pass_word"));
				
				BookDAO bookDao = DAOFactory.getBookDAO();
				user.setBooks(bookDao.getByRenter(user));
				
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void update(User updatedObj) throws SQLException {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "update users set full_name=?, username=?, pass_word=? "
					+ "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, updatedObj.getFullName());
			pStmt.setString(2, updatedObj.getUsername());
			pStmt.setString(3, updatedObj.getPassword());
			pStmt.setInt(4, 1);
			pStmt.setInt(5, updatedObj.getUserId());
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated==1) {
				conn.commit();
			} else {
				conn.rollback();
				throw new SQLException("ERROR: no object found to update");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(User objToDelete) throws SQLException {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "delete from users where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, objToDelete.getUserId());
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated==1) {
				conn.commit();
			} else {
				conn.rollback();
				throw new SQLException("ERROR: no object found to delete");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public User getByUsername(String username) {
		User user = null;
		try (Connection conn = connFactory.getConnection()) {
			String sql = "select * from users left join book_renter on users.id=book_renter.users_id"
					+ " where users.username = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, username);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt("id"));
				String fullName = resultSet.getString("full_name");
				user.setUsername(username);
				user.setPassword(resultSet.getString("pass_word"));
				
				BookDAO bookDao = DAOFactory.getBookDAO();
				user.setBooks(bookDao.getByRenter(user));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public void updateBooks(int bookId, int userId) throws SQLException {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "insert into book_renter (book_id, users_id) values (?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, bookId);
			pStmt.setInt(2, userId);
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated==1) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	

}
