package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//RestController stereotype puts @ResponseBody on top of every method implicitly
//(this means that the methods return resources instead of views)
@RestController
@RequestMapping(path = "/books") // all endpoints in this class start with /books
@CrossOrigin(origins = "http://localhost:4200") // where we're accepting requests from
public class BooksController {
	private UserService userServ;

	@Autowired
	public BooksController(UserService userServ) {
		this.userServ = userServ;
	}

	// the method for handling each endpoint is more intuitive:
	// the return type is a response entity, and the parameters are
	// whatever you want from the request (path variables,
	// request body, query params, etc)
	// @RequestMapping(path="/pets", method = RequestMethod.GET)
	@GetMapping
	// @ResponseBody // tells Spring to skip the ViewResolver and just return a
	// resource (rather than a view)
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> books = userServ.viewAvailableBooks();
		return ResponseEntity.ok(books);
	}

	@GetMapping(path = "/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
		Book book = userServ.getBookById(bookId);
		if (book != null) {
			return ResponseEntity.ok(book);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(path = "/{petId}/adopt")
	public ResponseEntity<User> adoptPet(@PathVariable int petId, @RequestBody User user) {
		Pet petToAdopt = userServ.getPetById(petId);

		try {
			user = userServ.adoptPet(user, petToAdopt);
			return ResponseEntity.ok(user);
		} catch (AlreadyAdoptedException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
