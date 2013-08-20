package org.sz.platform.controller.system;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.StringUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.Position;
import org.sz.platform.service.system.PositionService;
import org.sz.platform.service.system.UserPositionService;

@Controller
@RequestMapping({ "/platform/system/position/" })
public class PositionController extends BaseController {

	@Resource
	private PositionService positionService;

	@Resource
	UserPositionService userPositionService;

	@RequestMapping({ "selector" })
	public ModelAndView selector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "positionItem", false);
		Long posId = Long.valueOf(RequestUtil.getLong(request, "pid"));

		Position pos = (Position) this.positionService.getById(posId);
		if (pos != null) {
			filter.addFilter("nodePath", pos.getNodePath());
		}
		List positionList = this.positionService.getAll(filter);

		String posName = RequestUtil.getString(request, "posName");
		if (StringUtil.isEmpty(posName)) {
			positionList = this.positionService
					.coverTreeList(Long.valueOf(pos != null ? pos.getPosId()
							.longValue() : 0L), positionList);
		}
		ModelAndView mv = getAutoView().addObject("positionList", positionList);
		return mv;
	}

	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.positionService.getAll(new WebQueryFilter(request,
				"positionItem"));

		long parentId = RequestUtil.getLong(request, "parentId", 0L);
		Position parent = this.positionService
				.getParentPositionByParentId(parentId);
		String Q_nodePath_S = RequestUtil.getString(request, "Q_nodePath_S",
				"0.");

		ModelAndView mv = getAutoView().addObject("positionList", list)
				.addObject("Q_nodePath_S", Q_nodePath_S)
				.addObject("parent", parent)
				.addObject("parentId", Long.valueOf(parentId));

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除系统岗位")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "posId");
			this.positionService.delByIds(lAryId);
			message = new ResultMessage(1, "删除系统岗位成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除系统岗位失败");
		}
		addMessage(message, request);
		response.sendRedirect(returnUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑系统岗位")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		Long posId = Long.valueOf(RequestUtil.getLong(request, "posId"));
		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		Position position = null;
		if (posId.longValue() != 0L) {
			position = (Position) this.positionService.getById(posId);
			List userPositionList = this.userPositionService.getByPosId(posId);
			mv.addObject("userPositionList", userPositionList);
		} else {
			long parentId = RequestUtil.getLong(request, "parentId", 0L);
			position = new Position();
			position.setParentId(Long.valueOf(parentId));
		}
		return mv.addObject("position", position).addObject("returnUrl",
				returnUrl);
	}

	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "posId");
		Position position = (Position) this.positionService.getById(Long
				.valueOf(id));

		return getAutoView().addObject("position", position);
	}

	@RequestMapping({ "getChildTreeData" })
	@ResponseBody
	public List<Position> getChildTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long posId = RequestUtil.getLong(request, "posId", 0L);
		long parentId = RequestUtil.getLong(request, "parentId", 0L);
		List list = this.positionService.getAllChildByParentId(posId);
		if ((parentId == 0L) && (posId == 0L)) {
			Position parent = this.positionService
					.getParentPositionByParentId(parentId);
			list.add(parent);
		}
		return list;
	}

	@RequestMapping({ "sort" })
	@Action(description = "系统岗位排序", operateType = "系统操作")
	public void sort(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();

		Long[] lAryId = RequestUtil.getLongAryByStr(request, "posIds");
		if ((lAryId != null) && (lAryId.length > 0)) {
			this.positionService.updSn(lAryId);
		}

		resultObj = new ResultMessage(1, "排序岗位完成");
		out.print(resultObj);
	}

	@RequestMapping({ "move" })
	@Action(description = "转移系统岗位")
	public void move(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		long targetId = RequestUtil.getLong(request, "targetId", 0L);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "originalIds");
		if ((lAryId != null) && (lAryId.length > 0)) {
			Position target = null;
			if (targetId != 0L)
				target = (Position) this.positionService.getById(Long
						.valueOf(targetId));
			else {
				target = this.positionService.getParentPositionByParentId(0L);
			}
			for (int i = 0; i < lAryId.length; i++) {
				Position orgPo = (Position) this.positionService
						.getById(lAryId[i]);

				if (orgPo == null)
					continue;
				String oldNodePath = orgPo.getNodePath();

				List childrenList = this.positionService
						.getByNodePath(oldNodePath);

				long oldParentId = orgPo.getParentId().longValue();
				Position parent = (Position) this.positionService.getById(Long
						.valueOf(oldParentId));

				orgPo.setParentId(target.getPosId());
				orgPo.setNodePath(target.getNodePath() + orgPo.getPosId() + ".");
				orgPo.setDepth(Integer
						.valueOf(target.getDepth().intValue() + 1));
				this.positionService.update(orgPo);

				this.positionService
						.updateChildrenNodePath(orgPo, childrenList);

				if (parent != null) {
					this.positionService.updateIsParent(parent);
				}
			}
		}

		resultObj = new ResultMessage(1, "转动岗位完成");
		out.print(resultObj);
	}

	@RequestMapping({ "getTreeData" })
	@ResponseBody
	public List<Position> getTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List posList = this.positionService.getAll();
		Position pos = new Position();
		pos.setPosId(new Long(0L));
		pos.setPosName("全部");
		pos.setPosDesc("岗位");
		pos.setParentId(new Long(-1L));
		pos.setNodePath("0.");
		posList.add(pos);
		return posList;
	}
}
