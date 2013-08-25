 package org.sz.platform.bpm.controller.flow;
 
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
import org.sz.platform.bpm.model.flow.BpmDefVar;
import org.sz.platform.bpm.service.flow.BpmDefVarService;
 
 @Controller
 @RequestMapping({"/platform/bpm/bpmDefVar/"})
 public class BpmDefVarFormController extends BaseFormController
 {
 
   @Resource
   private BpmDefVarService bpmDefVarService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新流程变量定义")
   public void save(HttpServletRequest request, HttpServletResponse response, BpmDefVar bpmDefVar, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("bpmDefVar", bpmDefVar, bindResult, request);
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String nodeId = null;
     String nodeName = null;
     if ((bpmDefVar.getVarScope().equals("task")) && 
       (StringUtils.isNotEmpty(bpmDefVar.getNodeId()))) {
       String[] nodeSets = bpmDefVar.getNodeId().split(",");
       nodeId = nodeSets[0];
       nodeName = nodeSets[1];
     }
 
     String resultMsg = null;
     if (bpmDefVar.getVarId() == null) {
       boolean isExist = this.bpmDefVarService.isVarNameExist(bpmDefVar.getVarName(), bpmDefVar.getVarKey(), bpmDefVar.getDefId());
       if (!isExist) {
         bpmDefVar.setVarId(Long.valueOf(UniqueIdUtil.genId()));
         bpmDefVar.setNodeId(nodeId);
         bpmDefVar.setNodeName(nodeName);
         this.bpmDefVarService.add(bpmDefVar);
         resultMsg = getText("record.added", new Object[] { "流程变量" });
         writeResultMessage(response.getWriter(), resultMsg, 1);
       } else {
         resultMsg = getText("该流程中已经存在该变量名称或变量key!");
         writeResultMessage(response.getWriter(), resultMsg, 0);
       }
 
     }
     else
     {
       BpmDefVar var = (BpmDefVar)this.bpmDefVarService.getById(bpmDefVar.getVarId());
       if ((var.getVarName().equals(bpmDefVar.getVarName())) && (var.getVarKey().equals(bpmDefVar.getVarKey()))) {
         bpmDefVar.setNodeId(nodeId);
         bpmDefVar.setNodeName(nodeName);
         this.bpmDefVarService.update(bpmDefVar);
         resultMsg = getText("record.updated", new Object[] { "流程变量" });
         writeResultMessage(response.getWriter(), resultMsg, 1);
       } else if ((var.getVarName().equals(bpmDefVar.getVarName())) && (!var.getVarKey().equals(bpmDefVar.getVarKey()))) {
         boolean rtn = this.bpmDefVarService.isVarNameExist(null, bpmDefVar.getVarKey(), bpmDefVar.getDefId());
         if (rtn) {
           resultMsg = getText("流程中已经存在该变量key!");
           writeResultMessage(response.getWriter(), resultMsg, 0);
         } else {
           bpmDefVar.setNodeId(nodeId);
           bpmDefVar.setNodeName(nodeName);
           this.bpmDefVarService.update(bpmDefVar);
           resultMsg = getText("record.updated", new Object[] { "流程变量" });
           writeResultMessage(response.getWriter(), resultMsg, 1);
         }
       } else if ((!var.getVarName().equals(bpmDefVar.getVarName())) && (var.getVarKey().equals(bpmDefVar.getVarKey()))) {
         boolean rtn = this.bpmDefVarService.isVarNameExist(bpmDefVar.getVarName(), null, bpmDefVar.getDefId());
         if (rtn) {
           resultMsg = getText("流程中已经存在该变量名称!");
           writeResultMessage(response.getWriter(), resultMsg, 0);
         } else {
           bpmDefVar.setNodeId(nodeId);
           bpmDefVar.setNodeName(nodeName);
           this.bpmDefVarService.update(bpmDefVar);
           resultMsg = getText("record.updated", new Object[] { "流程变量" });
           writeResultMessage(response.getWriter(), resultMsg, 1);
         }
       } else {
         bpmDefVar.setNodeId(nodeId);
         bpmDefVar.setNodeName(nodeName);
         this.bpmDefVarService.update(bpmDefVar);
         resultMsg = getText("record.updated", new Object[] { "流程变量" });
         writeResultMessage(response.getWriter(), resultMsg, 1);
       }
     }
   }
 
   @ModelAttribute
   protected BpmDefVar getFormObject(@RequestParam("varId") Long varId, Model model)
     throws Exception
   {
     this.logger.debug("enter BpmDefVar getFormObject here....");
     BpmDefVar bpmDefVar = null;
     if (varId != null)
       bpmDefVar = (BpmDefVar)this.bpmDefVarService.getById(varId);
     else {
       bpmDefVar = new BpmDefVar();
     }
     return bpmDefVar;
   }
 }

