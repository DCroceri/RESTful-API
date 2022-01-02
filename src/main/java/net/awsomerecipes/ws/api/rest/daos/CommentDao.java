package net.awsomerecipes.ws.api.rest.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.awsomerecipes.ws.api.rest.beans.Comment;
import net.awsomerecipes.ws.api.rest.beans.Recipe;

public interface CommentDao extends JpaRepository<Comment, Long> {

	@Query("select c from Comment c where c.recipe = ?1")
	List<Comment> listByRecipe(Recipe recipe);

}
