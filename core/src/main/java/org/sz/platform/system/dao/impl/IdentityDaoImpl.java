package org.sz.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.IdentityDao;
import org.sz.platform.system.model.Identity;

@Repository("identityDao")
public class IdentityDaoImpl extends BaseDaoImpl<Identity> implements
		IdentityDao {
	public Class getEntityClass() {
		return Identity.class;
	}

	public Identity getByAlias(String alias) {
		String statment = getIbatisMapperNamespace() + ".getByAlias_"
				+ getDbType();
		Identity obj = (Identity) getSqlSessionTemplate().selectOne(statment,
				alias);
		return obj;
	}

	public boolean isAliasExisted(String alias) {
		return ((Integer) getOne("isAliasExisted", alias)).intValue() > 0;
	}

	public boolean isAliasExistedByUpdate(Identity indetity) {
		return ((Integer) getOne("isAliasExistedByUpdate", indetity))
				.intValue() > 0;
	}

	public List<Identity> getList() {
		return getBySqlKey("getList");
	}
}
