package org.sz.core.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONStringer;

public class ResultMessage implements Serializable {
	private static final long serialVersionUID = 4431648807299228509L;

	public static final int Success = 1;
	public static final int Fail = 0;
	private int result = 1;
	private String message = "";
	private Map<String, Object> params = new HashMap<String, Object>();

	public ResultMessage() {
	}

	public ResultMessage(int result, String message) {
		this.result = result;
		this.message = message;
	}

	public ResultMessage(int result, String message, Map<String, Object> params) {
		this.result = result;
		this.message = message;
		this.params = params;
	}

	public int getResult() {
		return this.result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		JSONStringer stringer = new JSONStringer();
		stringer.object();
		stringer.key("result");
		stringer.value(this.result);
		stringer.key("message");
		stringer.value(this.message);
		if (null != this.params && this.params.size() > 0) {
			for (String key : params.keySet()) {
				if (null != key && !"".equals(key)) {
					stringer.key(key);
					stringer.value(params.get(key));
				}
			}
		}
		stringer.endObject();
		return stringer.toString();
	}
}
