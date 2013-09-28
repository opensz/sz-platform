package org.sz.platform.bpm.service.flow.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmAgentDao;
import org.sz.platform.bpm.dao.flow.BpmDefRightsDao;
import org.sz.platform.bpm.dao.flow.BpmDefVarDao;
import org.sz.platform.bpm.dao.flow.BpmDefinitionDao;
import org.sz.platform.bpm.dao.flow.BpmNodeMessageDao;
import org.sz.platform.bpm.dao.flow.BpmNodeRuleDao;
import org.sz.platform.bpm.dao.flow.BpmNodeScriptDao;
import org.sz.platform.bpm.dao.flow.BpmNodeSetDao;
import org.sz.platform.bpm.dao.flow.BpmNodeSignDao;
import org.sz.platform.bpm.dao.flow.BpmNodeUserDao;
import org.sz.platform.bpm.dao.flow.BpmTaskCommentDao;
import org.sz.platform.bpm.dao.flow.ExecutionStackDao;
import org.sz.platform.bpm.dao.flow.TaskSignDataDao;
import org.sz.platform.bpm.model.flow.BpmAgent;
import org.sz.platform.bpm.model.flow.BpmDefRights;
import org.sz.platform.bpm.model.flow.BpmDefVar;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmNodeMessage;
import org.sz.platform.bpm.model.flow.BpmNodeRule;
import org.sz.platform.bpm.model.flow.BpmNodeScript;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
import org.sz.platform.bpm.model.flow.BpmNodeUser;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.util.BpmUtil;
import org.sz.platform.system.dao.MessageDao;
import org.sz.platform.system.model.Message;

