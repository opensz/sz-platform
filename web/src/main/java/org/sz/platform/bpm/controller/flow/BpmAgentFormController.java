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
import org.sz.platform.bpm.model.flow.BpmAgent;
import org.sz.platform.bpm.service.flow.BpmAgentService;
 
 @Controller
 @RequestMapping({"/platform/bpm/bpmAgent/"})
 public class BpmAgentFormController extends BaseFormController
 {
 
   @Resource
   private BpmAgentService bpmAgentService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新流程代理")
   public void save(HttpServletRequest request, HttpServletResponse response, BpmAgent bpmAgent, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("bpmAgent", bpmAgent, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (bpmAgent.getId() == null) {
       bpmAgent.setId(Long.valueOf(UniqueIdUtil.genId()));
       this.bpmAgentService.add(bpmAgent);
       resultMsg = getText("record.added", new Object[] { "流程代理" });
     } else {
       this.bpmAgentService.update(bpmAgent);
       resultMsg = getText("record.updated", new Object[] { "流程代理" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected BpmAgent getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter BpmAgent getFormObject here....");
     BpmAgent bpmAgent = null;
     if (id != null)
       bpmAgent = (BpmAgent)this.bpmAgentService.getById(id);
     else {
       bpmAgent = new BpmAgent();
     }
     return bpmAgent;
   }
 }

