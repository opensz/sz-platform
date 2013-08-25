package org.sz.platform.system.model;

import org.sz.platform.system.model.SysUserAgent;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class SysUserAgent extends BaseModel {
	public static short IS_ALL_FLAG = 1;

	public static short IS_NOT_ALL_FLAG = 0;
	protected Long agentid;
	protected Long agentuserid;
	protected Long touserid;
	protected String tofullname;
	protected Date starttime;
	protected Date endtime;
	protected Short isall;
	protected Short isvalid;

	public void setAgentid(Long agentid) {
		this.agentid = agentid;
	}

	public Long getAgentid() {
		return this.agentid;
	}

	public void setAgentuserid(Long agentuserid) {
		this.agentuserid = agentuserid;
	}

	public Long getAgentuserid() {
		return this.agentuserid;
	}

	public void setTouserid(Long touserid) {
		this.touserid = touserid;
	}

	public Long getTouserid() {
		return this.touserid;
	}

	public void setTofullname(String tofullname) {
		this.tofullname = tofullname;
	}

	public String getTofullname() {
		return this.tofullname;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setIsall(Short isall) {
		this.isall = isall;
	}

	public Short getIsall() {
		return this.isall;
	}

	public void setIsvalid(Short isvalid) {
		this.isvalid = isvalid;
	}

	public Short getIsvalid() {
		return this.isvalid;
	}

	public boolean equals(Object object) {
		if (!(object instanceof SysUserAgent)) {
			return false;
		}
		SysUserAgent rhs = (SysUserAgent) object;
		return new EqualsBuilder().append(this.agentid, rhs.agentid)
				.append(this.agentuserid, rhs.agentuserid)
				.append(this.touserid, rhs.touserid)
				.append(this.tofullname, rhs.tofullname)
				.append(this.starttime, rhs.starttime)
				.append(this.endtime, rhs.endtime)
				.append(this.isall, rhs.isall)
				.append(this.isvalid, rhs.isvalid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.agentid)
				.append(this.agentuserid).append(this.touserid)
				.append(this.tofullname).append(this.starttime)
				.append(this.endtime).append(this.isall).append(this.isvalid)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("agentid", this.agentid)
				.append("agentuserid", this.agentuserid)
				.append("touserid", this.touserid)
				.append("tofullname", this.tofullname)
				.append("starttime", this.starttime)
				.append("endtime", this.endtime).append("isall", this.isall)
				.append("isvalid", this.isvalid).toString();
	}
}
