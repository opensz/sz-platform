package org.sz.platform.bpm.service.form.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.form.BpmFormFieldDao;
import org.sz.platform.bpm.dao.form.BpmFormRightsDao;
import org.sz.platform.bpm.dao.form.BpmFormTableDao;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormRights;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.service.form.BpmFormRightsService;
import org.sz.platform.system.dao.SysUserOrgDao;
import org.sz.platform.system.model.Position;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.service.PositionService;
import org.sz.platform.system.service.SysOrgService;
import org.sz.platform.system.service.SysRoleService;

/**
 * 
 * 自定义表示字段权限  
 *
 */
@Service("bpmFormRightsService")
public class BpmFormRightsServiceImpl implements BpmFormRightsService {

	@Resource
	private BpmFormRightsDao bpmFormRightsDao;

	@Resource
	private BpmFormFieldDao bpmFormFieldDao;

	@Resource
	private BpmFormTableDao bpmFormTableDao;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private PositionService positionService;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private SysUserOrgDao sysUserOrgDao;

	private JSONObject getPermissionJson(String title, String memo) {
		String permissionJson = "{title:'"
				+ title
				+ "',memo:'"
				+ memo
				+ "',read:{type:'everyone',id:'', fullname:''},write:{type:'everyone',id:'', fullname:''}}";
		return JSONObject.fromObject(permissionJson);
	}

	public void save(long formKey, JSONObject permission) throws Exception {
		JSONArray fieldPermissions = permission.getJSONArray("field");
		JSONArray tablePermissions = permission.getJSONArray("subtable");
		JSONArray opinionPermissions = permission.getJSONArray("opinion");
		List<BpmFormRights> list = new ArrayList<BpmFormRights>();

		this.bpmFormRightsDao.delByFormDefId(Long.valueOf(formKey));
		Iterator i$;
		if (BeanUtils.isNotEmpty(fieldPermissions))
			for (i$ = fieldPermissions.iterator(); i$.hasNext();) {
				Object obj = i$.next();
				String json = obj.toString();
				JSONObject jsonObj = (JSONObject) obj;
				String name = (String) jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(
						Long.valueOf(UniqueIdUtil.genId()),
						Long.valueOf(formKey), name, json, Short.valueOf("1"));
				list.add(bpmFormRights);
			}
		if (BeanUtils.isNotEmpty(tablePermissions))
			for (i$ = tablePermissions.iterator(); i$.hasNext();) {
				Object obj = i$.next();
				String json = obj.toString();
				JSONObject jsonObj = (JSONObject) obj;
				String name = (String) jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(
						Long.valueOf(UniqueIdUtil.genId()),
						Long.valueOf(formKey), name, json, Short.valueOf("2"));
				list.add(bpmFormRights);
			}
		if (BeanUtils.isNotEmpty(opinionPermissions)) {
			for (i$ = opinionPermissions.iterator(); i$.hasNext();) {
				Object obj = i$.next();
				String json = obj.toString();
				JSONObject jsonObj = (JSONObject) obj;
				String name = (String) jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(
						Long.valueOf(UniqueIdUtil.genId()),
						Long.valueOf(formKey), name, json, Short.valueOf("3"));
				list.add(bpmFormRights);
			}
		}
		for (BpmFormRights right : list)
			this.bpmFormRightsDao.add(right);
	}

