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

@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private User author;
	@Column(nullable = false)
	private String ingredients;
	private String keywords;
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recipe")
	private List<Comment> coments;


	public Recipe() {}
	public Recipe(Long id, User author, String ingredients, String keywords, List<Comment> coments) {
		this.id = id;
		this.author = author;
		this.ingredients = ingredients;
		this.keywords = keywords;
		this.coments = coments;
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
	public List<Comment> getComents() {
		return coments;
	}
	public void setComents(List<Comment> coments) {
		this.coments = coments;
	}


	@Override
	public String toString() {
		return "Recipe [id=" + id + ", author=" + author + ", ingredients=" + ingredients + ", keywords=" + keywords
				+ ", coments=" + coments + "]";
	}
}
