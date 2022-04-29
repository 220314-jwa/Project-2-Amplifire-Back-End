package Data;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO <T> {
	// CRUD methods: create, read, update, delete
		public int create(T newObj); // returns the generated ID
		public T getById(int id); // read one
		public List<T> getAll(); // read all
		public void update(T updatedObj) throws SQLException;
		public void delete(T objToDelete) throws SQLException;

}
/*- generics are a way of getting more reusability out of your code.
 * here, i am creating the behaviors for a DAO (data access object) -
 * an object whose job is to interact with a database. however, i might
 * need to interact with different types of data - Pets, Users, etc.
 * 
 * using generics allows me to define those behaviors and then allow
 * the specific type to be decided later. the code will look different
 * depending on what type of objects i'm working with (Pets have different
 * fields than Users) but the behaviors that need to happen are the same,
 * so we can combine abstraction with generics to get the most reusability.
 * 
 * the generic DAO sets up the basic behaviors that we always need to be
 * able to do when interacting with a database (CRUD methods) but leaves
 * the type generic. we will set the type in the more specific DAOs.
 * the "T" is short for Type but it doesn't actually matter what letter you use.
 * sometimes you'll see K for key, V for value, N for number, etc.
 * 
 * the type of a generic (what replaces the T) is set at compile time. you can
 * either set it when instantiating an object or in a subclass
 */
