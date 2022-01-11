package net.awsomerecipes.ws.api.rest.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.awsomerecipes.ws.api.rest.beans.Recipe;
import net.awsomerecipes.ws.api.rest.beans.User;

public interface RecipeDao extends JpaRepository<Recipe, Long> {

	List<Recipe> findByAuthor(User author);

	List<Recipe> findByKeywordsContaining(String keyword);

}
