package net.awsomerecipes.ws.api.rest.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.awsomerecipes.ws.api.rest.beans.User;

public interface UserDao extends JpaRepository<User, Long> {

	@Query("select u from User u where u.username = ?1")
	User findByUsername(String username);

}
