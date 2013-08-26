package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysUserOrg;

public interface SysUserOrgService extends BaseService<SysUserOrg> {

	SysUserOrg getUserOrgModel(Long userId, Long orgId);

	List<SysUserOrg> getOrgByUserId(Long userId);

	SysOrg getChageNameByOrgId(Long orgId);

	void saveUserOrg(Long userId, Long[] aryOrgIds, Long primaryOrgId,
			Long primaryDeptId, Long[] aryOrgCharge, Long[] arrIsDept)
			throws Exception;

	void addUserOrg(String[] orgIds, String orgIdPrimary, Long userId)
			throws Exception;

	void addUser(Long orgId, String orgIsPrimary, Long userId) throws Exception;

	List<SysUserOrg> getUserByOrgId(QueryFilter filter);

	void addOrgUser(Long[] userIds, Long orgId) throws Exception;

	void setIsPrimary(Long userPosId);

	void setIsCharge(Long userPosId);

	List<String> getLeaderByUserId(Long userId);

	String getLeaderPosByUserId(Long userId);

}