package com.vivid.common.api.base;

import java.io.Serializable;
import java.util.List;

public class PageInfo<T> implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6408010506030813707L;

	List<T>  list;
	
	int totalPages;
	
	int totalNumbers;
	
	int number;
	
	int page;
	
	int pageSize;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalNumbers() {
		return totalNumbers;
	}

	public void setTotalNumbers(int totalNumbers) {
		this.totalNumbers = totalNumbers;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
