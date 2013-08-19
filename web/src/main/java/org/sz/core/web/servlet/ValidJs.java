package org.sz.core.web.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sz.core.util.StringUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.core.web.util.ValidationUtil;

import freemarker.template.TemplateException;

public class ValidJs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/javascript;charset=utf-8");
		String form = RequestUtil.getString(request, "form");
		Locale local = RequestUtil.getLocal(request);
		String str = "";
		if (StringUtil.isNotEmpty(form)) {
			try {
				str = ValidationUtil.getJs(form, local);
			} catch (TemplateException e) {
				str = "";
			}
		}
		response.getWriter().print(str);
	}
}
