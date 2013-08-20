package org.sz.platform.dao.system;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SysTypeKey;

public interface SysTypeKeyDao extends BaseDao<SysTypeKey> {

	SysTypeKey getByKey(String key);

	boolean isExistKey(String typeKey);

	boolean isKeyExistForUpdate(String typeKey, Long typeId);

	void updateSequence(Long typeId, int sn);

}