 package org.sz.platform.bpm.service.form.impl;
 
 import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.FileUtil;
import org.sz.platform.bpm.dao.form.BpmFormRuleDao;
import org.sz.platform.bpm.model.form.BpmFormRule;
import org.sz.platform.bpm.service.form.BpmFormRuleService;
import org.sz.platform.bpm.util.FormUtil;
 
 @Service("bpmFormRuleService")
 public class BpmFormRuleServiceImpl extends BaseServiceImpl<BpmFormRule> implements BpmFormRuleService
 {
 
   @Resource
   private BpmFormRuleDao dao;
 
   protected IEntityDao<BpmFormRule, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void generateJS()
     throws IOException
   {
     StringBuffer js = new StringBuffer();
 
     List<BpmFormRule> list = this.dao.getAll();
     for (BpmFormRule rule : list) {
       js.append("//").append(rule.getMemo().trim().replaceAll("\n", "")).append("\n");
       js.append("jQuery.validator.addMethod('").append(rule.getName().trim()).append("', function(value, element) {").append("\n");
       js.append("\treturn this.optional(element) || /" + rule.getRule().trim().replaceAll("(?<!\\\\)/", "\\\\/") + "/.test(value);").append("\n");
       js.append("}, '" + rule.getTipInfo().trim() + "');").append("\n");
       js.append("\n");
     }
     String fileName = FormUtil.getRuleJsPath();
 
     FileUtil.writeFile(fileName, js.toString());
   }
 }

