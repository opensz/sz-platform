 package org.sz.platform.system.controller;
 
  import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.ReportParam;
import org.sz.platform.system.service.ReportParamService;
 
 @Controller
 @RequestMapping({"/platform/system/reportParam/"})
 public class ReportParamController extends BaseController
 {
 
   @Resource
   private ReportParamService reportParamService;
 
   @RequestMapping({"list"})
   @Action(description="查看报表参数分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.reportParamService.getAll(new WebQueryFilter(request, "reportParamItem"));
     ModelAndView mv = getAutoView().addObject("reportParamList", list);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除报表参数")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "PARAMID");
       this.reportParamService.delByIds(lAryId);
       message = new ResultMessage(1, "删除报表参数成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑报表参数")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long PARAMID = Long.valueOf(RequestUtil.getLong(request, "PARAMID"));
     String returnUrl = RequestUtil.getPrePage(request);
     ReportParam reportParam = null;
     if (PARAMID.longValue() != 0L)
       reportParam = (ReportParam)this.reportParamService.getById(PARAMID);
     else {
       reportParam = new ReportParam();
     }
     return getAutoView().addObject("reportParam", reportParam).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看报表参数明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "PARAMID");
     ReportParam reportParam = (ReportParam)this.reportParamService.getById(Long.valueOf(id));
     return getAutoView().addObject("reportParam", reportParam);
   }
 }

