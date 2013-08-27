package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.UserPositionDao;
import org.sz.platform.system.model.UserPosition;

@Repository("userPositionDao")
public class UserPositionDaoImpl extends BaseDaoImpl<UserPosition> implements
		UserPositionDao {
	public Class getEntityClass() {
		return UserPosition.class;
	}

	public List<UserPosition> getByPosId(Long posId) {
		List list = getBySqlKey("getByPosId", posId);
		return list;
	}

	public void delByPosId(Long posId) {
		delBySqlKey("delByPosId", posId);
	}

	public UserPosition getUserPosModel(Long userId, Long posId) {
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("posId", posId);
		UserPosition userPosition = (UserPosition) getUnique("getUserPosModel",
				params);
		return userPosition;
	}

	public int delUserPosByIds(Long userId, Long posId) {
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("posId", posId);
		int affectCount = delBySqlKey("delUserPosByIds", param);

		return affectCount;
	}

	public List<Long> getUserIdsByPosId(Long posId) {
		List list = getBySqlKey("getUserIdsByPosId", posId);
		return list;
	}

	public void delByUserId(Long userId) {
		delBySqlKey("delByUserId", userId);
	}

	public List<UserPosition> getByUserId(Long userId) {
		return getBySqlKey("getByUserId", userId);
	}

	public void updNotPrimaryByUser(Long userId) {
		update("updNotPrimaryByUser", userId);
	}

	public UserPosition getMainPositionByUserId(Long userId) {
		return (UserPosition) getUnique("getMainPositionByUserId", userId);
	}
}
