package org.sz.platform.oa.model.info;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class Rss extends BaseModel {
	protected Long rssid;
	protected String subject;
	protected String rssaddr;
	protected Date orderdate;
	protected String username;
	protected Long userid;

	public void setRssid(Long rssid) {
		this.rssid = rssid;
	}

	public Long getRssid() {
		return this.rssid;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setRssaddr(String rssaddr) {
		this.rssaddr = rssaddr;
	}

	public String getRssaddr() {
		return this.rssaddr;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Rss)) {
			return false;
		}
		Rss rhs = (Rss) object;
		return new EqualsBuilder().append(this.rssid, rhs.rssid)
				.append(this.subject, rhs.subject)
				.append(this.rssaddr, rhs.rssaddr)
				.append(this.orderdate, rhs.orderdate)
				.append(this.username, rhs.username)
				.append(this.userid, rhs.userid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.rssid)
				.append(this.subject).append(this.rssaddr)
				.append(this.orderdate).append(this.username)
				.append(this.userid).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("rssid", this.rssid)
				.append("subject", this.subject)
				.append("rssaddr", this.rssaddr)
				.append("orderdate", this.orderdate)
				.append("username", this.username)
				.append("userid", this.userid).toString();
	}
}
