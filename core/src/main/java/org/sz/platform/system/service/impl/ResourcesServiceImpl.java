package org.sz.platform.system.service.impl;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.ResourcesDao;
import org.sz.platform.system.dao.ResourcesUrlDao;
import org.sz.platform.system.dao.RoleResourcesDao;
import org.sz.platform.system.dao.SubSystemDao;
import org.sz.platform.system.model.Resources;
import org.sz.platform.system.model.ResourcesUrl;
import org.sz.platform.system.model.RoleResources;
import org.sz.platform.system.model.Script;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.ResourcesService;

@Service("resourcesService")
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements
		ResourcesService {

	@Resource
	private ResourcesDao resourcesDao;

	@Resource
	private ResourcesUrlDao resourcesUrlDao;

	@Resource
	private SubSystemDao subSystemDao;

	@Resource
	private RoleResourcesDao roleResourcesDao;

	protected IEntityDao<Resources, Long> getEntityDao() {
		return this.resourcesDao;
	}

	public void addRes(Resources resources, String[] aryName, String[] aryUrl)
			throws Exception {
		Long resId = Long.valueOf(UniqueIdUtil.genId());
		resources.setResId(resId);
		this.resourcesDao.add(resources);

		if ((aryName == null) || (aryName.length == 0)) {
			return;
		}
		for (int i = 0; i < aryName.length; i++) {
			String url = aryUrl[i];
			if (StringUtil.isEmpty(url))
				continue;
			ResourcesUrl resouceUrl = new ResourcesUrl();
			resouceUrl.setResId(resId);
			resouceUrl.setResUrlId(Long.valueOf(UniqueIdUtil.genId()));
			resouceUrl.setName(aryName[i]);
			resouceUrl.setUrl(url);
			this.resourcesUrlDao.add(resouceUrl);
		}
	}

	public void updRes(Resources resources, String[] aryName, String[] aryUrl)
			throws Exception {
		Long resId = resources.getResId();

		this.resourcesDao.update(resources);

		this.resourcesUrlDao.delByResId(resId.longValue());

		if ((aryName == null) || (aryName.length == 0)) {
			return;
		}
		for (int i = 0; i < aryName.length; i++) {
			String url = aryUrl[i];
			if (StringUtil.isEmpty(url))
				continue;
			ResourcesUrl resouceUrl = new ResourcesUrl();
			resouceUrl.setResId(resId);
			resouceUrl.setResUrlId(Long.valueOf(UniqueIdUtil.genId()));
			resouceUrl.setName(aryName[i]);
			resouceUrl.setUrl(url);
			this.resourcesUrlDao.add(resouceUrl);
		}
	}

	public List<Resources> getChildByParentId(long systemId, long parentId,
			String ctx) {
		List<Resources> resourcesList = this.resourcesDao
				.getByParentId(parentId);
		if ((resourcesList == null) || (resourcesList.size() == 0))
			return resourcesList;

		if (ctx != null) {
			for (Resources res : resourcesList) {
				res.setIcon(ctx + res.getIcon());
			}
		}
		return resourcesList;
	}

	public List<Resources> getBySystemId(long systemId, String ctx) {
		List<Resources> resourcesList = this.resourcesDao
				.getBySystemId(systemId);
		if ((resourcesList == null) || (resourcesList.size() == 0))
			return resourcesList;
		for (Resources res : resourcesList) {
			res.setIcon(ctx + res.getIcon());
		}
		return resourcesList;
	}

	public Resources getParentResourcesByParentId(long systemId, long parentId,
			String ctx) {
		Resources parent = (Resources) this.resourcesDao.getById(Long
				.valueOf(parentId));
		if ((parentId == 0L) || (parent == null)) {
			SubSystem sys = (SubSystem) this.subSystemDao.getById(Long
					.valueOf(systemId));

			parent = new Resources();
			parent.setResId(Long.valueOf(0L));
			parent.setParentId(Long.valueOf(-1L));
			parent.setSn(Integer.valueOf(0));
			parent.setSystemId(Long.valueOf(systemId));

			parent.setAlias(sys.getAlias());

			parent.setIcon(ctx + sys.getLogo());
			parent.setIsDisplayInMenu(Resources.IS_DISPLAY_IN_MENU_Y);
			parent.setIsFolder(Resources.IS_FOLDER_Y);
			parent.setIsOpen(Resources.IS_OPEN_Y);
			parent.setResName(sys.getSysName());

			return parent;
		}
		return parent;
	}

	public void delByIds(Long[] ids) {
		if ((ids == null) || (ids.length == 0))
			return;

		Long[] arr$ = ids;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			long resId = arr$[i$].longValue();

			Resources res = (Resources) this.resourcesDao.getById(Long
					.valueOf(resId));

			Long parentId = res.getParentId();
			List childrenList = this.resourcesDao.getByParentId(resId);

			this.resourcesUrlDao.delByResId(resId);

			this.roleResourcesDao.delByResId(Long.valueOf(resId));

			updateChildrenNodePath(parentId, childrenList);

			this.resourcesDao.delById(Long.valueOf(resId));
		}
	}

	private void updateChildrenNodePath(Long parentId,
			List<Resources> childrenList) {
		if ((childrenList == null) || (childrenList.size() == 0))
			return;
		for (Resources res : childrenList) {
			res.setParentId(parentId);
			this.resourcesDao.update(res);
		}
	}

	public void updateChildrenNodeDisplay(Resources entity) {
		if (entity == null)
			return;

		if (entity.getIsDisplayInMenu().shortValue() == Resources.IS_DISPLAY_IN_MENU_N
				.shortValue()) {
			List<Resources> childrenList = this.resourcesDao
					.getByParentId(entity.getResId().longValue());
			if ((childrenList != null) && (childrenList.size() > 0)) {
				for (Resources res : childrenList) {
					res.setIsDisplayInMenu(entity.getIsDisplayInMenu());
					super.update(res);
					updateChildrenNodeDisplay(res);
				}
			}
		}
		if (entity.getIsDisplayInMenu().shortValue() == Resources.IS_DISPLAY_IN_MENU_Y
				.shortValue()) {
			Resources parent = getParentResourcesByParentId(entity
					.getSystemId().longValue(), entity.getParentId()
					.longValue(), "");
			if ((parent != null) && (parent.getParentId().longValue() != 0L)
					&& (parent.getParentId().longValue() != -1L)) {
				parent.setIsDisplayInMenu(entity.getIsDisplayInMenu());
				super.update(parent);
				updateChildrenNodeDisplay(parent);
			}
		}
	}

	public List<Resources> getBySysRolResChecked(Long systemId, Long roleId,
			String ctx) {
		List<Resources> resourcesList = this.resourcesDao
				.getBySystemId(systemId.longValue());
		List<RoleResources> roleResourcesList = this.roleResourcesDao
				.getBySysAndRole(systemId, roleId);

		Set set = new HashSet();
		if ((roleResourcesList != null) && (roleResourcesList.size() > 0)) {
			for (RoleResources rores : roleResourcesList) {
				set.add(Long.valueOf(rores.getResId().longValue()));
			}
		}

		if ((resourcesList != null) && (resourcesList.size() > 0)) {
			for (Resources res : resourcesList) {
				if (set.contains(Long.valueOf(res.getResId().longValue())))
					res.setChecked("true");
				else {
					res.setChecked("false");
				}
				res.setIcon(ctx + res.getIcon());
			}
		}

		return resourcesList;
	}

	public List<Resources> getSysMenu(SubSystem sys, SysUser user, String ctx) {
		Collection auths = ContextUtil.getCurrentUser().getAuthorities();
		List<Resources> resourcesList = new ArrayList();

		if ((auths != null) && (auths.size() > 0)
				&& (auths.contains(SysRole.ROLE_GRANT_SUPER))) {
			resourcesList = this.resourcesDao.getSuperMenu(Long.valueOf(sys
					.getSystemId()));
		} else {
			long userId = ContextUtil.getCurrentUser().getUserId().longValue();
			resourcesList = this.resourcesDao.getNormMenu(
					Long.valueOf(sys.getSystemId()), Long.valueOf(userId));
		}

		for (Resources res : resourcesList) {
			res.setIcon(ctx + res.getIcon());
			// res.setIsFolder(null);
		}
		short isLocal = sys.getIsLocal() == null ? 1 : sys.getIsLocal()
				.shortValue();

		if (isLocal == SubSystem.isLocal_N) {
			for (Resources res : resourcesList) {
				res.setDefaultUrl(sys.getDefaultUrl() + res.getDefaultUrl());
			}
		}

		return resourcesList;
	}

	public void update(Resources entity) {
		super.update(entity);

		updateChildrenNodeDisplay(entity);
	}

	public Integer isAliasExists(Resources resources) {
		Long systemId = resources.getSystemId();
		String alias = resources.getAlias();
		return this.resourcesDao.isAliasExists(systemId, alias);
	}

	public Integer isAliasExistsForUpd(Resources resources) {
		Long systemId = resources.getSystemId();
		String alias = resources.getAlias();
		Long resId = resources.getResId();
		return this.resourcesDao.isAliasExistsForUpd(systemId, resId, alias);
	}

	public String importXml(InputStream inputStream, Resources parent)
			throws Exception {
		String msg = "";

		Document doc = Dom4jUtil.loadXml(inputStream);
		List<Element> itemLists = doc.selectNodes("/items/item"); // <item>
		if ((itemLists != null) && (itemLists.size() > 0)) {
			for (Element elm : itemLists) {
				importXml(elm, parent);
			}
		}
		return msg;
	}

	public void importXml(Element item, Resources parent) throws Exception {

		Resources res = new Resources();
		res.setSystemId(parent.getSystemId());
		res.setParentId(parent.getResId());
		if (item.attribute("name") != null) {
			res.setResName(item.attribute("name").getText());
		}
		if (item.attribute("defaultUrl") != null) {
			res.setDefaultUrl(item.attribute("defaultUrl").getText());
		}
		if (item.attribute("isDisplayMenu") != null) {
			res.setIsDisplayInMenu(Short.valueOf(item
					.attribute("isDisplayMenu").getText()));
		}
		if (item.attribute("isFolder") != null) {
			res.setIsFolder(Short.valueOf(item.attribute("isFolder").getText()));
		}
		if (item.attribute("isOpen") != null) {
			res.setIsOpen(Short.valueOf(item.attribute("isOpen").getText()));
		}
		if (item.attribute("sn") != null) {
			res.setSn(Integer.valueOf(item.attribute("sn").getText()));
		}
		if (item.attribute("icon") != null) {
			res.setIcon(item.attribute("icon").getText());
		}
		if (item.attribute("param") != null) {
			res.setParam(item.attribute("param").getText());
		}

		addRes(res, null, null);

		List<Element> children = item.selectNodes("item");
		if ((children != null) && (children.size() > 0)) {
			for (Element elm : children) {
				importXml(elm, res);
			}
		}

	}

	public String exportXml(Resources res) {
		String strXml = "";
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("items");
		exportResources(root, res);
		strXml = doc.asXML();
		return strXml;
	}

	protected void exportResources(Element parent, Resources res) {
		if (parent == null || res == null)
			return;
		Element elem = parent.addElement("item");
		elem.addAttribute("name", res.getResName());
		elem.addAttribute("defaultUrl", res.getDefaultUrl());
		elem.addAttribute("param", res.getParam());
		elem.addAttribute("isDisplayMenu", res.getIsDisplayInMenu().toString());
		elem.addAttribute("isFolder", res.getIsFolder().toString());
		elem.addAttribute("isOpen", res.getIsOpen().toString());
		elem.addAttribute("icon", res.getIcon());
		elem.addAttribute("sn", res.getSn().toString());
		elem.addAttribute("alias", res.getAlias());

		List<Resources> children = getChildByParentId(0L, res.getResId(),
				(String) null);
		if (children != null) {
			for (Resources child : children) {
				exportResources(elem, child);
			}
		}

	}

	public String exportXml2(Long systemId, Long resId) {

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<items>");
		Resources res = (Resources) getById(resId);
		exportXml2(sb, res);
		sb.append("\r\n</items>");

		return sb.toString();
	}

	public String exportXml2(Resources res) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<items>");
		exportXml2(sb, res);
		sb.append("\r\n</items>");
		return sb.toString();
	}

	protected void exportXml2(StringBuilder sb, Resources res) {
		if (sb == null || res == null)
			return;
		sb.append("\r\n<item name=\"").append(res.getResName())
				.append("\" defaultUrl=\"").append(res.getDefaultUrl())
				.append("\" isDisplayMenu=\"").append(res.getIsDisplayInMenu())
				.append("\" isFolder=\"").append(res.getIsFolder())
				.append("\" isOpen=\"").append(res.getIsOpen())
				.append("\" icon=\"").append(res.getIcon()).append("\" sn=\"")
				.append(res.getSn()).append("\" ");
		if (res.getIsFolder() == 0) {
			sb.append("/>");
			return;
		}
		sb.append(">");
		List<Resources> children = getChildByParentId(0L, res.getResId(),
				(String) null);
		if (children != null) {
			for (Resources child : children) {
				exportXml2(sb, child);
			}
		}
		sb.append("\r\n</item>");
	}
}
