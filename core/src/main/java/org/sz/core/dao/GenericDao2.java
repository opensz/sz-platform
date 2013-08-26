package org.sz.core.dao;

import java.io.Serializable;

public interface GenericDao2<E, PK extends Serializable> extends
		IEntityDao<E, PK> {

}