	public void save(String actDefId, String nodeId, long formKey,
			JSONObject permission) throws Exception {
		JSONArray fieldPermissions = permission.getJSONArray("field");
		JSONArray tablePermissions = permission.getJSONArray("subtable");
		JSONArray opinionPermissions = permission.getJSONArray("opinion");
		List<BpmFormRights> list = new ArrayList<BpmFormRights>();

		this.bpmFormRightsDao.delByFlowFormNodeId(actDefId, nodeId);
		Iterator i$;
		if (BeanUtils.isNotEmpty(fieldPermissions))
			for (i$ = fieldPermissions.iterator(); i$.hasNext();) {
				Object obj = i$.next();
				String json = obj.toString();
				JSONObject jsonObj = (JSONObject) obj;
				String name = (String) jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(
						Long.valueOf(UniqueIdUtil.genId()),
						Long.valueOf(formKey), name, json, Short.valueOf("1"));
				bpmFormRights.setActDefId(actDefId);
				bpmFormRights.setNodeId(nodeId);
				list.add(bpmFormRights);
			}
		if (BeanUtils.isNotEmpty(tablePermissions))
			for (i$ = tablePermissions.iterator(); i$.hasNext();) {
				Object obj = i$.next();
				String json = obj.toString();
				JSONObject jsonObj = (JSONObject) obj;
				String name = (String) jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(
						Long.valueOf(UniqueIdUtil.genId()),
						Long.valueOf(formKey), name, json, Short.valueOf("2"));
				bpmFormRights.setActDefId(actDefId);
				bpmFormRights.setNodeId(nodeId);
				list.add(bpmFormRights);
			}
		if (BeanUtils.isNotEmpty(opinionPermissions)) {
			for (i$ = opinionPermissions.iterator(); i$.hasNext();) {
				Object obj = i$.next();
				String json = obj.toString();
				JSONObject jsonObj = (JSONObject) obj;
				String name = (String) jsonObj.get("title");
				BpmFormRights bpmFormRights = new BpmFormRights(
						Long.valueOf(UniqueIdUtil.genId()),
						Long.valueOf(formKey), name, json, Short.valueOf("3"));
				bpmFormRights.setActDefId(actDefId);
				bpmFormRights.setNodeId(nodeId);
				list.add(bpmFormRights);
			}
		}
		for (BpmFormRights right : list)
			this.bpmFormRightsDao.add(right);
	}

	public Map<String, List<JSONObject>> getPermissionByFormNode(
			String actDefId, String nodeId, Long formDefId) {
		Map map = new HashMap();
		List<BpmFormRights> rightList = this.bpmFormRightsDao
				.getByFlowFormNodeId(actDefId, nodeId);
		if (rightList.size() == 0) {
			rightList = this.bpmFormRightsDao.getByFormDefId(formDefId);
		}
		List fieldJsonList = new ArrayList();
		List tableJsonList = new ArrayList();
		List opinionJsonList = new ArrayList();
		for (BpmFormRights rights : rightList) {
			switch (rights.getType()) {
			case 1:
				fieldJsonList
						.add(JSONObject.fromObject(rights.getPermission()));
				break;
			case 2:
				tableJsonList
						.add(JSONObject.fromObject(rights.getPermission()));
				break;
			case 3:
				opinionJsonList.add(JSONObject.fromObject(rights
						.getPermission()));
			}
		}

		map.put("field", fieldJsonList);
		map.put("table", tableJsonList);
		map.put("opinion", opinionJsonList);
		return map;
	}

	public Map<String, List<JSONObject>> getPermissionByTableId(Long tableId,
			Long formDefId) {
		Map<String, List<JSONObject>> map= new HashMap<String, List<JSONObject>>();

		if (formDefId.longValue() > 0L) {
			List<BpmFormRights> rightList = this.bpmFormRightsDao
					.getByFormDefId(formDefId);
			List<JSONObject> fieldJsonList = new ArrayList<JSONObject>();
			List<JSONObject> tableJsonList = new ArrayList<JSONObject>();
			List<JSONObject> opinionJsonList = new ArrayList<JSONObject>();
			for (BpmFormRights rights : rightList) {
				switch (rights.getType()) {
				case 1:
					fieldJsonList.add(JSONObject.fromObject(rights
							.getPermission()));
					break;
				case 2:
					tableJsonList.add(JSONObject.fromObject(rights
							.getPermission()));
					break;
				case 3:
					opinionJsonList.add(JSONObject.fromObject(rights
							.getPermission()));
				}
			}

			map.put("field", fieldJsonList);
			map.put("table", tableJsonList);
			map.put("opinion", opinionJsonList);
		} else {
			List<BpmFormField> fieldList = this.bpmFormFieldDao
					.getByTableId(tableId);
			List<JSONObject> fieldJsonList = new ArrayList<JSONObject>();
			for (BpmFormField field : fieldList) {
				JSONObject permission = getPermissionJson(field.getFieldName(),
						field.getFieldDesc());
				fieldJsonList.add(permission);
			}
			List<JSONObject> tableJsonList = new ArrayList<JSONObject>();
			List<BpmFormTable> tableList = this.bpmFormTableDao
					.getSubTableByMainTableId(tableId);
			for (BpmFormTable table : tableList) {
				JSONObject permission = getPermissionJson(table.getTableName(),
						table.getTableDesc());
				tableJsonList.add(permission);
			}
			map.put("field", fieldJsonList);
			map.put("table", tableJsonList);
		}
		return map;
	}

