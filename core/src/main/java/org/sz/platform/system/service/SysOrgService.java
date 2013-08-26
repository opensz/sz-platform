package org.sz.platform.system.service;

import java.util.List;
import java.util.Map;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysOrg;

public interface SysOrgService extends BaseService<SysOrg> {

	List<SysOrg> getOrgByOrgId(QueryFilter queryFilter);

	List<SysOrg> getOrgsByDemIdOrAll(Long demId, String orgTypes);

	List<SysOrg> getOrgsByDemIdOrParam(Map map);

	Map getOrgMapByDemId(Long demId);

	void delById(Long id);

	void delByOrgId(Long id);

	List<SysOrg> getOrgsByUserId(Long userId);

	String getOrgIdsByUserId(Long userId);

	List<SysOrg> coverTreeList(Long rootId, List<SysOrg> instList);

	List<SysOrg> getByUserIdAndDemId(Long userId, Long demId);

	void move(Long targetId, Long dragId, String moveType);

	void addOrg(SysOrg sysOrg) throws Exception;

	void updOrg(SysOrg sysOrg) throws Exception;

	SysOrg getPrimaryOrgByUserId(Long userId);

	List<SysOrg> getByContractIds(String contractIds);

	/**
	 * 通过type字段得到org信息
	 * 
	 * @param type
	 * @return List<SysOrg>
	 */
	List<SysOrg> getOrgByType(Long type);

	/**
	 * 查询org下的客户
	 * 
	 * @param orgId
	 * @return
	 */
	List<SysOrg> getCustomer(QueryFilter queryFilter);
}