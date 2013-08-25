package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.model.SysUserOrg;

public interface SysUserOrgDao extends BaseDao<SysUserOrg> {

	SysUserOrg getUserOrgModel(Long userId, Long orgId);

	List<SysUserOrg> getByOrgId(Long orgId);

	SysUserOrg getPrimaryByUserId(Long userId);

	List<SysUserOrg> getChargeByOrgId(Long orgId);

	int updateByOrgId(Long orgId, Short isCharge);

	List<SysUserOrg> getOrgByUserId(Long userId);

	List<SysUserOrg> getChargeByUserId(Long userId);

	void delByUserId(Long userId);

	void updNotPrimaryByUserId(Long userId);

	List<SysUserOrg> getUserByOrgId(QueryFilter filter);

	void delChargeByOrgId(Long orgId);

}