 package org.sz.platform.bpm.controller.flow;
 
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
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
import org.sz.platform.bpm.service.flow.BpmNodeSignService;
 
 @Controller
 @RequestMapping({"/platform/bpm/bpmNodeSign/"})
 public class BpmNodeSignFormController extends BaseFormController
 {
 
   @Resource
   private BpmNodeSignService bpmNodeSignService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新会签任务投票规则")
   public void save(HttpServletRequest request, HttpServletResponse response, BpmNodeSign bpmNodeSign, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("bpmNodeSign", bpmNodeSign, bindResult, request);
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
 
     String isAllowAdd = request.getParameter("isAllowAdd");
     if (StringUtil.isNotEmpty(isAllowAdd))
       bpmNodeSign.setIsAllowAdd(BpmNodeSign.ADD_ALLOWED);
     else {
       bpmNodeSign.setIsAllowAdd(BpmNodeSign.ADD_DENY);
     }
 
     String resultMsg = null;
     if (bpmNodeSign.getSignId().longValue() == 0L)
     {
       bpmNodeSign.setSignId(Long.valueOf(UniqueIdUtil.genId()));
 
       this.bpmNodeSignService.add(bpmNodeSign);
 
       resultMsg = getText("record.added", new Object[] { "会签任务投票规则" });
     } else {
       this.bpmNodeSignService.update(bpmNodeSign);
       resultMsg = getText("record.updated", new Object[] { "会签任务投票规则" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected BpmNodeSign getFormObject(@RequestParam("signId") Long signId, Model model)
     throws Exception
   {
     this.logger.debug("enter BpmNodeSign getFormObject here....");
     BpmNodeSign bpmNodeSign = null;
     if (signId.longValue() != 0L)
       bpmNodeSign = (BpmNodeSign)this.bpmNodeSignService.getById(signId);
     else {
       bpmNodeSign = new BpmNodeSign();
     }
     return bpmNodeSign;
   }
 }

