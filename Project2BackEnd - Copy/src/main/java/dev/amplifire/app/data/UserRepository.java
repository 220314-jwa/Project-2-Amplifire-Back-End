package dev.amplifire.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.amplifire.app.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	public Users findByUsername(String username);
	//public Users findByUsernameAndPassword(String username, String password);
		// public List<User> findByRoleName(String roleName);

}
