package org.sz.platform.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.UserUnderDao;
import org.sz.platform.system.model.UserUnder;
import org.sz.platform.system.service.UserUnderService;

@Service("userUnderService")
public class UserUnderServiceImpl extends BaseServiceImpl<UserUnder> implements
		UserUnderService {

	@Resource
	private UserUnderDao dao;

	protected IEntityDao<UserUnder, Long> getEntityDao() {
		return this.dao;
	}

	public List<UserUnder> getMyUnderUser(Long userId) {
		return this.dao.getMyUnderUser(userId);
	}

	public void addMyUnderUser(Long userId, String userIds, String userNames)
			throws Exception {
		String[] idArray = userIds.split(",");
		String[] nameArray = userNames.split(",");
		UserUnder userUnder = new UserUnder();
		userUnder.setUserid(userId);
		for (int i = 0; i < idArray.length; i++) {
			userUnder.setId(Long.valueOf(UniqueIdUtil.genId()));
			userUnder.setUnderuserid(Long.valueOf(Long.parseLong(idArray[i])));
			userUnder.setUnderusername(nameArray[i]);
			if (this.dao.isExistUser(userUnder).intValue() <= 0)
				this.dao.add(userUnder);
		}
	}

	public Set<String> getMyUnderUserId(Long userId) {
		Set list = new HashSet();
		List<UserUnder> listUser = this.dao.getMyUnderUser(userId);
		for (UserUnder user : listUser) {
			list.add(user.getUnderuserid().toString());
		}
		return list;
	}

	public Set<String> getMyLeader(Long userId) {
		Set list = new HashSet();
		List<UserUnder> userList = this.dao.getMyLeader(userId);
		for (UserUnder user : userList) {
			list.add(user.getUserid().toString());
		}
		return list;
	}
}
