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
import org.sz.platform.model.system.DesktopMycolumn;
import org.sz.platform.service.system.DesktopMycolumnService;
 
 @Controller
 @RequestMapping({"/platform/system/desktopMycolumn/"})
 public class DesktopMycolumnFormController extends BaseFormController
 {
 
   @Resource
   private DesktopMycolumnService desktopMycolumnService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新桌面个人栏目")
   public void save(HttpServletRequest request, HttpServletResponse response, DesktopMycolumn desktopMycolumn, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("desktopMycolumn", desktopMycolumn, bindResult, request);
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (desktopMycolumn.getId() == null) {
       desktopMycolumn.setId(Long.valueOf(UniqueIdUtil.genId()));
       this.desktopMycolumnService.add(desktopMycolumn);
       resultMsg = getText("record.added", new Object[] { "桌面个人栏目" });
     } else {
       this.desktopMycolumnService.update(desktopMycolumn);
       resultMsg = getText("record.updated", new Object[] { "桌面个人栏目" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected DesktopMycolumn getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter DesktopMycolumn getFormObject here....");
     DesktopMycolumn desktopMycolumn = null;
     if (id != null)
       desktopMycolumn = (DesktopMycolumn)this.desktopMycolumnService.getById(id);
     else {
       desktopMycolumn = new DesktopMycolumn();
     }
     return desktopMycolumn;
   }
 }

