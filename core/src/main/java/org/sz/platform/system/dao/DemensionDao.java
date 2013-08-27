package org.sz.platform.system.dao;

import java.util.List;
import java.util.Map;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.model.Demension;

public interface DemensionDao extends BaseDao<Demension>{

	boolean getNotExists(Map params);

	List<Demension> getDemenByQuery(QueryFilter queryFilter);

}