package org.sz.platform.system.service;

import java.util.List;
import java.util.Set;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.GlobalType;

public interface GlobalTypeService extends BaseService<GlobalType> {

	GlobalType getInitGlobalType(int isRoot, long parentId) throws Exception;

	List<GlobalType> getByParentId(Long parentId);

	void delByTypeId(Long typeId);

	List<GlobalType> getByNodePath(String nodePath);

	List<GlobalType> getByParentId(long parentId);

	boolean isNodeKeyExists(String catKey, String nodeKey);

	boolean isNodeKeyExistsForUpdate(Long typeId, String catKey, String nodeKey);

	void updSn(Long typeId, Long sn);

	void move(Long targetId, Long dragId, String moveType);

	List<GlobalType> getByCatKey(String catKey, boolean hasRoot);

	Set<GlobalType> getByBpmRightCat(Long userId, String roleIds,
			String orgIds, boolean hasRoot);

	Set<GlobalType> getByFormRightCat(Long userId, String roleIds,
			String orgIds, boolean hasRoot);

	List<GlobalType> getPersonType(String catKey, Long userId, boolean hasRoot);

	String getXmlByCatkey(String catKEY);

	GlobalType getByDictNodeKey(String nodeKey);

	List<GlobalType> getByBpmRights(String catKey, Long userId, String roleIds,
			String orgIds);

}