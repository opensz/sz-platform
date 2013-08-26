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
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.service.GlobalTypeService;

@Controller
@RequestMapping({ "/platform/system/globalType/" })
public class GlobalTypeFormController extends BaseFormController {

	@Resource
	private GlobalTypeService globalTypeService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新系统分类")
	public void save(HttpServletRequest request, HttpServletResponse response,
			GlobalType globalType, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("globalType", globalType,
				bindResult, request);
		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}

		long parentId = RequestUtil.getLong(request, "parentId", 0L);

		int isRoot = RequestUtil.getInt(request, "isRoot");

		int isPrivate = RequestUtil.getInt(request, "isPrivate", 0);

		Long userId = ContextUtil.getCurrentUserId();

		String nodeKey = globalType.getNodeKey();
		String resultMsg = null;
		if (globalType.getTypeId().longValue() == 0L) {
			GlobalType tmpGlobalType = this.globalTypeService
					.getInitGlobalType(isRoot, parentId);
			String catKey = tmpGlobalType.getCatKey();
			if (StringUtil.isNotEmpty(nodeKey)) {
				boolean isExist = this.globalTypeService.isNodeKeyExists(
						catKey, nodeKey);
				if (isExist) {
					resultMsg = "节点KEY已存在!";
					writeResultMessage(response.getWriter(), resultMsg, 0);
					return;
				}
			}

			if (!catKey.equals("DIC")) {
				globalType.setType(tmpGlobalType.getType());
			}

			if (isPrivate == 1) {
				globalType.setUserId(userId);
			}
			globalType.setCatKey(catKey);
			globalType.setNodePath(tmpGlobalType.getNodePath());
			globalType.setTypeId(tmpGlobalType.getTypeId());
			globalType.setDepth(Long.valueOf(1L));
			globalType.setSn(Long.valueOf(0L));

			this.globalTypeService.add(globalType);
			resultMsg = getText("record.added", new Object[] { "系统分类" });
		} else {
			Long typeId = globalType.getTypeId();
			String catKey = globalType.getCatKey();

			boolean isExist = this.globalTypeService.isNodeKeyExistsForUpdate(
					typeId, catKey, nodeKey);
			if (isExist) {
				resultMsg = "节点KEY已存在!";
				writeResultMessage(response.getWriter(), resultMsg, 0);
				return;
			}
			this.globalTypeService.update(globalType);
			resultMsg = getText("record.updated", new Object[] { "系统分类" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected GlobalType getFormObject(@RequestParam("typeId") Long typeId,
			Model model) throws Exception {
		GlobalType globalType = null;

		if (typeId.longValue() != 0L)
			globalType = (GlobalType) this.globalTypeService.getById(typeId);
		else {
			globalType = new GlobalType();
		}
		return globalType;
	}
}
