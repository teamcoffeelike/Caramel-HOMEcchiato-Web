package com.hanul.coffeelike.caramelweb.data;

public class Page {
	private int totalCount;
	private int currentPage;
	
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getMaximumPage(int elementsInPages) {
		int pages = getTotalCount()/elementsInPages;
		if(getTotalCount()%elementsInPages>0) {
			pages++;
		}
		return pages;
	}
}
