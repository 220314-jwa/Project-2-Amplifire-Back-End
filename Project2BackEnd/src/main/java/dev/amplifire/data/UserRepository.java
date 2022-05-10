package dev.amplifire.data;

import dev.amplifire.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
//usually stereotypes go over classes, but in this case, it goes over the interface
//because we will not be writing the classes (spring data will)
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    public Users findByUsername(String username);
    // public User findByUsernameAndPassword(String username, String password);
    // public List<User> findByRoleName(String roleName);

}
