package org.sz.platform.bpm.model.form;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.dom4j.Document;
import org.dom4j.Element;

import org.sz.core.customertable.TableModel;
import org.sz.core.model.BaseModel;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.StringUtil;

@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormTable extends BaseModel {
	
	public static String parElmName = "table";
	public static String subElmName = "subTable";
	
	protected Long tableId;

	@XmlAttribute
	protected String tableName = "";

	@XmlAttribute
	protected String tableDesc = "";

	@XmlAttribute
	protected Short isMain = 1;
	
	@XmlAttribute
	protected String businessType; // 业务类型， CatType=BUSINESS_TYPE
	
	protected Long mainTableId;

	@XmlAttribute
	protected Short isPublished = 0;

	protected String publishedBy = "";
	protected Date publishTime;
	protected int isExternal = 0;

	protected String dsAlias = "";

	protected String dsName = "";

	protected String relation = "";

	protected Short keyType = 0;

	protected String keyValue = "";

	protected String pkField = "";

	protected String listTemplate = "";

	protected String detailTemplate = "";

	protected Integer hasForm = Integer.valueOf(0);

	protected List<BpmFormField> fieldList = Collections
			.<BpmFormField> emptyList();
	protected List<BpmFormTable> subTableList = Collections
			.<BpmFormTable> emptyList();

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return this.tableId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getTableDesc() {
		return this.tableDesc;
	}

	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}

	public Short getIsMain() {
		return this.isMain;
	}
	
	

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setMainTableId(Long mainTableId) {
		this.mainTableId = mainTableId;
	}

	public Long getMainTableId() {
		return this.mainTableId;
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

	public int getIsExternal() {
		return this.isExternal;
	}

	public void setIsExternal(int isExternal) {
		this.isExternal = isExternal;
	}

	public String getDsAlias() {
		return this.dsAlias;
	}

	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}

	public String getDsName() {
		return this.dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public Short getKeyType() {
		return this.keyType;
	}

	public void setKeyType(Short keyType) {
		this.keyType = keyType;
	}

	public String getKeyValue() {
		return this.keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getPkField() {
		return this.pkField;
	}

	public void setPkField(String pkField) {
		this.pkField = pkField;
	}

	public String getListTemplate() {
		return this.listTemplate;
	}

	public void setListTemplate(String listTemplate) {
		this.listTemplate = listTemplate;
	}

	public String getDetailTemplate() {
		return this.detailTemplate;
	}

	public void setDetailTemplate(String detailTemplate) {
		this.detailTemplate = detailTemplate;
	}

	public Integer getHasForm() {
		return this.hasForm;
	}

	public void setHasForm(Integer hasForm) {
		this.hasForm = hasForm;
	}

	public TableRelation getTableRelation() {
		if (this.isExternal == 0)
			return null;
		if (StringUtil.isEmpty(this.relation))
			return null;
		return getRelationsByXml(this.relation);
	}

	public List<BpmFormField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<BpmFormField> fieldList) {
		this.fieldList = fieldList;
	}

	public List<BpmFormTable> getSubTableList() {
		return subTableList;
	}

	public void setSubTableList(List<BpmFormTable> subTableList) {
		this.subTableList = subTableList;
	}

	public static TableRelation getRelationsByXml(String relationXml) {
		if (StringUtil.isEmpty(relationXml))
			return null;
		Document dom = Dom4jUtil.loadXml(relationXml);
		Element root = dom.getRootElement();
		String pk = root.attributeValue("pk");
		TableRelation tableRelation = new TableRelation(pk);
		Iterator tbIt = root.elementIterator();
		while (tbIt.hasNext()) {
			Element elTb = (Element) tbIt.next();
			String tbName = elTb.attributeValue("name");
			String fk = elTb.attributeValue("fk");
			tableRelation.addRelation(tbName, fk);
		}
		return tableRelation;
	}

	public static String removeTable(String relationXml, String tbName) {
		Document dom = Dom4jUtil.loadXml(relationXml);
		Element root = dom.getRootElement();
		List<Element> list = root.elements();
		for (Element el : list) {
			String name = el.attributeValue("name");
			if (name.equals(tbName)) {
				root.remove(el);
				break;
			}
		}
		list = root.elements();
		if (list.size() == 0)
			return "";
		return root.asXML();
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormTable)) {
			return false;
		}
		BpmFormTable rhs = (BpmFormTable) object;
		return new EqualsBuilder().append(this.tableId, rhs.tableId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.tableId)
				.append(this.tableName).append(this.tableDesc)
				.append(this.isMain).append(this.mainTableId)
				.append(this.isPublished).append(this.publishedBy)
				.append(this.publishTime).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("tableId", this.tableId)
				.append("tableName", this.tableName)
				.append("tableDesc", this.tableDesc)
				.append("isMain", this.isMain)
				.append("mainTableId", this.mainTableId)
				.append("isPublished", this.isPublished)
				.append("publishedBy", this.publishedBy)
				.append("publishTime", this.publishTime).toString();
	}

	public static Map<String, Object> fieldNameMapConvert(
			Map<String, Object> fields) {
		String[] removeFieldName = new String[] { "id", "refid",
				"curentuserid_", "flowrunid_", "status_", "createtime_",
				"offtime_", "sourcecaseid_", "changereason_", "sourcecaseno_" };
		Map<String, Object> newMap = new HashMap<String, Object>();
		for (String key : fields.keySet()) {
			boolean flag = false;
			for (String rfn : removeFieldName) {
				if (key.equals(rfn)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				continue;
			}

			newMap.put(TableModel.CUSTOMER_COLUMN_PREFIX + key, fields.get(key));
		}
		return newMap;
	}
}
