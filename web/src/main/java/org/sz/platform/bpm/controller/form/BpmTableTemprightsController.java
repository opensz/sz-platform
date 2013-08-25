 package org.sz.platform.bpm.controller.form;
 
  import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.bpm.model.form.BpmTableTemprights;
import org.sz.platform.bpm.service.flow.BpmTableTemprightsService;
import org.sz.platform.bpm.service.form.BpmTableTemplateService;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.service.GlobalTypeService;
 
 @Controller
 @RequestMapping({"/platform/form/bpmTableTemprights/"})
 public class BpmTableTemprightsController extends BaseController
 {
 
   @Resource
   private BpmTableTemprightsService bpmTableTemprightsService;
 
   @Resource
   private BpmTableTemplateService bpmTableTemplateService;
 
   @Resource
   private GlobalTypeService globalTypeService;
 
   @RequestMapping({"list"})
   @Action(description="查看查看业务数据模板的权限信息分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
     int type = RequestUtil.getInt(request, "type");
     ModelAndView mv = getAutoView();
     Map rightsMap = this.bpmTableTemprightsService.getRights(id, type);
 
     if (type == 0) {
       BpmTableTemplate bpmTableTemplate = (BpmTableTemplate)this.bpmTableTemplateService.getById(id);
       mv.addObject("bpmTableTemplate", bpmTableTemplate);
     } else {
       GlobalType globalType = (GlobalType)this.globalTypeService.getById(id);
       mv.addObject("globalType", globalType);
     }
     mv.addObject("rightsMap", rightsMap).addObject("id", id).addObject("type", Integer.valueOf(type));
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除查看业务数据模板的权限信息")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
       this.bpmTableTemprightsService.delByIds(lAryId);
       message = new ResultMessage(1, "删除查看业务数据模板的权限信息成功!");
     } catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑查看业务数据模板的权限信息")
   public ModelAndView edit(HttpServletRequest request) throws Exception { Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
     String returnUrl = RequestUtil.getPrePage(request);
     BpmTableTemprights bpmTableTemprights = null;
     if (id.longValue() != 0L)
       bpmTableTemprights = (BpmTableTemprights)this.bpmTableTemprightsService.getById(id);
     else {
       bpmTableTemprights = new BpmTableTemprights();
     }
     return getAutoView().addObject("bpmTableTemprights", bpmTableTemprights).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看查看业务数据模板的权限信息明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "id");
     BpmTableTemprights bpmTableTemprights = (BpmTableTemprights)this.bpmTableTemprightsService.getById(Long.valueOf(id));
     return getAutoView().addObject("bpmTableTemprights", bpmTableTemprights);
   }
 }

