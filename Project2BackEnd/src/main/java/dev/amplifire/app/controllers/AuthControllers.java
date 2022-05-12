package dev.amplifire.app.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.amplifire.app.exceptions.IncorrectCredentialsException;
import dev.amplifire.app.models.Users;
import dev.amplifire.app.services.UserService;


@RestController // responseBody above all methods 
@RequestMapping(path="/auth") // endpoints 
@CrossOrigin(origins="http://localhost:4200") // specifies where you receive request from
public class AuthControllers {
	
	private UserService userServ;
	
	public AuthControllers(UserService userServ) {
		this.userServ = userServ;
	}
	
	@PostMapping
	// ResponseEntity represents the whole HTTP response: status code, headers, and body
	public ResponseEntity<Users> logIn(@RequestBody Map<String, String> credentials) { 
		String username = credentials.get("username");
		String password = credentials.get("pass_word");
		
		try {
			Users user = userServ.logIn(username, password);
			return ResponseEntity.ok(user);
		} catch (IncorrectCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	
}
