package org.sz.platform.oa.model.desk;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class DesttopColumn extends BaseModel {
	protected Long ID;
	protected String NAME;
	protected String DATANAME;
	protected String TEMPNAME;
	protected String TEMPID;
	protected String TEMPPATH;
	protected String COLMORE;

	public void setID(Long ID) {
		this.ID = ID;
	}

	public Long getID() {
		return this.ID;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getNAME() {
		return this.NAME;
	}

	public void setDATANAME(String DATANAME) {
		this.DATANAME = DATANAME;
	}

	public String getDATANAME() {
		return this.DATANAME;
	}

	public void setTEMPNAME(String TEMPNAME) {
		this.TEMPNAME = TEMPNAME;
	}

	public String getTEMPNAME() {
		return this.TEMPNAME;
	}

	public void setTEMPID(String TEMPID) {
		this.TEMPID = TEMPID;
	}

	public String getTEMPID() {
		return this.TEMPID;
	}

	public void setTEMPPATH(String TEMPPATH) {
		this.TEMPPATH = TEMPPATH;
	}

	public String getTEMPPATH() {
		return this.TEMPPATH;
	}

	public void setCOLMORE(String COLMORE) {
		this.COLMORE = COLMORE;
	}

	public String getCOLMORE() {
		return this.COLMORE;
	}

	public boolean equals(Object object) {
		if (!(object instanceof DesttopColumn)) {
			return false;
		}
		DesttopColumn rhs = (DesttopColumn) object;
		return new EqualsBuilder().append(this.ID, rhs.ID)
				.append(this.NAME, rhs.NAME)
				.append(this.DATANAME, rhs.DATANAME)
				.append(this.TEMPNAME, rhs.TEMPNAME)
				.append(this.TEMPID, rhs.TEMPID)
				.append(this.TEMPPATH, rhs.TEMPPATH)
				.append(this.COLMORE, rhs.COLMORE).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.ID)
				.append(this.NAME).append(this.DATANAME).append(this.TEMPNAME)
				.append(this.TEMPID).append(this.TEMPPATH).append(this.COLMORE)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("ID", this.ID)
				.append("NAME", this.NAME).append("DATANAME", this.DATANAME)
				.append("TEMPNAME", this.TEMPNAME)
				.append("TEMPID", this.TEMPID)
				.append("TEMPPATH", this.TEMPPATH)
				.append("COLMORE", this.COLMORE).toString();
	}
}
