package dev.amplifire.controllers;

import java.util.Map;

import dev.amplifire.exceptions.UsernameAlreadyExistsException;
import dev.amplifire.models.Users;
import dev.amplifire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//RestController stereotype puts @ResponseBody on top of every method implicitly
//(this means that the methods return resources instead of views)
@RestController
@RequestMapping(path="/users")
@CrossOrigin(origins="http://localhost:4200") // where we're accepting requests from
public class UsersController {


    private static UserService userServ;

    @Autowired
    public UsersController(UserService userServ) {
        this.userServ = userServ;
    }


    // POST to /users
    @PostMapping
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        try {
            user = userServ.register(user);
            return ResponseEntity.ok(user);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping(path = "/{Id}")
    public ResponseEntity<Users> getUserById(@PathVariable int Id) {
        Users user = userServ.getUserById(Id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
