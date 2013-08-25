package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.Position;

public interface PositionDao extends BaseDao<Position> {

	List<Position> getByNodePath(String nodePath);

	List<Position> getChildByParentId(long parentId);

	Integer getChildCountByParentId(long parentId);

	void updSn(Long posId, Short sn);

	List<Position> getByUserId(Long userId);

	Position getPosByUserId(Long userId);

	Position getDirectStartUserPos(Long userId);

}