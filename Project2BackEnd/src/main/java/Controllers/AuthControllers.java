package Controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Exceptions.IncorrectCredentialsException;
import Models.Users;
import Services.UserService;


@RestController
@RequestMapping(path="/auth")
@CrossOrigin(origins="http://localhost:4200")
public class AuthControllers {
	
	private UserService userServ;
	
	public AuthControllers(UserService userServ) {
		this.userServ = userServ;
	}
	@PostMapping
	public ResponseEntity<Users> login(@RequestBody Map<String, String> credentials) {
		String username = credentials.get("username");
		String password = credentials.get("password");
		
		try {
			Users user = userServ.logIn(username, password);
			return ResponseEntity.ok(user);
		} catch (IncorrectCredentialsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	
}
