package dev.amplifire.controllers;

import dev.amplifire.exceptions.IncorrectCredentialsException;
import dev.amplifire.models.Users;
import dev.amplifire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/auth")
@CrossOrigin(origins="http://localhost:4200")
public class AuthControllers {

    private UserService userServ;

    @Autowired
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
