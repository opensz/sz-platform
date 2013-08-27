package org.sz.core.valid;

import java.util.ArrayList;
import java.util.List;

import org.sz.core.valid.ValidField;

public class ValidForm {
	private String formName = "";

	private List<ValidField> listField = new ArrayList();

	public String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public List<ValidField> getListField() {
		return this.listField;
	}

	public void setListField(List<ValidField> listField) {
		this.listField = listField;
	}

	public void addField(ValidField field) {
		this.listField.add(field);
	}
}
