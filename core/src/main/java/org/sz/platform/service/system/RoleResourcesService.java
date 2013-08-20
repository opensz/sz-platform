package org.sz.platform.service.system;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.RoleResources;

public interface RoleResourcesService extends BaseService<RoleResources>{

	void update(Long systemId, Long roleId, Long[] resIds) throws Exception;

}