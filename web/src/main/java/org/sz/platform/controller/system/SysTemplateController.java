 package org.sz.platform.controller.system;
 
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
import org.sz.platform.model.system.SysTemplate;
import org.sz.platform.service.system.SysTemplateService;
 
 @Controller
 @RequestMapping({"/platform/system/sysTemplate/"})
 public class SysTemplateController extends BaseController
 {
 
   @Resource
   private SysTemplateService sysTemplateService;
 
   @RequestMapping({"list"})
   @Action(description="查看模版管理分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.sysTemplateService.getAll(new WebQueryFilter(request, "sysTemplateItem"));
     ModelAndView mv = getAutoView().addObject("sysTemplateList", list);
 
     return mv;
   }
   @RequestMapping({"dialog"})
   @Action(description="查看模版管理分页列表")
   public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception { List list = this.sysTemplateService.getAll(new WebQueryFilter(request, "sysTemplateItem"));
     ModelAndView mv = getAutoView().addObject("sysTemplateList", list);
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除模版管理")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     ResultMessage message = null;
     String preUrl = RequestUtil.getPrePage(request);
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "templateId");
       this.sysTemplateService.delByIds(lAryId);
       message = new ResultMessage(1, "删除模板成功");
     } catch (Exception e) {
       message = new ResultMessage(0, "删除模板失败");
     }
 
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑模版管理")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long templateId = Long.valueOf(RequestUtil.getLong(request, "templateId"));
     String returnUrl = RequestUtil.getPrePage(request);
     SysTemplate sysTemplate = null;
     if (templateId.longValue() != 0L)
       sysTemplate = (SysTemplate)this.sysTemplateService.getById(templateId);
     else {
       sysTemplate = new SysTemplate();
     }
     return getAutoView().addObject("sysTemplate", sysTemplate).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看模版管理明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "templateId");
     SysTemplate sysTemplate = (SysTemplate)this.sysTemplateService.getById(Long.valueOf(id));
     return getAutoView().addObject("sysTemplate", sysTemplate);
   }
 }

