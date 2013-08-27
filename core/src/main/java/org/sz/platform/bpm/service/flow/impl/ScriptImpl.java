 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.sz.core.engine.IScript;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.TimeUtil;
import org.sz.platform.system.dao.PositionDao;
import org.sz.platform.system.dao.SysOrgDao;
import org.sz.platform.system.dao.SysRoleDao;
import org.sz.platform.system.model.Position;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.service.SysUserOrgService;
import org.sz.platform.system.service.UserUnderService;
 
 public class ScriptImpl
   implements IScript
 {
 
   @Resource
   private SysRoleDao sysRoleDao;
 
   @Resource
   private SysUserOrgService sysUserOrgService;
 
   @Resource
   private SysOrgDao sysOrgDao;
 
   @Resource
   private PositionDao positionDao;
 
   @Resource
   private UserUnderService userUnderService;
 
   public long getCurrentUserId()
   {
     return ContextUtil.getCurrentUserId().longValue();
   }
 
   public String getCurrentName()
   {
     return ContextUtil.getCurrentUser().getFullname();
   }
 
   public SysUser getCurrentUser()
   {
     return ContextUtil.getCurrentUser();
   }
 
   public String getCurrentDate()
   {
     return TimeUtil.getCurrentDate();
   }
 
   public List<SysUserOrg> getCurrentUserOrgs()
   {
     long userId = ContextUtil.getCurrentUserId().longValue();
     return this.sysUserOrgService.getOrgByUserId(Long.valueOf(userId));
   }
 
   public SysOrg getCurrentUserPrimaryOrg()
   {
     long userId = ContextUtil.getCurrentUserId().longValue();
     return this.sysOrgDao.getPrimaryOrgByUserId(Long.valueOf(userId));
   }
 
   public boolean hasRole(String alias)
   {
     long userId = ContextUtil.getCurrentUserId().longValue();
     List<SysRole> roleList = this.sysRoleDao.getByUserId(Long.valueOf(userId));
     for (SysRole role : roleList) {
       if (role.getAlias().equals(alias)) {
         return true;
       }
     }
     return false;
   }
 
   public List<SysRole> getCurrentUserRoles()
   {
     long userId = ContextUtil.getCurrentUserId().longValue();
     List list = this.sysRoleDao.getByUserId(Long.valueOf(userId));
     return list;
   }
 
   public String getStartUserPos(Long userId)
   {
     String posName = "";
     Position position = this.positionDao.getPosByUserId(userId);
     if (!BeanUtils.isEmpty(position)) {
       posName = position.getPosName();
     }
     return posName;
   }
 
   public String getCurUserPos()
   {
     long userId = ContextUtil.getCurrentUserId().longValue();
     String posName = getStartUserPos(Long.valueOf(userId));
     return posName;
   }
 
   public String getCurDirectLeaderPos()
   {
     String userId = ContextUtil.getCurrentUserId().toString();
     String posName = getDirectLeaderPosByUserId(userId);
     return posName;
   }
 
   public String getDirectLeaderPosByUserId(String userId)
   {
     String posName = "";
     posName = this.sysUserOrgService.getLeaderPosByUserId(Long.valueOf(Long.parseLong(userId)));
     return posName;
   }
 
   public Set<String> getDirectLeaderByUserId(String userId)
   {
     Set userSet = new HashSet();
     List<String> userList = this.sysUserOrgService.getLeaderByUserId(Long.valueOf(Long.parseLong(userId)));
     for (String user : userList) {
       userSet.add(user);
     }
     return userSet;
   }
 
   public Set<String> getMyLeader(Long userId)
   {
     return this.userUnderService.getMyLeader(userId);
   }
 
   public Set<String> getMyUnderUserId(Long userId)
   {
     return this.userUnderService.getMyUnderUserId(userId);
   }
 }

