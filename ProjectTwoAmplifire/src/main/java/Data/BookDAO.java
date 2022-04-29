package Data;

public interface BookDAO {
	public List<Pet> getByStatus(String status);
	public List<Pet> getByOwner(User owner);

}
//we can set the generic's type here since we are inheriting it
//i've set it to Pet so a class that implements this interface will
//have the types as Pet
