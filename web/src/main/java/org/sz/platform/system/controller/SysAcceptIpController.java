 package org.sz.platform.system.controller;
 
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
import org.sz.platform.system.model.SysAcceptIp;
import org.sz.platform.system.service.SysAcceptIpService;
 
 @Controller
 @RequestMapping({"/platform/system/sysAcceptIp/"})
 public class SysAcceptIpController extends BaseController
 {
 
   @Resource
   private SysAcceptIpService sysAcceptIpService;
 
   @RequestMapping({"list"})
   @Action(description="查看可访问地址分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.sysAcceptIpService.getAll(new WebQueryFilter(request, "sysAcceptIpItem"));
     ModelAndView mv = getAutoView().addObject("sysAcceptIpList", list);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除可访问地址")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "acceptId");
       this.sysAcceptIpService.delByIds(lAryId);
       message = new ResultMessage(1, "删除可访问地址成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑可访问地址")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long acceptId = Long.valueOf(RequestUtil.getLong(request, "acceptId"));
     String returnUrl = RequestUtil.getPrePage(request);
     SysAcceptIp sysAcceptIp = null;
     if (acceptId.longValue() != 0L)
       sysAcceptIp = (SysAcceptIp)this.sysAcceptIpService.getById(acceptId);
     else {
       sysAcceptIp = new SysAcceptIp();
     }
     return getAutoView().addObject("sysAcceptIp", sysAcceptIp).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看可访问地址明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "acceptId");
     SysAcceptIp sysAcceptIp = (SysAcceptIp)this.sysAcceptIpService.getById(Long.valueOf(id));
     return getAutoView().addObject("sysAcceptIp", sysAcceptIp);
   }
 }

