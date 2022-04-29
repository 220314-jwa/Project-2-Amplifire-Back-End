package Data;

public class DAOFactory {
	// initialize our book dao to be null
    // keep static instances of our DAOs
    // save memory, etc.
    private static BookDAO bookDAO = null;
    private static UserDAO userDAO = null;

    // make our constructor private, so it can't be accessed publicly
    private DAOFactory() { }

    public static BookDAO getBookDAO() {
        // make sure we're not recreating the dao if it already exists:
        if (bookDAO == null) {
            bookDAO = new BookPostgres();
        }
        return bookDAO;
    }
    
    public static UserDAO getUserDAO() {
    	if (userDAO == null)
    		userDAO = new UserPostgres();
    	return userDAO;
    }

}
