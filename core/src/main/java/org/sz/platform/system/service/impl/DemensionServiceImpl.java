package org.sz.platform.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.DemensionDao;
import org.sz.platform.system.model.Demension;
import org.sz.platform.system.service.DemensionService;

@Service("demensionService")
public class DemensionServiceImpl extends BaseServiceImpl<Demension> implements
		DemensionService {

	@Resource
	private DemensionDao dao;

	protected IEntityDao<Demension, Long> getEntityDao() {
		return this.dao;
	}

	public boolean getNotExists(Map params) {
		return this.dao.getNotExists(params);
	}

	public List<Demension> getDemenByQuery(QueryFilter queryFilter) {
		return this.dao.getDemenByQuery(queryFilter);
	}
}
