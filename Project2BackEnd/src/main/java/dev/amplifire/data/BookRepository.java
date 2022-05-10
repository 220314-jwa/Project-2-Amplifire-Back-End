package dev.amplifire.data;

import dev.amplifire.models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
    // if you follow a particular pattern with your abstract method names,
    // spring data will implement them for you
    public List<Books> findByStatusName(String name);
}
