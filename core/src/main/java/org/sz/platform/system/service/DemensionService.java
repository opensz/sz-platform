package org.sz.platform.system.service;

import java.util.List;
import java.util.Map;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.system.model.Demension;

public interface DemensionService extends BaseService<Demension> {

	boolean getNotExists(Map params);

	List<Demension> getDemenByQuery(QueryFilter queryFilter);

}