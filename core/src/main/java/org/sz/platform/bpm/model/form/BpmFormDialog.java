package org.sz.platform.bpm.model.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
import org.sz.core.query.PageBean;
import org.sz.core.util.StringUtil;

public class BpmFormDialog extends BaseModel {
	public static final String Page = "p";
	public static final String PageSize = "z";
	protected Long id = Long.valueOf(0L);

	protected String name = "";

	protected String alias = "";

	protected Integer style = Integer.valueOf(0);

	protected Integer width = Integer.valueOf(400);

	protected Integer height = Integer.valueOf(300);

	protected Integer issingle = Integer.valueOf(1);

	protected Integer needpage = Integer.valueOf(1);

	protected Integer istable = Integer.valueOf(1);

	protected String objname = "";

	protected String displayfield = "";

	protected String conditionfield = "";

	protected String resultfield = "";

	protected String dsname = "";

	protected String dsalias = "";

	protected String returnFields = "";
	
	protected Integer isSync = Integer.valueOf(1);

	private List<Map<String, Object>> list = new ArrayList();

	private PageBean pageBean = new PageBean(1, 20);

	private Integer pagesize = Integer.valueOf(10);

	public Map<String, String> getTreeField() {
		if (this.style.intValue() == 0) {
			return null;
		}
		Map map = new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(this.displayfield);
		map.put("id", jsonObject.get("id").toString());
		map.put("pid", jsonObject.get("pid").toString());
		map.put("displayName", jsonObject.get("displayName").toString());
		return map;
	}

	public List<DialogField> getDisplayList() {
		if (this.style.intValue() == 1) {
			return null;
		}
		if (StringUtil.isEmpty(this.displayfield)) {
			return new ArrayList();
		}
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(this.displayfield);
		for (Iterator i$ = jsonArray.iterator(); i$.hasNext();) {
			Object obj = i$.next();
			JSONObject jsonObj = (JSONObject) obj;
			String field = jsonObj.getString("field");
			String comment = jsonObj.getString("comment");

			DialogField dialogField = new DialogField();
			dialogField.setFieldName(field);
			dialogField.setComment(comment);
			list.add(dialogField);
		}
		return list;
	}

	public List<DialogField> getConditionList() {
		if (this.style.intValue() == 1) {
			return null;
		}
		if (StringUtil.isEmpty(this.conditionfield)) {
			return new ArrayList();
		}
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(this.conditionfield);
		for (Iterator i$ = jsonArray.iterator(); i$.hasNext();) {
			Object obj = i$.next();
			JSONObject jsonObj = (JSONObject) obj;
			String field = jsonObj.getString("field");
			String comment = jsonObj.getString("comment");
			String condition = jsonObj.getString("condition");
			String dbType = jsonObj.getString("dbType");

			DialogField dialogField = new DialogField();
			dialogField.setFieldName(field);
			dialogField.setComment(comment);
			dialogField.setCondition(condition);
			dialogField.setFieldType(dbType);
			list.add(dialogField);
		}
		return list;
	}

	public List<DialogField> getReturnList() {
		if (this.style.intValue() == 1) {
			return null;
		}
		if (StringUtil.isEmpty(this.resultfield)) {
			return new ArrayList();
		}
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(this.resultfield);
		for (Iterator i$ = jsonArray.iterator(); i$.hasNext();) {
			Object obj = i$.next();
			JSONObject jsonObj = (JSONObject) obj;
			String field = jsonObj.getString("field");
			String comment = jsonObj.getString("comment");
			DialogField dialogField = new DialogField();
			dialogField.setFieldName(field);
			dialogField.setComment(comment);
			list.add(dialogField);
		}
		return list;
	}

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

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Integer getStyle() {
		return this.style;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setIssingle(Integer issingle) {
		this.issingle = issingle;
	}

	public Integer getIssingle() {
		return this.issingle;
	}

	public void setNeedpage(Integer needpage) {
		this.needpage = needpage;
	}

	public Integer getNeedpage() {
		return this.needpage;
	}

	public void setIstable(Integer istable) {
		this.istable = istable;
	}

	public Integer getIstable() {
		return this.istable;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	public String getObjname() {
		return this.objname;
	}

	public void setDisplayfield(String displayfield) {
		this.displayfield = displayfield;
	}

	public String getDisplayfield() {
		return this.displayfield;
	}

	public void setConditionfield(String conditionfield) {
		this.conditionfield = conditionfield;
	}

	public String getConditionfield() {
		return this.conditionfield;
	}

	public void setResultfield(String resultfield) {
		this.resultfield = resultfield;
	}

	public String getResultfield() {
		return this.resultfield.trim();
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

	public String getDsname() {
		return this.dsname;
	}

	public void setDsalias(String dsalias) {
		this.dsalias = dsalias;
	}

	public String getDsalias() {
		return this.dsalias;
	}

	public Integer getPagesize() {
		return this.pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getIsSync() {
		return this.isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}

	public List<Map<String, Object>> getList() {
		return this.list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public String getReturnFields() {
		if (StringUtil.isEmpty(this.resultfield)) {
			return "";
		}
		JSONArray jsonArray = JSONArray.fromObject(this.resultfield);
		for (Iterator i$ = jsonArray.iterator(); i$.hasNext();) {
			Object obj = i$.next();
			JSONObject jsonObj = (JSONObject) obj;
			returnFields += jsonObj.getString("field") + ",";
		}
		if(!"".equals(returnFields)){
			returnFields = returnFields.substring(0, returnFields.length() - 1);
		}
		return returnFields;
	}

	public void setReturnFields(String returnFields) {
		this.returnFields = returnFields;
	}

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormDialog)) {
			return false;
		}
		BpmFormDialog rhs = (BpmFormDialog) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.name, rhs.name).append(this.alias, rhs.alias)
				.append(this.style, rhs.style).append(this.width, rhs.width)
				.append(this.height, rhs.height)
				.append(this.issingle, rhs.issingle)
				.append(this.needpage, rhs.needpage)
				.append(this.istable, rhs.istable)
				.append(this.objname, rhs.objname)
				.append(this.displayfield, rhs.displayfield)
				.append(this.conditionfield, rhs.conditionfield)
				.append(this.resultfield, rhs.resultfield)
				.append(this.dsname, rhs.dsname)
				.append(this.dsalias, rhs.dsalias).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.name).append(this.alias).append(this.style)
				.append(this.width).append(this.height).append(this.issingle)
				.append(this.needpage).append(this.istable)
				.append(this.objname).append(this.displayfield)
				.append(this.conditionfield).append(this.resultfield)
				.append(this.dsname).append(this.dsalias).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("alias", this.alias)
				.append("style", this.style).append("width", this.width)
				.append("height", this.height)
				.append("issingle", this.issingle)
				.append("needpage", this.needpage)
				.append("istable", this.istable)
				.append("objname", this.objname)
				.append("displayfield", this.displayfield)
				.append("conditionfield", this.conditionfield)
				.append("resultfield", this.resultfield)
				.append("dsname", this.dsname).append("dsalias", this.dsalias)
				.toString();
	}
}
