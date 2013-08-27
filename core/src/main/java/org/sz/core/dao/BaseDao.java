package org.sz.core.dao;

import org.apache.ibatis.session.SqlSessionFactory;

public interface BaseDao<E> extends GenericDao2<E, Long> {

	SqlSessionFactory getSqlSessionFactory();

}
