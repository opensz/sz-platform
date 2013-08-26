package org.sz.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.UserUnderDao;
import org.sz.platform.system.model.UserUnder;

@Repository("userUnderDao")
public class UserUnderDaoImpl extends BaseDaoImpl<UserUnder> implements
		UserUnderDao {
	public Class getEntityClass() {
		return UserUnder.class;
	}

	public List<UserUnder> getMyUnderUser(Long userId) {
		return getBySqlKey("getMyUnderUser", userId);
	}

	public Integer isExistUser(UserUnder userUnder) {
		return (Integer) getOne("isExistUser", userUnder);
	}

	public List<UserUnder> getMyLeader(Long userId) {
		return getBySqlKey("getMyLeader", userId);
	}
}
