package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class SysTemplate extends BaseModel {
	protected Long templateId;
	protected Integer templateType;
	protected String name;
	protected String content;

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Integer getTemplateType() {
		return this.templateType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public boolean equals(Object object) {
		if (!(object instanceof SysTemplate)) {
			return false;
		}
		SysTemplate rhs = (SysTemplate) object;
		return new EqualsBuilder().append(this.templateId, rhs.templateId)
				.append(this.templateType, rhs.templateType)
				.append(this.name, rhs.name).append(this.content, rhs.content)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.templateId).append(this.templateType)
				.append(this.name).append(this.content).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("templateId", this.templateId)
				.append("templateType", this.templateType)
				.append("name", this.name).append("content", this.content)
				.toString();
	}
}
