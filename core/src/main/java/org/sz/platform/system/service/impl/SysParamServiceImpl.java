package org.sz.platform.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.platform.system.dao.SysParamDao;
import org.sz.platform.system.model.SysParam;
import org.sz.platform.system.service.SysParamService;

@Service("sysParamService")
public class SysParamServiceImpl extends BaseServiceImpl<SysParam> implements
		SysParamService {

	@Resource
	private SysParamDao dao;

	protected IEntityDao<SysParam, Long> getEntityDao() {
		return this.dao;
	}

	public List<SysParam> getUserParam() {
		return this.dao.getUserParam();
	}

	public List<SysParam> getOrgParam() {
		return this.dao.getOrgParam();
	}

	private String getIconPath(int type) {
		String path = "";
		switch (type) {
		case 1:
			path = "/themes/img/commons/or.gif";
			break;
		case 2:
			path = "/themes/img/commons/and.gif";
			break;
		case 3:
			path = "/themes/img/commons/code.gif";
		}

		return path;
	}

	public String setParamIcon(String ctx, String json) {
		if ((json == null) || (json.equals("")))
			return null;
		JSONArray ja = JSONArray.fromObject(json);
		List ml = (List) JSONArray.toCollection(ja, Map.class);
		if (BeanUtils.isEmpty(ml))
			return null;
		for (int i = 0; i < ml.size(); i++) {
			Map m = (Map) ml.get(i);
			int type = Integer.parseInt(m.get("type").toString());
			String icon = ctx + getIconPath(type);
			m.put("icon", icon);
			JSONArray children = JSONArray.fromObject(m.get("children"));
			if (!children.get(0).toString().equals("null")) {
				List childrenMap = (List) JSONArray.toCollection(children,
						Map.class);
				if (BeanUtils.isEmpty(childrenMap))
					return null;
				for (int j = 0; j < childrenMap.size(); j++) {
					Map mc = (Map) childrenMap.get(j);
					int type_ = Integer.parseInt(mc.get("type").toString());
					String icon_ = ctx + getIconPath(type_);
					mc.put("icon", icon_);
				}
				m.put("children", JSONArray.fromObject(childrenMap));
			}
		}
		JSONArray j1 = JSONArray.fromObject(ml);
		return j1.toString();
	}
}
