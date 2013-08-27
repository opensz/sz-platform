package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class UserUnder extends BaseModel {
	protected Long id;
	protected Long userid;
	protected Long underuserid;
	protected String underusername;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUnderuserid(Long underuserid) {
		this.underuserid = underuserid;
	}

	public Long getUnderuserid() {
		return this.underuserid;
	}

	public void setUnderusername(String underusername) {
		this.underusername = underusername;
	}

	public String getUnderusername() {
		return this.underusername;
	}

	public boolean equals(Object object) {
		if (!(object instanceof UserUnder)) {
			return false;
		}
		UserUnder rhs = (UserUnder) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.userid, rhs.userid)
				.append(this.underuserid, rhs.underuserid)
				.append(this.underusername, rhs.underusername).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.userid).append(this.underuserid)
				.append(this.underusername).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("userid", this.userid)
				.append("underuserid", this.underuserid)
				.append("underusername", this.underusername).toString();
	}
}
