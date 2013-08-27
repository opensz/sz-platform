package org.sz.platform.system.dao;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.SysTypeKey;

public interface SysTypeKeyDao extends BaseDao<SysTypeKey> {

	SysTypeKey getByKey(String key);

	boolean isExistKey(String typeKey);

	boolean isKeyExistForUpdate(String typeKey, Long typeId);

	void updateSequence(Long typeId, int sn);

}