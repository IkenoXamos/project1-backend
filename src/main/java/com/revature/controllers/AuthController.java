package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.LoginDTO;
import com.revature.models.User;
import com.revature.services.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@Valid @RequestBody LoginDTO dto) {
		return ResponseEntity.ok(this.authService.login(dto.getUsername(), dto.getPassword()));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		this.authService.logout();
		return ResponseEntity.ok().build();
	}
}
