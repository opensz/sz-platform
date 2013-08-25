 package org.sz.platform.system.controller;
 
  import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.service.RoleResourcesService;
import org.sz.platform.system.service.SubSystemService;
import org.sz.platform.system.service.SysRoleService;
 
 @Controller
 @RequestMapping({"/platform/system/roleResources/"})
 public class RoleResourcesController extends BaseController
 {
 
   @Resource
   private RoleResourcesService roleResourcesService;
 
   @Resource
   private SubSystemService subSystemService;
 
   @Resource
   private SysRoleService sysRoleService;
 
   @RequestMapping({"edit"})
   public ModelAndView edit(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     ModelAndView mv = getAutoView();
     List subSystemList = this.subSystemService.getAll(new WebQueryFilter(request, "subSystemItem", false));
     long roleId = RequestUtil.getLong(request, "roleId");
     SysRole role = (SysRole)this.sysRoleService.getById(Long.valueOf(roleId));
     String returnUrl = RequestUtil.getString(request, "returnUrl", RequestUtil.getPrePage(request));
 
     mv.addObject("subSystemList", subSystemList);
     mv.addObject("roleId", Long.valueOf(roleId));
     mv.addObject("role", role);
     mv.addObject("returnUrl", returnUrl);
     mv.addObject("systemId", role.getSystemId());
     return mv;
   }
 
   @RequestMapping({"upd"})
   @Action(description="分配角色资源")
   public void upd(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     Long systemId = Long.valueOf(RequestUtil.getLong(request, "systemId", 0L));
     Long roleId = Long.valueOf(RequestUtil.getLong(request, "roleId", 0L));
 
     Long[] resIds = null;
     if ((request.getParameter("resIds") != null) && (!request.getParameter("resIds").equals(""))) {
       resIds = RequestUtil.getLongAryByStr(request, "resIds");
     }
 
     ResultMessage resultObj = new ResultMessage(1, "角色资源分配成功");
     try {
       this.roleResourcesService.update(systemId, roleId, resIds);
     }
     catch (Exception e) {
       e.printStackTrace();
       resultObj.setMessage("角色资源分配出错");
       resultObj.setResult(0);
     }
     PrintWriter out = response.getWriter();
     out.print(resultObj.toString());
   }
 }

