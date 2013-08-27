package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysFile;

public interface SysFileService extends BaseService<SysFile> {

	List<SysFile> getFileAttch(QueryFilter fileter);

}