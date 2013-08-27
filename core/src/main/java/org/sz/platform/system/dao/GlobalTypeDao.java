package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.GlobalType;

public interface GlobalTypeDao extends BaseDao<GlobalType> {

	List<GlobalType> getByNodePath(String nodePath);

	List<GlobalType> getByParentId(long parentId);

	boolean isNodeKeyExists(String catKey, String nodeKey);

	boolean isNodeKeyExistsForUpdate(Long typeId, String catKey, String nodeKey);

	void updSn(Long typeId, Long sn);

	List<GlobalType> getByCatKey(String catKey);

	GlobalType getByDictNodeKey(String nodeKey);

	List<GlobalType> getPersonType(String catKey, Long userId);

	List<GlobalType> getByBpmRights(String catKey, Long userId, String roleIds,
			String orgIds);

	List<GlobalType> getByFormRights(String catKey, Long userId,
			String roleIds, String orgIds);

}