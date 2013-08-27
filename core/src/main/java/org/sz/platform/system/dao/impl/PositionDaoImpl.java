package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.PositionDao;
import org.sz.platform.system.model.Position;

@Repository("positionDao")
public class PositionDaoImpl extends BaseDaoImpl<Position> implements
		PositionDao {
	public Class getEntityClass() {
		return Position.class;
	}

	public List<Position> getByNodePath(String nodePath) {
		Map p = new HashMap();
		p.put("nodePath", nodePath);
		return getSqlSessionTemplate().selectList(
				getIbatisMapperNamespace() + ".getAll", p);
	}

	public List<Position> getChildByParentId(long parentId) {
		return getSqlSessionTemplate().selectList(
				getIbatisMapperNamespace() + ".getByParentId",
				Long.valueOf(parentId));
	}

	public Integer getChildCountByParentId(long parentId) {
		return (Integer) getSqlSessionTemplate().selectOne(
				getIbatisMapperNamespace() + ".getChildCountByParentId",
				Long.valueOf(parentId));
	}

	public void updSn(Long posId, Short sn) {
		Map params = new HashMap();
		params.put("posId", posId);
		params.put("sn", sn);
		update("updSn", params);
	}

	public List<Position> getByUserId(Long userId) {
		return getBySqlKey("getByUserId", userId);
	}

	public Position getPosByUserId(Long userId) {
		return (Position) getUnique("getPosByUserId", userId);
	}

	public Position getDirectStartUserPos(Long userId) {
		return (Position) getUnique("getDirectStartUserPos", userId);
	}
}
