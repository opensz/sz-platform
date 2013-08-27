 package org.sz.platform.bpm.dao.form.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.form.BpmFormRuleDao;
import org.sz.platform.bpm.model.form.BpmFormRule;
 
 @Repository("bpmFormRuleDao")
 public class BpmFormRuleDaoImpl extends BaseDaoImpl<BpmFormRule> implements BpmFormRuleDao
 {
   public Class getEntityClass()
   {
     return BpmFormRule.class;
   }
 }

