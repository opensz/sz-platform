package org.sz.platform.bpm.controller.flow;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeMessage;
import org.sz.platform.bpm.service.flow.BpmNodeMessageService;
import org.sz.platform.system.model.Message;
import org.sz.platform.system.service.MessageService;
import org.sz.platform.system.service.SysTemplateService;

@Controller
@RequestMapping({ "/platform/bpm/bpmNodeMessage/" })
public class BpmNodeMessageController extends BaseController {

	@Resource
	private BpmNodeMessageService bpmNodeMessageService;

	@Resource
	private SysTemplateService sysTempplateService;

	@Resource
	private MessageService messageService;

	@RequestMapping({ "list" })
	@Action(description = "查看流程节点邮件分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.bpmNodeMessageService.getAll(new WebQueryFilter(
				request, "bpmNodeMessageItem"));
		ModelAndView mv = getAutoView().addObject("bpmNodeMessageList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流程节点邮件")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
		this.bpmNodeMessageService.delByIds(lAryId);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑流程节点邮件")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		List tempList = this.sysTempplateService.getAll(new WebQueryFilter(
				request, "sysTemplateItem"));
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String subject_mail = "";
		String receiver_mail = "";
		String copyTo_mail = "";
		String bcc_mail = "";
		Long templateId_mail = Long.valueOf(0L);

		String receiver_mobile = "";
		Long templateId_mobile = Long.valueOf(0L);

		String subject_inner = "";
		String receiver_inner = "";
		Long templateId_inner = Long.valueOf(0L);

		Map dataMap = this.messageService.getMapByActDefIdNodeId(actDefId,
				nodeId);

		Message mailModel = (Message) dataMap
				.get(org.sz.platform.system.model.Message.MAIL_TYPE);
		if (mailModel != null) {
			subject_mail = mailModel.getSubject();
			receiver_mail = mailModel.getReceiver();
			copyTo_mail = mailModel.getCopyTo();
			bcc_mail = mailModel.getBcc();
			templateId_mail = mailModel.getTemplateId();
		}
		Message mobileModel = (Message) dataMap
				.get(org.sz.platform.system.model.Message.MOBILE_TYPE);
		if (mobileModel != null) {
			receiver_mobile = mobileModel.getReceiver();
			templateId_mobile = mobileModel.getTemplateId();
		}
		Message innerModel = (Message) dataMap
				.get(org.sz.platform.system.model.Message.INNER_TYPE);
		if (innerModel != null) {
			subject_inner = innerModel.getSubject();
			receiver_inner = innerModel.getReceiver();
			templateId_inner = innerModel.getTemplateId();
		}

		return getAutoView().addObject("actDefId", actDefId)
				.addObject("nodeId", nodeId)
				.addObject("subject_mail", subject_mail)
				.addObject("receiver_mail", receiver_mail)
				.addObject("copyTo_mail", copyTo_mail)
				.addObject("bcc_mail", bcc_mail)
				.addObject("templateId_mail", templateId_mail)
				.addObject("receiver_mobile", receiver_mobile)
				.addObject("templateId_mobile", templateId_mobile)
				.addObject("subject_inner", subject_inner)
				.addObject("receiver_inner", receiver_inner)
				.addObject("templateId_inner", templateId_inner)
				.addObject("tempList", tempList);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看流程节点邮件明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		BpmNodeMessage bpmNodeMessage = (BpmNodeMessage) this.bpmNodeMessageService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("bpmNodeMessage", bpmNodeMessage);
	}
}
