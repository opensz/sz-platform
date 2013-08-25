 package org.sz.platform.system.controller;
 
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
import org.sz.platform.system.model.SysFile;
import org.sz.platform.system.service.SysFileService;
 
 @Controller
 @RequestMapping({"/platform/system/sysFile/"})
 public class SysFileFormController extends BaseFormController
 {
 
   @Resource
   private SysFileService sysFileService;
 
   @RequestMapping({"save"})
   public void save(HttpServletRequest request, HttpServletResponse response, SysFile sysFile, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("sysFile", sysFile, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (sysFile.getFileId() == null) {
       add(sysFile);
       resultMsg = getText("record.added", new Object[] { "附件" });
     } else {
       upd(sysFile);
       resultMsg = getText("record.updated", new Object[] { "附件" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @Action(description="添加附件")
   public void add(SysFile sysFile)
     throws Exception
   {
     sysFile.setFileId(Long.valueOf(UniqueIdUtil.genId()));
     this.sysFileService.add(sysFile);
   }
 
   @Action(description="更新附件")
   public void upd(SysFile sysFile)
     throws Exception
   {
     this.sysFileService.update(sysFile);
   }
 
   @ModelAttribute
   protected SysFile getFormObject(@RequestParam("fileId") Long fileId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysFile getFormObject here....");
     SysFile sysFile = null;
     if (fileId != null)
       sysFile = (SysFile)this.sysFileService.getById(fileId);
     else {
       sysFile = new SysFile();
     }
     return sysFile;
   }
 }

