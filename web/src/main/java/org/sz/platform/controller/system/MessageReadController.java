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
import org.sz.platform.model.system.MessageRead;
import org.sz.platform.model.system.MessageSend;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.MessageReadService;
import org.sz.platform.service.system.MessageReplyService;
import org.sz.platform.service.system.MessageSendService;
 
 @Controller
 @RequestMapping({"/platform/system/messageRead/"})
 public class MessageReadController extends BaseController
 {
 
   @Resource
   private MessageReadService readService;
 
   @Resource
   private MessageSendService sendService;
 
   @Resource
   private MessageReplyService replyService;
 
   @RequestMapping({"list"})
   @Action(description="查看接收状态分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     SysUser curUser = ContextUtil.getCurrentUser();
     Long userId = curUser.getUserId();
     String returnUrl = RequestUtil.getPrePage(request);
     Long messageId = Long.valueOf(RequestUtil.getLong(request, "messageId"));
 
     MessageSend messageSend = new MessageSend();
     messageSend = (MessageSend)this.sendService.getById(messageId);
     QueryFilter queryFilter = new WebQueryFilter(request, "messageReplyItem", false);
     queryFilter.addFilter("userId", userId);
     List list = this.replyService.getAll(queryFilter);
 
     this.readService.addMessageRead(messageId, curUser);
 
     ModelAndView mv = getAutoView().addObject("replyList", list.size() > 0 ? list : null).addObject("userId", userId).addObject("messageSend", messageSend).addObject("returnUrl", returnUrl);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除接收状态")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
       this.readService.delByIds(lAryId);
       message = new ResultMessage(1, "删除接收状态成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑接收状态")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
     String returnUrl = RequestUtil.getPrePage(request);
     MessageRead messageRead = null;
     if (id.longValue() != 0L)
       messageRead = (MessageRead)this.readService.getById(id);
     else {
       messageRead = new MessageRead();
     }
     return getAutoView().addObject("messageRead", messageRead).addObject("returnUrl", returnUrl);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看接收状态明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "id");
     MessageRead messageRead = (MessageRead)this.readService.getById(Long.valueOf(id));
     return getAutoView().addObject("messageRead", messageRead);
   }
 }

