 package org.sz.platform.controller.system;
 
  import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.model.system.ReportParam;
import org.sz.platform.service.system.ReportParamService;
 
 @Controller
 @RequestMapping({"/platform/system/reportParam/"})
 public class ReportParamFormController extends BaseFormController
 {
 
   @Resource
   private ReportParamService reportParamService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新报表参数")
   public void save(HttpServletRequest request, HttpServletResponse response, ReportParam reportParam, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("reportParam", reportParam, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (reportParam.getPARAMID() == null) {
       reportParam.setPARAMID(Long.valueOf(UniqueIdUtil.genId()));
       this.reportParamService.add(reportParam);
       resultMsg = getText("record.added", new Object[] { "报表参数" });
     } else {
       this.reportParamService.update(reportParam);
       resultMsg = getText("record.updated", new Object[] { "报表参数" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected ReportParam getFormObject(@RequestParam("PARAMID") Long PARAMID, Model model)
     throws Exception
   {
     this.logger.debug("enter ReportParam getFormObject here....");
     ReportParam reportParam = null;
     if (PARAMID != null)
       reportParam = (ReportParam)this.reportParamService.getById(PARAMID);
     else {
       reportParam = new ReportParam();
     }
     return reportParam;
   }
 }

