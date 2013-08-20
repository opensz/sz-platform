 package org.sz.platform.controller.system;
 
  import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.MessageReceiver;
import org.sz.platform.service.system.MessageReceiverService;
import org.sz.platform.service.system.MessageSendService;
 
 @Controller
 @RequestMapping({"/platform/system/messageReceiver/"})
 public class MessageReceiverController extends BaseController
 {
 
   @Resource
   private MessageReceiverService receiverService;
 
   @Resource
   private MessageSendService sendService;
 
   @RequestMapping({"list"})
   @Action(description="查看消息接收者分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     QueryFilter queryFilter = new WebQueryFilter(request, "messageReceiverItem");
     queryFilter.addFilter("receiverId", ContextUtil.getCurrentUserId());
     List list = this.sendService.getReceiverByUser(queryFilter);
     ModelAndView mv = getAutoView().addObject("messageReceiverList", list).addObject("pam", queryFilter.getFilters());
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除消息接收者")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
       this.receiverService.delByIds(lAryId);
       message = new ResultMessage(1, "删除消息接收者成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑消息接收者")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
     String returnUrl = RequestUtil.getPrePage(request);
     MessageReceiver messageReceiver = null;
     if (id.longValue() != 0L)
       messageReceiver = (MessageReceiver)this.receiverService.getById(id);
     else {
       messageReceiver = new MessageReceiver();
     }
     return getAutoView().addObject("messageReceiver", messageReceiver).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看消息接收者明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "id");
     MessageReceiver messageReceiver = (MessageReceiver)this.receiverService.getById(Long.valueOf(id));
     return getAutoView().addObject("messageReceiver", messageReceiver);
   }
 }

