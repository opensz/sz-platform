package org.displaytag.export;

import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.model.TableModel;

public class XmlView extends BaseExportView {
	public void setParameters(TableModel tableModel, boolean exportFullList,
			boolean includeHeader, boolean decorateValues) {
		super.setParameters(tableModel, exportFullList, includeHeader,
				decorateValues);
	}

	protected String getRowStart() {
		return "<row>\n";
	}

	protected String getRowEnd() {
		return "</row>\n";
	}

	protected String getCellStart() {
		return "<column>";
	}

	protected String getCellEnd() {
		return "</column>\n";
	}

	protected String getDocumentStart() {
		return "<?xml version=\"1.0\"?>\n<table>\n";
	}

	protected String getDocumentEnd() {
		return "</table>\n";
	}

	protected boolean getAlwaysAppendCellEnd() {
		return true;
	}

	protected boolean getAlwaysAppendRowEnd() {
		return true;
	}

	public String getMimeType() {
		return "text/xml;charset=GB2312";
	}

	protected String escapeColumnValue(Object value) {
		return StringEscapeUtils.escapeXml(value.toString());
	}
}
