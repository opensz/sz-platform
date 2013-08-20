package org.sz.core.query;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryFilter {
	protected Logger logger = LoggerFactory.getLogger(QueryFilter.class);
	
	public static final String ORDER_ASC = "1";
	public static final String ORDER_DESC = "2";

	protected Map<String, Object> filters = new HashMap();
	protected String sortColumns = "";
	protected String tableId = "";
	protected PageBean pageBean = null;
	

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public Map<String, Object> getFilters() {
		return this.filters;
	}

	public void addFilter(String filterName, Object params) {
		this.filters.put(filterName, params);
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public String getSortColumns() {
		return this.sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public void setForWeb(){};
}
