package org.sz.platform.system.model;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

/**
 * 企业及下级组织（部门）
 * 
 */
public class SysOrg extends BaseModel {
	public static final Long BEGIN_DEMID = Long.valueOf(1L);
	public static final Long BEGIN_ORGID = Long.valueOf(1L);
	public static final Integer BEGIN_DEPTH = Integer.valueOf(0);
	public static final String BEGIN_PATH = "1";
	public static final Short BEGIN_TYPE = 1;
	public static final Long BEGIN_ORGSUPID = Long.valueOf(-1L);
	public static final Short BEGIN_SN = 1;

	private Long orgId;
	private Long demId;
	private String demName;
	private String orgName;
	private String orgDesc;
	private Long orgSupId;

	private String orgSupName;
	private String path;
	private Integer depth;
	private Short orgType;

	private Long creatorId;
	private Date createtime;
	private Long updateId;
	private Date updatetime;
	private String ownUser;
	private String ownUserName;
	private String createName;
	private String updateName;

	protected Long sn = Long.valueOf(0L);

	private Integer onlineNum = Integer.valueOf(0);
	private Short isPrimary;
	private Short isRoot = 0;

	// /////// add by y.mao ///////////
	private String webSite;
	private String telephone;

	// /////////////////////////////////

	public Short getIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(Short isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Long getSn() {
		return this.sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public String getOwnUserName() {
		return this.ownUserName;
	}

	public void setOwnUserName(String ownUserName) {
		this.ownUserName = ownUserName;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setDemId(Long demId) {
		this.demId = demId;
	}

	public Long getDemId() {
		return this.demId;
	}

	public String getDemName() {
		return this.demName;
	}

	public void setDemName(String demName) {
		this.demName = demName;
	}

	public void setOrgName(String orgName) throws UnsupportedEncodingException {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public String getOrgDesc() {
		return this.orgDesc;
	}

	public void setOrgSupId(Long orgSupId) {
		this.orgSupId = orgSupId;
	}

	public Long getOrgSupId() {
		return this.orgSupId;
	}

	public String getOrgSupName() {
		return this.orgSupName;
	}

	public void setOrgSupName(String orgSupName) {
		this.orgSupName = orgSupName;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setOrgType(Short orgType) {
		this.orgType = orgType;
	}

	public Short getOrgType() {
		return this.orgType;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Long getUpdateId() {
		return this.updateId;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setOwnUser(String ownUser) {
		this.ownUser = ownUser;
	}

	public String getOwnUser() {
		return this.ownUser;
	}

	public Integer getOnlineNum() {
		return this.onlineNum;
	}

	public void setOnlineNum(Integer onlineNum) {
		this.onlineNum = onlineNum;
	}

	public Short getIsRoot() {
		return this.isRoot;
	}

	public void setIsRoot(Short isRoot) {
		this.isRoot = isRoot;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public boolean equals(Object object) {
		if (!(object instanceof SysOrg)) {
			return false;
		}
		SysOrg rhs = (SysOrg) object;
		return new EqualsBuilder().append(this.orgId, rhs.orgId)
				.append(this.demId, rhs.demId)
				.append(this.orgName, rhs.orgName)
				.append(this.orgDesc, rhs.orgDesc)
				.append(this.orgSupId, rhs.orgSupId)
				.append(this.path, rhs.path).append(this.depth, rhs.depth)
				.append(this.orgType, rhs.orgType)
				.append(this.creatorId, rhs.creatorId)
				.append(this.createtime, rhs.createtime)
				.append(this.updateId, rhs.updateId)
				.append(this.updatetime, rhs.updatetime)
				.append(this.ownUser, rhs.ownUser).append(this.sn, rhs.sn)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.orgId)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("orgId", this.orgId)
				.append("demId", this.demId).append("orgName", this.orgName)
				.toString();
	}
}
