package com.revature.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok(this.userService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") int id) {
		return ResponseEntity.ok(this.userService.findById(id));
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
		return ResponseEntity.ok(this.userService.findByUsername(username));
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
		return ResponseEntity.ok(this.userService.findByEmail(email));
	}

	
	@PostMapping
	@Authorized(allowedRoles = UserRole.Admin)
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		this.userService.insert(user);

		return ResponseEntity.created(URI.create(String.format("/users/%d", user.getId()))).body(user);
	}

	@PutMapping
	@Authorized(allowedRoles = { UserRole.Admin, UserRole.Employee })
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok(this.userService.update(user));
	}
}
