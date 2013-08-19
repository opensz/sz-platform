package org.sz.core.web.query;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.core.page.PageBean;
import org.sz.core.web.util.RequestUtil;

public class QueryFilter {
	private Logger logger = LoggerFactory.getLogger(QueryFilter.class);

	private Map<String, Object> filters = new HashMap();

	private String sortColumns = "";
	private ParamEncoder paramEncoder;
	public static final String ORDER_ASC = "1";
	public static final String ORDER_DESC = "2";
	private String tableId = "";
	private HttpServletRequest request;
	private PageBean pageBean = null;

	public QueryFilter(HttpServletRequest request, String tableId) {
		this(request, tableId, true);
	}

	public QueryFilter(HttpServletRequest request) {
		this(request, true);
	}

	public QueryFilter(HttpServletRequest request, String tableId,
			boolean needPage) {
		this.tableId = tableId;
		this.request = request;

		this.paramEncoder = new ParamEncoder(tableId);
		String tableIdCode = this.paramEncoder.encodeParameterName("");
		try {
			String orderField = request.getParameter(tableIdCode + "s");
			String orderSeqNum = request.getParameter(tableIdCode + "o");
			String orderSeq = "desc";
			if ((orderSeqNum != null) && ("1".equals(orderSeqNum))) {
				orderSeq = "asc";
			}
			Map map = RequestUtil.getQueryMap(request);
			if (orderField != null) {
				map.put("orderField", orderField);
				map.put("orderSeq", orderSeq);
			}
			this.filters = map;
			if (needPage) {
				int page = RequestUtil.getInt(request, tableIdCode + "p", 1);
				int pageSize = RequestUtil.getInt(request, tableIdCode + "z",
						PageBean.DEFAULT_PAGE_SIZE.intValue());
				this.pageBean = new PageBean(page, pageSize);
			}
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}
	}

	public QueryFilter(HttpServletRequest request, boolean needPage) {
		this.request = request;
		try {
			if (needPage) {
				int page = RequestUtil.getInt(request, "page", 1);
				int pageSize = RequestUtil.getInt(request, "pageSize", 15);
				this.pageBean = new PageBean(page, pageSize);
			}
			Map map = RequestUtil.getQueryMap(request);
			this.filters = map;
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public void setForWeb() {
		String pbName = "pageBean";
		String href = "requestURI";
		if (this.tableId != null) {
			pbName = pbName + this.tableId;
			href = href + this.tableId;
		}

		this.request.setAttribute(href, this.request.getRequestURI());
		this.request.setAttribute(pbName, this.pageBean);
	}

	public String encodeParameter(String parameterName) {
		if (this.paramEncoder == null) {
			this.paramEncoder = new ParamEncoder(this.tableId);
		}

		return this.paramEncoder.encodeParameterName(parameterName);
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
}
