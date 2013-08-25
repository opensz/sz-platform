package org.sz.platform.bpm.service.form;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.form.BpmFormDialog;

public interface BpmFormDialogService extends BaseService<BpmFormDialog>{

	boolean isExistAlias(String alias);

	boolean isExistAliasForUpd(Long id, String alias);

	BpmFormDialog getByAlias(String alias);

	List getTreeData(String alias) throws Exception;

	BpmFormDialog getData(String alias, Map<String, Object> params)
			throws Exception;

}