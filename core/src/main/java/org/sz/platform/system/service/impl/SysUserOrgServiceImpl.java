package org.sz.platform.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.PositionDao;
import org.sz.platform.system.dao.SysOrgDao;
import org.sz.platform.system.dao.SysUserOrgDao;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.model.UserPosition;
import org.sz.platform.system.service.SysUserOrgService;

@Service("sysUserOrgService")
public class SysUserOrgServiceImpl extends BaseServiceImpl<SysUserOrg>
		implements SysUserOrgService {

	@Resource
	private SysUserOrgDao dao;

	@Resource
	private SysOrgDao sysOrgDao;

	@Resource
	private PositionDao positionDao;

	protected IEntityDao<SysUserOrg, Long> getEntityDao() {
		return this.dao;
	}

	public SysUserOrg getUserOrgModel(Long userId, Long orgId) {
		return this.dao.getUserOrgModel(userId, orgId);
	}

	public List<SysUserOrg> getOrgByUserId(Long userId) {
		List<SysUserOrg> list = this.dao.getOrgByUserId(userId);
		for (SysUserOrg sysUserOrg : list) {
			Long orgId = sysUserOrg.getOrgId();
			SysOrg sysOrg = getChageNameByOrgId(orgId);
			sysUserOrg.setChargeName(sysOrg.getOwnUserName());
		}
		return list;
	}

	public SysOrg getChageNameByOrgId(Long orgId) {
		SysOrg sysOrg = new SysOrg();
		List<SysUserOrg> chargeList = this.dao.getChargeByOrgId(orgId);
		String chargeId = "";
		String chargeName = "";
		for (SysUserOrg charge : chargeList) {
			chargeId = chargeId + charge.getUserId() + ",";
			chargeName = chargeName + charge.getUserName() + ",";
		}
		if (chargeName.length() > 0) {
			chargeId = chargeId.substring(0, chargeId.length() - 1);
			chargeName = chargeName.substring(0, chargeName.length() - 1);
		}
		sysOrg.setOwnUser(chargeId);
		sysOrg.setOwnUserName(chargeName);
		return sysOrg;
	}

	public void saveUserOrg(Long userId, Long[] aryOrgIds, Long primaryOrgId,
			Long primaryDeptId, Long[] aryOrgCharge, Long[] arrIsDept)
			throws Exception {
		this.dao.delByUserId(userId);

		if (BeanUtils.isEmpty(aryOrgIds))
			return;
		int inta = 0;
		for (Long orgId : aryOrgIds) {

			SysUserOrg sysUserOrg = new SysUserOrg();
			sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
			sysUserOrg.setUserId(userId);
			sysUserOrg.setOrgId(orgId);
			// sysUserOrg.setIsDept(arrIsDept);
			if ((primaryOrgId != null) && (primaryOrgId.equals(orgId))) {
				sysUserOrg.setIsPrimary((short) 1);
			}
			if ((primaryDeptId != null) && (primaryDeptId.equals(orgId))) {
				sysUserOrg.setIsPrimary((short) 1);
			}
			if (BeanUtils.isNotEmpty(aryOrgCharge)) {
				for (Long tmpOrgId : aryOrgCharge) {
					if ((tmpOrgId != null) && (orgId.equals(tmpOrgId))) {
						sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_YES);
					}
				}
			}

			this.dao.add(sysUserOrg);
			inta++;
		}
	}

	public void addUserOrg(String[] orgIds, String orgIdPrimary, Long userId)
			throws Exception {
		if (BeanUtils.isEmpty(orgIds))
			return;
		for (int i = 0; i < orgIds.length; i++)
			if (!StringUtil.isEmpty(orgIds[i]))
				addUser(Long.valueOf(Long.parseLong(orgIds[i])), orgIdPrimary,
						userId);
	}

	public void addUser(Long orgId, String orgIsPrimary, Long userId)
			throws Exception {
		SysUserOrg sysUserOrg = getUserOrgModel(userId, orgId);
		if (sysUserOrg == null) {
			sysUserOrg = new SysUserOrg();
			sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
			sysUserOrg.setOrgId(orgId);
			sysUserOrg.setUserId(userId);
			if (orgId.toString().equals(orgIsPrimary)) {
				sysUserOrg.setIsPrimary((short) 1);
			} else {
				sysUserOrg.setIsPrimary((short) 0);
			}
			add(sysUserOrg);
		} else {
			String isPrimary = "";
			if (sysUserOrg.getIsPrimary() != null) {
				isPrimary = sysUserOrg.getIsPrimary().toString();
			}
			if (!"1".equals(isPrimary)) {
				if (orgId.toString().equals(orgIsPrimary)) {
					sysUserOrg.setIsPrimary((short) 1);
				}

			} else if (!orgId.toString().equals(orgIsPrimary)) {
				sysUserOrg.setIsPrimary((short) 0);
			} else {
				sysUserOrg.setIsPrimary((short) 1);
			}

			update(sysUserOrg);
		}
	}

	public List<SysUserOrg> getUserByOrgId(QueryFilter filter) {
		return this.dao.getUserByOrgId(filter);
	}

	public void addOrgUser(Long[] userIds, Long orgId) throws Exception {
		if ((BeanUtils.isEmpty(userIds))
				|| (StringUtil.isEmpty(orgId.toString())))
			return;
		SysUserOrg sysUserOrg = null;
		for (Long userId : userIds) {
			SysUserOrg userOrg = this.dao.getUserOrgModel(userId, orgId);
			if (userOrg != null)
				continue;
			this.dao.updNotPrimaryByUserId(userId);
			sysUserOrg = new SysUserOrg();
			sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
			sysUserOrg.setOrgId(orgId);
			sysUserOrg.setUserId(userId);
			sysUserOrg.setIsPrimary((short) 1);
			this.dao.add(sysUserOrg);
		}
	}

	public void setIsPrimary(Long userPosId) {
		SysUserOrg sysUserOrg = (SysUserOrg) this.dao.getById(userPosId);
		if (sysUserOrg.getIsPrimary().shortValue() == 0) {
			sysUserOrg.setIsPrimary((short) 1);
			this.dao.updNotPrimaryByUserId(sysUserOrg.getUserId());
		} else {
			sysUserOrg.setIsPrimary(UserPosition.PRIMARY_NO);
		}
		this.dao.update(sysUserOrg);
	}

	public void setIsCharge(Long userPosId) {
		SysUserOrg sysUserOrg = (SysUserOrg) this.dao.getById(userPosId);
		if (sysUserOrg.getIsCharge() == SysUserOrg.CHARRGE_NO) {
			sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_YES);
			this.dao.updNotPrimaryByUserId(sysUserOrg.getUserId());
		} else {
			sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_NO);
		}
		this.dao.update(sysUserOrg);
	}

	public List<String> getLeaderByUserId(Long userId) {
		SysOrg sysOrg = this.sysOrgDao.getPrimaryOrgByUserId(userId);
		if (sysOrg == null) {
			return null;
		}
		Long orgId = sysOrg.getOrgId();
		SysUserOrg sysUserOrg = this.dao.getUserOrgModel(userId, orgId);
		if (sysUserOrg.getIsCharge() == SysUserOrg.CHARRGE_YES) {
			SysOrg sysOrgParent = (SysOrg) this.sysOrgDao.getById(orgId);

			return getLeaderByOrgId(sysOrgParent.getOrgSupId());
		}

		return getLeaderByOrgId(orgId);
	}

	public String getLeaderPosByUserId(Long userId) {
		SysOrg sysOrg = this.sysOrgDao.getPrimaryOrgByUserId(userId);
		Long uId = Long.valueOf(0L);
		if (sysOrg == null) {
			return null;
		}
		Long orgId = sysOrg.getOrgId();
		SysUserOrg sysUserOrg = this.dao.getUserOrgModel(userId, orgId);
		if (sysUserOrg.getIsCharge() == SysUserOrg.CHARRGE_YES) {
			SysOrg sysOrgParent = (SysOrg) this.sysOrgDao.getById(orgId);

			if ((BeanUtils.isNotEmpty(getLeaderByOrgId(sysOrgParent
					.getOrgSupId())))
					&& (StringUtil.isNotEmpty((String) getLeaderByOrgId(
							sysOrgParent.getOrgSupId()).get(0)))) {
				uId = Long.valueOf(Long.parseLong((String) getLeaderByOrgId(
						sysOrgParent.getOrgSupId()).get(0)));
				return this.positionDao.getPosByUserId(uId).getPosName();
			}

		} else if ((BeanUtils.isNotEmpty(getLeaderByOrgId(orgId)))
				&& (StringUtil.isNotEmpty((String) getLeaderByOrgId(orgId).get(
						0)))) {
			uId = Long.valueOf(Long.parseLong((String) getLeaderByOrgId(orgId)
					.get(0)));
			return this.positionDao.getPosByUserId(uId).getPosName();
		}

		return null;
	}

	private List<String> getLeaderByOrgId(Long orgId) {
		List<SysUserOrg> list = this.dao.getChargeByOrgId(orgId);
		if (BeanUtils.isNotEmpty(list)) {
			List users = new ArrayList();
			for (SysUserOrg sysUserOrg : list) {
				users.add(sysUserOrg.getUserId().toString());
			}
			return users;
		}

		SysOrg sysOrg = (SysOrg) this.sysOrgDao.getById(orgId);
		if (sysOrg == null)
			return new ArrayList();
		Long parentOrgId = sysOrg.getOrgSupId();
		SysOrg sysOrgParent = (SysOrg) this.sysOrgDao.getById(parentOrgId);
		if (sysOrgParent == null) {
			return new ArrayList();
		}

		return getLeaderByOrgId(parentOrgId);
	}
}
