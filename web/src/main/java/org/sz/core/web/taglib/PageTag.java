package org.sz.core.web.taglib;

<<<<<<< HEAD
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

=======
import org.sz.core.web.taglib.PageTag;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
>>>>>>> 8726cb0028c02e99622ed0e79e5568178788cf9f
import org.apache.commons.collections.map.HashedMap;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.core.engine.FreemarkEngine;
<<<<<<< HEAD
import org.sz.core.query.PageBean;
=======
import org.sz.core.page.PageBean;
>>>>>>> 8726cb0028c02e99622ed0e79e5568178788cf9f
import org.sz.core.util.SpringContextHolder;

public class PageTag extends TagSupport {
	private static Logger logger = LoggerFactory.getLogger(PageTag.class);
	private String tableId;
	private boolean showExplain = true;

	private boolean showPageSize = true;

	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public boolean isShowExplain() {
		return this.showExplain;
	}

	public void setShowExplain(boolean showExplain) {
		this.showExplain = showExplain;
	}

	public boolean isShowPageSize() {
		return this.showPageSize;
	}

	public void setShowPageSize(boolean showPageSize) {
		this.showPageSize = showPageSize;
	}

	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		try {
			FreemarkEngine freemarkEngine = (FreemarkEngine) SpringContextHolder
					.getBean("freemarkEngine");
			Map model = new HashedMap();
			PageBean pb = null;
			logger.debug("table id:" + this.tableId);

			String url = null;

			if (this.tableId != null) {
				pb = (PageBean) request.getAttribute("pageBean" + this.tableId);
				url = (String) request
						.getAttribute("requestURI" + this.tableId);
				ParamEncoder paramEncoder = new ParamEncoder(this.tableId);
				model.put("tableIdCode", paramEncoder.encodeParameterName(""));
			} else {
				pb = (PageBean) request.getAttribute("pageBean");
				url = url + request.getRequestURI();
				model.put("tableIdCode", "");
			}
			if (pb == null) {
				url = request.getRequestURI();
				pb = new PageBean();

			}
			if (pb == null) {
				// pb=new PageBean();
				throw new RuntimeException("pagingBean can't no be null");
			}
			model.put("pageBean", pb);

			String params = getQueryParameters(request);
			if (!url.equals("") && url.indexOf("?") > 0) {
				if (!"".equals(params))
					url = url + "&" + params;
				else
					url = url + "?" + params;
			} else if (!"".equals(params)) {
				url = url + "?" + params;
			}
			logger.info("current url:" + url);
			model.put("showExplain", Boolean.valueOf(this.showExplain));
			model.put("showPageSize", Boolean.valueOf(this.showPageSize));
			model.put("baseHref", url);
			String html = freemarkEngine.mergeTemplateIntoString("page.ftl",
					model);
			out.println(html);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	private String getQueryParameters(HttpServletRequest request) {
		Enumeration names = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while (names.hasMoreElements()) {
			if (i++ > 0) {
				sb.append("&");
			}
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			sb.append(name).append("=").append(value);
		}
		return sb.toString();
	}
}
