package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SysFile;

public interface SysFileService extends BaseService<SysFile> {

	List<SysFile> getFileAttch(QueryFilter fileter);

}