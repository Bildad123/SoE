package de.ostfalia.se.pagination;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class WelcomeBean extends Pagination<TableEntrie> implements Serializable {

	@Override
	public List<TableEntrie> loadContent() {
		List<TableEntrie> entries = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			entries.add(new TableEntrie(i, "Text " + i));
		}
		return entries;
	}
}
