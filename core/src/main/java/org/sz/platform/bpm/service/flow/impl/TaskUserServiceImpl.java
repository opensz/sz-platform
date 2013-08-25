 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.TaskUserDao;
import org.sz.platform.bpm.model.flow.TaskUser;
import org.sz.platform.bpm.service.flow.TaskUserService;
import org.sz.platform.system.dao.SysUserDao;
import org.sz.platform.system.model.SysUser;
 
 @Service("taskUserService")
 public class TaskUserServiceImpl extends BaseServiceImpl<TaskUser> implements TaskUserService
 {
 
   @Resource
   private TaskUserDao taskUserDao;
 
   @Resource
   private SysUserDao sysUserDao;
 
   protected IEntityDao<TaskUser, Long> getEntityDao()
   {
     return this.taskUserDao;
   }
 
   public List<TaskUser> getByTaskId(String taskId) {
     return this.taskUserDao.getByTaskId(taskId);
   }
 
   public Set<SysUser> getUserCandidateUsers(String taskId)
   {
     Set taskUserSet = new HashSet();
     List<TaskUser> taskUsers = getByTaskId(taskId);
     for (TaskUser taskUser : taskUsers) {
       if (taskUser.getUserId() != null) {
         SysUser sysUser = (SysUser)this.sysUserDao.getById(new Long(taskUser.getUserId()));
         taskUserSet.add(sysUser);
       } else if (taskUser.getGroupId() != null) {
         if ("org".equals(taskUser.getType())) {
           List users = this.sysUserDao.getByOrgId(new Long(taskUser.getGroupId()));
           taskUserSet.addAll(users);
         } else if ("pos".equals(taskUser.getType())) {
           List users = this.sysUserDao.getByPosId(new Long(taskUser.getGroupId()));
           taskUserSet.addAll(users);
         } else if ("role".equals(taskUser.getType())) {
           List users = this.sysUserDao.getByRoleId(new Long(taskUser.getGroupId()));
           taskUserSet.addAll(users);
         }
       }
     }
     return taskUserSet;
   }
 }

