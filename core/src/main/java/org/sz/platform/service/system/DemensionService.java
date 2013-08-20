package org.sz.platform.service.system;

import java.util.List;
import java.util.Map;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.model.system.Demension;

public interface DemensionService extends BaseService<Demension>{

	boolean getNotExists(Map params);

	List<Demension> getDemenByQuery(QueryFilter queryFilter);

}