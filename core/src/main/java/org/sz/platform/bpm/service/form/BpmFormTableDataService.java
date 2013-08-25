package org.sz.platform.bpm.service.form;

import java.util.List;
import java.util.Map;

import org.sz.platform.bpm.model.form.BpmFormData;

public interface BpmFormTableDataService {

	List<Map<String, Object>> getAll(Long tableId, Map<String, Object> param)
			throws Exception;

	BpmFormData getByKey(Long tableId, String pkValue) throws Exception;

}