 package org.sz.platform.controller.system;
 
  import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.sz.platform.model.system.DesktopLayout;
import org.sz.platform.service.system.DesktopLayoutService;
 
 @Controller
 @RequestMapping({"/platform/system/desktopLayout/"})
 public class DesktopLayoutFormController extends BaseFormController
 {
 
   @Resource
   private DesktopLayoutService desktopLayoutService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新桌面布局")
   public void save(HttpServletRequest request, HttpServletResponse response, DesktopLayout desktopLayout, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("desktopLayout", desktopLayout, bindResult, request);
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (desktopLayout.getId() == null) {
       desktopLayout.setId(Long.valueOf(UniqueIdUtil.genId()));
       if (this.desktopLayoutService.getDefaultLayout() == null)
         desktopLayout.setIsDefault(Integer.valueOf(1));
       else
         desktopLayout.setIsDefault(Integer.valueOf(0));
       String[] aryWidth = desktopLayout.getWidth().split(",");
       System.out.println(StringUtils.join(aryWidth, ","));
       desktopLayout.setWidth(StringUtils.join(aryWidth, ","));
       this.desktopLayoutService.add(desktopLayout);
       resultMsg = getText("record.added", new Object[] { "桌面布局" });
     } else {
       this.desktopLayoutService.update(desktopLayout);
       resultMsg = getText("record.updated", new Object[] { "桌面布局" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected DesktopLayout getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter DesktopLayout getFormObject here....");
     DesktopLayout desktopLayout = null;
     if (id != null)
       desktopLayout = (DesktopLayout)this.desktopLayoutService.getById(id);
     else {
       desktopLayout = new DesktopLayout();
     }
     return desktopLayout;
   }
 }

