package org.sz.platform.bpm.model.flow;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmAgent extends BaseModel implements Cloneable {
	protected Long id;
	protected Long agentid;
	protected Long defid;
	protected Long agentuserid;
	protected Long touserid;
	protected String actdefid;
	protected String subject;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setAgentid(Long agentid) {
		this.agentid = agentid;
	}

	public Long getAgentid() {
		return this.agentid;
	}

	public void setDefid(Long defid) {
		this.defid = defid;
	}

	public Long getDefid() {
		return this.defid;
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

	public void setActdefid(String actdefid) {
		this.actdefid = actdefid;
	}

	public String getActdefid() {
		return this.actdefid;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmAgent)) {
			return false;
		}
		BpmAgent rhs = (BpmAgent) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.agentid, rhs.agentid)
				.append(this.defid, rhs.defid)
				.append(this.agentuserid, rhs.agentuserid)
				.append(this.touserid, rhs.touserid)
				.append(this.actdefid, rhs.actdefid)
				.append(this.subject, rhs.subject).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.agentid).append(this.defid)
				.append(this.agentuserid).append(this.touserid)
				.append(this.actdefid).append(this.subject).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("agentid", this.agentid).append("defid", this.defid)
				.append("agentuserid", this.agentuserid)
				.append("touserid", this.touserid)
				.append("actdefid", this.actdefid)
				.append("subject", this.subject).toString();
	}

	public Object clone() {
		BpmAgent obj = null;
		try {
			obj = (BpmAgent) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
