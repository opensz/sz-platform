 package org.sz.platform.controller.system;
 
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
import org.sz.platform.model.system.SysParam;
import org.sz.platform.service.system.SysParamService;
 
 @Controller
 @RequestMapping({"/platform/system/sysParam/"})
 public class SysParamController extends BaseController
 {
 
   @Resource
   private SysParamService sysParamService;
 
   @RequestMapping({"list"})
   @Action(description="查看组织或人员参数属性分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.sysParamService.getAll(new WebQueryFilter(request, "sysParamItem"));
     ModelAndView mv = getAutoView().addObject("sysParamList", list).addObject("dataTypeMap", SysParam.DATA_TYPE_MAP);
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除组织或人员参数属性")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "paramId");
       this.sysParamService.delByIds(lAryId);
       message = new ResultMessage(1, "删除组织或人员参数属性成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑组织或人员参数属性")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long paramId = Long.valueOf(RequestUtil.getLong(request, "paramId"));
     String returnUrl = RequestUtil.getPrePage(request);
     SysParam sysParam = null;
     if (paramId.longValue() != 0L)
       sysParam = (SysParam)this.sysParamService.getById(paramId);
     else {
       sysParam = new SysParam();
     }
     return getAutoView().addObject("sysParam", sysParam).addObject("returnUrl", returnUrl).addObject("dataTypeMap", SysParam.DATA_TYPE_MAP);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看组织或人员参数属性明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "paramId");
     SysParam sysParam = (SysParam)this.sysParamService.getById(Long.valueOf(id));
     return getAutoView().addObject("sysParam", sysParam).addObject("dataTypeMap", SysParam.DATA_TYPE_MAP);
   }
 }

