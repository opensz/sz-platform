 package org.sz.platform.bpm.controller.form;
 
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
import org.sz.platform.bpm.model.form.BpmFormDialog;
import org.sz.platform.bpm.service.form.BpmFormDialogService;
 
 @Controller
 @RequestMapping({"/platform/form/bpmFormDialog/"})
 public class BpmFormDialogFormController extends BaseFormController
 {
 
   @Resource
   private BpmFormDialogService bpmFormDialogService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新通用表单对话框")
   public void save(HttpServletRequest request, HttpServletResponse response, BpmFormDialog bpmFormDialog, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("bpmFormDialog", bpmFormDialog, bindResult, request);
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = "";
 
     if (bpmFormDialog.getId().longValue() == 0L) {
       bpmFormDialog.setId(Long.valueOf(UniqueIdUtil.genId()));
       String alias = bpmFormDialog.getAlias();
       boolean isExist = this.bpmFormDialogService.isExistAlias(alias);
       if (isExist) {
         resultMsg = getText("该别名已经存在！");
         writeResultMessage(response.getWriter(), resultMsg, 0);
         return;
       }
       this.bpmFormDialogService.add(bpmFormDialog);
       resultMsg = getText("record.added", new Object[] { "通用表单对话框" });
     }
     else {
       String alias = bpmFormDialog.getAlias();
       Long id = bpmFormDialog.getId();
       boolean isExist = this.bpmFormDialogService.isExistAliasForUpd(id, alias);
       if (isExist) {
         resultMsg = getText("该别名已经存在！");
         writeResultMessage(response.getWriter(), resultMsg, 0);
         return;
       }
       this.bpmFormDialogService.update(bpmFormDialog);
       resultMsg = getText("record.updated", new Object[] { "通用表单对话框" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected BpmFormDialog getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter BpmFormDialog getFormObject here....");
     BpmFormDialog bpmFormDialog = null;
     if (id.longValue() > 0L)
       bpmFormDialog = (BpmFormDialog)this.bpmFormDialogService.getById(id);
     else {
       bpmFormDialog = new BpmFormDialog();
     }
     return bpmFormDialog;
   }
 }

