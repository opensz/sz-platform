 package org.sz.platform.controller.system;
 
  import java.util.ArrayList;
import java.util.List;

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
import org.sz.core.util.BeanUtils;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.Position;
import org.sz.platform.model.system.UserPosition;
import org.sz.platform.service.system.PositionService;
import org.sz.platform.service.system.UserPositionService;
 
 @Controller
 @RequestMapping({"/platform/system/position/"})
 public class PositionFormController extends BaseFormController
 {
 
   @Resource
   private PositionService positionService;
 
   @Resource
   UserPositionService userPositionService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新系统岗位表")
   public void save(HttpServletRequest request, HttpServletResponse response, Position position, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("position", position, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     List upList = getUserPositions(request);
     if (position.getPosId() == null) {
       long parentId = RequestUtil.getLong(request, "parentId", 0L);
       Position parent = this.positionService.getParentPositionByParentId(parentId);
 
       position.setPosId(Long.valueOf(UniqueIdUtil.genId()));
       position.setDepth(Integer.valueOf(parent.getDepth().intValue() + 1));
       position.setParentId(parent.getPosId());
       position.setNodePath(parent.getNodePath() + position.getPosId() + ".");
       this.positionService.add(position, upList);
 
       resultMsg = getText("record.added", new Object[] { "系统岗位表" });
     } else {
       this.positionService.update(position);
       resultMsg = getText("record.updated", new Object[] { "系统岗位表" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected Position getFormObject(@RequestParam("posId") Long posId, Model model)
     throws Exception
   {
     this.logger.debug("enter Position getFormObject here....");
     Position position = null;
     if (posId != null)
       position = (Position)this.positionService.getById(posId);
     else {
       position = new Position();
     }
     return position;
   }
 
   private boolean getIsPrimary(Long userId, Long[] aryPrimary)
   {
     if (BeanUtils.isEmpty(aryPrimary)) return false;
     for (int i = 0; i < aryPrimary.length; i++) {
       if (userId == aryPrimary[i]) {
         return true;
       }
     }
     return false;
   }
 
   protected List<UserPosition> getUserPositions(HttpServletRequest request) throws Exception
   {
     List list = new ArrayList();
     Long[] aryUserId = RequestUtil.getLongAry(request, "userId");
     Long[] aryPrimary = RequestUtil.getLongAry(request, "isPrimary");
     if ((aryUserId != null) && (aryUserId.length > 0)) {
       for (int i = 0; i < aryUserId.length; i++) {
         Long userId = aryUserId[i];
         boolean isPrimary = getIsPrimary(userId, aryPrimary);
         UserPosition userPosition = new UserPosition();
         Short primary = isPrimary ? UserPosition.PRIMARY_YES : UserPosition.PRIMARY_NO;
         userPosition.setIsPrimary(primary);
         userPosition.setUserId(userId);
         list.add(userPosition);
       }
     }
     return list;
   }
 }