@Service("bpmDefinitionService")
public class BpmDefinitionServiceImpl extends BaseServiceImpl<BpmDefinition>
		implements BpmDefinitionService {

	@Resource
	private BpmDefinitionDao dao;

	@Resource
	private BpmNodeSetDao bpmNodeSetDao;

	@Resource
	private BpmDefVarDao bpmDefVarDao;

	@Resource
	private BpmService bpmService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private BpmNodeSignDao bpmNodeSignDao;

	@Resource
	private BpmNodeRuleDao bpmNodeRuleDao;

	@Resource
	private TaskSignDataDao taskSignDataDao;

	@Resource
	private BpmNodeMessageDao bpmNodeMessageDao;

	@Resource
	private MessageDao messageDao;

	@Resource
	private BpmDefVarDao bpmDefVarsDao;

	@Resource
	private ExecutionStackDao executionStackDao;

	@Resource
	private BpmNodeUserDao bpmNodeUserDao;

	@Resource
	private BpmTaskCommentDao bpmTaskCommentDao;

	@Resource
	private BpmNodeScriptDao bpmNodeScriptDao;

	@Resource
	private BpmAgentDao bpmAgentDao;

	@Resource
	private BpmDefRightsDao bpmDefRightDao;

	protected IEntityDao<BpmDefinition, Long> getEntityDao() {
		return this.dao;
	}

	public void deploy(BpmDefinition bpmDefinition, String actFlowDefXml)
			throws Exception {
		Deployment deployment = this.bpmService.deploy(
				bpmDefinition.getSubject(), actFlowDefXml);
		ProcessDefinitionEntity ent = this.bpmService
				.getProcessDefinitionByDeployId(deployment.getId());
		bpmDefinition.setActDeployId(new Long(deployment.getId()));
		bpmDefinition.setActDefId(ent.getId());
		bpmDefinition.setActDefKey(ent.getKey());
		bpmDefinition.setStatus(BpmDefinition.STATUS_DEPLOYED);
		this.dao.update(bpmDefinition);

		saveOrUpdateNodeSet(actFlowDefXml, bpmDefinition, false);
	}

	public void saveOrUpdate(BpmDefinition bpmDefinition, boolean isDeploy,
			String actFlowDefXml) throws Exception {
		Long oldDefId = bpmDefinition.getDefId();

		Long newDefId = bpmDefinition.getDefId();

		boolean isUpdate = false;

		if ((bpmDefinition.getDefId() == null)
				|| (bpmDefinition.getDefId().longValue() == 0L)) { // new
			if (isDeploy) {
				Deployment deployment = this.bpmService.deploy(
						bpmDefinition.getSubject(), actFlowDefXml);
				ProcessDefinitionEntity ent = this.bpmService
						.getProcessDefinitionByDeployId(deployment.getId());
				bpmDefinition.setActDeployId(new Long(deployment.getId()));
				bpmDefinition.setActDefId(ent.getId());
				bpmDefinition.setActDefKey(ent.getKey());
			}
			bpmDefinition.setVersionNo(Integer.valueOf(1));

			bpmDefinition.setDefId(Long.valueOf(UniqueIdUtil.genId()));

			bpmDefinition.setIsMain(BpmDefinition.MAIN);
			bpmDefinition.setCreatetime(new Date());
			bpmDefinition.setUpdatetime(new Date());
			Short status = isDeploy ? BpmDefinition.STATUS_DEPLOYED
					: BpmDefinition.STATUS_NOTDEPLOYED;
			bpmDefinition.setStatus(status);
			add(bpmDefinition);

			if (isDeploy) {
				saveOrUpdateNodeSet(actFlowDefXml, bpmDefinition, true);
			}

		} else { // update
			if (isDeploy) {
				newDefId = Long.valueOf(UniqueIdUtil.genId());
				this.dao.updateSubVersions(newDefId, bpmDefinition.getDefKey());

				Deployment deployment = this.bpmService.deploy(
						bpmDefinition.getSubject(), actFlowDefXml);
				ProcessDefinitionEntity ent = this.bpmService
						.getProcessDefinitionByDeployId(deployment.getId());
				String actDefId = ent.getId();

				BpmDefinition newBpmDefinition = (BpmDefinition) bpmDefinition
						.clone();

				newBpmDefinition
						.setVersionNo(Integer.valueOf(ent.getVersion()));
				newBpmDefinition.setActDeployId(new Long(deployment.getId()));
				newBpmDefinition.setActDefId(actDefId);
				newBpmDefinition.setActDefKey(ent.getKey());

				newBpmDefinition.setDefId(newDefId);
				newBpmDefinition.setParentDefId(newDefId);
				newBpmDefinition.setUpdatetime(new Date());
				newBpmDefinition.setStatus(BpmDefinition.STATUS_DEPLOYED);

				newBpmDefinition.setIsMain(BpmDefinition.MAIN);

				add(newBpmDefinition);

				isUpdate = true;

				saveOrUpdateNodeSet(actFlowDefXml, newBpmDefinition, true);

				syncStartGlobal(oldDefId, newDefId, actDefId);
			} else {
				if (bpmDefinition.getActDeployId() != null) {
					this.bpmService.wirteDefXml(bpmDefinition.getActDeployId()
							.toString(), actFlowDefXml);

					saveOrUpdateNodeSet(actFlowDefXml, bpmDefinition, false);
				}
				update(bpmDefinition);
			}
		}

		if (isUpdate) {
			saveOrUpdateBpmDefSeting(newDefId, oldDefId);
		}
	}

	/**
	 * 拷贝流程定义
	 * 
	 * @param sourceBpmDefinition
	 * @throws Exception
	 */
	public void copyFormDef(Long sourceDefId, boolean isDeploy)
			throws Exception {

		BpmDefinition sourceBpmDefinition = this.getById(sourceDefId);

		if (sourceBpmDefinition == null) {
			throw new RuntimeException(
					"源BpmDefinition为null, 请检查传入参数sourceDefId!");
		}

		BpmDefinition destBpmDefinition = (BpmDefinition) sourceBpmDefinition
				.clone();

		// 主键
		destBpmDefinition.setDefId(UniqueIdUtil.genId());
		destBpmDefinition.setDefKey("case_" + destBpmDefinition.getDefId());
		destBpmDefinition.setSubject(destBpmDefinition.getSubject() + "copy");
		destBpmDefinition.setParentDefId(null); // 置空parentDefId

		String actFlowDefXml = BpmUtil.transform(destBpmDefinition.getDefKey(),
				destBpmDefinition.getSubject(), destBpmDefinition.getDefXml());
		if (isDeploy) {
			Deployment deployment = this.bpmService.deploy(
					destBpmDefinition.getSubject(), actFlowDefXml);
			ProcessDefinitionEntity ent = this.bpmService
					.getProcessDefinitionByDeployId(deployment.getId());
			destBpmDefinition.setActDeployId(new Long(deployment.getId()));
			destBpmDefinition.setActDefId(ent.getId());
			destBpmDefinition.setActDefKey(ent.getKey());
		}
		destBpmDefinition.setVersionNo(Integer.valueOf(1));

		destBpmDefinition.setIsMain(BpmDefinition.MAIN);
		destBpmDefinition.setCreatetime(new Date());
		destBpmDefinition.setUpdatetime(new Date());
		Short status = isDeploy ? BpmDefinition.STATUS_DEPLOYED
				: BpmDefinition.STATUS_NOTDEPLOYED;
		destBpmDefinition.setStatus(status);
		// 新增拷贝的流程定义
		add(destBpmDefinition);
		if (isDeploy) {
			// 拷贝 node_set
			// 查询所有nodeSet 不查setType = 1(公共) 和 2(开始结点)
			List<BpmNodeSet> destBpmNodeSetList = bpmNodeSetDao
					.getByDefId(sourceDefId);
			if (destBpmNodeSetList != null && destBpmNodeSetList.size() > 0) {
				for (BpmNodeSet destBpmNodeSet : destBpmNodeSetList) {
					Long setId = destBpmNodeSet.getSetId();
					destBpmNodeSet.setSetId(UniqueIdUtil.genId());
					destBpmNodeSet.setDefId(destBpmDefinition.getDefId());
					destBpmNodeSet.setActDefId(destBpmDefinition.getActDefId());

					// 清除nodeSet表单关联信息
					destBpmNodeSet.setFormKey(0L);
					destBpmNodeSet.setBeforeHandler("");
					destBpmNodeSet.setAfterHandler("");
					destBpmNodeSet.setFormDefName("");
					destBpmNodeSet.setFormType((short) -1);
					bpmNodeSetDao.add(destBpmNodeSet);

					// 拷贝 node_user
					List<BpmNodeUser> bpmNodeUserList = bpmNodeUserDao
							.getBySetId(setId);
					if (bpmNodeUserList != null && bpmNodeUserList.size() > 0) {
						for (BpmNodeUser destBpmNodeUser : bpmNodeUserList) {
							destBpmNodeUser.setNodeUserId(UniqueIdUtil.genId());
							destBpmNodeUser.setSetId(destBpmNodeSet.getSetId());
							destBpmNodeUser.setActDefId(destBpmDefinition
									.getActDefId());
							bpmNodeUserDao.add(destBpmNodeUser);
						}
					}
				}
			}
		}

	}

	private void saveOrUpdateNodeSet(String actFlowDefXml,
			BpmDefinition bpmDefinition, boolean isAdd) throws Exception {
		Long defId = bpmDefinition.getDefId();
		Map nodesMap = (Map) BpmUtil.getTaskActivitys(actFlowDefXml)
				.get("任务节点");
		Map nodesMap1 = (Map) BpmUtil.getTaskActivitys(actFlowDefXml)
				.get("子流程");
		if (nodesMap1 != null) {
			nodesMap.putAll(nodesMap1);
		}
		Iterator keys = nodesMap.keySet().iterator();
		if (isAdd) {
			while (keys.hasNext()) {
				String actNodeId = (String) keys.next();
				String actNodeName = (String) nodesMap.get(actNodeId);
				addNodeSet(bpmDefinition, actNodeId, actNodeName);
			}
		}

		Map nodeSetMap = this.bpmNodeSetDao.getMapByDefId(defId);

		delNodeSet(nodeSetMap, nodesMap);

		updNodeSet(nodeSetMap, nodesMap, bpmDefinition);
	}

	private void updNodeSet(Map<String, BpmNodeSet> oldSetMap,
			Map<String, String> curNodeMap, BpmDefinition bpmDefinition)
			throws Exception {
		Iterator keys = curNodeMap.keySet().iterator();
		while (keys.hasNext()) {
			String nodeId = (String) keys.next();
			String actNodeName = (String) curNodeMap.get(nodeId);
			if (oldSetMap.containsKey(nodeId)) {
				BpmNodeSet bpmNodeSet = (BpmNodeSet) oldSetMap.get(nodeId);
				String nodeName = (String) curNodeMap.get(nodeId);
				bpmNodeSet.setNodeName(nodeName);
				this.bpmNodeSetDao.update(bpmNodeSet);
			} else {
				addNodeSet(bpmDefinition, nodeId, actNodeName);
			}
		}
	}

	private void delNodeSet(Map<String, BpmNodeSet> oldSetMap,
			Map<String, String> curNodeMap) {
		Iterator keys = oldSetMap.keySet().iterator();
		while (keys.hasNext()) {
			String nodeId = (String) keys.next();
			if (!curNodeMap.containsKey(nodeId)) {
				BpmNodeSet bpmNodeSet = (BpmNodeSet) oldSetMap.get(nodeId);
				this.bpmNodeSetDao.delById(bpmNodeSet.getSetId());
			}
		}
	}

	private void addNodeSet(BpmDefinition bpmDefinition, String actNodeId,
			String actNodeName) throws Exception {
		Long defId = bpmDefinition.getDefId();
		String actDefId = bpmDefinition.getActDefId();
		BpmNodeSet bpmNodeSet = new BpmNodeSet();
		bpmNodeSet.setSetId(Long.valueOf(UniqueIdUtil.genId()));
		bpmNodeSet.setFormType(Short.valueOf("-1"));
		bpmNodeSet.setActDefId(actDefId);
		bpmNodeSet.setDefId(defId);
		bpmNodeSet.setNodeId(actNodeId);
		bpmNodeSet.setNodeName(actNodeName);
		bpmNodeSet.setAssignMode(new Short("0"));
		this.bpmNodeSetDao.add(bpmNodeSet);
	}

	@SuppressWarnings("rawtypes")
	private void saveOrUpdateBpmDefSeting(Long newDefId, Long oldDefId)
			throws Exception {
		if ((oldDefId == null) || (oldDefId.longValue() <= 0L))
			return;

		List<BpmAgent> agentList = this.bpmAgentDao.getByDefId(oldDefId);
		if (BeanUtils.isNotEmpty(agentList)) {
			for (BpmAgent o : agentList) {
				BpmAgent n = (BpmAgent) o.clone();
				n.setId(Long.valueOf(UniqueIdUtil.genId()));
				n.setDefid(newDefId);
				this.bpmAgentDao.add(n);
			}

		}

		List<BpmDefRights> defRight = this.bpmDefRightDao.getByDefId(oldDefId);
		if (BeanUtils.isNotEmpty(defRight)) {
			for (BpmDefRights o : defRight) {
				BpmDefRights n = (BpmDefRights) o.clone();
				n.setId(Long.valueOf(UniqueIdUtil.genId()));
				n.setDefId(newDefId);
				this.bpmDefRightDao.add(n);
			}

		}

		List<BpmDefVar> defVarList = this.bpmDefVarsDao.getByDefId(oldDefId);
		if (BeanUtils.isNotEmpty(defVarList)) {
			for (BpmDefVar o : defVarList) {
				BpmDefVar n = (BpmDefVar) o.clone();
				n.setVarId(Long.valueOf(UniqueIdUtil.genId()));
				n.setDefId(newDefId);
				this.bpmDefVarsDao.add(n);
			}

		}

		List<BpmNodeSet> newNodeSetList = this.bpmNodeSetDao
				.getByDefId(newDefId);
		Map oldNodeSetMap = this.bpmNodeSetDao.getMapByDefId(oldDefId);
		if ((BeanUtils.isEmpty(newNodeSetList))
				|| (BeanUtils.isEmpty(oldNodeSetMap)))
			return;
		for (BpmNodeSet bpmNodeSet : newNodeSetList) {
			String nodeId = bpmNodeSet.getNodeId();
			if (!oldNodeSetMap.containsKey(nodeId))
				continue;
			BpmNodeSet oldBpmNodeSet = (BpmNodeSet) oldNodeSetMap.get(nodeId);
			String oldActDefId = oldBpmNodeSet.getActDefId();
			String newAceDefId = bpmNodeSet.getActDefId();
			Long oldSetId = oldBpmNodeSet.getSetId();

			updBpmNodeSet(bpmNodeSet, oldBpmNodeSet);

			List<BpmNodeRule> nodeRuleList = this.bpmNodeRuleDao
					.getByDefIdNodeId(oldActDefId, nodeId);
			if (BeanUtils.isNotEmpty(nodeRuleList)) {
				for (BpmNodeRule oR : nodeRuleList) {
					BpmNodeRule nR = (BpmNodeRule) oR.clone();
					nR.setRuleId(Long.valueOf(UniqueIdUtil.genId()));
					nR.setActDefId(newAceDefId);
					this.bpmNodeRuleDao.add(nR);
				}

			}

			List<BpmNodeScript> nodeScriptList = this.bpmNodeScriptDao
					.getByBpmNodeScriptId(nodeId, oldActDefId);
			if (BeanUtils.isNotEmpty(nodeScriptList)) {
				for (BpmNodeScript oS : nodeScriptList) {
					BpmNodeScript nS = (BpmNodeScript) oS.clone();
					nS.setId(Long.valueOf(UniqueIdUtil.genId()));
					nS.setActDefId(newAceDefId);
					this.bpmNodeScriptDao.add(nS);
				}

			}

			List<BpmNodeUser> nodeUserList = this.bpmNodeUserDao
					.getBySetId(oldSetId);
			if (BeanUtils.isNotEmpty(nodeUserList)) {
				for (BpmNodeUser oU : nodeUserList) {
					BpmNodeUser nU = (BpmNodeUser) oU.clone();

					nU.setNodeUserId(Long.valueOf(UniqueIdUtil.genId()));
					nU.setActDefId(newAceDefId);
					nU.setSetId(bpmNodeSet.getSetId());
					this.bpmNodeUserDao.add(nU);
				}

			}

			List<BpmNodeMessage> nodeMessageList = this.bpmNodeMessageDao
					.getByActDefId(oldActDefId);
			if (BeanUtils.isNotEmpty(nodeMessageList)) {
				for (BpmNodeMessage oM : nodeMessageList) {
					BpmNodeMessage nM = (BpmNodeMessage) oM.clone();
					nM.setId(Long.valueOf(UniqueIdUtil.genId()));
					nM.setActDefId(newAceDefId);
					this.bpmNodeMessageDao.add(nM);
				}

			}

			BpmNodeSign nodeSign = this.bpmNodeSignDao.getByDefIdAndNodeId(
					oldActDefId, nodeId);
			if (BeanUtils.isNotEmpty(nodeSign)) {
				BpmNodeSign newSign = (BpmNodeSign) nodeSign.clone();
				newSign.setSignId(Long.valueOf(UniqueIdUtil.genId()));
				newSign.setActDefId(newAceDefId);
				this.bpmNodeSignDao.add(newSign);
			}
		}
	}

	private void syncStartGlobal(Long oldDefId, Long newDefId,
			String newActDefId) throws Exception {
		List<BpmNodeSet> list = this.bpmNodeSetDao.getByStartGlobal(oldDefId);
		for (BpmNodeSet nodeSet : list) {
			nodeSet.setSetId(Long.valueOf(UniqueIdUtil.genId()));
			nodeSet.setDefId(newDefId);
			nodeSet.setActDefId(newActDefId);
			this.bpmNodeSetDao.add(nodeSet);
		}
	}

	/**
	 * 拷贝NodeSet属性
	 * 
	 * @param bpmNodeSet
	 * @param oldBpmNodeSet
	 */
	private void updBpmNodeSet(BpmNodeSet bpmNodeSet, BpmNodeSet oldBpmNodeSet) {
		bpmNodeSet.setAfterHandler(oldBpmNodeSet.getAfterHandler());
		bpmNodeSet.setBeforeHandler(oldBpmNodeSet.getBeforeHandler());
		bpmNodeSet.setFormType(oldBpmNodeSet.getFormType());
		bpmNodeSet.setFormKey(oldBpmNodeSet.getFormKey());
		bpmNodeSet.setFormDefName(oldBpmNodeSet.getFormDefName());
		bpmNodeSet.setFormUrl(oldBpmNodeSet.getFormUrl());
		bpmNodeSet.setNodeType(oldBpmNodeSet.getNodeType());
		bpmNodeSet.setJoinTaskKey(oldBpmNodeSet.getJoinTaskKey());
		bpmNodeSet.setJoinTaskName(oldBpmNodeSet.getJoinTaskName());
		bpmNodeSet.setJumpType(oldBpmNodeSet.getJumpType());
		this.bpmNodeSetDao.update(bpmNodeSet);
	}

	public List<BpmDefinition> getAllHistoryVersions(Long defId) {
		return this.dao.getByParentDefIdIsMain(defId, BpmDefinition.NOT_MAIN);
	}

	public BpmDefinition getByActDefId(String actDefId) {
		return this.dao.getByActDefId(actDefId);
	}

	public List<BpmDefinition> getByTypeId(Long typeId) {
		return this.dao.getByTypeId(typeId);
	}

	public List<BpmDefinition> getAllForAdmin(QueryFilter queryFilter) {
		return this.dao.getAllForAdmin(queryFilter);
	}

	public int saveParam(BpmDefinition bpmDefinition) {
		return this.dao.saveParam(bpmDefinition);
	}

	public void delDefbyDeployId(Long flowDefId, boolean isOnlyVersion) {
		if (BeanUtils.isEmpty(flowDefId))
			return;

		BpmDefinition definition = (BpmDefinition) this.dao.getById(flowDefId);

		if (isOnlyVersion) {
			delBpmDefinition(definition);
			return;
		}

		String flowKey = definition.getDefKey();

		List<BpmDefinition> list = this.dao.getByDefKey(flowKey);

		for (BpmDefinition bpmDefinition : list) {
			delBpmDefinition(bpmDefinition);
		}
	}

	private void delBpmDefinition(BpmDefinition bpmDefinition) {
		Long actDeployId = bpmDefinition.getActDeployId();

		if ((actDeployId != null) && (actDeployId.longValue() > 0L)) {
			this.repositoryService.deleteDeployment(actDeployId.toString(),
					true);
		}

		Long defId = bpmDefinition.getDefId();
		String actDefId = bpmDefinition.getActDefId();
		if (StringUtil.isNotEmpty(actDefId)) {
			this.executionStackDao.delByActDefId(actDefId);

			this.bpmNodeUserDao.delByActDefId(actDefId);

			this.bpmTaskCommentDao.delByactDefId(actDefId);

			this.bpmNodeSignDao.delActDefId(actDefId);

			this.bpmNodeRuleDao.delByActDefId(actDefId);

			this.taskSignDataDao.delByIdActDefId(actDefId);

			this.bpmNodeScriptDao.delByActDefId(actDefId);

			this.bpmNodeMessageDao.delByActDefId(actDefId);
		}

		this.bpmDefRightDao.delByDefId(defId);

		this.bpmDefVarsDao.delByDefId(defId);

		this.bpmNodeSetDao.delByDefId(defId);

		this.dao.delById(defId);
	}

	public void importXml(String fileStr) throws Exception {
		BpmDefinition bpmDefinition = null;
		Document doc = null;
		String actDefId = "";
		Long defId = Long.valueOf(0L);
		Long actDeployId = Long.valueOf(0L);
		doc = Dom4jUtil.loadXml(fileStr);
		Element root = doc.getRootElement();
		List<Element> itemLists = root.elements();
		for (Element itemList : itemLists) {
			List<Element> tables = itemList.elements();
			for (Element table : tables) {
				String tableName = table.getQName().getName();
				if (tableName.equals("bpm_definition")) {
					bpmDefinition = new BpmDefinition();
					importDefinition(bpmDefinition, table);
					actDefId = bpmDefinition.getActDefId();
					defId = bpmDefinition.getDefId();
					actDeployId = bpmDefinition.getActDeployId();
				}
				if (tableName.equals("BPM_NODE_RULE")) {
					importBpmnoderule(actDefId, table);
				}
				if (tableName.equals("BPM_NODE_SCRIPT")) {
					importBpmnodescript(actDefId, table);
				}
				if (tableName.equals("BPM_DEF_VARS")) {
					importBpmdefvar(defId, actDeployId, table);
				}
				if (tableName.equals("BPM_NODE_SIGN"))
					importBpmnodesign(actDefId, table);
			}
		}
	}

	private void importDefinition(BpmDefinition bpmDefinition, Element table)
			throws Exception {
		String actFlowDefXml = "";
		List<Element> columns = table.elements();
		for (Element column : columns) {
			if ("subject".equals(column.getName())) {
				bpmDefinition.setSubject(column.getText());
			}
			if ("defKey".equals(column.getName())) {
				bpmDefinition.setDefKey(column.getText());
			}
			if ("taskNameRule".equals(column.getName())) {
				bpmDefinition.setTaskNameRule(column.getText());
			}
			if ("descp".equals(column.getName())) {
				bpmDefinition.setDescp(column.getText());
			}
			if ("reason".equals(column.getName())) {
				bpmDefinition.setReason(column.getText());
			}
			if ("defXml".equals(column.getName())) {
				bpmDefinition.setDefXml(column.getText());
			}
		}
		actFlowDefXml = BpmUtil.transform(bpmDefinition.getDefKey(),
				bpmDefinition.getSubject(), bpmDefinition.getDefXml());
		saveOrUpdate(bpmDefinition, true, actFlowDefXml);
	}

	private void importBpmnoderule(String actDefId, Element table)
			throws Exception {
		BpmNodeRule bpmNodeRule;
		if (StringUtil.isNotEmpty(actDefId)) {
			bpmNodeRule = new BpmNodeRule();
			List<Element> columns = table.elements();
			for (Element column : columns) {
				if ("ruleName".equals(column.getName())) {
					bpmNodeRule.setRuleName(column.getText());
				}
				if ("conditionCode".equals(column.getName())) {
					bpmNodeRule.setConditionCode(column.getText());
				}
				if ("nodeId".equals(column.getName())) {
					bpmNodeRule.setNodeId(column.getText());
				}
				if ("priority".equals(column.getName())) {
					bpmNodeRule.setPriority(Long.valueOf(Long.parseLong(column
							.getText())));
				}
				if ("targetNode".equals(column.getName())) {
					bpmNodeRule.setTargetNode(column.getText());
				}
				if ("targetNodeName".equals(column.getName())) {
					bpmNodeRule.setTargetNodeName(column.getText());
				}
				if ("memo".equals(column.getName())) {
					bpmNodeRule.setMemo(column.getText());
				}
				bpmNodeRule.setRuleId(Long.valueOf(UniqueIdUtil.genId()));
				bpmNodeRule.setActDefId(actDefId);
				this.bpmNodeRuleDao.add(bpmNodeRule);
			}
		}
	}

	private void importBpmnodescript(String actDefId, Element table)
			throws Exception {
		BpmNodeScript bpmNodescript;
		if (StringUtil.isNotEmpty(actDefId)) {
			bpmNodescript = new BpmNodeScript();
			List<Element> columns = table.elements();
			for (Element column : columns) {
				if ("script".equals(column.getName())) {
					bpmNodescript.setScript(column.getText());
				}
				if ("scriptType".equals(column.getName())) {
					bpmNodescript.setScriptType(Integer.valueOf(Integer
							.parseInt(column.getText())));
				}
				if ("nodeId".equals(column.getName())) {
					bpmNodescript.setNodeId(column.getText());
				}
				if ("memo".equals(column.getName())) {
					bpmNodescript.setMemo(column.getText());
				}
				bpmNodescript.setId(Long.valueOf(UniqueIdUtil.genId()));
				bpmNodescript.setActDefId(actDefId);
				this.bpmNodeScriptDao.add(bpmNodescript);
			}
		}
	}

	private void importBpmdefvar(Long defId, Long actDeployId, Element table)
			throws Exception {
		BpmDefVar bpmDefVar;
		if ((defId.longValue() != 0L) && (actDeployId.longValue() != 0L)) {
			bpmDefVar = new BpmDefVar();
			List<Element> columns = table.elements();
			for (Element column : columns) {
				if ("varName".equals(column.getName())) {
					bpmDefVar.setVarName(column.getText());
				}
				if ("varKey".equals(column.getName())) {
					bpmDefVar.setVarKey(column.getText());
				}
				if ("varDataType".equals(column.getName())) {
					bpmDefVar.setVarDataType(column.getText());
				}
				if ("defValue".equals(column.getName())) {
					bpmDefVar.setDefValue(column.getText());
				}
				if ("nodeName".equals(column.getName())) {
					bpmDefVar.setNodeName(column.getText());
				}
				if ("versionId".equals(column.getName())) {
					bpmDefVar.setVersionId(Long.valueOf(Long.parseLong(column
							.getText())));
				}
				if ("nodeId".equals(column.getName())) {
					bpmDefVar.setNodeId(column.getText());
				}
				if ("varScope".equals(column.getName())) {
					bpmDefVar.setVarScope(column.getText());
				}
				bpmDefVar.setVarId(Long.valueOf(UniqueIdUtil.genId()));
				bpmDefVar.setDefId(defId);
				bpmDefVar.setActDeployId(actDeployId);
				this.bpmDefVarDao.add(bpmDefVar);
			}
		}
	}

	private void importBpmnodesign(String actDefId, Element table)
			throws Exception {
		BpmNodeSign bpmNodeSign;
		if (StringUtil.isNotEmpty(actDefId)) {
			bpmNodeSign = new BpmNodeSign();
			List<Element> columns = table.elements();
			for (Element column : columns) {
				if ("nodeId".equals(column.getName())) {
					bpmNodeSign.setNodeId(column.getText());
				}
				if ("voteAmount".equals(column.getName())) {
					bpmNodeSign.setVoteAmount(Long.valueOf(Long
							.parseLong(column.getText())));
				}
				if ("decideType".equals(column.getName())) {
					bpmNodeSign.setDecideType(new Short(column.getText()));
				}
				if ("voteType".equals(column.getName())) {
					bpmNodeSign.setVoteType(new Short(column.getText()));
				}
				bpmNodeSign.setSignId(Long.valueOf(UniqueIdUtil.genId()));
				bpmNodeSign.setActDefId(actDefId);
				this.bpmNodeSignDao.add(bpmNodeSign);
			}
		}
	}

	private void importBpmnodemess(String actDefId, Element table)
			throws Exception {
		BpmNodeMessage bpmNodeMessage;
		if (StringUtil.isNotEmpty(actDefId)) {
			bpmNodeMessage = new BpmNodeMessage();
			List<Element> columns = table.elements();
			for (Element column : columns) {
				if ("messageId".equals(column.getName())) {
					bpmNodeMessage.setMessageId(Long.valueOf(Long
							.parseLong(column.getText())));
				}
				if ("nodeId".equals(column.getName())) {
					bpmNodeMessage.setNodeId(column.getText());
				}
				bpmNodeMessage.setId(Long.valueOf(UniqueIdUtil.genId()));
				bpmNodeMessage.setActDefId(actDefId);
				this.bpmNodeMessageDao.add(bpmNodeMessage);
			}
		}
	}

	private void importSysmess(String actDefId, Element table) throws Exception {
		Message message;
		if (StringUtil.isNotEmpty(actDefId)) {
			message = new Message();
			List<Element> columns = table.elements();
			for (Element column : columns) {
				if ("messageId".equals(column.getName())) {
					message.setMessageId(Long.valueOf(Long.parseLong(column
							.getText())));
				}
				if ("nodeId".equals(column.getName())) {
					message.setNodeId(column.getText());
				}
				if ("subject".equals(column.getName())) {
					message.setSubject(column.getText());
				}
				if ("receiver".equals(column.getName())) {
					message.setReceiver(column.getText());
				}
				if ("copyTo".equals(column.getName())) {
					message.setCopyTo(column.getText());
				}
				if ("bcc".equals(column.getName())) {
					message.setBcc(column.getText());
				}
				if ("fromUser".equals(column.getName())) {
					message.setFromUser(column.getText());
				}
				if ("templateId".equals(column.getName())) {
					message.setTemplateId(Long.valueOf(Long.parseLong(column
							.getText())));
				}
				if ("messageType".equals(column.getName())) {
					message.setMessageType(Integer.valueOf(Integer
							.parseInt(column.getText())));
				}
				message.setActDefId(actDefId);
				this.messageDao.add(message);
			}
		}
	}

	public String exportXml(Long[] lActDeployId) throws FileNotFoundException,
			IOException {
		String strXml = "";
		Document doc = DocumentHelper.createDocument();
		Element docRoot = doc.addElement("items");
		for (int i = 0; i < lActDeployId.length; i++) {
			Element root = docRoot.addElement("FlowRef");
			BpmDefinition definition = (BpmDefinition) this.dao
					.getById(lActDeployId[i]);
			if (!BeanUtils.isNotEmpty(definition))
				continue;
			exportDefinition(definition, root);
			exportBpmnoderule(definition.getActDefId(), root);
			exportBpmnodescript(definition.getActDefId(), root);
			exportBpmdefvar(definition.getDefId(), root);
			exportBpmnodesign(definition.getActDefId(), root);
		}

		strXml = doc.asXML();
		return strXml;
	}

	private void exportDefinition(BpmDefinition definition, Element root) {
		Element elements = root.addElement("bpm_definition");
		if (StringUtil.isNotEmpty(definition.getSubject())) {
			elements.addElement("subject").setText(definition.getSubject());
		}
		if (StringUtil.isNotEmpty(definition.getDefKey())) {
			elements.addElement("defKey").setText(definition.getDefKey());
		}
		if (StringUtil.isNotEmpty(definition.getTaskNameRule())) {
			elements.addElement("taskNameRule").setText(
					definition.getTaskNameRule());
		}
		if (StringUtil.isNotEmpty(definition.getDescp())) {
			elements.addElement("descp").setText(definition.getDescp());
		}
		if (StringUtil.isNotEmpty(definition.getReason())) {
			elements.addElement("reason").setText(definition.getReason());
		}
		if (StringUtil.isNotEmpty(definition.getDefXml()))
			elements.addElement("defXml").setText(definition.getDefXml());
	}

	private void exportBpmnoderule(String actDefId, Element root) {
		if (StringUtil.isEmpty(actDefId))
			return;
		List<BpmNodeRule> ruleList = this.bpmNodeRuleDao.getByDefIdNodeId(
				actDefId, "");
		if (BeanUtils.isNotEmpty(ruleList)) {
			for (BpmNodeRule model : ruleList) {
				Element elements = root.addElement("BPM_NODE_RULE");
				if (StringUtil.isNotEmpty(model.getRuleName())) {
					elements.addElement("ruleName")
							.setText(model.getRuleName());
				}
				if (StringUtil.isNotEmpty(model.getConditionCode())) {
					elements.addElement("conditionCode").setText(
							model.getConditionCode());
				}
				if (StringUtil.isNotEmpty(model.getNodeId())) {
					elements.addElement("nodeId").setText(model.getNodeId());
				}
				if (BeanUtils.isNotEmpty(model.getPriority())) {
					elements.addElement("priority").setText(
							model.getPriority().toString());
				}
				if (StringUtil.isNotEmpty(model.getTargetNodeName())) {
					elements.addElement("targetNodeName").setText(
							model.getTargetNodeName());
				}
				if (StringUtil.isNotEmpty(model.getMemo()))
					elements.addElement("memo").setText(model.getMemo());
			}
		}
	}

	private void exportBpmnodescript(String actDefId, Element root) {
		if (StringUtil.isEmpty(actDefId))
			return;
		List<BpmNodeScript> scriptList = this.bpmNodeScriptDao
				.getByBpmNodeScriptId("", actDefId);
		if (BeanUtils.isNotEmpty(scriptList)) {
			for (BpmNodeScript model : scriptList) {
				Element elements = root.addElement("BPM_NODE_SCRIPT");
				if (StringUtil.isNotEmpty(model.getNodeId())) {
					elements.addElement("nodeId").setText(model.getNodeId());
				}
				if (BeanUtils.isNotEmpty(model.getScriptType())) {
					elements.addElement("scriptType").setText(
							model.getScriptType().toString());
				}
				if (StringUtil.isNotEmpty(model.getScript())) {
					elements.addElement("script").setText(model.getScript());
				}
				if (StringUtil.isNotEmpty(model.getMemo()))
					elements.addElement("memo").setText(model.getMemo());
			}
		}
	}

	private void exportBpmdefvar(Long defId, Element root) {
		if (StringUtil.isEmpty(defId.toString()))
			return;
		List<BpmDefVar> defVarList = this.bpmDefVarDao.getByDefId(defId);
		if (BeanUtils.isNotEmpty(defVarList))
			for (BpmDefVar model : defVarList) {
				Element elements = root.addElement("BPM_DEF_VARS");
				if (StringUtil.isNotEmpty(model.getVarName())) {
					elements.addElement("varName").setText(model.getVarName());
				}
				if (StringUtil.isNotEmpty(model.getVarKey())) {
					elements.addElement("varKey").setText(model.getVarKey());
				}
				if (StringUtil.isNotEmpty(model.getVarDataType())) {
					elements.addElement("varDataType").setText(
							model.getVarDataType());
				}
				if (StringUtil.isNotEmpty(model.getDefValue())) {
					elements.addElement("defValue")
							.setText(model.getDefValue());
				}
				if (StringUtil.isNotEmpty(model.getNodeName())) {
					elements.addElement("nodeName")
							.setText(model.getNodeName());
				}
				if (BeanUtils.isNotEmpty(model.getVersionId())) {
					elements.addElement("versionId").setText(
							model.getVersionId().toString());
				}
				if (StringUtil.isNotEmpty(model.getNodeId())) {
					elements.addElement("nodeId").setText(model.getNodeId());
				}
				if (StringUtil.isNotEmpty(model.getVarScope()))
					elements.addElement("varScope")
							.setText(model.getVarScope());
			}
	}

	private void exportBpmnodesign(String actDefId, Element root) {
		if (StringUtil.isEmpty(actDefId))
			return;
		List<BpmNodeSign> signList = this.bpmNodeSignDao.getByActDefId(
				actDefId, "");
		if (BeanUtils.isNotEmpty(signList)) {
			for (BpmNodeSign model : signList) {
				Element elements = root.addElement("BPM_NODE_SIGN");
				if (StringUtil.isNotEmpty(model.getNodeId())) {
					elements.addElement("nodeId").setText(model.getNodeId());
				}
				if (BeanUtils.isNotEmpty(model.getVoteAmount())) {
					elements.addElement("voteAmount").setText(
							model.getVoteAmount().toString());
				}
				if (BeanUtils.isNotEmpty(model.getDecideType())) {
					elements.addElement("decideType").setText(
							model.getDecideType().toString());
				}
				if (BeanUtils.isNotEmpty(model.getVoteType()))
					elements.addElement("voteType").setText(
							model.getVoteType().toString());
			}
		}
	}

	private void exportBpmnodemess(String actDefId, Element root) {
		if (StringUtil.isEmpty(actDefId))
			return;
		List<BpmNodeMessage> messList = this.bpmNodeMessageDao
				.getByActDefId(actDefId);
		if (BeanUtils.isNotEmpty(messList)) {
			for (BpmNodeMessage model : messList) {
				Element elements = root.addElement("BPM_NODE_MESSAGE");
				if (BeanUtils.isNotEmpty(model.getMessageId())) {
					elements.addElement("messageId").setText(
							model.getMessageId().toString());
				}
				if (StringUtil.isNotEmpty(model.getNodeId()))
					elements.addElement("nodeId").setText(model.getNodeId());
			}
		}
	}

	private void exportSysmess(String actDefId, Element root) {
		if (StringUtil.isEmpty(actDefId))
			return;
		List<Message> smessList = this.messageDao.getByActDefId(actDefId);
		if (BeanUtils.isNotEmpty(smessList)) {
			for (Message model : smessList) {
				Element elements = root.addElement("SYS_MESSAGE");
				if (BeanUtils.isNotEmpty(model.getMessageId())) {
					elements.addElement("messageId").setText(
							model.getMessageId().toString());
				}
				if (StringUtil.isNotEmpty(model.getSubject())) {
					elements.addElement("subject").setText(model.getSubject());
				}
				if (StringUtil.isNotEmpty(model.getReceiver())) {
					elements.addElement("receiver")
							.setText(model.getReceiver());
				}
				if (StringUtil.isNotEmpty(model.getCopyTo())) {
					elements.addElement("copyTo").setText(model.getCopyTo());
				}
				if (StringUtil.isNotEmpty(model.getBcc())) {
					elements.addElement("bcc").setText(model.getBcc());
				}
				if (StringUtil.isNotEmpty(model.getFromUser())) {
					elements.addElement("fromUser")
							.setText(model.getFromUser());
				}
				if (BeanUtils.isNotEmpty(model.getTemplateId())) {
					elements.addElement("templateId").setText(
							model.getTemplateId().toString());
				}
				if (BeanUtils.isNotEmpty(model.getMessageType())) {
					elements.addElement("messageType").setText(
							model.getMessageType().toString());
				}
				if (StringUtil.isNotEmpty(model.getNodeId()))
					elements.addElement("nodeId").setText(model.getNodeId());
			}
		}
	}

	public BpmDefinition getMainDefByActDefKey(String actDefKey) {
		return this.dao.getMainDefByActDefKey(actDefKey);
	}

	public List<BpmDefinition> getByUserId(QueryFilter queryFilter) {
		List list = this.dao.getByUserId(queryFilter);
		return list;
	}

	public List<BpmDefinition> getByUserIdFilter(QueryFilter queryFilter) {
		return this.dao.getByUserIdFilter(queryFilter);
	}

	public List<BpmDefinition> getBpmDefinitions() {
		return this.dao.getBpmDefinitions();
	}
}