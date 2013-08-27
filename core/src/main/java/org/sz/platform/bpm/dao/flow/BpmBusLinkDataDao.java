package org.sz.platform.bpm.dao.flow;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmBusLinkData;

public interface BpmBusLinkDataDao extends BaseDao<BpmBusLinkData> {

	void delByActDefIdLinkData(String actDefId);

}