 package org.sz.platform.service.system.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.ResourcesUrlDao;
import org.sz.platform.model.system.ResourcesUrl;
import org.sz.platform.service.system.ResourcesUrlService;
 
 @Service("resourcesUrlService")
 public class ResourcesUrlServiceImpl extends BaseServiceImpl<ResourcesUrl> implements ResourcesUrlService
 {
 
   @Resource
   private ResourcesUrlDao resourcesUrlDao;
 
   protected IEntityDao<ResourcesUrl, Long> getEntityDao()
   {
     return this.resourcesUrlDao;
   }
 
   public List<ResourcesUrl> getByResId(long resId)
   {
     return this.resourcesUrlDao.getByResId(resId);
   }
 
   public void update(long resId, List<ResourcesUrl> resourcesUrlList) {
     this.resourcesUrlDao.delByResId(resId);
     if ((resourcesUrlList != null) && (resourcesUrlList.size() > 0))
       for (ResourcesUrl url : resourcesUrlList)
         add(url);
   }
 }

