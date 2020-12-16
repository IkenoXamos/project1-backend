package com.revature.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest req;
	
	public User login(String username, String password) {
		User u;
		
		try {
			u = this.userService.findByUsername(username);
		} catch(UserNotFoundException e) {
			throw new InvalidCredentialsException("Invalid Credentials", e);
		}
		
		if(!u.getPassword().equals(password)) {
			throw new InvalidCredentialsException("Invalid Credentials");
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("currentUser", u);
		
		return u;
	}

	public void logout() {
		HttpSession session = req.getSession();
		session.invalidate();
	}
}
