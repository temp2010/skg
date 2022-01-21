package co.skg.test.model;

public class User {

	private Integer document;
	private String name;

	public User() {
	}

	public User(Integer document, String name) {
		this.document = document;
		this.name = name;
	}

	public Integer getDocument() {
		return document;
	}

	public void setDocument(Integer document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}