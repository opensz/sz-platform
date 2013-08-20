package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.Dictionary;

public interface DictionaryDao  extends BaseDao<Dictionary> {

	List<Dictionary> getByTypeId(long typeId);

	List<Dictionary> getByNodePath(String nodePath);

	void delByTypeId(Long typeId);

	boolean isItemKeyExists(long typeId, String itemKey);

	boolean isItemKeyExistsForUpdate(long dicId, long typeId, String itemKey);

	void updSn(Long dicId, Integer sn);

}