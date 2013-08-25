package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.model.SysFile;

public interface SysFileDao extends BaseDao<SysFile> {

	List<SysFile> getFileAttch(QueryFilter fileter);

	SysFile getByFilePath(String filePath);

}