package org.sz.platform.oa.model.desk;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class DesktopColumn extends BaseModel {
	protected Long id;
	protected String name;
	protected String serviceMethod;
	protected String templateName;
	protected String templateId;
	protected String templatePath;
	protected String columnUrl;
	protected Integer isSys;
	protected String html;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public String getServiceMethod() {
		return this.serviceMethod;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTemplatePath() {
		return this.templatePath;
	}

	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}

	public String getColumnUrl() {
		return this.columnUrl;
	}

	public Integer getIsSys() {
		return this.isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public boolean equals(Object object) {
		if (!(object instanceof DesktopColumn)) {
			return false;
		}
		DesktopColumn rhs = (DesktopColumn) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.serviceMethod, rhs.serviceMethod)
				.append(this.templateName, rhs.templateName)
				.append(this.templateId, rhs.templateId)
				.append(this.templatePath, rhs.templatePath)
				.append(this.columnUrl, rhs.columnUrl)
				.append(this.isSys, rhs.isSys).append(this.html, rhs.html)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.name).append(this.serviceMethod)
				.append(this.templateName).append(this.templateId)
				.append(this.templatePath).append(this.columnUrl)
				.append(this.isSys).append(this.html).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name)
				.append("serviceMethod", this.serviceMethod)
				.append("templateName", this.templateName)
				.append("templateId", this.templateId)
				.append("templatePath", this.templatePath)
				.append("columnUrl", this.columnUrl)
				.append("isSys", this.isSys).append("html", this.html)
				.toString();
	}
}
