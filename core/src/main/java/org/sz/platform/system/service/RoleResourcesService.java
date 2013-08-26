package org.sz.platform.system.service;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.RoleResources;

public interface RoleResourcesService extends BaseService<RoleResources> {

	void update(Long systemId, Long roleId, Long[] resIds) throws Exception;

}