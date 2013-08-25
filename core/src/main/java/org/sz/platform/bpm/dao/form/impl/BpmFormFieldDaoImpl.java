package org.sz.platform.bpm.dao.form.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.form.BpmFormFieldDao;
import org.sz.platform.bpm.model.form.BpmFormField;

@Repository("bpmFormFieldDao")
public class BpmFormFieldDaoImpl extends BaseDaoImpl<BpmFormField> implements
		BpmFormFieldDao {
	public Class getEntityClass() {
		return BpmFormField.class;
	}

	public List<BpmFormField> getByTableId(Long tableId) {
		return getBySqlKey("getByTableId", tableId);
	}

	public List<BpmFormField> getAllByTableId(Long tableId) {
		return getBySqlKey("getAllByTableId", tableId);
	}

	public int markDeletedByTableId(Long tableId) {
		return update("markDeletedByTableId", tableId);
	}

	public List<BpmFormField> getFlowVarByFlowDefId(Long defId) {
		return getBySqlKey("getFlowVarByFlowDefId", defId);
	}

	public void delByTableId(Long tableId) {
		delBySqlKey("delByTableId", tableId);
	}

	public Boolean hasFieldNameByTableId(Long tableId, String fieldName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableId", tableId);
		params.put("fieldName", fieldName);
		Object obj = this.getOne("hasFieldNameByTableId", params);
		if (obj != null) {
			if (Integer.valueOf(obj.toString()) > 0) {
				return true;
			}
		}
		return false;
	}
	
	public List<BpmFormField> getByTableIdAndValueForm(Long tableId, Short valueFrom){
		BpmFormField bpmFormField = new BpmFormField();
		bpmFormField.setTableId(tableId);
		bpmFormField.setValueFrom(valueFrom);
		return getBySqlKey("getByFormField", bpmFormField);
	}
}
