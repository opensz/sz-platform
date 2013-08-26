package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.ResourcesDao;
import org.sz.platform.system.model.Resources;
import org.sz.platform.system.model.ResourcesUrlExt;

@Repository("resourcesDao")
public class ResourcesDaoImpl extends BaseDaoImpl<Resources> implements
		ResourcesDao {
	public Class getEntityClass() {
		return Resources.class;
	}

	public List<Resources> getByParentId(long parentId) {
		return getBySqlKey("getByParentId", Long.valueOf(parentId));
	}

	public List<Resources> getBySystemId(long systemId) {
		return getBySqlKey("getBySystemId", Long.valueOf(systemId));
	}

	public List<Resources> getNormMenu(Long systemId, Long userId) {
		Map p = new HashMap();
		p.put("systemId", systemId);
		p.put("userId", userId);
		return getBySqlKey("getNormMenu", p);
	}

	public List<Resources> getSuperMenu(Long systemId) {
		return getBySqlKey("getSuperMenu", systemId);
	}

	public List<ResourcesUrlExt> getDefaultUrlAndRoleBySystemId(long systemId) {
		String stament = getIbatisMapperNamespace()
				+ ".getDefaultUrlAndRoleBySystemId";
		return getSqlSessionTemplate().selectList(stament,
				Long.valueOf(systemId));
	}

	public List<ResourcesUrlExt> getFunctionAndRoleBySystemId(long systemId) {
		String stament = getIbatisMapperNamespace()
				+ ".getFunctionAndRoleBySystemId";
		return getSqlSessionTemplate().selectList(stament,
				Long.valueOf(systemId));
	}

	public List<ResourcesUrlExt> getSubSystemFunction(String defaultUrl) {
		String stament = getIbatisMapperNamespace() + ".getSubSystemFunction";
		return getSqlSessionTemplate().selectList(stament, defaultUrl);
	}

	public List<ResourcesUrlExt> getSubSystemsFuncByAlias(String alias) {
		String stament = getIbatisMapperNamespace()
				+ ".getSubSystemsFuncByAlias";
		return getSqlSessionTemplate().selectList(stament, alias);
	}

	public List<ResourcesUrlExt> getSubSystemResources(String defaultUrl) {
		String stament = getIbatisMapperNamespace() + ".getSubSystemResources";
		return getSqlSessionTemplate().selectList(stament, defaultUrl);
	}

	public List<ResourcesUrlExt> getSubSystemResByAlias(String alias) {
		String stament = getIbatisMapperNamespace() + ".getSubSystemResByAlias";
		return getSqlSessionTemplate().selectList(stament, alias);
	}

	public Integer isAliasExists(Long systemId, String alias) {
		Map params = new HashMap();
		params.put("alias", alias);
		params.put("systemId", systemId);
		return (Integer) getOne("isAliasExists", params);
	}

	public Integer isAliasExistsForUpd(Long systemId, Long resId, String alias) {
		Map params = new HashMap();
		params.put("alias", alias);
		params.put("systemId", systemId);
		params.put("resId", resId);
		return (Integer) getOne("isAliasExistsForUpd", params);
	}
}
