package net.awsomerecipes.ws.api.rest.beans;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User author;
	@Column(nullable = false)
	private String ingredients;
	private String keywords;
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recipe")
	private List<Comment> comments;


	public Recipe() {}
	public Recipe(Long id, User author, String ingredients, String keywords, List<Comment> comments) {
		this.id = id;
		this.author = author;
		this.ingredients = ingredients;
		this.keywords = keywords;
		this.comments = comments;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public List<String> getKeywords() {
		return Stream.of(keywords.split(",")).map(String::trim).collect(Collectors.toList());
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = String.join(",", keywords);
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	@Override
	public String toString() {
		return "Recipe [id=" + id + ", author=" + author + ", ingredients=" + ingredients + ", keywords=" + keywords
				+ ", comments=" + comments + "]";
	}
}
