package Controllers;

import java.util.Map;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;
import Models.Users;
import Services.UserService;

//RestController stereotype puts @ResponseBody on top of every method implicitly
//(this means that the methods return resources instead of views)
@RestController
@CrossOrigin(origins="http://localhost:4200") // where we're accepting requests from

public class UsersController {

	private static UserService userServ;
	
	@Autowired
	@GetMapping(path = "/login")
	public ResponseEntity<Users> login(@PathVariable String username, String password) {
		
		try {
			Users user = userServ.logIn(username, password);
			return ResponseEntity.ok(user);
		} catch (IncorrectCredentialsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
		
	// POST to /users
	@PostMapping(path = "/{auth}")
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
	@GetMapping(path = "/{userId}")
	public ResponseEntity<Users> getUserById(@PathVariable int UserId) {
		Users user = userServ.getUserById(UserId);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
