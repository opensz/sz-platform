package org.sz.platform.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.dao.DemensionDao;
import org.sz.platform.system.model.Demension;

@Repository("demensionDao")
public class DemensionDaoImpl extends BaseDaoImpl<Demension> implements
		DemensionDao {
	public Class getEntityClass() {
		return Demension.class;
	}

	public boolean getNotExists(Map params) {
		int cnt = ((Integer) getOne("getExists", params)).intValue();
		return cnt == 0;
	}

	public List<Demension> getDemenByQuery(QueryFilter queryFilter) {
		return getBySqlKey("getDemenByQuery", queryFilter);
	}
}
