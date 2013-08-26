package org.sz.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.ScriptDao;
import org.sz.platform.system.model.Script;

@Repository("scriptDao")
public class ScriptDaoImpl extends BaseDaoImpl<Script> implements ScriptDao {
	public Class getEntityClass() {
		return Script.class;
	}

	public List<String> getDistinctCategory() {
		List list = getBySqlKey("getDistinctCategory");
		return list;
	}

	public Integer isExistWithName(String name) {
		return (Integer) getOne("isExistWithName", name);
	}
}
