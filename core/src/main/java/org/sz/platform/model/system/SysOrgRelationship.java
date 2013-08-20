package org.sz.platform.model.system;

import org.sz.core.model.BaseModel;

/**
 * 企业间关系
 *
 */
public class SysOrgRelationship extends BaseModel {
	
	public final static String TYPE_MAIN = "main"; //A是主企业
	public final static String TYPE_CUSTOME = "customer"; //A是B的客户
	public final static String TYPE_SUPPLIER = "supplier"; //A是B的供应商
	
	protected Long id;
	protected Long orgIdA;
	protected Long orgIdB;
	protected String type;
	protected Boolean enabled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrgIdA() {
		return orgIdA;
	}
	public void setOrgIdA(Long orgIdA) {
		this.orgIdA = orgIdA;
	}
	public Long getOrgIdB() {
		return orgIdB;
	}
	public void setOrgIdB(Long orgIdB) {
		this.orgIdB = orgIdB;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
