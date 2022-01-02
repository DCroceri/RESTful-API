package net.awsomerecipes.ws.api.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.awsomerecipes.ws.api.rest.beans.Role;
import net.awsomerecipes.ws.api.rest.facades.RoleFacade;

@RestController
@RequestMapping(path="/roles")
public class RoleController {

	@Autowired
	private RoleFacade roleFacade;

	@PostMapping("/")
	public void add(@RequestBody Role role) {
		roleFacade.saveRole(role);
	}

}
