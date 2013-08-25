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
import org.sz.platform.bpm.model.flow.TaskSignData;
import org.sz.platform.bpm.service.flow.TaskSignDataService;
 
 @Controller
 @RequestMapping({"/platform/bpm/taskSignData/"})
 public class TaskSignDataFormController extends BaseFormController
 {
 
   @Resource
   private TaskSignDataService taskSignDataService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新任务会签数据")
   public void save(HttpServletRequest request, HttpServletResponse response, TaskSignData taskSignData, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("taskSignData", taskSignData, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (taskSignData.getDataId() == null) {
       taskSignData.setDataId(Long.valueOf(UniqueIdUtil.genId()));
       this.taskSignDataService.add(taskSignData);
       resultMsg = getText("record.added", new Object[] { "任务会签数据" });
     } else {
       this.taskSignDataService.update(taskSignData);
       resultMsg = getText("record.updated", new Object[] { "任务会签数据" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected TaskSignData getFormObject(@RequestParam("dataId") Long dataId, Model model)
     throws Exception
   {
     this.logger.debug("enter TaskSignData getFormObject here....");
     TaskSignData taskSignData = null;
     if (dataId != null)
       taskSignData = (TaskSignData)this.taskSignDataService.getById(dataId);
     else {
       taskSignData = new TaskSignData();
     }
     return taskSignData;
   }
 }

