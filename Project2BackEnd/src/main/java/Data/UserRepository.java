package Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	public Users findByUsername(String username);
	// public User findByUsernameAndPassword(String username, String password);
		// public List<User> findByRoleName(String roleName);

}
