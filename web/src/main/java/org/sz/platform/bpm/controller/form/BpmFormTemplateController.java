 package org.sz.platform.bpm.controller.form;
 
  import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmFormTemplate;
import org.sz.platform.bpm.service.form.BpmFormTemplateService;
 
 @Controller
 @RequestMapping({"/platform/form/bpmFormTemplate/"})
 public class BpmFormTemplateController extends BaseController
 {
 
   @Resource
   private BpmFormTemplateService bpmFormTemplateService;
 
   @RequestMapping({"list"})
   @Action(description="查看表单模板分页列表", operateType="自定义表单模板")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.bpmFormTemplateService.getAll(new WebQueryFilter(request, "bpmFormTemplateItem"));
     ModelAndView mv = getAutoView().addObject("bpmFormTemplateList", list);
 
     return mv;
   }
 
   @RequestMapping({"selector"})
   @Action(description="查看表单模板分页列表", operateType="自定义表单模板")
   public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
     List list = this.bpmFormTemplateService.getAll(new WebQueryFilter(request, "bpmFormTemplateItem"));
     ModelAndView mv = getAutoView().addObject("bpmFormTemplateList", list);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除表单模板", operateType="自定义表单模板")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "templateId");
       this.bpmFormTemplateService.delByIds(lAryId);
       message = new ResultMessage(1, "删除表单模板成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑表单模板", operateType="自定义表单模板")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long templateId = Long.valueOf(RequestUtil.getLong(request, "templateId"));
     String returnUrl = RequestUtil.getPrePage(request);
     BpmFormTemplate bpmFormTemplate = null;
     if (templateId.longValue() != 0L)
       bpmFormTemplate = (BpmFormTemplate)this.bpmFormTemplateService.getById(templateId);
     else {
       bpmFormTemplate = new BpmFormTemplate();
     }
     List macroTemplates = this.bpmFormTemplateService.getAllMacroTemplate();
     return getAutoView().addObject("bpmFormTemplate", bpmFormTemplate).addObject("returnUrl", returnUrl).addObject("macroTemplates", macroTemplates);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看表单模板明细", operateType="自定义表单模板")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "templateId");
     BpmFormTemplate bpmFormTemplate = (BpmFormTemplate)this.bpmFormTemplateService.getById(Long.valueOf(id));
     return getAutoView().addObject("bpmFormTemplate", bpmFormTemplate);
   }
 
   @RequestMapping({"init"})
   @Action(description="初始化模板", operateType="自定义表单模板")
   public void init(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       this.bpmFormTemplateService.initAllTemplate();
       message = new ResultMessage(1, "初始化表单模板成功!");
     } catch (Exception ex) {
       message = new ResultMessage(0, "初始化表单模板失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 
   @RequestMapping({"backUp"})
   @Action(description="备份模板", operateType="自定义表单模板")
   public void backUp(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "templateId");
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       this.bpmFormTemplateService.backUpTemplate(Long.valueOf(id));
       message = new ResultMessage(1, "模板备份成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "模板备份失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 
   @RequestMapping({"copyTemplate"})
   @Action(description="复制模板", operateType="自定义表单模板")
   public void copyTemplate(HttpServletRequest request, HttpServletResponse response, BpmFormTemplate template)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "templateId");
     BpmFormTemplate bpmFormTemplate = (BpmFormTemplate)this.bpmFormTemplateService.getById(Long.valueOf(id));
     String name = RequestUtil.getString(request, "newTemplateName");
     String newAlias = RequestUtil.getString(request, "newAlias");
     boolean isExist = this.bpmFormTemplateService.isExistAlias(newAlias);
     String resultMsg = null;
     if (isExist) {
       writeResultMessage(response.getWriter(), new ResultMessage(0, "该别名已被使用"));
     } else {
       long newId = UniqueIdUtil.genId();
       template.setTemplateId(Long.valueOf(newId));
       template.setTemplateName(name);
       template.setAlias(newAlias);
       template.setCanEdit(1);
       template.setHtml(bpmFormTemplate.getHtml());
       template.setMacroTemplateAlias(bpmFormTemplate.getMacroTemplateAlias());
       template.setTemplateDesc(bpmFormTemplate.getTemplateDesc());
       template.setTemplateType(bpmFormTemplate.getTemplateType());
       this.bpmFormTemplateService.add(template);
       writeResultMessage(response.getWriter(), new ResultMessage(1, "复制模板成功"));
     }
   }
 }

