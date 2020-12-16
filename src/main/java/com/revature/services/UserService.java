package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.repositories.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public User findByUsername(String username) {
		return userDAO.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("No match found for username"));
	}

	public User findByEmail(String email) {
		return userDAO.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No match found for email"));
	}

	public List<User> findByRole(UserRole role) {
		return userDAO.findByRole(role);
	}

	public List<User> findAll() {
		return userDAO.findAll();
	}

	public User findById(int id) {
		return userDAO.findById(id).orElseThrow(() -> new UserNotFoundException("No match found for id"));
	}

	public User insert(User user) {
		return userDAO.save(user);
	}

	public User update(User user) {
		return userDAO.save(user);
	}
}
