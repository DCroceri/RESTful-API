package net.awsomerecipes.ws.api.rest.facades.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.awsomerecipes.ws.api.rest.beans.Recipe;
import net.awsomerecipes.ws.api.rest.beans.User;
import net.awsomerecipes.ws.api.rest.daos.RecipeDao;
import net.awsomerecipes.ws.api.rest.facades.RecipeFacade;

@Service
@Transactional
public class RecipeFacadeImpl implements RecipeFacade {

	@Autowired
	private RecipeDao recipeDao;

	@Override
	public List<Recipe> listAllRecipes() {
		return recipeDao.findAll();
	}

	@Override
	public void saveRecipe(Recipe recipe) {
		recipeDao.save(recipe);
	}

	@Override
	public Recipe getRecipe(Long id) {
		return recipeDao.findById(id).get();
	}

	@Override
	public void deleteRecipe(Long id) {
		recipeDao.deleteById(id);
	}

	@Override
	public List<Recipe> findByAuthor(User author) {
		return recipeDao.findByAuthor(author);
	}

	@Override
	public List<Recipe> findByKeyword(List<String> keywords) {
		List<Recipe> result = new ArrayList<Recipe>();
		for (String keyword: keywords) {
			result.addAll(recipeDao.findByKeywordsContaining(keyword));
		}
		return result;
	}
}
