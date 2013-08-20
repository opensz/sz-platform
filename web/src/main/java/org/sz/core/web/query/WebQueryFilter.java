package org.sz.core.web.query;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.web.util.RequestUtil;

public class WebQueryFilter extends QueryFilter {
	private Logger logger = LoggerFactory.getLogger(WebQueryFilter.class);

	private ParamEncoder paramEncoder;
	private HttpServletRequest request;

	public WebQueryFilter(HttpServletRequest request, String tableId) {
		this(request, tableId, true);
	}

	public WebQueryFilter(HttpServletRequest request) {
		this(request, true);
	}

	public WebQueryFilter(HttpServletRequest request, String tableId,
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

	public WebQueryFilter(HttpServletRequest request, boolean needPage) {
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

}
