package Data;

public class DAOFactory {
	// initialize our pet dao to be null
    // keep static instances of our DAOs
    // save memory, etc.
    private static PetDAO petDAO = null;
    private static UserDAO userDAO = null;

    // make our constructor private, so it can't be accessed publicly
    private DAOFactory() { }

    public static PetDAO getPetDAO() {
        // make sure we're not recreating the dao if it already exists:
        if (petDAO == null) {
            petDAO = new PetPostgres();
        }
        return petDAO;
    }
    
    public static UserDAO getUserDAO() {
    	if (userDAO == null)
    		userDAO = new UserPostgres();
    	return userDAO;
    }

}
