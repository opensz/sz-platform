package org.sz.platform.bpm.model.flow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class BpmVar {
	private String varName;
	private Object varVal;

	public BpmVar() {
	}

	public BpmVar(String varName, Object varVal) {
		this.varName = varName;
		this.varVal = varVal;
	}

	public String getVarName() {
		return this.varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public Object getVarVal() {
		return this.varVal;
	}

	public void setVarVal(Object varVal) {
		this.varVal = varVal;
	}
}
