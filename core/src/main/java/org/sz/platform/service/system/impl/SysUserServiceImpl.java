package org.sz.platform.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.encrypt.EncryptUtil;
import org.sz.core.model.LabelValue;
import org.sz.core.model.OnlineUser;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.PinyinUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.SysUserDao;
import org.sz.platform.model.system.Demension;
import org.sz.platform.model.system.Position;
import org.sz.platform.model.system.SysOrg;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.model.system.SysUserOrg;
import org.sz.platform.service.system.ParamSearch;
import org.sz.platform.service.system.PositionService;
import org.sz.platform.service.system.SecurityUtil;
import org.sz.platform.service.system.SysOrgService;
import org.sz.platform.service.system.SysUserOrgService;
import org.sz.platform.service.system.SysUserService;
import org.sz.platform.service.system.UserPositionService;
import org.sz.platform.service.system.UserRoleService;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements
		SysUserService {

	@Resource
	private SysUserDao dao;

//	@Resource
//	private BpmNodeUserUplowService bpmNodeUserUplowService;

	@Resource
	private PositionService positionService;

	@Resource
	private SysOrgService sysOrgService;

//	@Resource
//	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private SysUserOrgService sysUserOrgService;

	@Resource
	private UserPositionService userPositionService;

	@Resource
	private UserRoleService userRoleService;

	protected IEntityDao<SysUser, Long> getEntityDao() {
		return this.dao;
	}

	public SysUser getByAccount(String account, Long orgId) {
		return this.dao.getByAccount(account, orgId);
	}

	public SysUser getByAccount(String account) {
		return this.dao.getByAccount(account);
	}

	public List<SysUser> getUserByOrgId(QueryFilter queryFilter) {
		return this.dao.getUserByOrgId(queryFilter);
	}

	public List<SysUser> getUserByQuery(QueryFilter queryFilter) {
		return this.dao.getUserByQuery(queryFilter);
	}

	public List<Long> getUserIdsByRoleId(Long roleId) {
		return this.dao.getUserIdsByRoleId(roleId);
	}

	public List<SysUser> getUserByRoleId(QueryFilter queryFilter) {
		return this.dao.getUserByRoleId(queryFilter);
	}

	public List<SysUser> getUserNoOrg(QueryFilter queryFilter) {
		return this.dao.getUserNoOrg(queryFilter);
	}

	public List<SysUser> getDistinctUserByPosPath(QueryFilter queryFilter) {
		return this.dao.getDistinctUserByPosPath(queryFilter);
	}

	public List<SysUser> getDistinctUserByOrgPath(QueryFilter queryFilter) {
		return this.dao.getDistinctUserByOrgPath(queryFilter);
	}

	public boolean isAccountExist(String account) {
		return this.dao.isAccountExist(account);
	}

	public boolean isAccountExistForUpd(Long userId, String account) {
		return this.dao.isAccountExistForUpd(userId, account);
	}

	public List<SysUser> getByUserParam(String userParam) throws Exception {
		ParamSearch search = new ParamSearch() {
			public List getFromDataBase(Map property) {
				return SysUserServiceImpl.this.dao.getByOrgOrParam(property);
			}
		};
		return search.getByParam(userParam);
	}

	public List<SysUser> getByOrgParam(String userParam) throws Exception {
		ParamSearch search = new ParamSearch() {
			public List getFromDataBase(Map property) {
				return SysUserServiceImpl.this.dao.getByOrgOrParam(property);
			}
		};
		return search.getByParam(userParam);
	}

	public List<SysUser> getByParam(long nodeUserId) throws Exception {
		List list = null;
//		BpmNodeUser bpmNodeUser = (BpmNodeUser) this.bpmNodeUserService
//				.getById(Long.valueOf(nodeUserId));
//		Short ssignType = bpmNodeUser.getAssignType();
//		String param = bpmNodeUser.getCmpIds();
//		if (ssignType.shortValue() == 7)
//			list = getByUserParam(param);
//		else if (ssignType.shortValue() == 8) {
//			list = getByOrgParam(param);
//		}
		return list;
	}

	public List<SysUser> getByUserIdAndUplow(long userId, long nodeUserId) {
//		List uplowList = this.bpmNodeUserUplowService
//				.getByNodeUserId(nodeUserId);
//		return getByUserIdAndUplow(userId, uplowList);
		return null;
	}

	public List<SysUser> getByUserIdAndUplow(long userId, List uplowList) {return null;}
//	public List<SysUser> getByUserIdAndUplow(long userId, List<BpmNodeUserUplow> uplowList) {
//		if (uplowList == null)
//			return null;
//		List list = new ArrayList();
//
//		List<Position> pl = null;
//
//		List<SysOrg> ol = null;
//		for (BpmNodeUserUplow uplow : uplowList) {
//			Short upLowType = uplow.getUpLowType().shortValue();
//			Integer upLowLevel = uplow.getUpLowLevel().intValue();
//			if (uplow.getDemensionId().longValue() == Demension.positionDem
//					.getDemId().longValue()) {
//				if (pl == null)
//					pl = this.positionService.getByUserId(Long.valueOf(userId));
//				if (pl != null)
//					for (Position p : pl) {
//						String currentPath = p.getNodePath();
//						int currentDepth = p.getDepth().intValue();
//						Map param = handlerCondition(currentPath, currentDepth,
//								upLowType, upLowLevel);
//
//						List l = this.dao.getUpLowPost(param);
//						list.addAll(l);
//					}
//			} else {
//				Long demensionId = uplow.getDemensionId().longValue();
//				if (ol == null)
//					ol = this.sysOrgService.getByUserIdAndDemId(
//							Long.valueOf(userId), Long.valueOf(demensionId));
//				if (ol != null)
//					for (SysOrg o : ol) {
//						String currentPath = o.getPath();
//						int currentDepth = o.getDepth().intValue();
//						Map param = handlerCondition(currentPath, currentDepth,
//								upLowType, upLowLevel);
//						param.put("demensionId", Long.valueOf(demensionId));
//
//						List l = this.dao.getUpLowOrg(param);
//						list.addAll(l);
//					}
//			}
//		}
//		short upLowType;
//		int upLowLevel;
//		long demensionId;
//		return list;
//	}

	private static Map<String, Object> handlerCondition(String currentPath,
			int currentDepth, short upLowType, int upLowLevel) {
		String[] pathArr = currentPath.split("\\.");

		String path = null;
		Integer depth = null;

		depth = Integer.valueOf(currentDepth + upLowType * upLowLevel * -1
				+ upLowType);
		path = coverArray2Str(pathArr, depth.intValue());

		Map returnMap = new HashMap();
		returnMap.put("path", path);
		returnMap.put("depth", depth);
		String condition = "=";

		switch (upLowType) {
		case 1:
			condition = "<";
			break;
		case -1:
			condition = ">";
			break;
		case 0:
			condition = "=";
		}

		returnMap.put("condition", condition);
		return returnMap;
	}

	private static String coverArray2Str(String[] pathArr, int len) {
		if (len < 0)
			return pathArr[0];
		if (len > pathArr.length) {
			len = pathArr.length;
		}
		StringBuilder sb = new StringBuilder();
		if (pathArr.length > 1) {
			int i = 0;
			do {
				sb.append(pathArr[i]);
				sb.append(".");
				i++;
			} while (i < len);

			sb = sb.delete(sb.length() - 1, sb.length());
		} else if (pathArr.length > 0) {
			sb = sb.append(pathArr[0]);
		}
		return sb.toString();
	}

	public List<SysUser> getOnlineUser(List<SysUser> list) {
		List listOnl = new ArrayList();
//		Map<Long, OnlineUser> onlineUsers = AppUtil.getOnlineUsers();
//		List<OnlineUser> onlineList = new ArrayList();
//		for (Long sessionId : onlineUsers.keySet()) {
//			OnlineUser onlineUser = (OnlineUser) onlineUsers.get(sessionId);
//
//			onlineList.add(onlineUser);
//		}
//		for (Iterator i$ = list.iterator(); i$.hasNext();) {
//			SysUser sysUser = (SysUser) i$.next();
//			for (OnlineUser onlineUser : onlineList) {
//				Long sysUserId = sysUser.getUserId();
//				Long onlineUserId = onlineUser.getUserId();
//				if (sysUserId.toString().equals(onlineUserId.toString()))
//					listOnl.add(sysUser);
//			}
//		}
		return listOnl;
	}

	public List<SysUser> getByIdSet(Set uIds) {
		return this.dao.getByIdSet(uIds);
	}

	/**
	 * 根据角色ID返回要发送人员的 account,用”,“分隔
	 * 
	 * @param roleId
	 * @return
	 */
	public String getReceiversByRoleId(Long roleId) {
		List<SysUser> list = this.dao.getByRoleId(roleId);
		List<String> accountList = new ArrayList<String>();
		if (BeanUtils.isNotEmpty(list)) {
			for (SysUser sysUser : list) {
				accountList.add(sysUser.getAccount());
			}
		}
		if (accountList.size() > 0) {
			return StringUtils.join(accountList, ",");
		}
		return null;
	}

	/**
	 * 根据用户ID 返回领导account
	 * 
	 * @param userId
	 * @return
	 */
	public String getReceiversByMyLeader(Long userId) {
		List<String> uIds = sysUserOrgService.getLeaderByUserId(userId);
		List<String> accountList = new ArrayList<String>();
		if (uIds != null && uIds.size() > 0) {
			for (String uId : uIds) {
				SysUser sysUser = this.getById(Long.valueOf(uId));
				if (sysUser != null) {
					accountList.add(sysUser.getAccount());
				}
			}
		}
		if (accountList.size() > 0) {
			return StringUtils.join(accountList, ",");
		}
		return null;
	}

	public SysUser getByMail(String address) {
		return this.dao.getByMail(address);
	}

	public void updPwd(Long userId, String pwd) {
		String enPassword = EncryptUtil.encryptSha256(pwd);
		this.dao.updPwd(userId, enPassword);
	}

	public void updStatus(Long userId, Short status, Short isLock) {
		this.dao.updStatus(userId, status, isLock);
	}

	public Long saveUser(Integer bySelf, SysUser sysUser, Long[] aryOrgIds,
			Long[] orgIdCharge, Long[] arrIsDept, Long orgIdPrimary,
			Long deptIdPrimary, Long[] posIds, Long posIdPrimary, Long[] roleIds)
			throws Exception {
		Long userId = 0L;
		if (sysUser.getUserId() == null) {
			sysUser.setUserId(Long.valueOf(UniqueIdUtil.genId()));
			this.dao.add(sysUser);
		} else {
			this.dao.update(sysUser);
		}
		if (bySelf.intValue() == 0) {
			userId = sysUser.getUserId();

			this.sysUserOrgService.saveUserOrg(userId, aryOrgIds, orgIdPrimary,
					deptIdPrimary, orgIdCharge, arrIsDept);

			this.userPositionService.saveUserPos(userId, posIds, posIdPrimary);

			this.userRoleService.saveUserRole(userId, roleIds);

			SecurityUtil.removeUserRoleCache(userId);
		}
		return userId;
	}

	@Override
	public Long saveUser(String fullName, String userName, String password,
			Long orgId) throws Exception {
		SysUser sysUser = new SysUser();
		sysUser.setUserId(Long.valueOf(UniqueIdUtil.genId()));
		sysUser.setAccount(userName);
		String enPassword = EncryptUtil.encryptSha256(password);
		sysUser.setCreatetime(new Date());
		String spell = PinyinUtil.getFirstSpell(fullName);
		sysUser.setFullname(fullName);
		sysUser.setFirstSpell(spell);
		sysUser.setStatus((short) 1);
		sysUser.setPassword(enPassword);
		sysUser.setIsExpired((short) 0);
		sysUser.setIsLock((short) 0);
		sysUser.setDesc("");
		this.dao.add(sysUser);

		SysUserOrg sysUserOrg = new SysUserOrg();
		sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
		sysUserOrg.setOrgId(orgId);
		sysUserOrg.setUserId(sysUser.getUserId());
		sysUserOrg.setIsPrimary((short) 1);
		this.sysUserOrgService.add(sysUserOrg);

		return sysUserOrg.getUserId();
	}


	@Override
	public void updStatus(Long[] userIds, Long subSystemId) {
		Short status = -1;
		try {
			this.dao.updStatus(userIds, status);
//			this.dao.delDcomUserAndShift(userIds, subSystemId);
		} catch (Exception f) {
			f.printStackTrace();
		}
	}

}
