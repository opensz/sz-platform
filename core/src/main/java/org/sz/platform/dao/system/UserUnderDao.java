package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.UserUnder;

public interface UserUnderDao extends BaseDao<UserUnder> {

	List<UserUnder> getMyUnderUser(Long userId);

	Integer isExistUser(UserUnder userUnder);

	List<UserUnder> getMyLeader(Long userId);

}