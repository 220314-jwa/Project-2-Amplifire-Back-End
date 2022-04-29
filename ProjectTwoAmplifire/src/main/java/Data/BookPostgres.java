package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Beans.Book;
import Beans.User;
import Utility.ConnectionFactory;

public class BookPostgres implements BookDAO {
	private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();

	@Override
    // this method needs to insert the object into the database:
    // so, we need to connect to the database:
    public int create(Book newObj) {
		Connection connection = connFactory.getConnection();
		
        // this stores our sql command, that we would normally to DBeaver/command line
        //                             0   1     2        3            4    5
        String sql = "insert into book_collection (id, title, genre, description, return_date, issued_date, status)" +
                "values (default, ?, ?, ?, ?, ?, ?)";

        try {
            // create a prepared statement, we pass in the sql command
            // also the flag "RETURN_GENERATED_KEYS" so we can get that id that is generated
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // set the fields:
            preparedStatement.setString(1, newObj.getTitle());
            preparedStatement.setString(2, newObj.getGenre());
            preparedStatement.setString(3, newObj.getDescription());

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
                System.out.println("Book added!");
                // return the generated id:
                // before we call resultSet.next(), it's basically pointing to nothing useful
                // but moving that pointer allows us to get the information that we want
                resultSet.next();
                int id = resultSet.getInt(1);
                newObj.setBookId(id);
                connection.commit(); // commit the changes to the DB
            }
            // if 0 rows are affected, something went wrong:
            else {
                System.out.println("Something went wrong when trying to add book!");
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
        
        return newObj.getBookId();
    }

    @Override
    // take in an id, return the corresponding pet
    public Book getById(int id) {
        // initialize our pet object to be null:
        Book book = null;
        // placeholder for our final sql string
        // ? placeholder for our id:
        // * means all fields
        // but we specify an id so we only get one single pet:
        String sql = "SELECT * FROM book_collection WHERE id = ?";

        try (Connection connection = connFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // if result set doesn't point to a next value, that means something went wrong
            if(resultSet.next()) {
                book = parseResultSet(resultSet);
                // now, we've created a pet Java object based on the info from our table
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public List<Book> getAll() {
        // initialize empty Pet List:
        List<Book> books = new ArrayList<Book>();

        String sql = "SELECT * FROM book_collection";
        try (Connection connection = connFactory.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // get the result from our query:
            ResultSet resultSet = preparedStatement.executeQuery();
            // because the resultSet has multiple pets in it, we don't just want an if-statement. We want a loop:
            while(resultSet.next()) {
                Book book = parseResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // given a result Set, parse it out and then return the pet:
    private Book parseResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        // do something with the return value:
        book.setBookId(resultSet.getInt(1));
        book.setTitle(resultSet.getString(2));
        book.setGenre(resultSet.getString(3));
        book.setDescription(resultSet.getString(4));
        int status_id = resultSet.getInt(6);
        // TODO: get the status from the status db:

        // ternary operator:
        //               check this cond   if true       if false
        String status = (status_id == 1) ? "Available" : "Issued";
        // exact same thing as this conditional:
//        if (status_id == 1) {
//            status = "Available";
//        }
//        else {
//            status = "Adopted";
//        }
        book.setStatus(status);
        return book;
    }

    @Override
    public void update(Book updatedObj) throws SQLException {
    	Connection connection = connFactory.getConnection();
    	// we create the template for the SQL string:
    	String sql = "update book_collection set title = ?, genre = ?, description = ? where id = ?;";
    	try {
        	PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	// fill in the template:
        	preparedStatement.setString(1,updatedObj.getTitle());
        	preparedStatement.setString(2,updatedObj.getGenre());
        	preparedStatement.setString(3, updatedObj.getDescription());
        	// TODO: Check the status database for the real value:
        	int status_id = (updatedObj.getStatus().equals("Available")) ? 1 : 2;
        	preparedStatement.setInt(5, status_id);
        	preparedStatement.setInt(6, updatedObj.getBookId());
        	
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
    public void delete(Book objToDelete) throws SQLException {
    	Connection connection = connFactory.getConnection();
    	
    	String sql = "delete from book_collection where id = ?;";
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		preparedStatement.setInt(1, objToDelete.getBookId());
    		
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
    public List<Book> getByStatus(String status) {
    	List<Book> books = new LinkedList<>();
    	try (Connection conn = connFactory.getConnection()) {
    		String sql = "select * from book_collection where status_id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		// may need modified later if new statuses are added
    		int statusId = (status.equals("Available")?1:2);
    		pStmt.setInt(1, statusId);
    		
    		ResultSet resultSet = pStmt.executeQuery();
    		while (resultSet.next()) {
    			Book book = parseResultSet(resultSet);
    			books.add(book);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
        return books;
    }

    @Override
    public List<Book> getByRenter(User renter) {
    	List<Book> books = new LinkedList<>();
    	try (Connection conn = connFactory.getConnection()) {
    		String sql = "select * from book_collection join book_renter on book_collection.id=book_renter.book_id"
    				+ " where book_renter.user_id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, renter.getUserId());
    		
    		ResultSet resultSet = pStmt.executeQuery();
    		while (resultSet.next()) {
    			Book book = parseResultSet(resultSet);
    			books.add(book);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
        return books;
    }

	

	



}
