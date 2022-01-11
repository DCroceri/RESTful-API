package net.awsomerecipes.ws.api.rest.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String commentText;
	@ManyToOne
	@JoinColumn(name="recipe_id")
	@JsonIgnore
	private Recipe recipe;
	@ManyToOne
	private User user;


	public Comment() {}
	public Comment(Long id, String commentText, Recipe recipe, User user) {
		this.id = id;
		this.commentText = commentText;
		this.recipe = recipe;
		this.user = user;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", commentText=" + commentText + ", recipe=" + recipe + ", user=" + user + "]";
	}
}
