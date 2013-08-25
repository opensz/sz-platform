package org.sz.platform.oa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.oa.service.desk.DesktopMycolumnService;
import org.sz.platform.system.model.MessageSend;
import org.sz.platform.system.model.Resources;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.service.MessageSendService;
import org.sz.platform.system.service.ResourcesService;
import org.sz.platform.system.service.SubSystemService;
import org.sz.platform.system.webservice.impl.SystemResourcesServiceImpl;


@Controller
@RequestMapping({ "/platform/console" })
public class MainController extends BaseController {

	@Resource
	private SubSystemService subSystemService;

	@Resource
	private ResourcesService resourcesService;

	@Resource
	private DesktopMycolumnService desktopMycolumnService;

	@Resource
	private MessageSendService msgSendService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@RequestMapping({ "main" })
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SystemResourcesServiceImpl.reSetModify();

		List<SubSystem> subSystemList = this.subSystemService
				.getByUser(ContextUtil.getCurrentUser());

		SubSystem currentSystem = AppUtil.getCurrentSystem(request);

		List<MessageSend> list = this.msgSendService.getNotReadMsg(ContextUtil
				.getCurrentUserId());
		Long currentUserId = ContextUtil.getCurrentUser().getUserId();
		
		if (currentSystem != null) {
			return getView("console", "main")
					.addObject("currentSystem", currentSystem)
					.addObject("currentSystemId",
							Long.valueOf(currentSystem.getSystemId()))
					.addObject("subSystemList", subSystemList)
					.addObject("readMsg", Integer.valueOf(list.size()))
					.addObject("userId", currentUserId);
		}

		if ((subSystemList != null) && (subSystemList.size() == 1)) {
			AppUtil.setCurrentSystem(Long
					.valueOf(((SubSystem) subSystemList.get(0)).getSystemId()),
					request, response);
			return getView("console", "main")
					.addObject("currentSystem", subSystemList.get(0))
					.addObject(
							"currentSystemId",
							Long.valueOf(((SubSystem) subSystemList.get(0))
									.getSystemId()))
					.addObject("subSystemList", subSystemList)
					.addObject("readMsg", Integer.valueOf(list.size()))
					.addObject("userId", currentUserId);
		}

		if (currentSystem == null) {
			return getView("console", "selectCurrSys")
					.addObject("subSystemList", subSystemList)
					.addObject("readMsg", Integer.valueOf(list.size()))
					.addObject("userId", currentUserId);
		}

		if (!subSystemList.contains(currentSystem)) {
			return getView("console", "selectCurrSys")
					.addObject("subSystemList", subSystemList)
					.addObject("readMsg", Integer.valueOf(list.size()))
					.addObject("userId", currentUserId);
		}
		

		return null;
	}
	
	
	
	
	@RequestMapping({ "home" })
	public ModelAndView home(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long userId = ContextUtil.getCurrentUserId().longValue();
		String ctxPath = request.getContextPath();
		Map mapData = this.desktopMycolumnService.getMyDeskData(
				Long.valueOf(userId), ctxPath);
		String html = this.freemarkEngine.mergeTemplateIntoString(
				"desktop/getDeskTop.ftl", mapData);
		return getView("console", "home").addObject("html", html);
	}

	@RequestMapping({ "saveCurrSys" })
	public void saveCurrSys(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long systemId = Long.valueOf(RequestUtil.getLong(request, "systemId"));
		AppUtil.setCurrentSystem(systemId, request, response);
		response.sendRedirect(request.getContextPath()
				+ "/platform/console/main.xht");
	}

	
	
	@RequestMapping({ "getSysRolResTreeData" })
	@ResponseBody
	public List<Resources> getSysRolResTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SubSystem currentSystem = AppUtil.getCurrentSystem(request);
		List resourcesList = this.resourcesService.getSysMenu(currentSystem,
				ContextUtil.getCurrentUser(), request.getContextPath());
		return resourcesList;
	}

	@RequestMapping({ "customerDialog" })
	public ModelAndView getCustomerTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String html = "[{'customerId':1,'customerName':'网络运营中心','parentId':0},{'customerId':2,'customerName':'网络运营中心2','parentId':0}]";
		return getView("console", "customerDialog").addObject("html", html);
	}
}
