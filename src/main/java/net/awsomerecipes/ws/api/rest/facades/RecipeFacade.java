package net.awsomerecipes.ws.api.rest.facades;

import java.util.List;

import net.awsomerecipes.ws.api.rest.beans.Recipe;
import net.awsomerecipes.ws.api.rest.beans.User;

public interface RecipeFacade {

	List<Recipe> listAllRecipes();

	void saveRecipe(Recipe recipe);

	Recipe getRecipe(Long id);

	void deleteRecipe(Long id);

	List<Recipe> findByAuthor(User author);

	List<Recipe> findByKeyword(List<String> keywords);

}
