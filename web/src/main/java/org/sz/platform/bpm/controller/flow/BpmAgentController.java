package org.sz.platform.bpm.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.core.annotion.Action;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.controller.BaseController;
import org.sz.platform.bpm.service.flow.BpmAgentService;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.model.SysUserAgent;
import org.sz.platform.system.service.SysUserAgentService;
import org.sz.platform.system.service.SysUserService;

@Controller
@RequestMapping({ "/platform/bpm/bpmAgent/" })
public class BpmAgentController extends BaseController {

	@Resource
	private BpmAgentService bpmAgentService;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysUserAgentService sysUserAgentService;

	@RequestMapping({ "tree" })
	@Action(description = "取得代理人树信息")
	@ResponseBody
	public String tree(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<SysUserAgent> list = this.sysUserAgentService
				.getByTouserId(ContextUtil.getCurrentUserId());

		StringBuffer json = new StringBuffer(
				"[{name:'所有授权人', alias:'', open:true,id:'0',nodes: [");

		for (SysUserAgent sua : list) {
			SysUser su = (SysUser) this.sysUserService.getById(sua
					.getAgentuserid());
			json.append("{name:'").append(su.getFullname())
					.append("',alias:'',link:'',id:'").append(sua.getAgentid())
					.append("'}").append(",");
		}

		if (list.size() > 0) {
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]}]");
		return json.toString();
	}
}
