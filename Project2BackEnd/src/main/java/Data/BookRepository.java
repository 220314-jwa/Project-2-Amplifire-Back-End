package Data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Models.Books;

//usually stereotypes go over classes, but in this case, it goes over the interface
//because we will not be writing the classes (spring data will)
@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
	// if you follow a particular pattern with your abstract method names,
		// spring data will implement them for you
		public List<Books> findByStatusName(String statusName);

}
