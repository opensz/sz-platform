package org.sz.platform.bpm.model.form;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmFormTemplate extends BaseModel {

	public static final String MainTable = "main";
	public static final String SubTable = "subTable";
	public static final String Macro = "macro";
	public static final String List = "list";
	public static final String Detail = "detail";

	protected Long templateId;
	protected String templateName;
	protected String alias;
	protected String templateType;
	protected String macroTemplateAlias;
	protected String html;
	protected String templateDesc;
	protected int canEdit;

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getCanEdit() {
		return this.canEdit;
	}

	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getMacroTemplateAlias() {
		return this.macroTemplateAlias;
	}

	public void setMacroTemplateAlias(String macroTemplateAlias) {
		this.macroTemplateAlias = macroTemplateAlias;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getHtml() {
		return this.html;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}

	public String getTemplateDesc() {
		return this.templateDesc;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormTemplate)) {
			return false;
		}
		BpmFormTemplate rhs = (BpmFormTemplate) object;
		return new EqualsBuilder().append(this.templateId, rhs.templateId)
				.append(this.templateName, rhs.templateName)
				.append(this.templateType, rhs.templateType)
				.append(this.macroTemplateAlias, rhs.macroTemplateAlias)
				.append(this.html, rhs.html)
				.append(this.templateDesc, rhs.templateDesc).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.templateId).append(this.templateName)
				.append(this.templateType).append(this.macroTemplateAlias)
				.append(this.html).append(this.templateDesc).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("templateId", this.templateId)
				.append("templateName", this.templateName)
				.append("templateType", this.templateType)
				.append("macroTemplateId", this.macroTemplateAlias)
				.append("html", this.html)
				.append("templateDesc", this.templateDesc).toString();
	}
}
