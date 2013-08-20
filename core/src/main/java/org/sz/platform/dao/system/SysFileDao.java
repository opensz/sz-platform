package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.model.system.SysFile;

public interface SysFileDao extends BaseDao<SysFile> {

	List<SysFile> getFileAttch(QueryFilter fileter);

	SysFile getByFilePath(String filePath);

}