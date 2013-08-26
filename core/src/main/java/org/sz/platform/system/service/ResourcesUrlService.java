package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.ResourcesUrl;

public interface ResourcesUrlService extends BaseService<ResourcesUrl> {

	List<ResourcesUrl> getByResId(long resId);

	void update(long resId, List<ResourcesUrl> resourcesUrlList);

}