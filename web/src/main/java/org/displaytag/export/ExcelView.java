package org.displaytag.export;

import org.apache.commons.lang.StringUtils;
import org.displaytag.model.TableModel;

public class ExcelView extends BaseExportView {
	public void setParameters(TableModel tableModel, boolean exportFullList,
			boolean includeHeader, boolean decorateValues) {
		super.setParameters(tableModel, exportFullList, includeHeader,
				decorateValues);
	}

	public String getMimeType() {
		return "application/vnd.ms-excel;charset=GB2312";
	}

	protected String getRowEnd() {
		return "\n";
	}

	protected String getCellEnd() {
		return "\t";
	}

	protected boolean getAlwaysAppendCellEnd() {
		return false;
	}

	protected boolean getAlwaysAppendRowEnd() {
		return false;
	}

	protected String escapeColumnValue(Object value) {
		if (value != null) {
			return "\""
					+ StringUtils.replace(StringUtils.trim(value.toString()),
							"\"", "\"\"") + "\"";
		}

		return null;
	}
}
