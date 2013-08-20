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
import org.sz.platform.model.system.DesktopColumn;
import org.sz.platform.service.system.DesktopColumnService;
 
 @Controller
 @RequestMapping({"/platform/system/desktopColumn/"})
 public class DesktopColumnFormController extends BaseFormController
 {
 
   @Resource
   private DesktopColumnService desktopColumnService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新桌面栏目")
   public void save(HttpServletRequest request, HttpServletResponse response, DesktopColumn desktopColumn, BindingResult bindResult)
     throws Exception
   {
     String resultMsg = null;
     ResultMessage resultMessage = validForm("desktopColumn", desktopColumn, bindResult, request);
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     if (desktopColumn.getId() == null) {
       boolean isExist = this.desktopColumnService.isExistDesktopColumn(desktopColumn.getName());
       if (isExist) {
         resultMsg = getText("该栏目已经存在不能重复添加！");
         writeResultMessage(response.getWriter(), resultMsg, 0);
         return;
       }
       desktopColumn.setId(Long.valueOf(UniqueIdUtil.genId()));
       this.desktopColumnService.add(desktopColumn);
       resultMsg = getText("record.added", new Object[] { "桌面栏目" });
     } else {
       this.desktopColumnService.update(desktopColumn);
       resultMsg = getText("record.updated", new Object[] { "桌面栏目" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected DesktopColumn getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter DesktopColumn getFormObject here....");
     DesktopColumn desktopColumn = null;
     if (id != null)
       desktopColumn = (DesktopColumn)this.desktopColumnService.getById(id);
     else {
       desktopColumn = new DesktopColumn();
     }
     return desktopColumn;
   }
 }

