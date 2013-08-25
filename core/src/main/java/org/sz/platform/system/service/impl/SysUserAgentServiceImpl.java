 package org.sz.platform.system.service.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysUserAgentDao;
import org.sz.platform.system.model.SysUserAgent;
import org.sz.platform.system.service.SysUserAgentService;
 
 @Service("sysUserAgentService")
 public class SysUserAgentServiceImpl extends BaseServiceImpl<SysUserAgent> implements SysUserAgentService
 {
 
   @Resource
   private SysUserAgentDao dao;
 
//   @Resource
//   private BpmAgentDao bpmAgentDao;
 
   protected IEntityDao<SysUserAgent, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void add(SysUserAgent sysUserAgent, List upList){}
//   public void add(SysUserAgent sysUserAgent, List<BpmAgent> upList)
//   {
//     add(sysUserAgent);
//     if ((sysUserAgent.getIsall().intValue() != SysUserAgent.IS_ALL_FLAG) && (upList != null) && (upList.size() > 0))
//     {
//       for (BpmAgent up : upList) {
//         up.setAgentid(sysUserAgent.getAgentid());
//         up.setAgentuserid(sysUserAgent.getAgentuserid());
//         up.setTouserid(sysUserAgent.getTouserid());
//         this.bpmAgentDao.add(up);
//       }
//     }
//   }
 
   public void update(SysUserAgent sysUserAgent, List upList){}
//   public void update(SysUserAgent sysUserAgent, List<BpmAgent> upList)
//   {
//     update(sysUserAgent);
//     this.bpmAgentDao.delByAgentId(sysUserAgent.getAgentid());
//     if ((sysUserAgent.getIsall().intValue() != SysUserAgent.IS_ALL_FLAG) && (upList != null) && (upList.size() > 0))
//     {
//       for (BpmAgent up : upList) {
//         up.setAgentid(sysUserAgent.getAgentid());
//         up.setAgentuserid(sysUserAgent.getAgentuserid());
//         up.setTouserid(sysUserAgent.getTouserid());
//         this.bpmAgentDao.add(up);
//       }
//     }
//   }
 
   public List<SysUserAgent> getByTouserId(Long userId) {
     return this.dao.getByTouserId(userId);
   }
 }

