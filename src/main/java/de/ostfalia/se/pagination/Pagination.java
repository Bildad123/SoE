package de.ostfalia.se.pagination;


import jakarta.faces.event.AjaxBehaviorEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Pagination<T> {

	private static final String SUCCESS = "SUCCESS";

	private int maxTblRows = 5;

	private boolean showPageList;

	private int currentRows;

	private int currentPageNumber;

	private int selectedPage = 1;

	private boolean displayWeiterBtn;
	private boolean displayZurueckBtn;

	protected List<T> tblContent = new ArrayList<>();

	private List<Integer> pages = new ArrayList<>();

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public abstract List<T> loadContent();

	public String doRefresh() {
		tblContent.clear();
		tblContent.addAll(loadContent());

		this.showPageList = this.maxTblRows < this.tblContent.size();
		pages.clear();
		int maxPages = (int) Math.ceil((double) (tblContent.size() / maxTblRows));
		for (int i = 1; i <= maxPages; i++) {
			pages.add(i);
		}

		handlePagination();
		return SUCCESS;
	}

	public String weiter() {
		currentRows += maxTblRows;
		if (currentRows > tblContent.size()) {
			currentRows = 0;
		}
		handlePagination();
		return SUCCESS;
	}

	public String zurueck() {
		currentRows -= maxTblRows;
		if (currentRows <= maxTblRows) {
			currentRows = 0;
		}
		handlePagination();
		return SUCCESS;
	}

	private void handlePagination() {
		displayWeiterBtn = (currentRows + maxTblRows) < getTblContent().size();
		displayZurueckBtn = currentRows >= maxTblRows;
		selectedPage = (int) Math.ceil((double) (currentRows / maxTblRows)) + 1;
	}

	public void pageChange(AjaxBehaviorEvent abe) {
		currentRows = (selectedPage - 1) * maxTblRows;
		handlePagination();
	}

	public int getCurrentRows() {
		return currentRows;
	}

	public void setCurrentRows(int currentRows) {
		this.currentRows = currentRows;
	}

	public List<T> getTblContent() {
		return tblContent;
	}

	public void setTblContent(List<T> tblContent) {
		this.tblContent = tblContent;
	}

	public boolean isDisplayWeiterBtn() {
		return displayWeiterBtn;
	}

	public void setDisplayWeiterBtn(boolean displayWeiterBtn) {
		this.displayWeiterBtn = displayWeiterBtn;
	}

	public boolean isDisplayZurueckBtn() {
		return displayZurueckBtn;
	}

	public void setDisplayZurueckBtn(boolean displayZurueckBtn) {
		this.displayZurueckBtn = displayZurueckBtn;
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

	public int getMaxTblRows() {
		return maxTblRows;
	}

	public void setMaxTblRows(int maxTblRows) {
		this.maxTblRows = maxTblRows;
	}

	public boolean isShowPageList() {
		return showPageList;
	}

	public void setShowPageList(boolean showPageList) {
		this.showPageList = showPageList;
	}
}
