package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.dao.SysUserOrgDao;
import org.sz.platform.system.model.SysUserOrg;

@Repository("sysUserOrgDao")
public class SysUserOrgDaoImpl extends BaseDaoImpl<SysUserOrg> implements
		SysUserOrgDao {
	public Class getEntityClass() {
		return SysUserOrg.class;
	}

	public SysUserOrg getUserOrgModel(Long userId, Long orgId) {
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("orgId", orgId);
		SysUserOrg sysUserOrg = (SysUserOrg) getUnique("getUserOrgModel", param);
		return sysUserOrg;
	}

	public List<SysUserOrg> getByOrgId(Long orgId) {
		return getBySqlKey("getByOrgId", orgId);
	}

	public SysUserOrg getPrimaryByUserId(Long userId) {
		return (SysUserOrg) getUnique("getPrimaryByUserId", userId);
	}

	public List<SysUserOrg> getChargeByOrgId(Long orgId) {
		return getBySqlKey("getChargeByOrgId", orgId);
	}

	public int updateByOrgId(Long orgId, Short isCharge) {
		Map params = new HashMap();
		params.put("orgId", orgId);
		params.put("isCharge", isCharge);
		int affectCount = update("updateByOrgId", params);
		return affectCount;
	}

	public List<SysUserOrg> getOrgByUserId(Long userId) {
		List sysUserOrg = getBySqlKey("getOrgByUserId", userId);
		return sysUserOrg;
	}

	public List<SysUserOrg> getChargeByUserId(Long userId) {
		return getBySqlKey("getChargeByUserId", userId);
	}

	public void delByUserId(Long userId) {
		delBySqlKey("delByUserId", userId);
	}

	public void updNotPrimaryByUserId(Long userId) {
		update("updNotPrimaryByUserId", userId);
	}

	public List<SysUserOrg> getUserByOrgId(QueryFilter filter) {
		return getBySqlKey("getUserByOrgId", filter);
	}

	public void delChargeByOrgId(Long orgId) {
		delBySqlKey("delChargeByOrgId", orgId);
	}
}
