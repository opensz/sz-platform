package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.ResourcesUrl;

public interface ResourcesUrlService extends BaseService<ResourcesUrl>{

	List<ResourcesUrl> getByResId(long resId);

	void update(long resId, List<ResourcesUrl> resourcesUrlList);

}