 package org.sz.platform.controller.system;
 
  import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.SubSystem;
import org.sz.platform.model.system.SysRole;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.SubSystemService;
import org.sz.platform.service.system.SysRoleService;
 
 @Controller
 @RequestMapping({"/platform/system/sysRole/"})
 public class SysRoleController extends BaseController
 {
 
   @Resource
   private SysRoleService sysRoleService;
 
   @Resource
   private SubSystemService subSystemService;
 
   @RequestMapping({"selector"})
   public ModelAndView selector(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     QueryFilter queryFilter = new WebQueryFilter(request, "sysRoleItem");
//     queryFilter.getPageBean().setPagesize(10);
     List list = this.sysRoleService.getAll(queryFilter);
     ModelAndView mv = getAutoView().addObject("sysRoleList", list);
     return mv;
   }
 
   @RequestMapping({"list"})
   @Action(description="查看系统角色表分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
	   SysUser sysUser =(SysUser) request.getSession().getAttribute("sysUser");
	   Long systemId=sysUser.getSystemId();
	   QueryFilter queryFilter = new WebQueryFilter(request, "sysRoleItem");
	   if(systemId!=1L){
	    	 queryFilter.addFilter("systemId", systemId);
	   }
      List list = this.sysRoleService.getRoleList(queryFilter);
      ModelAndView mv = getAutoView().addObject("sysRoleList", list);
     
      mv.addObject("systemId", sysUser.getSystemId());
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除系统角色表")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "roleId");
       this.sysRoleService.delByIds(lAryId);
       message = new ResultMessage(1, "删除系统角色成功");
     } catch (Exception e) {
       message = new ResultMessage(0, "删除系统角色失败:" + e.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"copy"})
   @Action(description="编辑系统角色表")
   public ModelAndView copy(HttpServletRequest request) throws Exception {
     Long roleId = Long.valueOf(RequestUtil.getLong(request, "roleId"));
 
     SysRole sysRole = (SysRole)this.sysRoleService.getById(roleId);
     Long systemId = sysRole.getSystemId();
 
     if (systemId != null) {
       SubSystem subSystem = (SubSystem)this.subSystemService.getById(systemId);
       String sysAlias = subSystem.getAlias();
       String roleAlias = sysRole.getAlias().replaceFirst(sysAlias + "_", "");
       sysRole.setAlias(roleAlias);
     }
     return getAutoView().addObject("sysRole", sysRole);
   }
 
   @RequestMapping({"edit"})
   @Action(description="编辑系统角色表")
   public ModelAndView edit(HttpServletRequest request)
     throws Exception
   {
     Long roleId = Long.valueOf(RequestUtil.getLong(request, "roleId"));
     Long systemId = Long.valueOf(RequestUtil.getLong(request, "systemId"));
     String returnUrl = RequestUtil.getPrePage(request);
     SysRole sysRole = null;
     if (roleId.longValue() != 0L) {
       sysRole = (SysRole)this.sysRoleService.getById(roleId);
       Long systemIdTemp = sysRole.getSystemId();
 
       if (systemIdTemp != null) {
         SubSystem subSystem = (SubSystem)this.subSystemService.getById(systemIdTemp);
         String sysAlias = subSystem.getAlias();
         String roleAlias = sysRole.getAlias().replaceFirst(sysAlias + "_", "");
         sysRole.setAlias(roleAlias);
       }
     } else {
       sysRole = new SysRole();
     }
     List list = this.subSystemService.getActiveSystem();
 
     return getAutoView().addObject("sysRole", sysRole).addObject("subsystemList", list).addObject("returnUrl", returnUrl).addObject("systemId", systemId);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看系统角色表明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "roleId");
     SysRole sysRole = (SysRole)this.sysRoleService.getById(Long.valueOf(id));
     SubSystem subsystem = null;
     if (sysRole.getSystemId() != null) {
       subsystem = (SubSystem)this.subSystemService.getById(sysRole.getSystemId());
     }
 
     return getAutoView().addObject("sysRole", sysRole).addObject("subsystem", subsystem);
   }
 
   @RequestMapping({"copyRole"})
   @Action(description="复制角色")
   public void copyRole(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     PrintWriter writer = response.getWriter();
     long roleId = RequestUtil.getLong(request, "roleId");
 
     String roleName = RequestUtil.getString(request, "newRoleName");
     String alias = RequestUtil.getString(request, "newAlias");
     long newRoleId = UniqueIdUtil.genId();
 
     SysRole sysRole = (SysRole)this.sysRoleService.getById(Long.valueOf(roleId));
     Long systemId = sysRole.getSystemId();
     if (systemId != null) {
       SubSystem subSystem = (SubSystem)this.subSystemService.getById(systemId);
       alias = subSystem.getAlias() + "_" + alias;
     }
 
     boolean rtn = this.sysRoleService.isExistRoleAlias(alias);
     if (rtn) {
       writeResultMessage(writer, "输入的别名已存在", 0);
       return;
     }
 
     SysRole newRole = (SysRole)sysRole.clone();
     newRole.setRoleId(Long.valueOf(newRoleId));
     newRole.setAlias(alias);
     newRole.setRoleName(roleName);
     try
     {
       this.sysRoleService.copyRole(newRole, roleId);
       writeResultMessage(writer, "复制角色成功!", 1);
     }
     catch (Exception e) {
       writeResultMessage(writer, "复制失败:" + e.getMessage(), 0);
     }
   }
 
   @RequestMapping({"getTreeData"})
   @ResponseBody
   public List<SysRole> getTreeData(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
	   SysUser sysUser =(SysUser) request.getSession().getAttribute("sysUser");
	   Long systemId=sysUser.getSystemId();
	   QueryFilter queryFilter = new WebQueryFilter(request, "sysRole");
	   if(systemId!=1L){
	    	 queryFilter.addFilter("systemId", systemId);
	   }
	   queryFilter.setPageBean(null);
     List rolList = this.sysRoleService.getRoleTree(queryFilter);
     SysRole rol = new SysRole();
     rol.setRoleId(new Long(0L));
     rol.setRoleName("全部");
     rol.setSystemId(new Long(0L));
     rol.setSubSystem(null);
     rol.setAlias("allRole");
     rol.setMemo("全部");
     //rolList.add(0, rol);
     return rolList;
   }
 
   @RequestMapping({"getAll"})
   @ResponseBody
   public List<SysRole> getAll(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.sysRoleService.getAll(new WebQueryFilter(request, false));
     return list;
   }
 
   @RequestMapping({"runEnable"})
   @Action(description="禁用或启用角色")
   public void runEnableRole(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long roleId = RequestUtil.getLong(request, "roleId");
     long enabled = RequestUtil.getLong(request, "enabled");
     SysRole sysRole = (SysRole)this.sysRoleService.getById(Long.valueOf(roleId));
     if (enabled == 0L)
       sysRole.setEnabled(Short.valueOf("1"));
     else {
       sysRole.setEnabled(Short.valueOf("0"));
     }
     this.sysRoleService.update(sysRole);
     response.sendRedirect(RequestUtil.getPrePage(request));
   }
 }

