package net.awsomerecipes.ws.api.rest.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import net.awsomerecipes.ws.api.rest.beans.Role;

public interface RoleDao extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
