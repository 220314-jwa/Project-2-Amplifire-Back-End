package dev.amplifire.controllers;

import dev.amplifire.exceptions.AlreadyIssuedException;
import dev.amplifire.models.Books;
import dev.amplifire.models.Users;
import dev.amplifire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Books>> getBooks() {
        List<Books> books = userServ.viewAvailableBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<Books> getBookById(@PathVariable int bookId) {
        Books book = userServ.getBookById(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/{bookId}/checkout")
    public ResponseEntity<Users> checkoutBook(@PathVariable int bookId, @RequestBody Users user) {
        Books bookToCheckout= userServ.getBookById(bookId);
        try {
            user = userServ.checkoutBook(user, bookToCheckout);
            return ResponseEntity.ok(user);
        } catch (AlreadyIssuedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
