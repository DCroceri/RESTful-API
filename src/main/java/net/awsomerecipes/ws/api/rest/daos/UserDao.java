package net.awsomerecipes.ws.api.rest.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import net.awsomerecipes.ws.api.rest.beans.User;

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByEmail(String email);

}
