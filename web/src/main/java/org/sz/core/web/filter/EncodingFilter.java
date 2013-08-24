package org.sz.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * replace org.springframework.web.filter.CharacterEncodingFilter
 *
 */
public class EncodingFilter extends OncePerRequestFilter implements Filter {

	private String encoding = "UTF-8";
	private String contentType = "text/html;charset=UTF-8";
	private boolean forceEncoding = true;

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (this.encoding != null
				&& (this.forceEncoding || request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
				response.setContentType(this.contentType);
			}
		}
		chain.doFilter(request, response);
	}

}
