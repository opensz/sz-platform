package org.sz.platform.bpm.service.form.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.form.BpmFormFieldDao;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.service.form.BpmFormFieldService;

@Service("bpmFormFieldService")
public class BpmFormFieldServiceImpl extends BaseServiceImpl<BpmFormField>
		implements BpmFormFieldService {

	@Resource
	private BpmFormFieldDao dao;

	protected IEntityDao<BpmFormField, Long> getEntityDao() {
		return this.dao;
	}

	public List<BpmFormField> getByTableId(Long tableId) {
		return this.dao.getByTableId(tableId);
	}

	public List<BpmFormField> getAllByTableId(Long tableId) {
		return this.dao.getAllByTableId(tableId);
	}

	public List<BpmFormField> getFlowVarByFlowDefId(Long defId) {
		return this.dao.getFlowVarByFlowDefId(defId);
	}

	public List<BpmFormField> getByTableIdAndValueForm(Long tableId,
			Short valueFrom) {
		return this.dao.getByTableIdAndValueForm(tableId, valueFrom);
	}

}
