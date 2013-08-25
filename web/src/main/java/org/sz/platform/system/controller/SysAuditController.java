 package org.sz.platform.system.controller;
 
  import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysAudit;
import org.sz.platform.system.service.SysAuditService;
 
 @Controller
 @RequestMapping({"/platform/system/sysAudit/"})
 public class SysAuditController extends BaseController
 {
 
   @Resource
   private SysAuditService sysAuditService;
 
   @RequestMapping({"list"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.sysAuditService.getAll(new WebQueryFilter(request, "sysAuditItem"));
     ModelAndView mv = getAutoView().addObject("sysAuditList", list);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     ResultMessage message = null;
     String preUrl = RequestUtil.getPrePage(request);
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "auditId");
       this.sysAuditService.delByIds(lAryId);
       message = new ResultMessage(1, "删除系统日志成功");
     } catch (Exception e) {
       message = new ResultMessage(0, "删除系统日志失败");
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 
   public void add(SysAudit sysAudit)
     throws Exception
   {
     sysAudit.setAuditId(Long.valueOf(UniqueIdUtil.genId()));
     this.sysAuditService.add(sysAudit);
   }
 
   public void upd(SysAudit sysAudit)
     throws Exception
   {
     this.sysAuditService.update(sysAudit);
   }
 
   @RequestMapping({"edit"})
   public ModelAndView edit(HttpServletRequest request, @RequestParam("auditId") Long auditId) throws Exception
   {
     String returnUrl = RequestUtil.getPrePage(request);
     SysAudit po = null;
     if (auditId != null)
       po = (SysAudit)this.sysAuditService.getById(auditId);
     else {
       po = new SysAudit();
     }
     return getAutoView().addObject("sysAudit", po).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "auditId");
     SysAudit po = (SysAudit)this.sysAuditService.getById(Long.valueOf(id));
     return getAutoView().addObject("sysAudit", po);
   }
 }

