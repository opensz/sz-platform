package org.sz.platform.system.controller;

import java.io.File;
import java.util.Calendar;
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
import org.sz.core.util.ContextUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysOfficeTemplate;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.SysOfficeTemplateService;

@Controller
@RequestMapping({ "/platform/system/sysOfficeTemplate/" })
public class SysOfficeTemplateController extends BaseController {

	@Resource
	private SysOfficeTemplateService sysOfficeTemplateService;

	@RequestMapping({ "selector" })
	@Action(description = "查看系统模版分页列表")
	public ModelAndView selector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.sysOfficeTemplateService.getAll(new WebQueryFilter(
				request, "sysOfficeTemplateItem"));
		ModelAndView mv = getAutoView()
				.addObject("sysOfficeTemplateList", list);

		return mv;
	}

	@RequestMapping({ "list" })
	@Action(description = "查看系统模版分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.sysOfficeTemplateService.getAll(new WebQueryFilter(
				request, "sysOfficeTemplateItem"));
		ModelAndView mv = getAutoView()
				.addObject("sysOfficeTemplateList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除系统模版")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.sysOfficeTemplateService.delByIds(lAryId);
			message = new ResultMessage(1, "删除系统模版成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑系统模版")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		SysOfficeTemplate sysOfficeTemplate = null;
		if (id.longValue() != 0L)
			sysOfficeTemplate = (SysOfficeTemplate) this.sysOfficeTemplateService
					.getById(id);
		else {
			sysOfficeTemplate = new SysOfficeTemplate();
		}
		return getAutoView().addObject("sysOfficeTemplate", sysOfficeTemplate)
				.addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看系统模版明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		SysOfficeTemplate sysOfficeTemplate = (SysOfficeTemplate) this.sysOfficeTemplateService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("sysOfficeTemplate", sysOfficeTemplate);
	}

	@RequestMapping({ "saveTemplate" })
	@Action(description = "保存更新系统模板")
	public void saveTemplate(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resultMsg = "";
		try {
			SysUser user = ContextUtil.getCurrentUser();
			String memo = RequestUtil.getString(request, "memo");
			String subject = RequestUtil.getString(request, "subject");
			int templatetype = RequestUtil.getInt(request, "templatetype");
			long id = RequestUtil.getLong(request, "id");

			SysOfficeTemplate sysOfficeTemplate = new SysOfficeTemplate();
			sysOfficeTemplate.setCreatetime(new Date());
			sysOfficeTemplate.setCreator(user.getFullname());
			sysOfficeTemplate.setCreatorid(user.getUserId());
			sysOfficeTemplate.setMemo(memo);
			sysOfficeTemplate.setSubject(subject);
			sysOfficeTemplate.setTemplatetype(Integer.valueOf(templatetype));

			Map files = request.getFileMap();
			Iterator it = files.values().iterator();

			if (it.hasNext()) {
				Long fileId = Long.valueOf(UniqueIdUtil.genId());
				MultipartFile f = (MultipartFile) it.next();
				String oriFileName = f.getOriginalFilename();
				String extName = FileUtil.getFileExt(oriFileName);
				String doc = this.configproperties.getProperty("officedoc");

				if (!isOfficeFile(extName)) {
					writeResultMessage(response.getWriter(), new ResultMessage(
							0, "上传的模板文件格式必须为：" + doc));
					return;
				}

				String fileName = fileId + "." + extName;

				String filePath = createFilePath(
						AppUtil.getRealPath("/attachFiles/temp"), fileName);

				FileUtil.writeByte(filePath, f.getBytes());

				Calendar cal = Calendar.getInstance();
				Integer year = Integer.valueOf(cal.get(1));
				Integer month = Integer.valueOf(cal.get(2) + 1);

				sysOfficeTemplate.setPath("attachFiles/temp/" + year + "/"
						+ month + "/" + fileName);

				if (id == 0L) {
					sysOfficeTemplate.setId(Long.valueOf(UniqueIdUtil.genId()));
					this.sysOfficeTemplateService.add(sysOfficeTemplate);
					resultMsg = "添加系统模板成功";
				} else {
					sysOfficeTemplate.setId(Long.valueOf(id));
					this.sysOfficeTemplateService.update(sysOfficeTemplate);
					resultMsg = "更新系统模板成功";
				}
			} else {
				sysOfficeTemplate.setId(Long.valueOf(id));
				sysOfficeTemplate
						.setPath(((SysOfficeTemplate) this.sysOfficeTemplateService
								.getById(Long.valueOf(id))).getPath());
				this.sysOfficeTemplateService.update(sysOfficeTemplate);
				resultMsg = "更新系统模板成功";
			}
			writeResultMessage(response.getWriter(), new ResultMessage(1,
					resultMsg));
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), new ResultMessage(0,
					"操作系统模板失败"));
		}
	}

	private String createFilePath(String tempPath, String fileName) {
		File one = new File(tempPath);
		Calendar cal = Calendar.getInstance();
		Integer year = Integer.valueOf(cal.get(1));
		Integer month = Integer.valueOf(cal.get(2) + 1);
		one = new File(tempPath + "/" + year + "/" + month);
		if (!one.exists()) {
			one.mkdirs();
		}
		return one.getPath() + File.separator + fileName;
	}

	private boolean isOfficeFile(String extName) {
		String doc = this.configproperties.getProperty("officedoc");
		String[] fileExts = doc.split(",");
		boolean isOfficeFile = true;
		for (String ext : fileExts) {
			if (extName.equals(ext)) {
				return true;
			}
			isOfficeFile = false;
		}

		return isOfficeFile;
	}
}
