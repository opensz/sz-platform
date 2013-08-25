package org.sz.platform.system.service;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.Position;
import org.sz.platform.system.model.UserPosition;

public interface PositionService extends BaseService<Position> {

	Position getParentPositionByParentId(long parentId);

	List<Position> getByNodePath(String nodePath);

	void updateChildrenNodePath(Position father, List<Position> childrenList);

	Map<Long, List<Position>> coverMapData(Long rootId, List<Position> instList);

	List<Position> coverTreeList(Long rootId, List<Position> instList);

	void delByIds(Long[] ids);

	void add(Position position, List<UserPosition> upList) throws Exception;

	List<Position> getChildByParentId(long parentId);

	List<Position> getAllChildByParentId(long parentId);

	void add(Position position);

	void update(Position position);

	void delById(long posId);

	void updSn(Long[] aryPosId);

	void updateIsParent(Position position);

	List<Position> getByUserId(Long userId);

}