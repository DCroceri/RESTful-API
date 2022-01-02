package net.awsomerecipes.ws.api.rest.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.awsomerecipes.ws.api.rest.beans.Recipe;
import net.awsomerecipes.ws.api.rest.beans.User;

public interface RecipeDao extends JpaRepository<Recipe, Long> {

	@Query("select r from Recipe r where r.author = ?1")
	List<Recipe> listByAuthor(User author);

}
