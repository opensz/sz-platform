package org.sz.platform.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.SysOrgDao;
import org.sz.platform.dao.system.SysUserOrgDao;
import org.sz.platform.model.system.SysOrg;
import org.sz.platform.model.system.SysUserOrg;
import org.sz.platform.service.system.SysOrgService;

@Service("sysOrgService")
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrg> implements
		SysOrgService {

	@Resource
	private SysOrgDao dao;

	@Resource
	private SysUserOrgDao sysUserOrgDao;

	protected IEntityDao<SysOrg, Long> getEntityDao() {
		return this.dao;
	}

	public List<SysOrg> getOrgByOrgId(QueryFilter queryFilter) {
		return this.dao.getOrgByOrgId(queryFilter);
	}

	public List<SysOrg> getOrgsByDemIdOrAll(Long demId, String orgTypes) {
		List list = this.dao.getOrgsByDemIdOrAll(demId, orgTypes);

		return list;
	}

	public Map getOrgMapByDemId(Long demId) {
		String userNameStr = "";
		String userNameCharge = "";
		Map orgMap = new HashMap();
		List<SysOrg> list = this.dao.getOrgByDemId(demId);
		for (SysOrg sysOrg : list) {
			List<SysUserOrg> userlist = this.sysUserOrgDao.getByOrgId(sysOrg
					.getOrgId());
			for (SysUserOrg userOrg : userlist) {
				if (userNameStr.isEmpty())
					userNameStr = userOrg.getUserName();
				else {
					userNameStr = userNameStr + "," + userOrg.getUserName();
				}
				String isCharge = "";
				if (BeanUtils.isNotEmpty(userOrg.getIsCharge())) {
					isCharge = userOrg.getIsCharge().toString();
				}

				if (SysUserOrg.CHARRGE_YES.equals(isCharge)) {
					if (userNameCharge.isEmpty())
						userNameCharge = userOrg.getUserName();
					else {
						userNameCharge = userNameCharge + ","
								+ userOrg.getUserName();
					}
				}
			}

			sysOrg.setOwnUserName(userNameCharge);
			if (sysOrg.getOrgSupId() != null
					&& sysOrg.getOrgSupId().longValue() != 0L)
				orgMap.put(sysOrg.getOrgId(), sysOrg);
		}
		return orgMap;
	}

	public void delById(Long id) {
		SysOrg sysOrg = (SysOrg) this.dao.getById(id);
		this.dao.delByPath(sysOrg.getPath());
	}

	public void delByOrgId(Long id) {

		this.dao.delByOrgId(id);
	}

	public List<SysOrg> getOrgsByUserId(Long userId) {
		return this.dao.getOrgsByUserId(userId);
	}

	public String getOrgIdsByUserId(Long userId) {
		StringBuffer sb = new StringBuffer();
		List<SysOrg> orgList = this.dao.getOrgsByUserId(userId);
		for (SysOrg org : orgList) {
			sb.append(org.getOrgId() + ",");
		}
		if (orgList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private Map<Long, List<SysOrg>> coverTreeData(Long rootId,
			List<SysOrg> instList) {
		Map dataMap = new HashMap();
		dataMap.put(Long.valueOf(rootId.longValue()), new ArrayList());
		if ((instList != null) && (instList.size() > 0)) {
			for (SysOrg sysOrg : instList) {
				long parentId = sysOrg.getOrgSupId().longValue();
				if (dataMap.get(Long.valueOf(parentId)) == null) {
					dataMap.put(Long.valueOf(parentId), new ArrayList());
				}
				((List) dataMap.get(Long.valueOf(parentId))).add(sysOrg);
			}
		}
		return dataMap;
	}

	public List<SysOrg> coverTreeList(Long rootId, List<SysOrg> instList) {
		Map dataMap = coverTreeData(rootId, instList);

		List list = new ArrayList();

		list.addAll(getChildList(rootId, dataMap));

		return list;
	}

	private List<SysOrg> getChildList(Long parentId,
			Map<Long, List<SysOrg>> dataMap) {
		List list = new ArrayList();

		List<SysOrg> orgList = (List) dataMap.get(Long.valueOf(parentId
				.longValue()));
		if ((orgList != null) && (orgList.size() > 0)) {
			for (SysOrg sysOrg : orgList) {
				list.add(sysOrg);
				List childList = getChildList(sysOrg.getOrgId(), dataMap);
				list.addAll(childList);
			}
		}
		return list;
	}

	public List<SysOrg> getByUserIdAndDemId(Long userId, Long demId) {
		return this.dao.getByUserIdAndDemId(userId, demId);
	}

	public void move(Long targetId, Long dragId, String moveType) {
		SysOrg target = (SysOrg) this.dao.getById(targetId);
		SysOrg dragged = (SysOrg) this.dao.getById(dragId);

		if (!target.getDemId().equals(dragged.getDemId()))
			return;
		String nodePath;
		if (("prev".equals(moveType)) || ("next".equals(moveType))) {
			String targetNodePath = target.getPath();
			int idx = targetNodePath.lastIndexOf(target.getOrgId() + ".");
			String basePath = targetNodePath.substring(0, idx);
			String dragedNodePath = basePath + dragId + ".";
			dragged.setPath(dragedNodePath);
			dragged.setOrgSupId(target.getOrgSupId());
			dragged.setDepth(target.getDepth());
			if ("prev".equals(moveType))
				dragged.setSn(Long.valueOf(target.getSn().longValue() - 1L));
			else {
				dragged.setSn(Long.valueOf(target.getSn().longValue() + 1L));
			}
			dragged.setDepth(Integer.valueOf(dragedNodePath.split("[.]").length));
			this.dao.update(dragged);
		} else {
			nodePath = dragged.getPath();

			List<SysOrg> list = this.dao.getByOrgPath(nodePath);

			for (SysOrg org : list) {
				if (org.getOrgId().equals(dragId)) {
					org.setOrgSupId(targetId);

					org.setPath(target.getPath() + org.getOrgId() + ".");

					org.setDepth(Integer
							.valueOf(org.getPath().split("[.]").length - 1));
				} else {
					String path = org.getPath();

					String tmpPath = path.replaceAll(nodePath, "");

					String targetPath = target.getPath();

					String tmp = targetPath + dragged.getOrgId() + "."
							+ tmpPath;

					org.setPath(tmp);

					org.setDepth(Integer.valueOf(tmp.split("[.]").length - 1));
				}

				this.dao.update(org);
			}
		}
	}

	public void addOrg(SysOrg sysOrg) throws Exception {
		sysOrg.setSn(sysOrg.getOrgId());

		this.dao.add(sysOrg);

		String ownerId = sysOrg.getOwnUser();
		if (StringUtil.isEmpty(ownerId))
			return;

		String[] aryUserId = ownerId.split(",");
		for (int i = 0; i < aryUserId.length; i++) {
			String userId = aryUserId[i];
			if (!StringUtil.isEmpty(userId)) {
				Long lUserId = Long.valueOf(Long.parseLong(userId));
				SysUserOrg sysUserOrg = new SysUserOrg();
				sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
				sysUserOrg.setOrgId(sysOrg.getOrgId());
				sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_YES);
				sysUserOrg.setUserId(lUserId);
				this.sysUserOrgDao.updNotPrimaryByUserId(lUserId);
				sysUserOrg.setIsPrimary((short) 1);
				this.sysUserOrgDao.add(sysUserOrg);
			}
		}
	}

	public void updOrg(SysOrg sysOrg) throws Exception {
		Long orgId = sysOrg.getOrgId();

		this.dao.update(sysOrg);

		this.sysUserOrgDao.delChargeByOrgId(orgId);

		String ownerId = sysOrg.getOwnUser();
		if (StringUtil.isEmpty(ownerId))
			return;

		String[] aryUserId = ownerId.split(",");
		for (int i = 0; i < aryUserId.length; i++) {
			String userId = aryUserId[i];
			if (!StringUtil.isEmpty(userId)) {
				Long lUserId = Long.valueOf(Long.parseLong(userId));
				SysUserOrg sysUserOrg = new SysUserOrg();
				sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
				sysUserOrg.setOrgId(orgId);
				sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_YES);
				sysUserOrg.setUserId(lUserId);
				this.sysUserOrgDao.updNotPrimaryByUserId(lUserId);
				sysUserOrg.setIsPrimary((short) 1);
				this.sysUserOrgDao.add(sysUserOrg);
			}
		}
	}

	public SysOrg getPrimaryOrgByUserId(Long userId) {
		return this.dao.getPrimaryOrgByUserId(userId);
	}

	@Override
	public List<SysOrg> getOrgsByDemIdOrParam(Map map) {
		// TODO Auto-generated method stub
		return this.dao.getOrgsByDemIdOrParam(map);
	}

	@Override
	public List<SysOrg> getByContractIds(String contractIds) {
		// TODO Auto-generated method stub
		return dao.getByContractIds(contractIds);
	}

	@Override
	public List<SysOrg> getOrgByType(Long type) {
		return this.dao.getOrgByType(type);
	}

	@Override
	public List<SysOrg> getCustomer(QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return this.dao.getCustomer(queryFilter);
	}

}
