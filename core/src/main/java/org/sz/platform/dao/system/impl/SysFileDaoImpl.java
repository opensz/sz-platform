package org.sz.platform.dao.system.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.dao.system.SysFileDao;
import org.sz.platform.model.system.SysFile;

@Repository("sysFileDao")
public class SysFileDaoImpl extends BaseDaoImpl<SysFile> implements SysFileDao {
	public Class getEntityClass() {
		return SysFile.class;
	}

	public List<SysFile> getFileAttch(QueryFilter fileter) {
		return getBySqlKey("getAllPersonalFile", fileter);
	}

	public SysFile getByFilePath(String filePath) {
		return (SysFile) getUnique("getByFilePath", filePath);
	}
}
