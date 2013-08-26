package org.sz.platform.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysTypeKeyDao;
import org.sz.platform.system.model.SysTypeKey;
import org.sz.platform.system.service.SysTypeKeyService;

@Service("sysTypeKeyService")
public class SysTypeKeyServiceImpl extends BaseServiceImpl<SysTypeKey>
		implements SysTypeKeyService {

	@Resource
	private SysTypeKeyDao dao;

	protected IEntityDao<SysTypeKey, Long> getEntityDao() {
		return this.dao;
	}

	public SysTypeKey getByKey(String key) {
		return this.dao.getByKey(key);
	}

	public boolean isExistKey(String typeKey) {
		typeKey = typeKey.toLowerCase();
		return this.dao.isExistKey(typeKey);
	}

	public boolean isKeyExistForUpdate(String typeKey, Long typeId) {
		typeKey = typeKey.toLowerCase();
		return this.dao.isKeyExistForUpdate(typeKey, typeId);
	}

	public void saveSequence(Long[] aryTypeId) {
		for (int i = 0; i < aryTypeId.length; i++)
			this.dao.updateSequence(aryTypeId[i], i);
	}
}
