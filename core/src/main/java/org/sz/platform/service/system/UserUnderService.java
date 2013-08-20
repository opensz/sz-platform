package org.sz.platform.service.system;

import java.util.List;
import java.util.Set;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.UserUnder;

public interface UserUnderService extends BaseService<UserUnder> {

	List<UserUnder> getMyUnderUser(Long userId);

	void addMyUnderUser(Long userId, String userIds, String userNames)
			throws Exception;

	Set<String> getMyUnderUserId(Long userId);

	Set<String> getMyLeader(Long userId);

}