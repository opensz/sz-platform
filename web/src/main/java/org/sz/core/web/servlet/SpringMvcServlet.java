package org.sz.core.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.sz.core.web.util.ConfigUtil;

public class SpringMvcServlet extends DispatcherServlet {
	private static final long serialVersionUID = 1L;

	protected void noHandlerFound(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String requestURI = request.getRequestURI();
		this.logger.debug("not foud handle mapping for url: " + requestURI);

		// modified by bobo, 20130213
		String jspPath = ConfigUtil.getJspPath(requestURI, true);

		this.logger.debug("requestURI:" + request.getRequestURI()
				+ " and forward to " + jspPath);
		request.getRequestDispatcher(jspPath).forward(request, response);

	}
}
