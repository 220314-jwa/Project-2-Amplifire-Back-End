package Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Models.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
	public Status findByName(String name);
}
