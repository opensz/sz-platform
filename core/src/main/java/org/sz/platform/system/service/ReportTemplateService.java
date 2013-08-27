package org.sz.platform.system.service;

import java.util.Date;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.ReportTemplate;

public interface ReportTemplateService extends BaseService<ReportTemplate> {

	void saveReportTemplate(ReportTemplate reportTemplate, String localPath,
			Date createTime) throws Exception;

}