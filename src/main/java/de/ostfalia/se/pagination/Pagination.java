package de.ostfalia.se.pagination;


import jakarta.faces.event.AjaxBehaviorEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Pagination<T> {

	private static final String SUCCESS = "SUCCESS";

	private int maxTableRows = 10;

	private boolean showPageList;

	private int currentRows;

	private int currentPageNumber;

	private int selectedPage = 1;

	private boolean displayNextButton;
	private boolean displayBackButton;

	protected List<T> tableContent = new ArrayList<>();

	private List<Integer> pages = new ArrayList<>();

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public abstract List<T> loadContent();

	public String doRefresh() {
		tableContent.clear();
		tableContent.addAll(loadContent());

		this.showPageList = this.maxTableRows < this.tableContent.size();
		pages.clear();
		int maxPages = (int) Math.ceil((double) (tableContent.size() / maxTableRows));
		for (int i = 1; i <= maxPages; i++) {
			pages.add(i);
		}

		handlePagination();
		return SUCCESS;
	}

	public String next() {
		currentRows += maxTableRows;
		if (currentRows > tableContent.size()) {
			currentRows = 0;
		}
		handlePagination();
		return SUCCESS;
	}

	public String back() {
		currentRows -= maxTableRows;
		if (currentRows <= maxTableRows) {
			currentRows = 0;
		}
		handlePagination();
		return SUCCESS;
	}

	private void handlePagination() {
		displayNextButton = (currentRows + maxTableRows) < getTableContent().size();
		displayBackButton = currentRows >= maxTableRows;
		selectedPage = (int) Math.ceil((double) (currentRows / maxTableRows)) + 1;
	}

	public void pageChange(AjaxBehaviorEvent abe) {
		currentRows = (selectedPage - 1) * maxTableRows;
		handlePagination();
	}

	public int getCurrentRows() {
		return currentRows;
	}

	public void setCurrentRows(int currentRows) {
		this.currentRows = currentRows;
	}

	public List<T> getTableContent() {
		return tableContent;
	}

	public void setTableContent(List<T> tableContent) {
		this.tableContent = tableContent;
	}

	public boolean isDisplayNextButton() {
		return displayNextButton;
	}

	public void setDisplayNextButton(boolean displayNextButton) {
		this.displayNextButton = displayNextButton;
	}

	public boolean isDisplayBackButton() {
		return displayBackButton;
	}

	public void setDisplayBackButton(boolean displayBackButton) {
		this.displayBackButton = displayBackButton;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getSelectedPage() {
		return selectedPage;
	}

	public void setSelectedPage(int selectedPage) {
		this.selectedPage = selectedPage;
	}

	public int getMaxTableRows() {
		return maxTableRows;
	}

	public void setMaxTableRows(int maxTableRows) {
		this.maxTableRows = maxTableRows;
	}

	public boolean isShowPageList() {
		return showPageList;
	}

	public void setShowPageList(boolean showPageList) {
		this.showPageList = showPageList;
	}
}
