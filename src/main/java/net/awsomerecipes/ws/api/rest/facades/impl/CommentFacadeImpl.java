package net.awsomerecipes.ws.api.rest.facades.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.awsomerecipes.ws.api.rest.beans.Comment;
import net.awsomerecipes.ws.api.rest.beans.Recipe;
import net.awsomerecipes.ws.api.rest.daos.CommentDao;
import net.awsomerecipes.ws.api.rest.facades.CommentFacade;

@Service
@Transactional
public class CommentFacadeImpl implements CommentFacade {

	@Autowired
	private CommentDao commentDao;

	@Override
	public void saveComment(Comment comment) {
		commentDao.save(comment);
	}

	@Override
	public List<Comment> listByRecipe(Recipe recipe) {
		return commentDao.listByRecipe(recipe);
	}

}
