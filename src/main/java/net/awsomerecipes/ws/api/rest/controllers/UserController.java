package net.awsomerecipes.ws.api.rest.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.awsomerecipes.ws.api.rest.beans.User;
import net.awsomerecipes.ws.api.rest.facades.UserFacade;

@RestController
@RequestMapping(path="/users")
public class UserController {

	@Autowired
	private UserFacade userFacade;

	@GetMapping("/")
	public List<User> list() {
		return userFacade.listAllUsers();
	}
	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable Long id) {
		try {
			User user = userFacade.getUser(id);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/")
	public void add(@RequestBody User user) {
		userFacade.saveUser(user);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
		try {
			@SuppressWarnings("unused")
			User existsUser = userFacade.getUser(id);
			user.setId(id);
			userFacade.saveUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userFacade.deleteUser(id);
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<User> getByUsername(@PathVariable String username) {
		User user = userFacade.getUserByUsername(username);
		if(user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
