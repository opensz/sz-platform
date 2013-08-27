package org.sz.platform.system.controller;

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
import org.sz.core.dao.JdbcHelper;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.system.model.SysDataSource;
import org.sz.platform.system.service.SysDataSourceService;

@Controller
@RequestMapping({ "/platform/system/sysDataSource/" })
public class SysDataSourceFormController extends BaseFormController {

	@Resource
	private SysDataSourceService service;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新系统数据源")
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysDataSource po, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("sysDataSource", po,
				bindResult, request);
		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}

		if (po.getId() == null) {
			if (this.service.isAliasExisted(po.getAlias())) {
				String msg = getText("errors.sysDataSource.aliasExisted",
						new Object[] { po.getAlias() });
				writeResultMessage(response.getWriter(), msg, 0);
			} else {
				po.setId(Long.valueOf(UniqueIdUtil.genId()));
				this.service.add(po);
				String msg = getText("record.added", new Object[] { "系统数据源管理" });
				writeResultMessage(response.getWriter(), msg, 1);
			}
		} else if (this.service.isAliasExistedByUpdate(po)) {
			String msg = getText("errors.sysDataSource.aliasExisted",
					new Object[] { po.getAlias() });
			writeResultMessage(response.getWriter(), msg, 0);
		} else {
			this.service.update(po);

			JdbcHelper.getInstance().removeAlias(po.getAlias());
			String msg = getText("record.updated", new Object[] { "系统数据源管理" });
			writeResultMessage(response.getWriter(), msg, 1);
		}
	}

	@ModelAttribute
	protected SysDataSource getFormObject(@RequestParam("id") Long id,
			Model model) throws Exception {
		SysDataSource sysDataSource = null;
		if (id != null) {
			sysDataSource = (SysDataSource) this.service.getById(id);
		} else {
			sysDataSource = new SysDataSource();
		}
		return sysDataSource;
	}
}
