package org.sz.platform.bpm.service.form.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.service.form.BpmFormControlService;

@Service("bpmFormControlService")
public class BpmFormControlServiceImpl implements BpmFormControlService {
	public String getField(String fieldName, String html,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission) {
		fieldName = fieldName.toLowerCase();

		Object object = ((Map) model.get("main")).get(fieldName);
		Object objectId = ((Map) model.get("main")).get(fieldName + "id");
		String value = "";
		String valueId = "";
		if (object != null) {
			value = object.toString();
		}
		
		if(objectId != null){
			valueId = objectId.toString();
		}

		if ((((Map) permission.get("field")).get(fieldName) == null)
				|| ("w".equals(((Map) permission.get("field")).get(fieldName)))) {
			if (objectId != null) {
				html = html.replaceAll("#valueId", valueId);
			}
			else{
				html = html.replaceAll("#valueId", "");
			}
			return html.replaceAll("#value", value);
		}
		if ("r".equals(((Map) permission.get("field")).get(fieldName))) {
			return value;
		}
		return "";
	}
	
	public String getField(String fieldName, String html,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission,String htmlTag) {
		fieldName = fieldName.toLowerCase();

		Object object = ((Map) model.get("main")).get(fieldName);
		String value = "";
		if (object != null) {
			value = object.toString();
		}

		if ((((Map) permission.get("field")).get(fieldName) == null)
				|| ("w".equals(((Map) permission.get("field")).get(fieldName))))
			return html.replaceAll("#value", value);
		if ("r".equals(((Map) permission.get("field")).get(fieldName)) && htmlTag.indexOf("div")>-1) {
			//Parser parser = Parser.createParser(html, "utf-8");
			return value;
		}
		return "";
	}

	public String getFieldRight(String fieldName,
			Map<String, Map<String, Map>> permission) {
		fieldName = fieldName.toLowerCase();

		if ((((Map) permission.get("field")).get(fieldName) == null)
				|| ("w".equals(((Map) permission.get("field")).get(fieldName))))
			return "w";
		if ("r".equals(((Map) permission.get("field")).get(fieldName))) {
			return "r";
		}
		return "no";
	}

	public String getFieldValue(String fieldName,
			Map<String, Map<String, Map>> model) {
		fieldName = fieldName.toLowerCase();

		Object object = ((Map) model.get("main")).get(fieldName);
		String value = "";
		if (object != null) {
			value = object.toString();
		}
		return value;
	}

	public String getOpinion(String opinionName, String html,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission) {
		opinionName = opinionName.toLowerCase();

		Object object = ((Map) model.get("opinion")).get(opinionName);
		String value = "";
		if (object != null) {
			value = object.toString();
		}
		if ((((Map) permission.get("opinion")).get(opinionName) == null)
				|| ("w".equals(((Map) permission.get("opinion"))
						.get(opinionName)))) {
			return value + html;
		}
		if ("r".equals(((Map) permission.get("opinion")).get(opinionName))) {
			return value;
		}
		return "";
	}

	public String getRdoChkBox(String fieldName, String html, String ctlVal,
			Map<String, Map<String, Map>> model,
			Map<String, Map<String, Map>> permission) {
		fieldName = fieldName.toLowerCase();
		Object object = ((Map) model.get("main")).get(fieldName);
		String value = "";
		if (object != null) {
			value = object.toString();
		}
		if ((((Map) permission.get("field")).get(fieldName) == null)
				|| ("w".equals(((Map) permission.get("field")).get(fieldName)))) {
			html = html.replace("disabled=disabled", "");
			html = getHtml(html, ctlVal, value);
			return html;
		}
		if ("r".equals(((Map) permission.get("field")).get(fieldName))) {
			html = getHtml(html, ctlVal, value);
			return html;
		}

		return "";
	}

	public String getRdoChkBox(String fieldName, String html, String ctlVal,
			Map<String, Object> table) {
		String value = (String) table.get(fieldName.toLowerCase());

		html = getHtml(html, ctlVal, value);
		return html;
	}

	private String getHtml(String html, String ctlVal, String value) {
		if (StringUtil.isEmpty(value)) {
			html = html.replace("chk=1", "");
		} else {
			html = html.replace("checked=\"checked\"", "");
			if (value.contains(ctlVal)) {
				html = html.replace("chk=1", "checked=\"checked\"");
			} else {
				html = html.replace("chk=1", "");
			}
		}
		return html;
	}
}
