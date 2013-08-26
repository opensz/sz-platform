package org.sz.platform.oa.model.worktime;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class Vacation extends BaseModel {
	protected Long id;
	protected String name;
	protected Short years;
	protected Date statTime;
	protected Date endTime;
	protected String sTime;
	protected String eTime;

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

	public void setYears(Short years) {
		this.years = years;
	}

	public Short getYears() {
		return this.years;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

	public Date getStatTime() {
		return this.statTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public String getsTime() {
		return this.sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String geteTime() {
		return this.eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Vacation)) {
			return false;
		}
		Vacation rhs = (Vacation) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.name, rhs.name).append(this.years, rhs.years)
				.append(this.statTime, rhs.statTime)
				.append(this.endTime, rhs.endTime).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.name).append(this.years).append(this.statTime)
				.append(this.endTime).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("years", this.years)
				.append("statTime", this.statTime)
				.append("endTime", this.endTime).toString();
	}
}
