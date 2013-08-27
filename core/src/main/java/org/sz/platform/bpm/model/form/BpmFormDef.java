package org.sz.platform.bpm.model.form;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmFormDef extends BaseModel implements Cloneable {
	public static final String EditInline = "edit";
	public static final String EditForm = "form";
	public static final String EditWindow = "window";
	protected Long formDefId = Long.valueOf(0L);

	protected Long formKey = Long.valueOf(0L);

	protected Long categoryId = Long.valueOf(0L);

	protected String categoryName = "";

	protected String subject = "";

	protected String formDesc = "";

	protected String tabTitle = "";
	protected String html;
	protected String template;
	protected Short isDefault = 0;
	protected Long tableId;
	protected Integer versionNo;
	protected Short isPublished = 0;
	protected String publishedBy;
	protected Date publishTime;
	protected String tableName = "";
	protected Long templateId; // 表单使用的模板ID

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public Long getFormDefId() {
		return this.formDefId;
	}

	public void setFormKey(Long formKey) {
		this.formKey = formKey;
	}

	public Long getFormKey() {
		return this.formKey;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public String getFormDesc() {
		return this.formDesc;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getHtml() {
		return this.html;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public Short getIsDefault() {
		return this.isDefault;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return this.tableId;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getVersionNo() {
		return this.versionNo;
	}

	public void setIsPublished(Short isPublished) {
		this.isPublished = isPublished;
	}

	public Short getIsPublished() {
		return this.isPublished;
	}

	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	public String getPublishedBy() {
		return this.publishedBy;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getPublishTime() {
		return this.publishTime;
	}

	public String getTabTitle() {
		return this.tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormDef)) {
			return false;
		}
		BpmFormDef rhs = (BpmFormDef) object;
		return new EqualsBuilder().append(this.formDefId, rhs.formDefId)
				.append(this.formKey, rhs.formKey)
				.append(this.subject, rhs.subject)
				.append(this.formDesc, rhs.formDesc)
				.append(this.html, rhs.html)
				.append(this.template, rhs.template)
				.append(this.isDefault, rhs.isDefault)
				.append(this.tableId, rhs.tableId)
				.append(this.versionNo, rhs.versionNo)
				.append(this.isPublished, rhs.isPublished)
				.append(this.publishedBy, rhs.publishedBy)
				.append(this.publishTime, rhs.publishTime).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.formDefId).append(this.formKey)
				.append(this.subject).append(this.formDesc).append(this.html)
				.append(this.template).append(this.isDefault)
				.append(this.tableId).append(this.versionNo)
				.append(this.isPublished).append(this.publishedBy)
				.append(this.publishTime).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("formDefId", this.formDefId)
				.append("formKey", this.formKey)
				.append("subject", this.subject)
				.append("formDesc", this.formDesc).append("html", this.html)
				.append("template", this.template)
				.append("isDefault", this.isDefault)
				.append("tableId", this.tableId)
				.append("versionNo", this.versionNo)
				.append("isPublished", this.isPublished)
				.append("publishedBy", this.publishedBy)
				.append("publishTime", this.publishTime).toString();
	}

	public Object clone() {
		BpmFormDef obj = null;
		try {
			obj = (BpmFormDef) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}