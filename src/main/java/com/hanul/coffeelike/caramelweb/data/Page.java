package com.hanul.coffeelike.caramelweb.data;

public class Page {
	private int totalCount;		//총 글 건수
	private int currentPage;	//현재 페이지번호
	private String search;		//검색조건
	private String keyword;		//검색키워드

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
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getMaximumPage(int elementsInPages) {
		int pages = getTotalCount()/elementsInPages;
		if(getTotalCount()%elementsInPages>0) {
			pages++;
		}
		return pages;
	}
}
