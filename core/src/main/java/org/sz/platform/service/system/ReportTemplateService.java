package org.sz.platform.service.system;

import java.util.Date;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.ReportTemplate;

public interface ReportTemplateService  extends BaseService<ReportTemplate>{

	void saveReportTemplate(ReportTemplate reportTemplate, String localPath,
			Date createTime) throws Exception;

}