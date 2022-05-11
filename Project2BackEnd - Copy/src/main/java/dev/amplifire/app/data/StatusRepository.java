package dev.amplifire.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.amplifire.app.models.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
	public Status findByName(String name);
}
