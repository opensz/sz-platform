package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.UserPosition;

public interface UserPositionService extends BaseService<UserPosition>{

	void add(Long posId, Long[] userIds) throws Exception;

	void setIsPrimary(Long userPosId);

	List<UserPosition> getByPosId(Long posId) throws Exception;

	void delByPosId(Long posId);

	UserPosition getUserPosModel(Long userId, Long posId);

	void delUserPosByIds(String[] lAryId, Long userId);

	void saveUserPos(Long userId, Long[] posIds, Long posIdPrimary)
			throws Exception;

	List<Long> getUserIdsByPosId(Long posId);

}