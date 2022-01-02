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

import net.awsomerecipes.ws.api.rest.beans.Comment;
import net.awsomerecipes.ws.api.rest.beans.Recipe;
import net.awsomerecipes.ws.api.rest.beans.User;
import net.awsomerecipes.ws.api.rest.facades.CommentFacade;
import net.awsomerecipes.ws.api.rest.facades.RecipeFacade;
import net.awsomerecipes.ws.api.rest.facades.UserFacade;

@RestController
@RequestMapping(path="/recipes")
public class RecipeController {

	@Autowired
	private RecipeFacade recipeFacade;
	@Autowired
	private CommentFacade commentFacade;
	@Autowired
	private UserFacade userFacade;

	@GetMapping("/")
	public List<Recipe> list() {
		return recipeFacade.listAllRecipes();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> get(@PathVariable Long id) {
		try {
			Recipe recipe = recipeFacade.getRecipe(id);
			return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/")
	public void add(@RequestBody Recipe recipe) {
		recipeFacade.saveRecipe(recipe);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Recipe recipe, @PathVariable Long id) {
		try {
			@SuppressWarnings("unused")
			Recipe existsRecipe = recipeFacade.getRecipe(id);
			recipe.setId(id);
			recipeFacade.saveRecipe(recipe);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		recipeFacade.deleteRecipe(id);
	}

	@GetMapping("/author/{userid}")
	public ResponseEntity<?> listByAuthor(@PathVariable Long userid) {
		try {
			User author = userFacade.getUser(userid);
			List<Recipe> list = recipeFacade.listByAuthor(author);
			return new ResponseEntity<List<Recipe>>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}/comments")
	public ResponseEntity<?> listComments(@PathVariable Long id) {
		try {
			Recipe existsRecipe = recipeFacade.getRecipe(id);
			List<Comment> list = commentFacade.listByRecipe(existsRecipe);
			return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/{id}/comments")
	public ResponseEntity<?> addComment(@RequestBody Comment comment, @PathVariable Long id) {
		try {
			Recipe existsRecipe = recipeFacade.getRecipe(id);
			comment.setRecipe(existsRecipe);
			commentFacade.saveComment(comment);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
