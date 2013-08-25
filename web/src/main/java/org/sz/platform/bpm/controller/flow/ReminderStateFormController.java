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
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.bpm.model.flow.ReminderState;
import org.sz.platform.bpm.service.flow.ReminderStateService;
 
 @Controller
 @RequestMapping({"/platform/bpm/reminderState/"})
 public class ReminderStateFormController extends BaseFormController
 {
 
   @Resource
   private ReminderStateService reminderStateService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新任务催办执行情况")
   public void save(HttpServletRequest request, HttpServletResponse response, ReminderState reminderState, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("reminderState", reminderState, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (reminderState.getId() == null) {
       reminderState.setId(Long.valueOf(UniqueIdUtil.genId()));
       this.reminderStateService.add(reminderState);
       resultMsg = getText("record.added", new Object[] { "任务催办执行情况" });
     } else {
       this.reminderStateService.update(reminderState);
       resultMsg = getText("record.updated", new Object[] { "任务催办执行情况" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected ReminderState getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter ReminderState getFormObject here....");
     ReminderState reminderState = null;
     if (id != null)
       reminderState = (ReminderState)this.reminderStateService.getById(id);
     else {
       reminderState = new ReminderState();
     }
     return reminderState;
   }
 }

