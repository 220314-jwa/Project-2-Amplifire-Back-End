package Data;

public class UserPostgres implements UserDAO {
private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();
	
	@Override
	public int create(User newObj) {
		int generatedId = 0;
		Connection conn = connFactory.getConnection();
		try {
			String sql = "insert into person (full_name, username, passwd, role_id)"
					+ " values (?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, newObj.getFirstName() + " " + newObj.getLastName());
			pStmt.setString(2, newObj.getUsername());
			pStmt.setString(3, newObj.getPassword());
			pStmt.setInt(4, 1);
			
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
			String sql = "select * from person left join pet_owner on person.id=pet_owner.owner_id"
					+ " where person.id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(id);
				String fullName = resultSet.getString("full_name");
				user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("passwd"));
				
				PetDAO petDao = DAOFactory.getPetDAO();
				user.setPets(petDao.getByOwner(user));
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
			String sql = "select * from person left join pet_owner on person.id=pet_owner.owner_id";
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				String fullName = resultSet.getString("full_name");
				user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("passwd"));
				
				PetDAO petDao = DAOFactory.getPetDAO();
				user.setPets(petDao.getByOwner(user));
				
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
			String sql = "update person set full_name=?, username=?, passwd=?, role_id=? "
					+ "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, updatedObj.getFirstName() + " " + updatedObj.getLastName());
			pStmt.setString(2, updatedObj.getUsername());
			pStmt.setString(3, updatedObj.getPassword());
			pStmt.setInt(4, 1);
			pStmt.setInt(5, updatedObj.getId());
			
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
			String sql = "delete from person where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, objToDelete.getId());
			
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
			String sql = "select * from person left join pet_owner on person.id=pet_owner.owner_id"
					+ " where person.username = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, username);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				String fullName = resultSet.getString("full_name");
				user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(username);
				user.setPassword(resultSet.getString("passwd"));
				
				PetDAO petDao = DAOFactory.getPetDAO();
				user.setPets(petDao.getByOwner(user));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public void updatePets(int petId, int userId) throws SQLException {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "insert into pet_owner (pet_id, owner_id) values (?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, petId);
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
