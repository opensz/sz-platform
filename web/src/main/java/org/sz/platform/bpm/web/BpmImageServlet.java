package org.sz.platform.bpm.web;

import org.sz.core.util.StringUtil;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskNodeStatus;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ProcessRunService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.StringUtils;

public class BpmImageServlet extends HttpServlet {


	protected BpmService bpmService = (BpmService)AppUtil.getBean("bpmService");
	protected RepositoryService repositoryService= (RepositoryService)AppUtil.getBean("repositoryService");

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String deployId = RequestUtil.getString(request, "deployId");
		String taskId = RequestUtil.getString(request, "taskId");
		String processInstanceId = RequestUtil.getString(request,
				"processInstanceId");
		String definitionId = RequestUtil.getString(request, "definitionId");
		String runId = request.getParameter("runId");

		InputStream is = null;
		if (StringUtil.isNotEmpty(deployId)) {
			ProcessDefinitionEntity ent = this.bpmService
					.getProcessDefinitionByDeployId(deployId);
			is = generatePngDiagram(ent);
		} else if (StringUtils.isNotEmpty(definitionId)) {
			ProcessDefinitionEntity ent = this.bpmService
					.getProcessDefinitionByDefId(definitionId);
			is = generatePngDiagram(ent);
		} else if (StringUtil.isNotEmpty(taskId)) {
			ProcessDefinitionEntity ent = this.bpmService
					.getProcessDefinitionByTaskId(taskId);
			TaskEntity taskEntity = this.bpmService.getTask(taskId);
			List list = this.bpmService.getNodeCheckStatusInfo(taskEntity
					.getProcessInstanceId());
			is = generateDiagram(ent, list, "png");
		} else if (StringUtils.isNotEmpty(processInstanceId)) {
			ProcessDefinitionEntity ent = this.bpmService
					.getProcessDefinitionByProcessInanceId(processInstanceId);
			if (ent == null) {
				ProcessRunService processRunService = (ProcessRunService) AppUtil
						.getBean(ProcessRunService.class);
				ProcessRun processRun = processRunService
						.getByActInstanceId(processInstanceId);
				ent = this.bpmService.getProcessDefinitionByDefId(processRun
						.getActDefId());
			}
			List<TaskNodeStatus> list = this.bpmService
					.getNodeCheckStatusInfo(processInstanceId);
			is = generateDiagram(ent, list, "png");
		} else if (StringUtils.isNotEmpty(runId)) {
			ProcessRunService processRunService = (ProcessRunService) AppUtil
					.getBean(ProcessRunService.class);
			ProcessRun processRun = (ProcessRun) processRunService
					.getById(new Long(runId));
			ProcessDefinitionEntity ent = this.bpmService
					.getProcessDefinitionByProcessInanceId(processRun
							.getActInstId());
			if (ent == null) {
				ent = this.bpmService.getProcessDefinitionByDefId(processRun
						.getActDefId());
			}
			List list = this.bpmService.getNodeCheckStatusInfo(processRun
					.getActInstId());
			is = generateDiagram(ent, list, "png");
		}

		if (is != null) {
			response.setContentType("image/png");
			OutputStream out = response.getOutputStream();
			try {
				byte[] bs = new byte[1024];
				int n = 0;
				while ((n = is.read(bs)) != -1) {
					out.write(bs, 0, n);
				}
				out.flush();
			} catch (Exception ex) {
			} finally {
				is.close();
				out.close();
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	protected InputStream generateDiagram(ProcessDefinitionEntity ent,
			List<TaskNodeStatus> listNodeStatus, String imageType) {
		if (ent != null) {
			List<String> highLightedActivities = new ArrayList<String>();
			for (TaskNodeStatus nodeStatus : listNodeStatus) {
				highLightedActivities.add(nodeStatus.getActInstId());
			}
			return ProcessDiagramGenerator.generateDiagram(
					repositoryService.getBpmnModel(ent.getId()), imageType,
					highLightedActivities);
		}
		return null;
	}

	protected InputStream generatePngDiagram(ProcessDefinitionEntity ent) {
		if (ent != null) {
			return ProcessDiagramGenerator.generatePngDiagram(repositoryService
					.getBpmnModel(ent.getId()));
		}
		return null;
	}
}
