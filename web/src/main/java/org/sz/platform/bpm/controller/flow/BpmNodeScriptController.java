package org.sz.platform.bpm.controller.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.StringUtil;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeScript;
import org.sz.platform.bpm.service.flow.BpmNodeScriptService;

@Controller
@RequestMapping({ "/platform/bpm/bpmNodeScript/" })
public class BpmNodeScriptController extends BaseController {

	@Resource
	private BpmNodeScriptService bpmNodeScriptService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新节点运行脚本")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = RequestUtil.getString(request, "nodeId");
		String actDefId = RequestUtil.getString(request, "actDefId");
		String[] aryMemo = request.getParameterValues("memo");
		String[] aryScript = request.getParameterValues("script");
		String[] aryScriptType = request.getParameterValues("scriptType");
		List list = new ArrayList();
		for (int i = 0; i < aryScriptType.length; i++) {
			String memo = aryMemo[i];
			String script = aryScript[i];
			Integer type = Integer.valueOf(Integer.parseInt(aryScriptType[i]));
			if ((StringUtil.isEmpty(script))
					|| (!StringUtil.isNotEmpty(script)))
				continue;
			BpmNodeScript bpmNodeScript = new BpmNodeScript();
			bpmNodeScript.setMemo(memo);
			bpmNodeScript.setScript(script);
			bpmNodeScript.setScriptType(type);
			list.add(bpmNodeScript);
		}
		try {
			this.bpmNodeScriptService.saveScriptDef(actDefId, nodeId, list);
			writeResultMessage(response.getWriter(), "保存节点脚本成功", 1);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					"保存节点脚本失败:" + e.getMessage(), 0);
		}
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑节点运行脚本")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		String nodeId = RequestUtil.getString(request, "nodeId");
		String actDefId = RequestUtil.getString(request, "actDefId");
		String type = RequestUtil.getString(request, "type");

		Map map = this.bpmNodeScriptService.getMapByNodeScriptId(nodeId,
				actDefId);
		return getAutoView().addObject("map", map).addObject("type", type)
				.addObject("nodeId", nodeId).addObject("actDefId", actDefId)
				.addObject("defId", defId);
	}
}
