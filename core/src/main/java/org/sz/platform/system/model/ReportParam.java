package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class ReportParam extends BaseModel {
	protected Long PARAMID;
	protected Long REPORTID;
	protected String PARAMNAME;
	protected String PARAMKEY;
	protected String DEFAULTVAL;
	protected String PARAMTYPE;
	protected Long SN;
	protected String PARAMTYPESTR;

	public void setPARAMID(Long PARAMID) {
		this.PARAMID = PARAMID;
	}

	public Long getPARAMID() {
		return this.PARAMID;
	}

	public void setREPORTID(Long REPORTID) {
		this.REPORTID = REPORTID;
	}

	public Long getREPORTID() {
		return this.REPORTID;
	}

	public void setPARAMNAME(String PARAMNAME) {
		this.PARAMNAME = PARAMNAME;
	}

	public String getPARAMNAME() {
		return this.PARAMNAME;
	}

	public void setPARAMKEY(String PARAMKEY) {
		this.PARAMKEY = PARAMKEY;
	}

	public String getPARAMKEY() {
		return this.PARAMKEY;
	}

	public void setDEFAULTVAL(String DEFAULTVAL) {
		this.DEFAULTVAL = DEFAULTVAL;
	}

	public String getDEFAULTVAL() {
		return this.DEFAULTVAL;
	}

	public void setPARAMTYPE(String PARAMTYPE) {
		this.PARAMTYPE = PARAMTYPE;
	}

	public String getPARAMTYPE() {
		return this.PARAMTYPE;
	}

	public void setSN(Long SN) {
		this.SN = SN;
	}

	public Long getSN() {
		return this.SN;
	}

	public void setPARAMTYPESTR(String PARAMTYPESTR) {
		this.PARAMTYPESTR = PARAMTYPESTR;
	}

	public String getPARAMTYPESTR() {
		return this.PARAMTYPESTR;
	}

	public boolean equals(Object object) {
		if (!(object instanceof ReportParam)) {
			return false;
		}
		ReportParam rhs = (ReportParam) object;
		return new EqualsBuilder().append(this.PARAMID, rhs.PARAMID)
				.append(this.REPORTID, rhs.REPORTID)
				.append(this.PARAMNAME, rhs.PARAMNAME)
				.append(this.PARAMKEY, rhs.PARAMKEY)
				.append(this.DEFAULTVAL, rhs.DEFAULTVAL)
				.append(this.PARAMTYPE, rhs.PARAMTYPE).append(this.SN, rhs.SN)
				.append(this.PARAMTYPESTR, rhs.PARAMTYPESTR).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.PARAMID)
				.append(this.REPORTID).append(this.PARAMNAME)
				.append(this.PARAMKEY).append(this.DEFAULTVAL)
				.append(this.PARAMTYPE).append(this.SN)
				.append(this.PARAMTYPESTR).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("PARAMID", this.PARAMID)
				.append("REPORTID", this.REPORTID)
				.append("PARAMNAME", this.PARAMNAME)
				.append("PARAMKEY", this.PARAMKEY)
				.append("DEFAULTVAL", this.DEFAULTVAL)
				.append("PARAMTYPE", this.PARAMTYPE).append("SN", this.SN)
				.append("PARAMTYPESTR", this.PARAMTYPESTR).toString();
	}
}
