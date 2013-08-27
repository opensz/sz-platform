package org.sz.platform.oa.service.desk.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.oa.dao.desk.DesktopColumnDao;
import org.sz.platform.oa.dao.desk.DesktopLayoutDao;
import org.sz.platform.oa.dao.desk.DesktopLayoutcolDao;
import org.sz.platform.oa.dao.desk.DesktopMycolumnDao;
import org.sz.platform.oa.model.desk.DesktopColumn;
import org.sz.platform.oa.model.desk.DesktopLayout;
import org.sz.platform.oa.model.desk.DesktopLayoutcol;
import org.sz.platform.oa.model.desk.DesktopMycolumn;
import org.sz.platform.oa.service.desk.DesktopMycolumnService;

@Service("desktopMycolumnService")
public class DesktopMycolumnServiceImpl extends
		BaseServiceImpl<DesktopMycolumn> implements DesktopMycolumnService {

	@Resource
	private DesktopMycolumnDao dao;

	@Resource
	private DesktopLayoutDao desktopLayoutDao;

	@Resource
	private DesktopLayoutcolDao desktopLayoutcolDao;

	@Resource
	private DesktopColumnDao desktopColumnDao;

	@Resource
	private FreemarkEngine freemarkEngine;

	protected IEntityDao<DesktopMycolumn, Long> getEntityDao() {
		return this.dao;
	}

	public List<DesktopMycolumn> getByUserId(Long userId) {
		return this.dao.getByUserId(userId);
	}

	public void delByLayoutId(Long layoutId) {
		this.dao.delByLayoutId(layoutId);
	}

	public void delByUserId(Long userId) {
		this.dao.delByUserId(userId);
	}

	public Map getMyDeskData(Long userId, String ctxPath) throws Exception {
		List<DesktopMycolumn> list = this.dao.getMyDeskData(userId);
		DesktopLayout layout = null;
		if (list.size() == 0) {
			list = this.dao.getDefaultDeskData();
			layout = this.desktopLayoutDao.getDefaultLayout();
		} else {
			layout = this.desktopLayoutDao.getLayoutByUserId(userId);
		}
		Map mapData = null;
		if (layout != null) {
			String[] widths = layout.getWidth().split(",");
			String[] columns = new String[layout.getCols().shortValue()];
			Map map = new HashMap();
			Map widthMap = new HashMap();
			for (int i = 1; i <= layout.getCols().shortValue(); i++) {
				columns[(i - 1)] = ("" + i);
				widthMap.put("" + i, widths[(i - 1)]);
			}
			for (DesktopMycolumn desktopMycolumn : list) {
				desktopMycolumn.setColumnHtml(getHtmlByMycolumn(
						desktopMycolumn, ctxPath));
				if (!StringUtil.isEmpty(desktopMycolumn.getColumnUrl()))
					desktopMycolumn
							.setColumnUrl(desktopMycolumn.getColumnUrl());
				// desktopMycolumn.setColumnUrl(ctxPath +
				// desktopMycolumn.getColumnUrl());
				List subList = (List) map.get(desktopMycolumn.getCol()
						.toString());
				if (subList == null) {
					subList = new ArrayList();
					map.put(desktopMycolumn.getCol().toString(), subList);
				}
				subList.add(desktopMycolumn);
			}
			mapData = new HashMap();
			mapData.put("layoutId", layout.getId());
			mapData.put("columns", columns);
			mapData.put("widthMap", widthMap);
			mapData.put("datas", map);
		}
		return mapData;
	}

	public Map getDefaultDeskDataById(Long layoutId, String ctxPath)
			throws Exception {
		List<DesktopMycolumn> list = this.dao.getDefaultDeskDataById(layoutId);
		DesktopLayout layout = (DesktopLayout) this.desktopLayoutDao
				.getById(layoutId);
		String[] widths = layout.getWidth().split(",");
		String[] columns = new String[layout.getCols().shortValue()];
		Map map = new HashMap();
		Map widthMap = new HashMap();
		for (int i = 1; i <= layout.getCols().shortValue(); i++) {
			columns[(i - 1)] = ("" + i);
			widthMap.put("" + i, widths[(i - 1)]);
		}
		for (DesktopMycolumn desktopMycolumn : list) {
			desktopMycolumn.setColumnHtml(getHtmlByMycolumn(desktopMycolumn,
					ctxPath));
			if (!StringUtil.isEmpty(desktopMycolumn.getColumnUrl()))
				desktopMycolumn.setColumnUrl(ctxPath
						+ desktopMycolumn.getColumnUrl());
			List subList = (List) map.get(desktopMycolumn.getCol().toString());
			if (subList == null) {
				subList = new ArrayList();
				map.put(desktopMycolumn.getCol().toString(), subList);
			}
			subList.add(desktopMycolumn);
		}
		Map mapData = new HashMap();
		mapData.put("layoutId", layout.getId());
		mapData.put("columns", columns);
		mapData.put("widthMap", widthMap);
		mapData.put("datas", map);
		return mapData;
	}

	private String getHtmlByMycolumn(DesktopMycolumn desktopMycolumn,
			String ctxPath) throws Exception {
		String[] aryHandler = desktopMycolumn.getServicemethod().split("[.]");
		Object model = null;
		if (aryHandler != null) {
			String beanId = aryHandler[0];
			String method = aryHandler[1];

			Object serviceBean = ContextUtil.getBean(beanId);
			if (serviceBean != null) {
				Method invokeMethod = serviceBean.getClass().getDeclaredMethod(
						method, null);
				model = invokeMethod.invoke(serviceBean, null);
			}
		}
		Map map = new HashMap();
		map.put("model", model);
		map.put("ctxPath", ctxPath);
		String html = this.freemarkEngine.parseByStringTemplate(map,
				desktopMycolumn.getColumnHtml());
		return html;
	}

	public List<DesktopMycolumn> getMyListData(long userId) throws Exception {
		List listMy = new ArrayList();

		Long layoutId = Long.valueOf(this.desktopLayoutDao.getDefaultId());

		List<DesktopLayoutcol> listcol = this.desktopLayoutcolDao
				.getByLayoutId(layoutId);

		for (DesktopLayoutcol bean : listcol) {
			DesktopMycolumn thisBean = new DesktopMycolumn();
			thisBean.setId(Long.valueOf(UniqueIdUtil.genId()));
			thisBean.setUserId(Long.valueOf(userId));
			thisBean.setCol(Short.valueOf(bean.getCol().shortValue()));
			thisBean.setColumnId(bean.getColumnId());
			thisBean.setLayoutId(bean.getLayoutId());
			thisBean.setSn(bean.getSn());
			thisBean.setColumnName(bean.getColumnName());
			thisBean.setColumnUrl(bean.getColumnUrl());
			listMy.add(thisBean);
		}
		return listMy;
	}

	public void saveMycolumn(List<DesktopMycolumn> list, Long layoutId,
			Long userId) throws Exception {
		this.dao.delByUserId(userId);
		for (DesktopMycolumn bean : list)
			this.dao.add(bean);
	}

	public DesktopLayoutcol newsData(Long id,
			Map<String, String> desktopLayoutAllmap,
			Map<String, String> desktopColumnmap,
			Map<String, String> desktopLayoutmap) {
		List<DesktopLayout> desktopLayoutList = this.desktopLayoutDao.getAll();
		List<DesktopColumn> desktopColumnList = this.desktopColumnDao.getAll();
		DesktopLayout desktopLayout = (DesktopLayout) this.desktopLayoutDao
				.getById(id);
		for (DesktopLayout dl : desktopLayoutList) {
			desktopLayoutAllmap.put("" + dl.getCols(), dl.getName());
		}

		for (DesktopColumn dc : desktopColumnList) {
			desktopColumnmap.put("" + dc.getId(), dc.getName());
		}

		desktopLayoutmap.put("cols", "" + desktopLayout.getCols());
		desktopLayoutmap.put("id", "" + desktopLayout.getId());
		DesktopLayoutcol desktopLayoutcol = (DesktopLayoutcol) this.desktopLayoutcolDao
				.getById(id);
		return desktopLayoutcol;
	}

	public DesktopLayout getShowData(long userId) {
		List list = this.dao.getByUserId(Long.valueOf(userId));
		if (list.size() == 0)
			return new DesktopLayout();
		DesktopLayout bean = (DesktopLayout) this.desktopLayoutDao
				.getById(((DesktopMycolumn) list.get(0)).getLayoutId());
		return bean;
	}

	public Map<String, String> desktopSync(long userId) throws Exception {
		List list = this.dao.getByUserId(Long.valueOf(userId));
		Long layoutId = Long.valueOf(0L);

		if (list.size() == 0) {
			DesktopLayout temp = this.desktopLayoutDao.getDefaultLayout();
			if (temp != null) {
				List collist = this.desktopLayoutcolDao.getByLayoutId(temp
						.getId());
				layoutId = ((DesktopLayoutcol) collist.get(0)).getLayoutId();
			}
		} else {
			List mylist = this.dao.getByUserId(Long.valueOf(userId));
			if (mylist.size() != 0) {
				layoutId = ((DesktopMycolumn) mylist.get(0)).getLayoutId();
			}
		}
		DesktopLayout bean = (DesktopLayout) this.desktopLayoutDao
				.getById(layoutId);
		Map desktopLayoutmap = new HashMap();
		desktopLayoutmap.put("cols", "" + bean.getCols());
		desktopLayoutmap.put("widths", bean.getWidth());

		return desktopLayoutmap;
	}
}
