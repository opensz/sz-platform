package org.sz.platform.bpm.service.form;

import java.util.Map;

public interface BpmFormControlService {

	String getField(String fieldName, String html,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission);

	String getField(String fieldName, String html,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission,String tagHtml);
	
	String getFieldRight(String fieldName,
			Map<String, Map<String, Map>> permission);

	String getFieldValue(String fieldName, Map<String, Map<String, Map>> model);

	String getOpinion(String opinionName, String html,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission);

	String getRdoChkBox(String fieldName, String html, String ctlVal,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission);

	String getRdoChkBox(String fieldName, String html, String ctlVal,
			Map<String, Object> table);

}