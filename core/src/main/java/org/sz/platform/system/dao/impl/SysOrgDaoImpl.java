package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.dao.SysOrgDao;
import org.sz.platform.system.model.SysOrg;

@Repository("sysOrgDao")
public class SysOrgDaoImpl extends BaseDaoImpl<SysOrg> implements SysOrgDao {
	public Class getEntityClass() {
		return SysOrg.class;
	}

	public List<SysOrg> getOrgByOrgId(QueryFilter queryFilter) {
		return getBySqlKey("getOrgByOrgId", queryFilter);
	}

	public List<SysOrg> getOrgByDemId(Long demId) {
		return getBySqlKey("getOrgByDemId", demId);
	}

	public void updSn(Long orgId, Short sn) {
		Map params = new HashMap();
		params.put("orgId", orgId);
		params.put("sn", sn);
		update("updSn", params);
	}

	public Long getOneByuserOrgId(Long userOrgId) {
		return (Long) getOne("getOneByuserOrgId", userOrgId);
	}

	public List<SysOrg> getOrgsByUserId(Long userId) {
		return getBySqlKey("getOrgsByUserId", userId);
	}

	public List<SysOrg> getOrgsByDemIdOrAll(Long demId, String orgTypes) {
		Map params = new HashMap();
		if (demId.longValue() != 0L) {
			params.put("demId", demId);
			params.put("orgType", orgTypes);
		}
		return getBySqlKey("getOrgsByDemIdOrAll", params);
	}

	public List<SysOrg> getByUserIdAndDemId(Long userId, Long demId) {
		Map m = new HashMap();
		m.put("userId", userId);
		m.put("demId", demId);
		return getBySqlKey("getByUserIdAndDemId", m);
	}

	public List<SysOrg> getByDepth(Integer depth) {
		Map params = new HashMap();
		params.put("depth", depth);
		return getBySqlKey("getByDepth", params);
	}

	public List<SysOrg> getByOrgPath(String path) {
		Map params = new HashMap();
		params.put("path", path);
		return getBySqlKey("getByOrgPath", params);
	}

	public void delByPath(String path) {
		Map params = new HashMap();
		params.put("path", path);
		delBySqlKey("delByPath", params);
	}

	public void delByOrgId(Long id) {
		int inta = getSqlSessionTemplate().delete(
				getIbatisMapperNamespace() + "." + "delByOrgId", id);
		return;
	}

	public SysOrg getPrimaryOrgByUserId(Long userId) {
		return (SysOrg) getUnique("getPrimaryOrgByUserId", userId);
	}

	public SysOrg getPrimaryOrgAndDeptByUserId(Long userId) {
		return (SysOrg) getUnique("getPrimaryOrgAndDeptByUserId", userId);
	}

	@Override
	public List<SysOrg> getOrgsByDemIdOrParam(Map queryFilter) {
		return getBySqlKey("getOrgsByDemIdOrAll", queryFilter);
	}

	@Override
	public List<SysOrg> getByContractIds(String contractIds) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("contractId", contractIds);
		return getBySqlKey("getOrgByContractIds", map);
	}

	@Override
	public List<SysOrg> getOrgByType(Long orgType) {
		return getBySqlKey("getOrgByType", orgType);
	}

	@Override
	public List<SysOrg> getCustomer(QueryFilter queryFilter) {
		// queryFilter.setPageBean(null);
		return getBySqlKey("getCustomer", queryFilter);
	}

}
