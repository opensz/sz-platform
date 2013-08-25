 package org.sz.platform.system.controller;
 
  import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.DateUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.ReportTemplate;
import org.sz.platform.system.service.GlobalTypeService;
import org.sz.platform.system.service.ReportTemplateService;
 
 @Controller
 @RequestMapping({"/platform/system/reportTemplate/"})
 public class ReportTemplateController extends BaseController
 {
 
   @Resource
   private ReportTemplateService reportTemplateService;
 
   @Resource
   private GlobalTypeService globalTypeService;
 
   @RequestMapping({"list"})
   @Action(description="查看报表模板分页列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     List list = this.reportTemplateService.getAll(new WebQueryFilter(request, "reportTemplateItem"));
     ModelAndView mv = getAutoView().addObject("reportTemplateList", list);
 
     return mv;
   }
 
   @RequestMapping({"del"})
   @Action(description="删除报表模板")
   public void del(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String preUrl = RequestUtil.getPrePage(request);
     ResultMessage message = null;
     try {
       Long[] lAryId = RequestUtil.getLongAryByStr(request, "reportId");
       this.reportTemplateService.delByIds(lAryId);
       message = new ResultMessage(1, "删除报表模板成功!");
     }
     catch (Exception ex) {
       message = new ResultMessage(0, "删除失败:" + ex.getMessage());
     }
     addMessage(message, request);
     response.sendRedirect(preUrl);
   }
   @RequestMapping({"edit"})
   @Action(description="编辑报表模板")
   public ModelAndView edit(HttpServletRequest request) throws Exception {
     Long reportId = Long.valueOf(RequestUtil.getLong(request, "reportId"));
     String returnUrl = RequestUtil.getPrePage(request);
     ReportTemplate reportTemplate = null;
     if (reportId.longValue() != 0L)
       reportTemplate = (ReportTemplate)this.reportTemplateService.getById(reportId);
     else {
       reportTemplate = new ReportTemplate();
     }
     List list = this.globalTypeService.getByCatKey("REPORT_TYPE", true);
     return getAutoView().addObject("reportTemplate", reportTemplate).addObject("returnUrl", returnUrl).addObject("typelist", list);
   }
 
   @RequestMapping({"get"})
   @Action(description="查看报表模板明细")
   public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     long id = RequestUtil.getLong(request, "reportId");
     ReportTemplate reportTemplate = (ReportTemplate)this.reportTemplateService.getById(Long.valueOf(id));
     return getAutoView().addObject("reportTemplate", reportTemplate);
   }
 
   @RequestMapping({"save"})
   @Action(description="添加或更新流程任务评论")
   public void save(MultipartHttpServletRequest request, HttpServletResponse response, ReportTemplate reportTemplate)
     throws Exception
   {
     String createTime = RequestUtil.getString(request, "tmpCreateTime");
     String st = "";
     if (StringUtil.isNotEmpty(createTime)) {
       st = DateUtil.timeStrToDateStr(createTime);
     }
 
     Map files = request.getFileMap();
     Iterator it = files.values().iterator();
 
     if (it.hasNext()) {
       MultipartFile f = (MultipartFile)it.next();
       String oriFileName = f.getOriginalFilename();
 
       String filePath = FileUtil.getRootPath() + "WEB-INF\\" + "reportlets" + "\\" + oriFileName;
       FileUtil.writeByte(filePath, f.getBytes());
 
       this.reportTemplateService.saveReportTemplate(reportTemplate, "\\WEB-INF\\reportlets\\" + oriFileName, st.length() > 0 ? DateUtil.parseDate(st) : new Date());
     }
 
     PrintWriter writer = response.getWriter();
     String result = "{\"result\":1,\"message\":\"上传报表模板成功\"}";
     writer.print(result);
   }
 }

