 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.form.BpmTableTemplateDao;
import org.sz.platform.bpm.dao.form.BpmTableTemprightsDao;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.bpm.service.form.BpmTableTemplateService;
 
 @Service("bpmTableTemplateService")
 public class BpmTableTemplateServiceImpl extends BaseServiceImpl<BpmTableTemplate> implements BpmTableTemplateService
 {
 
   @Resource
   private BpmTableTemplateDao dao;
 
   @Resource
   private BpmTableTemprightsDao bpmTableTemprightsDao;
 
   public List<BpmTableTemplate> getList(QueryFilter filter)
   {
     return this.dao.getList(filter);
   }
 
   protected IEntityDao<BpmTableTemplate, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void delByIds(Long[] ids) {
     if ((ids == null) || (ids.length == 0)) return;
     for (Long id : ids)
       delByTempId(id);
   }
 
   private void delByTempId(Long id)
   {
     this.dao.delById(id);
     this.bpmTableTemprightsDao.delByTemplateId(id);
   }
 
   public List<BpmTableTemplate> getByUserIdFilter(QueryFilter queryFilter)
   {
     return this.dao.getByUserIdFilter(queryFilter);
   }
 }

