package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.Script;

public interface ScriptDao extends BaseDao<Script> {

	List<String> getDistinctCategory();

	Integer isExistWithName(String name);

}