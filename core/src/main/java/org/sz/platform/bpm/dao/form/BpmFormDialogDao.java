package org.sz.platform.bpm.dao.form;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.form.BpmFormDialog;

public interface BpmFormDialogDao extends BaseDao<BpmFormDialog>{

	BpmFormDialog getByAlias(String alias);

	Integer isExistAlias(String alias);

	Integer isExistAliasForUpd(Long id, String alias);

}