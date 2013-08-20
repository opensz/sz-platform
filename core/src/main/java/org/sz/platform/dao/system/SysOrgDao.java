package org.sz.platform.dao.system;

import java.util.List;
import java.util.Map;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.model.system.SysOrg;

public interface SysOrgDao extends BaseDao<SysOrg>{

	List<SysOrg> getOrgByOrgId(QueryFilter queryFilter);

	List<SysOrg> getOrgByDemId(Long demId);

	void updSn(Long orgId, Short sn);

	Long getOneByuserOrgId(Long userOrgId);

	List<SysOrg> getOrgsByUserId(Long userId);

	List<SysOrg> getOrgsByDemIdOrAll(Long demId,String orgTypes);
	
	List<SysOrg> getOrgsByDemIdOrParam(Map	map);

	List<SysOrg> getByUserIdAndDemId(Long userId, Long demId);

	List<SysOrg> getByDepth(Integer depth);

	List<SysOrg> getByOrgPath(String path);

	void delByPath(String path);

	void delByOrgId(Long id);
	
	SysOrg getPrimaryOrgByUserId(Long userId);
	
	SysOrg getPrimaryOrgAndDeptByUserId(Long userId);
	
	List<SysOrg> getByContractIds(String contractIds);
	
	/**
	 * 通过type字段得到org信息
	 * @param type
	 * @return List<SysOrg>
	 */
	List<SysOrg> getOrgByType(Long orgType);
	
	/**
	 * 查询org下的客户
	 * @param orgId
	 * @return
	 */
	List<SysOrg> getCustomer(QueryFilter queryFilter);
}