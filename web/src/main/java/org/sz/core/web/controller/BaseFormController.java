 package org.sz.core.web.controller;
 
 import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springmodules.validation.commons.ConfigurableBeanValidator;
import org.sz.core.web.ResultMessage;
 
 public class BaseFormController extends GenericController
 {
   public Logger logger = LoggerFactory.getLogger(BaseFormController.class);
 
   @Resource
   protected ConfigurableBeanValidator confValidator;
 
   @InitBinder
   protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
   {
     this.logger.debug("init binder ....");
     binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
     binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
//     binder.registerCustomEditor(Boolean.class, new ByteArrayMultipartFileEditor());
     binder.registerCustomEditor(Boolean.class, null, new CustomBooleanEditor(true));
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     dateFormat.setLenient(false);
     binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
   }
 
   protected ResultMessage validForm(String form, Object obj, BindingResult result, HttpServletRequest request)
   {
     ResultMessage resObj = new ResultMessage(1, "");
     this.confValidator.setFormName(form);
     this.confValidator.validate(obj, result);
     if (result.hasErrors())
     {
       resObj.setResult(0);
       List<FieldError> list = result.getFieldErrors();
       String errMsg = "";
       for (FieldError err : list)
       {
         String msg = getText(err.getDefaultMessage(), err.getArguments(), request);
         errMsg = errMsg + msg + "\r\n";
       }
       resObj.setMessage(errMsg);
     }
     return resObj;
   }
 }

