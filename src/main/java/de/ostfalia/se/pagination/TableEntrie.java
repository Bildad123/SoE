package de.ostfalia.se.pagination;

public class TableEntrie {

	private long id;
	
	private String text;
	
	public TableEntrie(long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
