package org.sz.platform.bpm.web;

import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.util.StringUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.TaskSignData;
import org.sz.platform.bpm.util.BpmUtil;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class BpmWebUtil extends BpmUtil {

	public static ProcessCmd getProcessCmd(HttpServletRequest request)
			throws Exception {
		ProcessCmd cmd = new ProcessCmd();

		String temp = request.getParameter("taskId");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setTaskId(temp);
		}

		temp = request.getParameter("formData");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setFormData(temp);
		}

		Map paraMap = RequestUtil.getParameterValueMap(request, false, true);
		cmd.setFormDataMap(paraMap);

		temp = request.getParameter("actDefId");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setActDefId(temp);
		} else {
			temp = request.getParameter("flowKey");
			if (StringUtil.isNotEmpty(temp)) {
				cmd.setFlowKey(temp);
			}
		}

		temp = request.getParameter("destTask");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setDestTask(temp);
		}

		temp = request.getParameter("businessKey");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setBusinessKey(temp);
		}

		temp = request.getParameter("businessType");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setBusinessType(temp);
		}

		temp = request.getParameter("businessUrl");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setBusinessUrl(temp);
		}

		temp = request.getParameter("caseId");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setCaseId(Long.valueOf(temp));
		}

		temp = request.getParameter("tabData");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setTabData(temp);
		} else {
			cmd.getVariables().put("isDeal", 1);
		}

		String[] destTaskIds = request.getParameterValues("lastDestTaskId");
		if (destTaskIds != null) {
			cmd.setLastDestTaskIds(destTaskIds);
			String[] destTaskUserIds = new String[destTaskIds.length];
			for (int i = 0; i < destTaskIds.length; i++) {
				String[] userIds = request.getParameterValues(destTaskIds[i]
						+ "_userId");
				if (userIds != null) {
					destTaskUserIds[i] = StringUtil.getArrayAsString(userIds);
				}
			}
			cmd.setLastDestTaskUids(destTaskUserIds);
		}

		// 抄送人 设置
		String[] ccUserIds = request.getParameterValues("ccUserIds");
		if (ccUserIds != null && ccUserIds.length > 0) {
			cmd.setCcUserIds(StringUtil.getArrayAsString(ccUserIds));
		}

		cmd.setServiceItemId(RequestUtil.getLong(request, "serviceItemId"));

		cmd.setDeskRequestId(RequestUtil.getLong(request, "deskRequestId"));

		temp = request.getParameter("forkUserUids");
		if (StringUtils.isNotEmpty(temp)) {
			cmd.setForkUserUids(temp);
		}
		temp = request.getParameter("forkUserType");
		if (StringUtils.isNotEmpty(temp)) {
			cmd.setForkUserType(temp);
		}
		temp = request.getParameter("signUserIds");

		if (StringUtil.isNotEmpty(temp)) {
			cmd.setSignUserIds(temp);
		}

		temp = request.getParameter("back");
		if ("true".equals(temp)) {
			cmd.setBack(true);
		}

		temp = request.getParameter("backType");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setBack(true);
			if ("2".equals(temp)) {
				cmd.setBack(true, "first");
			}
		}

		cmd.setVoteContent(request.getParameter("voteContent"));
		cmd.setAssigneeIds(request.getParameter("assigneeIds"));

		temp = request.getParameter("stackId");
		if (StringUtils.isNotEmpty(temp)) {
			cmd.setStackId(new Long(temp));
		}
		temp = request.getParameter("voteAgree");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setVoteAgree(new Short(temp));

			if (TaskSignData.BACK.equals(cmd.getVoteAgree())) {
				cmd.setBack(true);
			}
		}

		temp = request.getParameter("isSignTask");
		if ("true".equals(temp)) {
			cmd.setSignTask(true);
		}

		Enumeration paramEnums = request.getParameterNames();
		while (paramEnums.hasMoreElements()) {
			String paramName = (String) paramEnums.nextElement();
			if (paramName.startsWith("v_")) {
				String[] vnames = paramName.split("[_]");
				if ((vnames != null) && (vnames.length == 3)) {
					String varName = vnames[1];
					String val = request.getParameter(paramName);
					if (val.isEmpty())
						continue;
					Object valObj = getValue(vnames[2], val);
					cmd.getVariables().put(varName, valObj);
				}
			}
		}

		return cmd;
	}
}
