package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.Dictionary;

public interface DictionaryService extends BaseService<Dictionary>{

	List<Dictionary> getByNodeKey(String nodeKey);

	List<Dictionary> getByTypeId(long typeId, boolean needRoot);

	void delByDicId(Long dicId);

	boolean isItemKeyExists(long typeId, String itemKey);

	boolean isItemKeyExistsForUpdate(long dicId, long typeId, String itemKey);

	void updSn(Long[] lAryId);

	void move(Long targetId, Long dragId, String moveType);

}