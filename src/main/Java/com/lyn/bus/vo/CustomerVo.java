package com.lyn.bus.vo;


import com.lyn.bus.domain.Customer;

public class CustomerVo extends Customer {

	/**
	 * 分页参数
	 */
	private Integer page;
	private Integer limit;

	// 接收多个id
	private String[] ids;

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
