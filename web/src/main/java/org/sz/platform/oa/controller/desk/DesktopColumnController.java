 package org.sz.platform.oa.controller.desk;
 
  import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.oa.model.desk.DesktopColumn;
import org.sz.platform.oa.service.desk.DesktopColumnService;
 
 @Controller
 @RequestMapping({"/platform/system/desktopColumn/"})
 public class DesktopColumnController extends BaseController
 {
 
   @Resource
   private FreemarkEngine freemarkEngine;
 
   @Resource
   private DesktopColumnService desktopColumnService;
 
   @RequestMapping({"list"})
   @Action(description="查看桌面栏目分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.desktopColumnService.getAll(new WebQueryFilter(request, "desktopColumnItem"));
     ModelAndView mv = getAutoView().addObject("desktopColumnList", list);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除桌面栏目")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
       this.desktopColumnService.delDesktopColumn(lAryId);
       message = new ResultMessage(1, "删除桌面栏目成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 
   @RequestMapping({"edit"})
   @Action(description="编辑桌面栏目")
   public ModelAndView edit(HttpServletRequest request)
     throws Exception
   {
     Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
     String returnUrl = RequestUtil.getPrePage(request);
     DesktopColumn desktopColumn = null;
     if (id.longValue() != 0L)
       desktopColumn = (DesktopColumn)this.desktopColumnService.getById(id);
     else {
       desktopColumn = new DesktopColumn();
     }
     return getAutoView().addObject("desktopColumn", desktopColumn).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看桌面栏目明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "id");
     DesktopColumn desktopColumn = (DesktopColumn)this.desktopColumnService.getById(Long.valueOf(id));
     return getAutoView().addObject("desktopColumn", desktopColumn);
   }
 
   @RequestMapping({"getTemp"})
   @Action(description="查看桌面栏目明细")
   public void getTemp(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String ctxPath = request.getContextPath();
     long id = RequestUtil.getLong(request, "id");
     DesktopColumn desktopColumn = (DesktopColumn)this.desktopColumnService.getById(Long.valueOf(id));
     String handler = desktopColumn.getServiceMethod();
     String[] aryHandler = handler.split("[.]");
     Object model = null;
     if (aryHandler != null) {
       String beanId = aryHandler[0];
       String method = aryHandler[1];
 
       Object serviceBean = AppUtil.getBean(beanId);
       if (serviceBean != null) {
         Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method, null);
         model = invokeMethod.invoke(serviceBean, null);
       }
     }
     Map map = new HashMap();
     map.put("model", model);
     map.put("ctxPath", ctxPath);
 
     String html = this.freemarkEngine.parseByStringTemplate(map, desktopColumn.getHtml());
     PrintWriter out = response.getWriter();
     out.println(html);
   }
 
   @RequestMapping({"init"})
   @Action(description="初始化模板")
   public void init(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       this.desktopColumnService.initAllTemplate(ContextUtil.getCurrentUserId().longValue());
       message = new ResultMessage(1, "初始化表单模板成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "初始化表单模板失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
 }

