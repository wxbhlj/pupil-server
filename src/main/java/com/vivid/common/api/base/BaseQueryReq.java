package com.vivid.common.api.base;

public class BaseQueryReq {
	protected int page;
	protected int pageSize;
	protected String keyword;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public String toString() {
		return "BaseQueryReq [page=" + page + ", pageSize=" + pageSize + ", keyword=" + keyword + "]";
	}
}
