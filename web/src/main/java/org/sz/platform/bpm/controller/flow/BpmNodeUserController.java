 package org.sz.platform.bpm.controller.flow;
 
  import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeUser;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
 
 @Controller
 @RequestMapping({"/platform/bpm/bpmNodeUser/"})
 public class BpmNodeUserController extends BaseController
 {
 
   @Resource
   private BpmNodeUserService bpmNodeUserService;
 
   @RequestMapping({"list"})
   @Action(description="查看InnoDB free: 11264 kB分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.bpmNodeUserService.getAll(new WebQueryFilter(request, "bpmNodeUserItem"));
     ModelAndView mv = getAutoView().addObject("bpmNodeUserList", list);
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除InnoDB free: 11264 kB")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     Long[] lAryId = RequestUtil.getLongAryByStr(request, "userNodeId");
     this.bpmNodeUserService.delByIds(lAryId);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑InnoDB free: 11264 kB")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long userNodeId = Long.valueOf(RequestUtil.getLong(request, "userNodeId"));
     String returnUrl = RequestUtil.getPrePage(request);
     BpmNodeUser bpmNodeUser = null;
     if (userNodeId != null)
       bpmNodeUser = (BpmNodeUser)this.bpmNodeUserService.getById(userNodeId);
     else {
       bpmNodeUser = new BpmNodeUser();
     }
     return getAutoView().addObject("bpmNodeUser", bpmNodeUser).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看InnoDB free: 11264 kB明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "userNodeId");
     BpmNodeUser bpmNodeUser = (BpmNodeUser)this.bpmNodeUserService.getById(Long.valueOf(id));
     return getAutoView().addObject("bpmNodeUser", bpmNodeUser);
   }
 }

