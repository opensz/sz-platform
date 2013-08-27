package org.sz.platform.system.service;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysTypeKey;

public interface SysTypeKeyService extends BaseService<SysTypeKey> {

	SysTypeKey getByKey(String key);

	boolean isExistKey(String typeKey);

	boolean isKeyExistForUpdate(String typeKey, Long typeId);

	void saveSequence(Long[] aryTypeId);

}