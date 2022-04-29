package Data;

public class BookPostgres {
	private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();

	@Override
    // this method needs to insert the object into the database:
    // so, we need to connect to the database:
    public int create(Pet newObj) {
		Connection connection = connFactory.getConnection();
		
        // this stores our sql command, that we would normally to DBeaver/command line
        //                             0   1     2        3            4    5
        String sql = "insert into pet (id, name, species, description, age, status_id)" +
                "values (default, ?, ?, ?, ?, ?)";

        try {
            // create a prepared statement, we pass in the sql command
            // also the flag "RETURN_GENERATED_KEYS" so we can get that id that is generated
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // set the fields:
            preparedStatement.setString(1, newObj.getName());
            preparedStatement.setString(2, newObj.getSpecies());
            preparedStatement.setString(3, newObj.getDescription());
            preparedStatement.setInt(4, newObj.getAge());

            // shortcut for now, but we need the corresponding id for the status
            int status_id;
            if (newObj.getStatus().equals("Available")) {
                status_id = 1;
            }
            else {
                status_id = 2;
            }
            preparedStatement.setInt(5, status_id);
            
            connection.setAutoCommit(false); // for tx management (ACID)
            // execute this command, return number of rows affected:
            int count = preparedStatement.executeUpdate();
            // lets us return the id that is auto-generated
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            // if we affected one or more rows:
            if (count > 0) {
                System.out.println("Pet added!");
                // return the generated id:
                // before we call resultSet.next(), it's basically pointing to nothing useful
                // but moving that pointer allows us to get the information that we want
                resultSet.next();
                int id = resultSet.getInt(1);
                newObj.setId(id);
                connection.commit(); // commit the changes to the DB
            }
            // if 0 rows are affected, something went wrong:
            else {
                System.out.println("Something went wrong when trying to add pet!");
                connection.rollback(); // rollback the changes
            }
        } catch (SQLException e){
            // print out what went wrong:
            e.printStackTrace();
            try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
        return newObj.getId();
    }

    @Override
    // take in an id, return the corresponding pet
    public Pet getById(int id) {
        // initialize our pet object to be null:
        Pet pet = null;
        // placeholder for our final sql string
        // ? placeholder for our id:
        // * means all fields
        // but we specify an id so we only get one single pet:
        String sql = "SELECT * FROM pet WHERE id = ?";

        try (Connection connection = connFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // if result set doesn't point to a next value, that means something went wrong
            if(resultSet.next()) {
                pet = parseResultSet(resultSet);
                // now, we've created a pet Java object based on the info from our table
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return pet;
    }

    @Override
    public List<Pet> getAll() {
        // initialize empty Pet List:
        List<Pet> pets = new ArrayList<Pet>();

        String sql = "SELECT * FROM pet";
        try (Connection connection = connFactory.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // get the result from our query:
            ResultSet resultSet = preparedStatement.executeQuery();
            // because the resultSet has multiple pets in it, we don't just want an if-statement. We want a loop:
            while(resultSet.next()) {
                Pet pet = parseResultSet(resultSet);
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    // given a result Set, parse it out and then return the pet:
    private Pet parseResultSet(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        // do something with the return value:
        pet.setId(resultSet.getInt(1));
        pet.setName(resultSet.getString(2));
        pet.setSpecies(resultSet.getString(3));
        pet.setDescription(resultSet.getString(4));
        pet.setAge(resultSet.getInt(5));
        int status_id = resultSet.getInt(6);
        // TODO: get the status from the status db:

        // ternary operator:
        //               check this cond   if true       if false
        String status = (status_id == 1) ? "Available" : "Adopted";
        // exact same thing as this conditional:
//        if (status_id == 1) {
//            status = "Available";
//        }
//        else {
//            status = "Adopted";
//        }
        pet.setStatus(status);
        return pet;
    }

    @Override
    public void update(Pet updatedObj) throws SQLException {
    	Connection connection = connFactory.getConnection();
    	// we create the template for the SQL string:
    	String sql = "update pet set name = ?, species = ?, description = ?, age = ?, status_id = ? where id = ?;";
    	try {
        	PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	// fill in the template:
        	preparedStatement.setString(1,updatedObj.getName());
        	preparedStatement.setString(2,updatedObj.getSpecies());
        	preparedStatement.setString(3, updatedObj.getDescription());
        	preparedStatement.setInt(4,  updatedObj.getAge());
        	// TODO: Check the status database for the real value:
        	int status_id = (updatedObj.getStatus().equals("Available")) ? 1 : 2;
        	preparedStatement.setInt(5, status_id);
        	preparedStatement.setInt(6, updatedObj.getId());
        	
        	connection.setAutoCommit(false);
        	// return a count of how many records were updated
        	int count = preparedStatement.executeUpdate();
        	if(count != 1) {
        		connection.rollback();
        		throw new SQLException("ERROR: no object found to update");
        	} else connection.commit();
        	
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    		try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    		throw e;
    	} finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}

    }

    @Override
    public void delete(Pet objToDelete) throws SQLException {
    	Connection connection = connFactory.getConnection();
    	
    	String sql = "delete from pet where id = ?;";
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		preparedStatement.setInt(1, objToDelete.getId());
    		
    		connection.setAutoCommit(false);
    		int count = preparedStatement.executeUpdate();
    		if (count != 1) {
    			connection.rollback();
    			throw new SQLException("ERROR: no object found to update");
    		} else connection.commit();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    		throw e;
    	} finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }

    @Override
    public List<Pet> getByStatus(String status) {
    	List<Pet> pets = new LinkedList<>();
    	try (Connection conn = connFactory.getConnection()) {
    		String sql = "select * from pet where status_id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		// may need modified later if new statuses are added
    		int statusId = (status.equals("Available")?1:2);
    		pStmt.setInt(1, statusId);
    		
    		ResultSet resultSet = pStmt.executeQuery();
    		while (resultSet.next()) {
    			Pet pet = parseResultSet(resultSet);
    			pets.add(pet);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
        return pets;
    }

    @Override
    public List<Pet> getByOwner(User owner) {
    	List<Pet> pets = new LinkedList<>();
    	try (Connection conn = connFactory.getConnection()) {
    		String sql = "select * from pet join pet_owner on pet.id=pet_owner.pet_id"
    				+ " where pet_owner.owner_id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, owner.getId());
    		
    		ResultSet resultSet = pStmt.executeQuery();
    		while (resultSet.next()) {
    			Pet pet = parseResultSet(resultSet);
    			pets.add(pet);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
        return pets;
    }

}