	public Map<String, Map> getByFormKeyAndUserId(Long formKey, Long userId,
			String actDefId, String nodeId) {
		List<BpmFormRights> rightList = null;

		if ((StringUtil.isNotEmpty(actDefId))
				&& (StringUtil.isNotEmpty(nodeId))) {
			rightList = this.bpmFormRightsDao.getByFlowFormNodeId(actDefId,
					nodeId);
		}
		if (BeanUtils.isEmpty(rightList)) {
			rightList = this.bpmFormRightsDao.getByFormDefId(formKey);
		}
		List<SysRole> roles = this.sysRoleService.getByUserId(userId);
		List<Position> positions = this.positionService.getByUserId(userId);
		List<SysOrg> orgs = this.sysOrgService.getOrgsByUserId(userId);
		List<SysUserOrg> ownOrgs = this.sysUserOrgDao.getChargeByUserId(userId);

		Map permissions = new HashMap();

		Map fieldPermission = new HashMap();
		Map tablePermission = new HashMap();
		Map opinionPermission = new HashMap();

		for (BpmFormRights rights : rightList) {
			JSONObject permission = JSONObject.fromObject(rights
					.getPermission());
			String name = rights.getName().toLowerCase();

			String right = getRight(permission, roles, positions, orgs,
					ownOrgs, userId);
			switch (rights.getType()) {
			case 1:
				fieldPermission.put(name, right);
				break;
			case 2:
				tablePermission.put(name, right);
				break;
			case 3:
				opinionPermission.put(name, right);
			}
		}

		permissions.put("field", fieldPermission);
		permissions.put("table", tablePermission);
		permissions.put("opinion", opinionPermission);

		return permissions;
	}

	private String getRight(JSONObject jsonObject, List<SysRole> roles,
			List<Position> positions, List<SysOrg> orgs,
			List<SysUserOrg> ownOrgs, Long userId) {
		String right = "";
		if (hasRight(jsonObject, "write", roles, positions, orgs, ownOrgs,
				userId))
			right = "w";
		else if (hasRight(jsonObject, "read", roles, positions, orgs, ownOrgs,
				userId)) {
			right = "r";
		}
		return right;
	}

	private boolean hasRight(JSONObject permission, String mode,
			List<SysRole> roles, List<Position> positions, List<SysOrg> orgs,
			List<SysUserOrg> ownOrgs, Long userId) {
		boolean hasRight = false;
		JSONObject node = permission.getJSONObject(mode);
		String type = node.get("type").toString();
		String id = node.get("id").toString();
		if ("none".equals(type)) {
			return false;
		}
		if ("everyone".equals(type)) {
			return true;
		}

		if ("user".equals(type)) {
			hasRight = id.contains(userId.toString());
			return hasRight;
		}

		if ("role".equals(type)) {
			for (SysRole role : roles) {
				if (id.contains(role.getRoleId().toString())) {
					return true;
				}
			}

		} else if ("org".equals(type)) {
			for (SysOrg org : orgs) {
				if (id.contains(org.getOrgId().toString())) {
					return true;
				}
			}

		} else if ("orgMgr".equals(type)) {
			for (SysUserOrg sysUserOrg : ownOrgs) {
				if (id.contains(sysUserOrg.getOrgId().toString())) {
					return true;
				}
			}

		} else if ("pos".equals(type)) {
			for (Position position : positions) {
				if (id.contains(position.getPosId().toString())) {
					return true;
				}
			}
		}
		return false;
	}
}
