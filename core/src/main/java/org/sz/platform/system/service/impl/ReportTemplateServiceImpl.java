package org.sz.platform.system.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.ReportTemplateDao;
import org.sz.platform.system.model.ReportTemplate;
import org.sz.platform.system.service.ReportTemplateService;

@Service("reportTemplateService")
public class ReportTemplateServiceImpl extends BaseServiceImpl<ReportTemplate>
		implements ReportTemplateService {

	@Resource
	private ReportTemplateDao dao;

	protected IEntityDao<ReportTemplate, Long> getEntityDao() {
		return this.dao;
	}

	public void saveReportTemplate(ReportTemplate reportTemplate,
			String localPath, Date createTime) throws Exception {
		if (reportTemplate.getReportId() == null) {
			reportTemplate.setReportId(Long.valueOf(UniqueIdUtil.genId()));
			reportTemplate.setReportLocation(localPath);
			reportTemplate.setCreateTime(createTime);
			reportTemplate.setUpdateTime(createTime);
			add(reportTemplate);
		} else {
			reportTemplate.setReportLocation(localPath);
			reportTemplate.setCreateTime(createTime);
			reportTemplate.setUpdateTime(new Date());
			update(reportTemplate);
		}
	}
}
