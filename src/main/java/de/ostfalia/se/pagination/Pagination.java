package de.ostfalia.se.pagination;


import jakarta.faces.event.AjaxBehaviorEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Class used for Pagination on the JSF pages
 *
 * @param <T>
 */

public abstract class Pagination<T> {
	private static final String SUCCESS = "SUCCESS";
	private int maxTableRows = 10;   //default is 10.
	private boolean showPageList;    //determines if pageList is rendered
	private int currentRows;    //keeps track of current row
	private int currentPageNumber;  //keeps track of current page number
	private int selectedPage = 1;   //first page is shown by default
	private boolean displayNextButton;   //determines if nextButton is disabled
	private boolean displayBackButton;   //determines if backButton is disabled
	protected List<T> tableContent = new ArrayList<>();  //T could be Customer, Product, Order, Stock, Stores, ...
	private List<Integer> pages = new ArrayList<>();     //List for storing all pages


	public abstract List<T> loadContent();


	/**
	 * Updates the pageList
	 * Updates showPageList
	 * Updates the number of pages
	 *
	 * @return 'SUCCESS'
	 */
	public String doRefresh() {
		tableContent.clear();
		tableContent.addAll(loadContent());

		this.showPageList = this.maxTableRows < this.tableContent.size();
		pages.clear();
		int maxPages = (int)Math.ceil(tableContent.size()*1.0 / maxTableRows);
		for (int i = 1; i <= maxPages; i++) {
			pages.add(i);
		}

		handlePagination();
		return SUCCESS;
	}

	/**
	 * Updates the currentRows by setting it maxTableRow times forward
	 *
	 * @return 'SUCCESS'
	 */
	public String next() {
		currentRows += maxTableRows;
		if (currentRows > tableContent.size()) {
			currentRows = 0;
		}
		handlePagination();
		return SUCCESS;
	}

	/**
	 * updates the currentRows by setting it maxTableRow times backward
	 *
	 * @return 'SUCCESS'
	 */
	public String back() {
		currentRows -= maxTableRows;
		if (currentRows <= maxTableRows) {
			currentRows = 0;
		}
		handlePagination();
		return SUCCESS;
	}

	/**
	 * updates the attribute displayNextButton
	 * updates the attribute displayBackButton
	 * updates the selectedPage
	 *
	 */
	private void handlePagination() {
		displayNextButton = (currentRows + maxTableRows) < getTableContent().size();
		displayBackButton = currentRows >= maxTableRows;
		selectedPage = (int) Math.ceil((double) (currentRows / maxTableRows)) + 1;
	}

	public void pageChange(AjaxBehaviorEvent ajaxBehaviorEvent) {
		currentRows = (selectedPage - 1) * maxTableRows;
		handlePagination();
	}


	// Getters and Setters
	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
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
