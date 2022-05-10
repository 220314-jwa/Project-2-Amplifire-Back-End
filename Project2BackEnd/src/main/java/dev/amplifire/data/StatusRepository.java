package dev.amplifire.data;

import dev.amplifire.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
    public Status findByName(String name);


}
