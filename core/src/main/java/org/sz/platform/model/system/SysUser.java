package org.sz.platform.model.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.sz.core.cache.ICache;
import org.sz.core.model.BaseModel;
import org.sz.core.util.ContextUtil;
import org.sz.platform.dao.system.SysRoleDao;
import org.sz.platform.webservice.api.util.adapter.GrantedAuthorityAdapter;
@Entity
@Table(name="sys_user")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(namespace = "bpm")
public class SysUser extends BaseModel implements UserDetails {
	
	public static final String SEARCH_BY_ROL = "1";
	public static final String SEARCH_BY_ORG = "2";
	public static final String SEARCH_BY_POS = "3";
	public static final String SEARCH_BY_ONL = "4";
	
	public static final Short UN_LOCKED = 0;
	public static final Short LOCKED = 1;

	public static final Short UN_EXPIRED = 0;
	public static final Short EXPIRED = 1;

	public static final Short STATUS_OK = 1;
	public static final Short STATUS_NO = 0;
	public static final Short STATUS_Del = -1;

	public static Long CUR_ORG_ID = 0l;
	
	
	protected Long userOrgId;
	protected Long userId;
	protected String fullname;
	protected String account;
	protected String password;
	protected Short isExpired;
	protected Short isLock;
	protected Date createtime;
	protected Short status;
	protected String email;
	protected String mobile;
	protected String phone;
	protected String sex;
	protected String picture;
	protected String retype;
	protected String orgName;
	
	/////// field that added by y.mao /////////
	private String empId;
	protected String firstSpell; // 拼音首字母
	private String desc;
	
	
	//////////////////////////////////////////
	@Transient
	public String getRetype() {
		return this.retype;
	}

	public void setRetype(String retype) {
		this.retype = retype;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Transient
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Transient
	public Long getUserOrgId() {
		return this.userOrgId;
	}

	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Id
	public Long getUserId() {
		return this.userId;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return this.account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setIsExpired(Short isExpired) {
		this.isExpired = isExpired;
	}

	public Short getIsExpired() {
		return this.isExpired;
	}

	public void setIsLock(Short isLock) {
		this.isLock = isLock;
	}

	public Short getIsLock() {
		return this.isLock;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}
	@Column(name="first_spell")
	public String getFirstSpell() {
		return firstSpell;
	}

	public void setFirstSpell(String firstSpell) {
		this.firstSpell = firstSpell;
	}
	
	
	
	@Column(name="des")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public static String getSearchByRol() {
		return "1";
	}

	public static String getSearchByOrg() {
		return "2";
	}

	public static String getSearchByPos() {
		return "3";
	}

	public static String getSearchByOnl() {
		return "4";
	}

	public static Short getUnLocked() {
		return UN_LOCKED;
	}

	public static Short getLocked() {
		return LOCKED;
	}

	public static Short getUnExpired() {
		return UN_EXPIRED;
	}

	public static Short getExpired() {
		return EXPIRED;
	}

	public static Short getStatusOk() {
		return STATUS_OK;
	}

	public static Short getStatusNo() {
		return STATUS_NO;
	}

	public static Short getStatusDel() {
		return STATUS_Del;
	}

	public boolean equals(Object object) {
		if (!(object instanceof SysUser)) {
			return false;
		}
		SysUser rhs = (SysUser) object;
		return new EqualsBuilder().append(this.userId, rhs.userId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.userId)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("userId", this.userId)
				.append("fullname", this.fullname)
				.append("account", this.account)
				.append("password", this.password)
				.append("isExpired", this.isExpired)
				.append("isLock", this.isLock)
				.append("createtime", this.createtime)
				.append("status", this.status).append("email", this.email)
				.append("mobile", this.mobile).append("phone", this.phone)
				.append("orgName", this.orgName).append("sex", this.sex)
				.append("picture", this.picture).append("retype", this.retype)
				.toString();
	}

	@Transient
	public Collection<String> getRoles() {
		Collection roleList = new ArrayList();
		Collection<GrantedAuthority> roles = getAuthorities();
		for (GrantedAuthority role : roles) {
			roleList.add(role.getAuthority());
		}
		return roleList;
	}

	@Transient
	@XmlJavaTypeAdapter(GrantedAuthorityAdapter.class)
	public Collection<GrantedAuthority> getAuthorities() {
		ICache iCache = (ICache) ContextUtil.getBean(ICache.class);
		Map userRoleMap = null;
		if (iCache != null) {
			userRoleMap = (Map) iCache.getByKey("UserRole");
		}
		if (userRoleMap == null) {
			userRoleMap = new HashMap();
			iCache.add("UserRole", userRoleMap, 2000L);
		}

		if (userRoleMap.containsKey(this.userId)) {
			Collection roleCollection = (Collection) userRoleMap
					.get(this.userId);
			return roleCollection;
		}

		SysRoleDao sysRoleDao = (SysRoleDao) ContextUtil.getBean(SysRoleDao.class);
		List<SysRole> roleList = sysRoleDao.getByUserId(this.userId);
		Collection roleCollection = new ArrayList();
		if ((roleList != null) && (roleList.size() > 0)) {
			for (SysRole role : roleList) {
				roleCollection.add(new GrantedAuthorityImpl(role.getAlias()));
			}
		}

		if ("admin".equals(this.account)) {
			roleCollection.add(SysRole.ROLE_GRANT_SUPER);
		}
		userRoleMap.put(this.userId, roleCollection);
		return roleCollection;
	}

	@Transient
	public String getUsername() {
		return this.account;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return this.isLock.shortValue() == UN_LOCKED.shortValue();
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	public boolean isEnabled() {
		return this.status == STATUS_OK;
	}
	

}
