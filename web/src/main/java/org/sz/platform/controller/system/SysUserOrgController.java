 package org.sz.platform.controller.system;
 
  import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.dao.system.SysOrgDao;
import org.sz.platform.model.system.SysOrg;
import org.sz.platform.model.system.SysUserOrg;
import org.sz.platform.service.system.SysOrgParamService;
import org.sz.platform.service.system.SysUserOrgService;
 
 @Controller
 @RequestMapping({"/platform/system/sysUserOrg/"})
 public class SysUserOrgController extends BaseController
 {
 
   @Resource
   private SysUserOrgService sysUserOrgService;
 
   @Resource
   private SysOrgDao sysOrgDao;
 
   @Resource
   private SysOrgParamService sysOrgParamService;
 
   @RequestMapping({"list"})
   @Action(description="查看用户所属组织或部门分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.sysUserOrgService.getAll(new WebQueryFilter(request, "sysUserOrgItem"));
     ModelAndView mv = getAutoView().addObject("sysUserOrgList", list);
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除用户所属组织或部门")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     Long[] lAryId = RequestUtil.getLongAryByStr(request, "userOrgId");
     this.sysUserOrgService.delByIds(lAryId);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑用户所属组织或部门")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long userOrgId = Long.valueOf(RequestUtil.getLong(request, "userOrgId"));
     String returnUrl = RequestUtil.getPrePage(request);
     SysUserOrg sysUserOrg = null;
     if (userOrgId != null)
       sysUserOrg = (SysUserOrg)this.sysUserOrgService.getById(userOrgId);
     else {
       sysUserOrg = new SysUserOrg();
     }
     return getAutoView().addObject("sysUserOrg", sysUserOrg).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看用户所属组织或部门明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "userOrgId");
     SysUserOrg sysUserOrg = (SysUserOrg)this.sysUserOrgService.getById(Long.valueOf(id));
     return getAutoView().addObject("sysUserOrg", sysUserOrg);
   }
 
   @RequestMapping({"userList"})
   @Action(description="取得用户列表")
   public ModelAndView userList(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
     SysOrg sysOrg = (SysOrg)this.sysOrgDao.getById(orgId);
     ModelAndView mv = getAutoView();
     if (sysOrg == null) {
       mv.addObject("sysOrg", sysOrg);
     }
     else {
       QueryFilter filter = new WebQueryFilter(request, "sysOrgItem");
       filter.addFilter("path", sysOrg.getPath());
       List list = this.sysUserOrgService.getUserByOrgId(filter);
       mv.addObject("sysOrgUserList", list).addObject("orgId", orgId).addObject("sysOrg", sysOrg);
     }
 
     return mv;
   }
 
   @RequestMapping({"paramList"})
   @Action(description="取得用户列表")
   public ModelAndView paramList(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
     ModelAndView mv = getAutoView();
     if (orgId.longValue() == 0L) {
       mv.addObject("sysOrg", null);
     } else {
       SysOrg sysOrg = (SysOrg)this.sysOrgDao.getById(orgId);
       List list = this.sysOrgParamService.getListByOrgId(orgId);
       mv.addObject("userParamList", list).addObject("orgId", orgId).addObject("sysOrg", sysOrg);
     }
 
     return mv;
   }
 
   @RequestMapping({"addOrgUser"})
   public void addOrgUser(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     Long[] userIds = RequestUtil.getLongAryByStr(request, "userIds");
     Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
     ResultMessage message = null;
     String preUrl = RequestUtil.getPrePage(request);
     try {
       this.sysUserOrgService.addOrgUser(userIds, orgId);
       message = new ResultMessage(1, "在组织添加用户成功!");
     } catch (Exception e) {
       message = new ResultMessage(0, "在组织添加用户中失败!");
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 
   @RequestMapping({"setIsPrimary"})
   public void setIsPrimary(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     Long userPosId = Long.valueOf(RequestUtil.getLong(request, "userPosId", 0L));
     ResultMessage message = null;
     String preUrl = RequestUtil.getPrePage(request);
     try {
       this.sysUserOrgService.setIsPrimary(userPosId);
       message = new ResultMessage(1, "设置组织成功");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "设置组织失败");
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 
   @RequestMapping({"setIsCharge"})
   public void setIsCharge(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     Long userPosId = Long.valueOf(RequestUtil.getLong(request, "userPosId", 0L));
     ResultMessage message = null;
     String preUrl = RequestUtil.getPrePage(request);
     try {
       this.sysUserOrgService.setIsCharge(userPosId);
       message = new ResultMessage(1, "设置组织成功");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "设置组织失败");
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 }

