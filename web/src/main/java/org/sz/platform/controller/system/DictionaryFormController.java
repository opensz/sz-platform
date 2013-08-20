 package org.sz.platform.controller.system;
 
  import java.io.PrintWriter;

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
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.model.system.Dictionary;
import org.sz.platform.service.system.DictionaryService;
 
 @Controller
 @RequestMapping({"/platform/system/dictionary/"})
 public class DictionaryFormController extends BaseFormController
 {
 
   @Resource
   private DictionaryService dictionaryService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新数据字典")
   public void save(HttpServletRequest request, HttpServletResponse response, Dictionary dictionary, BindingResult bindResult)
     throws Exception
   {
     PrintWriter out = response.getWriter();
     ResultMessage resultMessage = validForm("dictionary", dictionary, bindResult, request);
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
 
     Long dicId = dictionary.getDicId();
     Long typeId = dictionary.getTypeId();
     Long parentId = dictionary.getParentId();
     String itemKey = dictionary.getItemKey();
 
     if (dicId.longValue() == 0L) {
       if (StringUtil.isNotEmpty(itemKey)) {
         boolean rtn = this.dictionaryService.isItemKeyExists(typeId.longValue(), itemKey);
         if (rtn) {
           resultMessage = new ResultMessage(0, "字典关键字已存在");
           writeResultMessage(out, resultMessage);
           return;
         }
       }
       try {
         dicId = Long.valueOf(UniqueIdUtil.genId());
         dictionary.setDicId(dicId);
         dictionary.setSn(Long.valueOf(0L));
 
         if (parentId.equals(typeId)) {
           dictionary.setParentId(typeId);
           dictionary.setNodePath(parentId + "." + dicId + ".");
         }
         else {
           Dictionary parentDic = (Dictionary)this.dictionaryService.getById(parentId);
           dictionary.setParentId(parentId);
           dictionary.setNodePath(parentDic.getNodePath() + dicId + ".");
         }
         this.dictionaryService.add(dictionary);
         resultMessage = new ResultMessage(1, "添加字典成功!");
       }
       catch (Exception ex) {
         resultMessage = new ResultMessage(0, "添加字典失败!");
       }
       writeResultMessage(out, resultMessage);
     }
     else
     {
       if (StringUtil.isNotEmpty(itemKey)) {
         boolean rtn = this.dictionaryService.isItemKeyExistsForUpdate(dicId.longValue(), typeId.longValue(), itemKey);
         if (rtn) {
           resultMessage = new ResultMessage(0, "字典关键字已存在");
           writeResultMessage(out, resultMessage);
           return;
         }
       }
       try {
         this.dictionaryService.update(dictionary);
         resultMessage = new ResultMessage(1, "编辑字典成功!");
       }
       catch (Exception e) {
         resultMessage = new ResultMessage(0, "编辑字典失败!");
       }
       writeResultMessage(out, resultMessage);
     }
   }
 
   @ModelAttribute
   protected Dictionary getFormObject(@RequestParam("dicId") Long dicId, Model model)
     throws Exception
   {
     Dictionary dictionary = null;
 
     if (dicId.longValue() != 0L)
       dictionary = (Dictionary)this.dictionaryService.getById(dicId);
     else {
       dictionary = new Dictionary();
     }
     return dictionary;
   }
 }

