package org.sz.platform.bpm.service.form;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.form.BpmFormField;

public interface BpmFormFieldService  extends BaseService<BpmFormField>{

	List<BpmFormField> getByTableId(Long tableId);

	List<BpmFormField> getAllByTableId(Long tableId);

	List<BpmFormField> getFlowVarByFlowDefId(Long defId);
	
	List<BpmFormField> getByTableIdAndValueForm(Long tableId, Short valueFrom);

}