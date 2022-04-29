package Data;

import java.util.List;

import Beans.Book;
import Beans.User;

public interface BookDAO extends GenericDAO<Book> {
	public List<Book> getByStatus(String status);
	public List<Book> getByRenter(User renter);

}
//we can set the generic's type here since we are inheriting it
//i've set it to Pet so a class that implements this interface will
//have the types as Pet
