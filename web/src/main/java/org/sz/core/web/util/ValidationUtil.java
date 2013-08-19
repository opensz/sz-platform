 package org.sz.core.web.util;
 
 import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springmodules.validation.commons.ValidatorFactory;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.util.AppUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.valid.Rule;
import org.sz.core.valid.ValidEnum;
import org.sz.core.valid.ValidField;
import org.sz.core.valid.ValidForm;

import freemarker.template.TemplateException;
 
 public class ValidationUtil
 {
   private static Map<String, ValidEnum> map = new HashMap();
 
   private static ValidForm getForm(String formName, Locale local)
   {
     ValidForm form = new ValidForm();
     form.setFormName(formName);
     ApplicationContext ctx = AppUtil.getContext();
     ValidatorFactory factory = (ValidatorFactory)BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, ValidatorFactory.class, true, true);
     ValidatorResources resources = factory.getValidatorResources();
     Form frm = resources.getForm(local, formName);
     if (frm == null) return null;
     List<Field> list = frm.getFields();
     for (Field fld : list)
     {
       Arg arg = fld.getArg(0);
       String displayName = ResourceUtil.getText(arg.getKey(), null, local);
       ValidField vFld = new ValidField();
       vFld.setDisplayName(displayName);
       vFld.setFormName(fld.getProperty());
       getRuleByField(fld, vFld, local);
       form.addField(vFld);
     }
     return form;
   }
 
   private static void getRuleByField(Field field, ValidField vFld, Locale local)
   {
     List<String> list = field.getDependencyList();
     for (String str : list)
     {
       Rule rule = new Rule();
       rule.setName(str);
 
       vFld.addRule(rule);
       handRule(field, rule, local);
     }
   }
 
   private static void handRule(Field field, Rule rule, Locale local)
   {
     Arg arg = field.getArg(0);
     String key = arg.getKey();
     String displayName = ResourceUtil.getText(key, null, local);
     String ruleName = rule.getName();
     ValidEnum ve = (ValidEnum)map.get(ruleName);
 
     String tipInfo = "";
     Arg argE;
     String keyE;
     switch (ve)
     {
     case required:  //ValidEnum.required:
       tipInfo = ResourceUtil.getText("errors.required", displayName, local);
       String value = field.getVarValue(ruleName);
       if (value != null) {
         rule.setRuleInfo(value);
       }
       else {
         rule.setRuleInfo("true");
       }
       rule.setTipInfo(tipInfo);
       break;
     case creditcard:
       tipInfo = ResourceUtil.getText("errors.creditcard", null, local);
       rule.setRuleInfo("true");
       break;
     case date:
       String datePattern = field.getVarValue("datePattern");
       Object[] aryObjDate = { displayName, datePattern };
       tipInfo = ResourceUtil.getText("errors.date", aryObjDate, local);
       rule.setRuleInfo("true");
       break;
     case equalTo:
       argE = field.getArg(1);
       keyE = argE.getKey();
       String equalName = ResourceUtil.getText(keyE, null, local);
 
       String equalTo = field.getVarValue("equalTo");
 
       if (StringUtil.isEmpty(equalName)) equalName = "";
       rule.setRuleInfo(equalTo);
       Object[] aryEqual = { displayName, equalName };
       tipInfo = ResourceUtil.getText("errors.equalTo", aryEqual, local);
 
       break;
     case digits:
       tipInfo = ResourceUtil.getText("errors.digits", displayName, local);
       rule.setRuleInfo("true");
       break;
     case email:
       tipInfo = ResourceUtil.getText("errors.email", displayName, local);
       rule.setRuleInfo("true");
       break;
     case max:
       String max = field.getVarValue("max").replace(",", "");
       Object[] aryObj = { displayName, max };
       tipInfo = ResourceUtil.getText("errors.max", aryObj, local);
       rule.setRuleInfo(max);
       break;
     case maxlength:
       String maxlength = field.getVarValue("maxlength").replace(",", "");
       Object[] aryMaxlength = { displayName, maxlength };
       tipInfo = ResourceUtil.getText("errors.maxlength", aryMaxlength, local);
       rule.setRuleInfo(maxlength);
       break;
     case min:
       String min = field.getVarValue("min");
       Object[] aryMin = { displayName, min };
       tipInfo = ResourceUtil.getText("errors.min", aryMin, local);
       rule.setRuleInfo(min);
       break;
     case minlength:
       String minlength = field.getVarValue("minlength");
       Object[] aryMinlength = { displayName, minlength };
       tipInfo = ResourceUtil.getText("errors.minlength", aryMinlength, local);
       rule.setRuleInfo(minlength);
       break;
     case number:
       tipInfo = ResourceUtil.getText("errors.number", displayName, local);
       rule.setRuleInfo("true");
       break;
     case range:
       String rmin = field.getVarValue("min");
       String rmax = field.getVarValue("max").replace(",", "");
       Object[] aryRange = { displayName, rmin, rmax };
       tipInfo = ResourceUtil.getText("errors.range", aryRange, local);
       String ruleInfo = "[" + rmin + "," + rmax + "]";
       rule.setRuleInfo(ruleInfo);
       break;
     case rangelength:
       String rminlength = field.getVarValue("minlength");
       String rmaxlength = field.getVarValue("maxlength").replace(",", "");
       Object[] aryRangeLength = { displayName, rminlength, rmaxlength };
       tipInfo = ResourceUtil.getText("errors.rangelength", aryRangeLength, local);
       rule.setRuleInfo("[" + rminlength + "," + rmaxlength + "]");
       break;
     case regex:
       String regex = field.getVarValue("regex");
       tipInfo = ResourceUtil.getText("errors.regex", displayName, local);
       rule.setRuleInfo(regex);
       break;
     case url:
       tipInfo = ResourceUtil.getText("errors.url", null, local);
       rule.setRuleInfo("true");
       break;
     case mobile:
       tipInfo = ResourceUtil.getText("errors.mobile", null, local);
       rule.setRuleInfo("true");
       break;
     case phone:
       tipInfo = ResourceUtil.getText("errors.phone", null, local);
       rule.setRuleInfo("true");
       break;
     case zip:
       tipInfo = ResourceUtil.getText("errors.zip", null, local);
       rule.setRuleInfo("true");
       break;
     case qq:
       tipInfo = ResourceUtil.getText("errors.qq", null, local);
       rule.setRuleInfo("true");
       break;
     case chinese:
       tipInfo = ResourceUtil.getText("errors.chinese", displayName, local);
       rule.setRuleInfo("true");
       break;
     case chrnum:
       tipInfo = ResourceUtil.getText("errors.chrnum", displayName, local);
       rule.setRuleInfo("true");
       break;
     case ip:
       tipInfo = ResourceUtil.getText("errors.ip", null, local);
       rule.setRuleInfo("true");
       break;
     case compStartEndTime:
       argE = field.getArg(1);
       keyE = argE.getKey();
       String eTime = ResourceUtil.getText(keyE, null, local);
       String varValue = field.getVarValue("compStartEndTime");
 
       rule.setRuleInfo(varValue);
       Object[] aryEquals = { displayName, eTime };
       tipInfo = ResourceUtil.getText("errors.compDate", aryEquals, local);
     }
 
     rule.setTipInfo(tipInfo);
   }
 
   public static String getJs(String roleForm, Locale local)
     throws IOException, TemplateException
   {
     FreemarkEngine freemaker = (FreemarkEngine)AppUtil.getBean(FreemarkEngine.class);
 
     ValidForm form = getForm(roleForm, local);
 
     Map map = new HashMap();
     map.put("form", form);
     String str = freemaker.mergeTemplateIntoString("validJs.ftl", map);
     return str;
   }
 
   static
   {
     EnumSet<ValidEnum> stateSet = EnumSet.allOf(ValidEnum.class);
     for (ValidEnum s : stateSet)
       map.put(s.name(), s);
   }
 }

