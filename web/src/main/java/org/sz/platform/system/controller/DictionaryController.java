package org.sz.platform.system.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.Dictionary;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.service.DictionaryService;
import org.sz.platform.system.service.GlobalTypeService;

@Controller
@RequestMapping({ "/platform/system/dictionary/" })
public class DictionaryController extends BaseController {

	@Resource
	private DictionaryService dictionaryService;

	@Resource
	private GlobalTypeService globalTypeService;

	@RequestMapping({ "edit" })
	@Action(description = "编辑系统分类")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int isAdd = RequestUtil.getInt(request, "isAdd", 0);
		int isRoot = RequestUtil.getInt(request, "isRoot", 0);
		Long dicId = Long.valueOf(RequestUtil.getLong(request, "dicId", 0L));
		ModelAndView mv = getAutoView();
		Dictionary dictionary = null;
		if (isAdd == 1) {
			dictionary = new Dictionary();

			if (isRoot == 1) {
				GlobalType globalType = (GlobalType) this.globalTypeService
						.getById(dicId);
				dictionary.setTypeId(dicId);
				dictionary.setParentId(dicId);
				dictionary.setType(globalType.getType());
			} else {
				Dictionary parentDic = (Dictionary) this.dictionaryService
						.getById(dicId);
				dictionary.setParentId(dicId);
				dictionary.setTypeId(parentDic.getTypeId());
				dictionary.setType(parentDic.getType());
			}
		} else {
			dictionary = (Dictionary) this.dictionaryService.getById(dicId);
		}
		mv.addObject("dictionary", dictionary).addObject("isAdd",
				Integer.valueOf(isAdd));

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除数据字典")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		ResultMessage message = null;
		try {
			Long dicId = Long.valueOf(RequestUtil.getLong(request, "dicId"));
			this.dictionaryService.delByDicId(dicId);
			message = new ResultMessage(1, "删除数据字典成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除数据字典失败");
		}
		writeResultMessage(out, message);
	}

	@RequestMapping({ "sort" })
	@Action(description = "数据字典排序")
	public void sort(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();

		Long[] lAryId = RequestUtil.getLongAryByStr(request, "dicIds");
		ResultMessage message;
		try {
			this.dictionaryService.updSn(lAryId);
			message = new ResultMessage(1, "字典排序成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "字典排序失败!");
		}
		writeResultMessage(out, message);
	}

	@RequestMapping({ "getByTypeId" })
	@ResponseBody
	public List<Dictionary> getByTypeId(HttpServletRequest request) {
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId"));
		List list = this.dictionaryService
				.getByTypeId(typeId.longValue(), true);
		return list;
	}

	@RequestMapping({ "getByNodeKey" })
	@ResponseBody
	public List<Dictionary> getByNodeKey(HttpServletRequest request) {
		String nodeKey = RequestUtil.getString(request, "nodeKey");
		List list = this.dictionaryService.getByNodeKey(nodeKey);
		return list;
	}

	@RequestMapping({ "getMapByNodeKey" })
	@ResponseBody
	public Map<String, Object> getMapByNodeKey(HttpServletRequest request) {
		String nodeKey = RequestUtil.getString(request, "nodeKey");
		Map map = new HashMap();
		GlobalType globalType = this.globalTypeService
				.getByDictNodeKey(nodeKey);
		List list = this.dictionaryService.getByTypeId(globalType.getTypeId()
				.longValue(), false);
		map.put("globalType", globalType);
		map.put("dicList", list);
		return map;
	}

	@RequestMapping({ "move" })
	@Action(description = "移动字典")
	public void move(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		long targetId = RequestUtil.getLong(request, "targetId", 0L);
		long dragId = RequestUtil.getLong(request, "dragId", 0L);
		String moveType = RequestUtil.getString(request, "moveType");
		try {
			this.dictionaryService.move(Long.valueOf(targetId),
					Long.valueOf(dragId), moveType);
			resultObj = new ResultMessage(1, "移动字典成功");
		} catch (Exception ex) {
			resultObj = new ResultMessage(0, "移动字典失败!");
		}
		out.print(resultObj);
	}
}
