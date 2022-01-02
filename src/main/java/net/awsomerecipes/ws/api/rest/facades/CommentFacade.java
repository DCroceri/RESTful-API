package net.awsomerecipes.ws.api.rest.facades;

import java.util.List;

import net.awsomerecipes.ws.api.rest.beans.Comment;
import net.awsomerecipes.ws.api.rest.beans.Recipe;

public interface CommentFacade {

	void saveComment(Comment comment);

	List<Comment> listByRecipe(Recipe recipe);

}
