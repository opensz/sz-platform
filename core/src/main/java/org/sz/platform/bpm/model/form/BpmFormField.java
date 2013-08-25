package org.sz.platform.bpm.model.form;

import org.sz.platform.bpm.model.form.BpmFormField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormField extends BaseModel implements Cloneable {
	public static short VALUE_FROM_FORM = 0;
	public static short VALUE_FROM_SCRIPT_SHOW = 1;
	public static short VALUE_FROM_SCRIPT_HIDDEN = 2;
	public static short VALUE_FROM_IDENTITY = 3;
	public static final String FieldHidden = "Id";
	public static final int Selector_Employ = 4;
	public static final int Selector_EmployMulti = 8;
	public static final int Selector_Role = 5;
	public static final int Selector_Org = 6;
	public static final int Selector_Position = 7;
	public static String elmName = "field";

	public String eventClick = "";// 事件方式
	public String callFunction = "";// 调用方法
	protected Long fieldId;
	protected Long tableId;

	@XmlAttribute
	protected String fieldName = "";

	@XmlAttribute
	protected String fieldType = "";

	@XmlAttribute
	protected Short isRequired;

	@XmlAttribute
	protected Short isList;

	@XmlAttribute
	protected Short isQuery;

	@XmlAttribute
	protected String fieldDesc = "";

	@XmlAttribute
	protected Integer charLen;

	@XmlAttribute
	protected Integer intLen;

	@XmlAttribute
	protected Integer decimalLen;
	protected String dictType = "";

	@XmlAttribute
	protected Short isDeleted = Short.valueOf((short) 0);

	@XmlAttribute
	protected String validRule = "";

	@XmlAttribute
	protected String originalName = "";
	protected Integer sn;

	@XmlAttribute
	protected Short valueFrom = Short.valueOf((short) 0);

	@XmlAttribute
	protected String script = "";

	@XmlAttribute
	protected Short controlType;

	@XmlAttribute
	protected Short isHidden = Short.valueOf((short) 0);

	@XmlAttribute
	protected Short isFlowVar = Short.valueOf((short) 0);

	protected String identity = "";

	@XmlAttribute
	protected String options = "";

	@XmlAttribute
	protected String ctlProperty = "";

	protected boolean isAdded = false;

	protected Integer isPk = Integer.valueOf(0);

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Long getFieldId() {
		return this.fieldId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return this.tableId;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setIsRequired(Short isRequired) {
		this.isRequired = isRequired;
	}

	public Short getIsRequired() {
		return this.isRequired;
	}

	public void setIsList(Short isList) {
		this.isList = isList;
	}

	public Short getIsList() {
		return this.isList;
	}

	public void setIsQuery(Short isQuery) {
		this.isQuery = isQuery;
	}

	public Short getIsQuery() {
		return this.isQuery;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getFieldDesc() {
		return this.fieldDesc;
	}

	public void setCharLen(Integer charLen) {
		this.charLen = charLen;
	}

	public Integer getCharLen() {
		return this.charLen;
	}

	public void setIntLen(Integer intLen) {
		this.intLen = intLen;
	}

	public Integer getIntLen() {
		return this.intLen;
	}

	public void setDecimalLen(Integer decimalLen) {
		this.decimalLen = decimalLen;
	}

	public Integer getDecimalLen() {
		return this.decimalLen;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictType() {
		return this.dictType;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Short getIsDeleted() {
		return this.isDeleted;
	}

	public void setValidRule(String validRule) {
		this.validRule = validRule;
	}

	public String getValidRule() {
		return this.validRule;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOriginalName() {
		return this.originalName;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setValueFrom(Short valueFrom) {
		this.valueFrom = valueFrom;
	}

	public Short getValueFrom() {
		return this.valueFrom;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getScript() {
		return this.script;
	}

	public void setControlType(Short controlType) {
		this.controlType = controlType;
	}

	public Short getControlType() {
		return this.controlType;
	}

	public Short getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(Short isHidden) {
		this.isHidden = isHidden;
	}

	public Short getIsFlowVar() {
		return this.isFlowVar;
	}

	public void setIsFlowVar(Short isFlowVar) {
		this.isFlowVar = isFlowVar;
	}

	public String getIdentity() {
		return this.identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getIsPk() {
		return this.isPk;
	}

	public void setIsPk(Integer isPk) {
		this.isPk = isPk;
	}

	public String getOptions() {
		return this.options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String[] getAryOptions() {
		return this.options.split("\n");
	}

	public boolean isAdded() {
		return this.isAdded;
	}

	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	public String getCtlProperty() {
		return this.ctlProperty;
	}

	public void setCtlProperty(String ctlProperty) {
		this.ctlProperty = ctlProperty;
	}

	public String getEventClick() {
		return eventClick;
	}

	public void setEventClick(String eventClick) {
		this.eventClick = eventClick;
	}

	public String getCallFunction() {
		return callFunction;
	}

	public void setCallFunction(String callFunction) {
		this.callFunction = callFunction;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormField)) {
			return false;
		}
		BpmFormField rhs = (BpmFormField) object;
		return new EqualsBuilder().append(this.fieldName, rhs.fieldName)
				.append(this.fieldName, rhs.fieldName)
				.append(this.fieldDesc, rhs.fieldDesc)
				.append(this.fieldType, rhs.fieldType)
				.append(this.charLen, rhs.charLen)
				.append(this.intLen, rhs.intLen)
				.append(this.decimalLen, rhs.decimalLen).isEquals();
	}

	public Object clone() {
		BpmFormField obj = null;
		try {
			obj = (BpmFormField) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.tableId)
				.append(this.fieldName).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("fieldName", this.fieldName)
				.toString();
	}

	//是否选择控件
	public Boolean isSelectControl() {
		if ((this.getControlType().shortValue() == 16)
				|| (this.getControlType().shortValue() == 4)
				|| (this.getControlType().shortValue() == 8)
				|| (this.getControlType().shortValue() == 6)
				|| (this.getControlType().shortValue() == 7)
				|| (this.getControlType().shortValue() == 5)
				|| (this.getControlType().shortValue() == 17)	//项目
				|| (this.getControlType().shortValue() == 18)	//合同
				|| (this.getControlType().shortValue() == 19)	//交换机
				|| (this.getControlType().shortValue() == 20)	//机房
				|| (this.getControlType().shortValue() == 21)	//光电收发
				|| (this.getControlType().shortValue() == 22)	//VLAN
				|| (this.getControlType().shortValue() == 23)	//供应商 
				|| (this.getControlType().shortValue() == 24)	//链路 
		) {
			return true;

		}
		return false;
	}
}