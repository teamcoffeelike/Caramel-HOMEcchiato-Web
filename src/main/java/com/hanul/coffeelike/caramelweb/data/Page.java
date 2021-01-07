package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class Page {
	/**
	 * 총 글 건수
	 */
	private int totalCount;
	/**
	 * 현재 페이지번호
	 */
	private int currentPage;
	/**
	 * 검색조건
	 */
	@Nullable private String search;
	/**
	 * 검색키워드
	 */
	@Nullable private String keyword;
	private int elementsInPages;

	public Page(){}
	public Page(int totalCount,
	            int currentPage,
	            @Nullable String search,
	            @Nullable String keyword,
	            int elementsInPages){
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.search = search;
		this.keyword = keyword;
		this.elementsInPages = elementsInPages;
	}

	public int getTotalCount(){
		return totalCount;
	}
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}
	public int getCurrentPage(){
		return currentPage;
	}
	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}
	@Nullable public String getSearch(){
		return search;
	}
	public void setSearch(@Nullable String search){
		this.search = search;
	}
	@Nullable public String getKeyword(){
		return keyword;
	}
	public void setKeyword(@Nullable String keyword){
		this.keyword = keyword;
	}
	public int getElementsInPages(){
		return elementsInPages;
	}
	public void setElementsInPages(int elementsInPages){
		this.elementsInPages = elementsInPages;
	}

	public int getMaximumPage(){
		return getPageOf(getTotalCount());
	}
	
	public int getPageOf(int index) {
		int page = index/elementsInPages;
		if(index%elementsInPages>0){
			page++;
		}
		return page;
	}
	
	public int getStartingPage(){
		return Math.max(1, getCurrentPage()-2);
	}
	public int getEndPage(){
		return Math.min(getMaximumPage(), getStartingPage()+4);
	}
}
