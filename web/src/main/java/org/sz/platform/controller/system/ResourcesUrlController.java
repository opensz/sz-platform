 package org.sz.platform.controller.system;
 
  import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.Resources;
import org.sz.platform.model.system.ResourcesUrl;
import org.sz.platform.service.system.ResourcesService;
import org.sz.platform.service.system.ResourcesUrlService;
 
 @Controller
 @RequestMapping({"/platform/system/resourcesUrl/"})
 public class ResourcesUrlController extends BaseController
 {
 
   @Resource
   private ResourcesUrlService resourcesUrlService;
 
   @Resource
   private ResourcesService resourcesService;
 
   @RequestMapping({"edit"})
   @Action(description="编辑资源URL")
   public ModelAndView edit(HttpServletRequest request)
     throws Exception
   {
     Long resId = Long.valueOf(RequestUtil.getLong(request, "resId", 0L));
     String returnUrl = RequestUtil.getString(request, "returnUrl", RequestUtil.getPrePage(request));
     List resourcesUrlList = this.resourcesUrlService.getByResId(resId.longValue());
     Resources resources = (Resources)this.resourcesService.getById(resId);
     return getAutoView().addObject("resourcesUrlList", resourcesUrlList).addObject("returnUrl", returnUrl).addObject("resources", resources);
   }
 
   @RequestMapping({"upd"})
   @Action(description="添加或更新资源URL")
   public void upd(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     PrintWriter out = response.getWriter();
     long resId = RequestUtil.getLong(request, "resId", 0L);
     String[] name = request.getParameterValues("name");
     String[] url = request.getParameterValues("url");
     String[] parameter = request.getParameterValues("parameter");
     if (resId != 0L) {
       List resourcesUrlList = new ArrayList();
       if (name != null) {
         for (int i = 0; i < name.length; i++) {
           ResourcesUrl resUrl = new ResourcesUrl();
           resUrl.setResUrlId(Long.valueOf(UniqueIdUtil.genId()));
           resUrl.setResId(Long.valueOf(resId));
           String nameTemp = name[i];
           if (nameTemp != null) nameTemp = nameTemp.trim();
           resUrl.setName(nameTemp);
           String urlTemp = url[i];
           if (urlTemp != null) urlTemp = urlTemp.trim();
           resUrl.setUrl(urlTemp);
 
           resourcesUrlList.add(resUrl);
         }
       }
 
       this.resourcesUrlService.update(resId, resourcesUrlList);
       String defaultUrl = RequestUtil.getSecureString(request, "defaultUrl", "");
       if ((defaultUrl != null) && (!defaultUrl.equals(""))) {
         Resources res = (Resources)this.resourcesService.getById(Long.valueOf(resId));
         res.setDefaultUrl(defaultUrl.trim());
         this.resourcesService.update(res);
       }
     }
 
     ResultMessage message = new ResultMessage(1, "编辑资源URL成功");
     out.print(message.toString());
   }
 }

