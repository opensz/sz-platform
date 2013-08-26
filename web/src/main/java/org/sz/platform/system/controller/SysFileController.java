package org.sz.platform.system.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.model.SysFile;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.GlobalTypeService;
import org.sz.platform.system.service.SysFileService;
import org.sz.platform.system.service.SysUserService;

@Controller
@RequestMapping({ "/platform/system/sysFile/" })
public class SysFileController extends BaseController {
	private Log logger = LogFactory.getLog(SysFileController.class);

	@Resource
	private SysFileService sysFileService;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private GlobalTypeService globalTypeService;

	@RequestMapping({ "list" })
	@Action(description = "查看附件分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId"));
		QueryFilter filter = new WebQueryFilter(request, "sysFileItem");
		if (typeId.longValue() != 0L) {
			filter.addFilter("typeId", typeId);
		}

		List list = this.sysFileService.getAll(filter);
		return getAutoView().addObject("sysFileList", list);
	}

	@RequestMapping({ "del" })
	@Action(description = "删除附件")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String returnUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "fileId");
		try {
			for (Long id : lAryId) {
				SysFile sysFile = (SysFile) this.sysFileService.getById(id);
				String filePath = sysFile.getFilePath();

				FileUtil.deleteFile(AppUtil.getRealPath(filePath));
			}

			this.sysFileService.delByIds(lAryId);
			message = new ResultMessage(1, "删除附件成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除附件失败");
		}
		addMessage(message, request);
		response.sendRedirect(returnUrl);
	}

	@RequestMapping({ "delByFileId" })
	@Action(description = "删除附件")
	public void delByFileId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "ids");
		try {
			for (Long id : lAryId) {
				SysFile sysFile = (SysFile) this.sysFileService.getById(id);
				String filePath = sysFile.getFilePath();

				FileUtil.deleteFile(AppUtil.getRealPath(filePath));
			}

			this.sysFileService.delByIds(lAryId);
			out.print("{\"success\":\"true\"}");
		} catch (Exception e) {
			out.print("{\"success\":\"false\"}");
		}
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑附件")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long fileId = Long.valueOf(RequestUtil.getLong(request, "fileId"));
		String returnUrl = RequestUtil.getPrePage(request);
		SysFile sysFile = null;

		if (fileId.longValue() != 0L)
			sysFile = (SysFile) this.sysFileService.getById(fileId);
		else {
			sysFile = new SysFile();
		}
		return getAutoView().addObject("sysFile", sysFile).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看附件明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "fileId");
		SysFile sysFile = (SysFile) this.sysFileService.getById(Long
				.valueOf(id));
		return getAutoView().addObject("sysFile", sysFile);
	}

	@RequestMapping({ "fileUpload" })
	@Action(description = "文件上传")
	public void fileUpload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try {
			long userId = ContextUtil.getCurrentUserId().longValue();
			long typeId = RequestUtil.getLong(request, "typeId");
			SysUser appUser = null;
			if (userId > 0L) {
				appUser = (SysUser) this.sysUserService.getById(Long
						.valueOf(userId));
			}

			GlobalType globalType = null;
			if (typeId > 0L) {
				globalType = (GlobalType) this.globalTypeService.getById(Long
						.valueOf(typeId));
			}

			Map files = request.getFileMap();
			List<MultipartFile> list = request.getFiles("flexupload");
			Iterator it = files.values().iterator();

			while (it.hasNext()) {
				Long fileId = Long.valueOf(UniqueIdUtil.genId());
				MultipartFile f = (MultipartFile) it.next();
				String oriFileName = f.getOriginalFilename();
				String extName = FileUtil.getFileExt(oriFileName);
				String fileName = fileId + "." + extName;

				String filePath = createFilePath(
						AppUtil.getRealPath("/attachFiles/temp"), fileName);
				FileUtil.writeByte(filePath, f.getBytes());

				SysFile sysFile = new SysFile();
				sysFile.setFileId(fileId);

				sysFile.setFileName(oriFileName.substring(0,
						oriFileName.lastIndexOf('.')));

				Calendar cal = Calendar.getInstance();
				Integer year = Integer.valueOf(cal.get(1));
				Integer month = Integer.valueOf(cal.get(2) + 1);

				sysFile.setFilePath("attachFiles/temp/" + year + "/" + month
						+ "/" + fileName);

				if (globalType != null) {
					sysFile.setTypeId(globalType.getTypeId());
					sysFile.setFileType(globalType.getTypeName());
				} else {
					sysFile.setFileType("-");
				}

				sysFile.setCreatetime(new Date());

				sysFile.setExt(extName);

				sysFile.setTotalBytes(Long.valueOf(f.getSize()));

				sysFile.setNote(FileUtil.getSize(f.getSize()));

				if (appUser != null) {
					sysFile.setCreatorId(appUser.getUserId());
					sysFile.setCreator(appUser.getUsername());
				} else {
					sysFile.setCreator(SysFile.FILE_UPLOAD_UNKNOWN);
				}

				sysFile.setDelFlag(SysFile.FILE_NOT_DEL);
				this.sysFileService.add(sysFile);

				writer.println("{\"success\":\"true\",\"fileId\":\"" + fileId
						+ "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("{\"success\":\"false\"}");
		}
	}

	@RequestMapping({ "saveFile" })
	@Action(description = "保存文件")
	public void saveFile(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try {
			long userId = ContextUtil.getCurrentUserId().longValue();
			long typeId = RequestUtil.getLong(request, "typeId");
			long fileId = RequestUtil.getLong(request, "fileId");
			SysUser appUser = null;
			if (userId > 0L) {
				appUser = (SysUser) this.sysUserService.getById(Long
						.valueOf(userId));
			}

			GlobalType globalType = null;
			if (typeId > 0L) {
				globalType = (GlobalType) this.globalTypeService.getById(Long
						.valueOf(typeId));
			}
			Map files = request.getFileMap();
			Iterator it = files.values().iterator();

			while (it.hasNext()) {
				boolean isAdd = true;
				SysFile sysFile = null;
				if (fileId == 0L) {
					fileId = UniqueIdUtil.genId();
					sysFile = new SysFile();
					sysFile.setFileId(Long.valueOf(fileId));
				} else {
					sysFile = (SysFile) this.sysFileService.getById(Long
							.valueOf(fileId));
					isAdd = false;
				}

				MultipartFile f = (MultipartFile) it.next();
				String oriFileName = f.getOriginalFilename();
				String extName = FileUtil.getFileExt(oriFileName);
				String fileName = fileId + "." + extName;

				String filePath = createFilePath(
						AppUtil.getRealPath("/attachFiles/temp"), fileName);
				FileUtil.writeByte(filePath, f.getBytes());

				sysFile.setFileName(oriFileName.substring(0,
						oriFileName.lastIndexOf('.')));

				Calendar cal = Calendar.getInstance();
				Integer year = Integer.valueOf(cal.get(1));
				Integer month = Integer.valueOf(cal.get(2) + 1);

				sysFile.setFilePath("attachFiles/temp/" + year + "/" + month
						+ "/" + fileName);

				if (globalType != null) {
					sysFile.setTypeId(globalType.getTypeId());
					sysFile.setFileType(globalType.getTypeName());
				} else {
					sysFile.setTypeId(Long.valueOf(0L));
					sysFile.setFileType("-");
				}

				sysFile.setCreatetime(new Date());

				sysFile.setExt(extName);

				sysFile.setTotalBytes(Long.valueOf(f.getSize()));

				sysFile.setNote(FileUtil.getSize(f.getSize()));

				if (appUser != null) {
					sysFile.setCreatorId(appUser.getUserId());
					sysFile.setCreator(appUser.getUsername());
				} else {
					sysFile.setCreator(SysFile.FILE_UPLOAD_UNKNOWN);
				}

				sysFile.setDelFlag(SysFile.FILE_NOT_DEL);
				if (isAdd) {
					this.sysFileService.add(sysFile);
				} else {
					this.sysFileService.update(sysFile);
				}

				writer.print(fileId);
			}
		} catch (Exception e) {
			this.logger.warn(e.getMessage());
			writer.print(-1);
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

	@RequestMapping({ "selector" })
	public ModelAndView selector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView result = getAutoView();
		QueryFilter filter = new WebQueryFilter(request, "sysFileItem");
		filter.addFilter("creatorId", this.getCurrentUser().getUserId());
		List list = this.sysFileService.getAll(filter);
		result.addObject("sysFileList", list);
		int isSingle = RequestUtil.getInt(request, "isSingle", 0);
		result.addObject("isSingle", Integer.valueOf(isSingle));
		return result;
	}

	@RequestMapping({ "download" })
	public void downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long fileId = RequestUtil.getLong(request, "fileId", 0L);
		if (fileId == 0L)
			return;
		SysFile sysFile = (SysFile) this.sysFileService.getById(Long
				.valueOf(fileId));
		if (sysFile == null)
			return;
		String filePath = sysFile.getFilePath();
		if (StringUtil.isEmpty(filePath))
			return;

		String fullPath = FileUtil.getRootPath()
				+ filePath.replace("/", File.separator);

		String fileName = sysFile.getFileName() + "." + sysFile.getExt();

		// TODO
		// FileUtil.downLoadFile(response, fullPath, fileName);
	}

	@RequestMapping({ "getFileById" })
	public void getFileById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long fileId = RequestUtil.getLong(request, "fileId", 0L);
		if (fileId == 0L)
			return;
		SysFile sysFile = (SysFile) this.sysFileService.getById(Long
				.valueOf(fileId));

		String filePath = sysFile.getFilePath();

		String fullPath = FileUtil.getRootPath()
				+ filePath.replace("/", File.separator);

		byte[] bytes = FileUtil.readByte(fullPath);
		response.getOutputStream().write(bytes);
	}

	@RequestMapping({ "personalList" })
	@Action(description = "查看附件分页列表")
	public ModelAndView fileList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId"));
		QueryFilter filter = new WebQueryFilter(request, "sysFileItem");
		if (typeId.longValue() != 0L) {
			filter.addFilter("typeId", typeId);
		}
		List list = this.sysFileService.getFileAttch(filter);
		return getAutoView().addObject("sysFileList", list);
	}
}
