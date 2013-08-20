package org.sz.platform.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.model.system.SysUserAgent;
import org.sz.platform.service.system.SysUserAgentService;

@Controller
@RequestMapping({ "/platform/system/sysUserAgent/" })
public class SysUserAgentFormController extends BaseFormController {

	@Resource
	private SysUserAgentService sysUserAgentService;

//	@Resource
//	private BpmAgentService bpmAgentService;

//	@Resource
//	private BpmDefinitionService bpmDefinitionService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新SYS_USER_AGENT")
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysUserAgent sysUserAgent, BindingResult bindResult)
			throws Exception {
		ResultMessage resultMessage = validForm("sysUserAgent", sysUserAgent,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
//		List bpmAgentList = getBpmAgents(request);
		if (sysUserAgent.getAgentid() == null) {
			SysUser su = ContextUtil.getCurrentUser();
			sysUserAgent.setAgentuserid(su.getUserId());
			sysUserAgent.setAgentid(Long.valueOf(UniqueIdUtil.genId()));
//			this.sysUserAgentService.add(sysUserAgent, bpmAgentList);
			resultMsg = getText("record.added", new Object[] { "用户代理" });
		} else {
//			this.sysUserAgentService.update(sysUserAgent, bpmAgentList);
			resultMsg = getText("record.updated", new Object[] { "用户代理" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected SysUserAgent getFormObject(@RequestParam("agentid") Long agentid,
			Model model) throws Exception {
		this.logger.debug("enter SysUserAgent getFormObject here....");
		SysUserAgent sysUserAgent = null;
		if (agentid != null)
			sysUserAgent = (SysUserAgent) this.sysUserAgentService
					.getById(agentid);
		else {
			sysUserAgent = new SysUserAgent();
		}
		return sysUserAgent;
	}

//	protected List<BpmAgent> getBpmAgents(HttpServletRequest request)
//			throws Exception {
//		List list = new ArrayList();
//
//		String[] agentId = request.getParameterValues("agentId");
//		String[] defId = request.getParameterValues("defId");
//
//		if ((defId != null) && (defId.length > 0)) {
//			for (int i = 0; i < defId.length; i++) {
//				if (StringUtils.isNotEmpty(defId[i])) {
//					BpmAgent bpmAgent = null;
//					if ((agentId != null)
//							&& (StringUtils.isNotEmpty(agentId[i]))) {
//						bpmAgent = (BpmAgent) this.bpmAgentService
//								.getById(new Long(agentId[i]));
//					} else {
//						bpmAgent = new BpmAgent();
//						bpmAgent.setId(Long.valueOf(UniqueIdUtil.genId()));
//					}
//					BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
//							.getById(new Long(defId[i]));
//					bpmAgent.setDefid(new Long(defId[i]));
//					bpmAgent.setSubject(bpmDefinition.getSubject());
//					bpmAgent.setActdefid(bpmDefinition.getActDefId());
//					list.add(bpmAgent);
//				}
//			}
//		}
//
//		return list;
//	}
}
