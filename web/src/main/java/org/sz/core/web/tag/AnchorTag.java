package org.sz.core.web.tag;

import org.sz.core.web.tag.AnchorTag;

import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
//import org.sz.core.util.ContextUtil;
//import org.sz.platform.model.system.SysRole;
//import org.sz.platform.model.system.SysUser;
//import org.sz.platform.service.system.SecurityUtil;
//import org.sz.platform.service.system.SubSystemUtil;

public class AnchorTag extends BodyTagSupport {
	private Log logger = LogFactory.getLog(AnchorTag.class);
	private String css;
	private String alias;
	private String name;
	private String id;
	private String href;
	private String action;
	private String onclick;

	private boolean canAccess(String function) {
		// TODO
		// Long systemId =
		// SubSystemUtil.getCurrentSystemId((HttpServletRequest)this.pageContext.getRequest());
		// Map functionRole =
		// (Map)SecurityUtil.getFunctionRoleMap().get(systemId);
		// SysUser currentUser = ContextUtil.getCurrentUser();
		//
		// if (currentUser.getAuthorities().contains(SysRole.ROLE_GRANT_SUPER))
		// {
		// return true;
		// }
		//
		// if ((functionRole != null) && (functionRole.containsKey(function))) {
		// Collection configAttributes = (Collection)functionRole.get(function);
		//
		// for (GrantedAuthority hadRole : currentUser.getAuthorities()) {
		// if (configAttributes.contains(new
		// SecurityConfig(hadRole.getAuthority()))) {
		// return true;
		// }
		// }
		// return false;
		// }
		return true;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public int doStartTag() throws JspTagException {
		return 2;
	}

	private String getOutput() throws Exception {
		boolean canAccess = canAccess(this.alias);

		String body = getBodyContent().getString();
		StringBuffer content = new StringBuffer("<a ");
		if (this.id != null) {
			content.append("id=\"" + this.id + "\" ");
		}
		if (this.name != null) {
			content.append("name=\"" + this.name + "\" ");
		}

		if (canAccess) {
			if (this.css != null) {
				content.append(" class=\"" + this.css + "\" ");
			}
			if (this.href != null) {
				content.append(" href=\"" + this.href + "\" ");
			}
			if (this.action != null) {
				content.append(" action=\"" + this.action + "\" ");
			}

		} else if (this.css != null) {
			content.append(" class=\"" + this.css + " disabled\" ");
		} else {
			content.append(" class=\"disabled\" ");
		}

		if (this.onclick != null) {
			content.append("onclick=\"" + this.onclick + "\" ");
		}
		content.append(">");
		content.append(body);
		content.append("</a>");

		return content.toString();
	}

	public int doEndTag() throws JspTagException {
		try {
			String str = getOutput();
			this.pageContext.getOut().print(str);
		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
		return 0;
	}
}
