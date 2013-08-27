package org.sz.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.dao.SysFileDao;
import org.sz.platform.system.model.SysFile;

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
