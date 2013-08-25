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
import org.sz.platform.system.model.Message;
import org.sz.platform.system.service.MessageService;
 
 @Controller
 @RequestMapping({"/platform/system/message/"})
 public class MessageController extends BaseController
 {
 
   @Resource
   private MessageService messageService;
 
   @RequestMapping({"list"})
   @Action(description="查看消息设置分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.messageService.getAll(new WebQueryFilter(request, "messageItem"));
     ModelAndView mv = getAutoView().addObject("messageList", list);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除消息设置")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "messageId");
       this.messageService.delByIds(lAryId);
       message = new ResultMessage(1, "删除消息设置成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑消息设置")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long messageId = Long.valueOf(RequestUtil.getLong(request, "messageId"));
     String returnUrl = RequestUtil.getPrePage(request);
     Message message = null;
     if (messageId.longValue() != 0L)
       message = (Message)this.messageService.getById(messageId);
     else {
       message = new Message();
     }
     return getAutoView().addObject("message", message).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看消息设置明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "messageId");
     Message message = (Message)this.messageService.getById(Long.valueOf(id));
     return getAutoView().addObject("message", message);
   }
 }

