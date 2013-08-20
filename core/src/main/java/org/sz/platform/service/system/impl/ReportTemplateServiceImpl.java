 package org.sz.platform.service.system.impl;
 
  import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.ReportTemplateDao;
import org.sz.platform.model.system.ReportTemplate;
import org.sz.platform.service.system.ReportTemplateService;
 
 @Service("reportTemplateService")
 public class ReportTemplateServiceImpl extends BaseServiceImpl<ReportTemplate> implements ReportTemplateService
 {
 
   @Resource
   private ReportTemplateDao dao;
 
   protected IEntityDao<ReportTemplate, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void saveReportTemplate(ReportTemplate reportTemplate, String localPath, Date createTime)
     throws Exception
   {
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

