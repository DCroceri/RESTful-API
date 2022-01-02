package net.awsomerecipes.ws.api.rest.facades;

import java.util.List;

import net.awsomerecipes.ws.api.rest.beans.User;

public interface UserFacade {

	List<User> listAllUsers();

	void saveUser(User user);

	User getUser(Long id);

	void deleteUser(Long id);

	User getUserByUsername(String username);

}
