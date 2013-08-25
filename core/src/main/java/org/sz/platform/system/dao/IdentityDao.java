package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.Identity;

public interface IdentityDao extends BaseDao<Identity> {

	Identity getByAlias(String alias);

	boolean isAliasExisted(String alias);

	boolean isAliasExistedByUpdate(Identity indetity);

	List<Identity> getList();

}