package net.awsomerecipes.ws.api.rest.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
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
@RequestMapping(path="/api/recipes")
public class RecipeController {

	@Autowired
	private RecipeFacade recipeFacade;
	@Autowired
	private CommentFacade commentFacade;
	@Autowired
	private UserFacade userFacade;

	@GetMapping()
	public List<Recipe> list() {
		return recipeFacade.listAllRecipes();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> get(@PathVariable Long id) {
		try {
			Recipe recipe = recipeFacade.getRecipe(id);
			return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping()
	@PreAuthorize("hasAuthority('chef')")
	public void add(@RequestBody Recipe recipe,
						@CurrentSecurityContext(expression="authentication?.name") String username) {
		recipe.setAuthor(
				userFacade.findByUsername(username));
		recipeFacade.saveRecipe(recipe);
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('chef')")
	public ResponseEntity<?> update(@RequestBody Recipe recipe, @PathVariable Long id,
										@CurrentSecurityContext(expression="authentication?.name") String username) {
		try {
			Recipe existsRecipe = recipeFacade.getRecipe(id);
			User chef = userFacade.findByUsername(username);
			if (existsRecipe.getAuthor().getId().equals(chef.getId())) {
				recipe.setAuthor(chef);
				recipe.setId(id);
				recipeFacade.saveRecipe(recipe);
				return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('chef')")
	public ResponseEntity<?> delete(@PathVariable Long id,
										@CurrentSecurityContext(expression="authentication?.name") String username) {
		try {
			Recipe existsRecipe = recipeFacade.getRecipe(id);
			User chef = userFacade.findByUsername(username);
			if (existsRecipe.getAuthor().getId().equals(chef.getId())) {
				recipeFacade.deleteRecipe(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/author/{userid}")
	public ResponseEntity<?> listByAuthor(@PathVariable Long userid) {
		try {
			User author = userFacade.getUser(userid);
			List<Recipe> list = recipeFacade.findByAuthor(author);
			return new ResponseEntity<List<Recipe>>(list, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/keywords")
	public List<Recipe> searchByKeywords(@RequestBody KeywordList list) {
		list.keywords = list.keywords.stream().map(String::trim).collect(Collectors.toList());
		return recipeFacade.findByKeyword(list.keywords);
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
	@PreAuthorize("hasAuthority('user')")
	public ResponseEntity<?> addComment(@RequestBody Comment comment, @PathVariable Long id,
											@CurrentSecurityContext(expression="authentication?.name") String username) {
		try {
			Recipe existsRecipe = recipeFacade.getRecipe(id);
			comment.setRecipe(existsRecipe);
			comment.setUser(userFacade.findByUsername(username));
			commentFacade.saveComment(comment);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	static class KeywordList {
		public List<String> keywords;
	}
}
