package org.displaytag.export;

import org.apache.commons.lang.StringUtils;
import org.displaytag.model.TableModel;

public class CsvView extends BaseExportView {
	public void setParameters(TableModel tableModel, boolean exportFullList,
			boolean includeHeader, boolean decorateValues) {
		super.setParameters(tableModel, exportFullList, includeHeader,
				decorateValues);
	}

	protected String getRowEnd() {
		return "\n";
	}

	protected String getCellEnd() {
		return ",";
	}

	protected boolean getAlwaysAppendCellEnd() {
		return false;
	}

	protected boolean getAlwaysAppendRowEnd() {
		return true;
	}

	public String getMimeType() {
		return "text/csv;charset=GB2312";
	}

	protected String escapeColumnValue(Object value) {
		String stringValue = StringUtils.trim(value.toString());
		if (!StringUtils.containsNone(stringValue, new char[] { '\n', ',' })) {
			return "\"" + StringUtils.replace(stringValue, "\"", "\\\"") + "\"";
		}

		return stringValue;
	}
}
