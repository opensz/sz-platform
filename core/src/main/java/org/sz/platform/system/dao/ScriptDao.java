package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.Script;

public interface ScriptDao extends BaseDao<Script> {

	List<String> getDistinctCategory();

	Integer isExistWithName(String name);

}