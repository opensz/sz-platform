package org.sz.platform.bpm.dao.flow.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.DateUtil;
import org.sz.platform.bpm.dao.flow.TaskDao;

@Repository("taskDao")
public class TaskDaoImpl extends BaseDaoImpl<TaskEntity> implements TaskDao {
	public Class<TaskEntity> getEntityClass() {
		return TaskEntity.class;
	}

	public String getIbatisMapperNamespace() {
		return "org.sz.core.bpm.model.ProcessTask";
	}

	public List<TaskEntity> getMyTasks(Long userId, QueryFilter queryFilter) {
		String statmentName = "getAllMyTask";
		queryFilter.getFilters().put("userId", userId);
		return getBySqlKey(statmentName, queryFilter);
	}

	public List<ProcessTask> getTasks(Long userId, String taskName,
			String subject, String processName, String orderField,
			String orderSeq, PageBean pb) {
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("name", taskName);
		params.put("subject", subject);
		params.put("processName", processName);
		params.put("orderField", orderField);
		params.put("orderSeq", orderSeq);
		List list = getBySqlKey("getAllMyTask", params, pb);
		return list;
	}

	public List<TaskEntity> getMyEvents(Object param) {
		Log logger = LogFactory.getLog(getClass());
		Map map = (Map) param;
		String mode = (String) map.get("mode");
		String sDate = (String) map.get("startDate");
		String eDate = (String) map.get("endDate");

		Date startDate = null;
		Date endDate = null;

		if ("month".equals(mode)) {
			try {
				Date reqDate = DateUtils.parseDate(sDate,
						new String[] { "MM/dd/yyyy" });
				Calendar cal = Calendar.getInstance();
				cal.setTime(reqDate);
				startDate = DateUtil.setStartDay(cal).getTime();
				reqDate = DateUtils.parseDate(eDate,
						new String[] { "MM/dd/yyyy" });
				cal.setTime(reqDate);
				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else if ("day".equals(mode)) {
			try {
				Date reqDay = DateUtils.parseDate(sDate,
						new String[] { "MM/dd/yyyy" });

				Calendar cal = Calendar.getInstance();
				cal.setTime(reqDay);

				startDate = DateUtil.setStartDay(cal).getTime();

				cal.add(2, 1);
				cal.add(5, -1);

				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else if ("week".equals(mode)) {
			try {
				Date reqStartWeek = DateUtils.parseDate(sDate,
						new String[] { "MM/dd/yyyy" });
				Date reqEndWeek = DateUtils.parseDate(eDate,
						new String[] { "MM/dd/yyyy" });
				Calendar cal = Calendar.getInstance();

				cal.setTime(reqStartWeek);

				startDate = DateUtil.setStartDay(cal).getTime();
				cal.setTime(reqEndWeek);

				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else if ("workweek".equals(mode)) {
			try {
				Date reqStartWeek = DateUtils.parseDate(sDate,
						new String[] { "MM/dd/yyyy" });
				Date reqEndWeek = DateUtils.parseDate(eDate,
						new String[] { "MM/dd/yyyy" });
				Calendar cal = Calendar.getInstance();

				cal.setTime(reqStartWeek);

				startDate = DateUtil.setStartDay(cal).getTime();
				cal.setTime(reqEndWeek);

				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}

		Map params = new HashMap();
		params.put("userId", ContextUtil.getCurrentUserId());
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return getBySqlKey("getAllMyEvent", params);
	}

	public List<TaskEntity> getAgentTasks(Long userId, String actDefId,
			QueryFilter queryFilter) {
		String statmentName = "getByAgent";
		queryFilter.getFilters().put("userId", userId);
		queryFilter.getFilters().put("actDefId", actDefId);
		return getBySqlKey(statmentName, queryFilter);
	}

	public List<Long> getAgentIdByTaskId(QueryFilter queryFilter) {
		List list = getBySqlKey("getAgentIdByTaskId", queryFilter);
		return list;
	}

	public int setDueDate(String taskId, Date dueDate) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		params.put("dueDate", dueDate);
		return update("setDueDate", params);
	}

	public void insertTask(ProcessTask task) {
		String statement = getIbatisMapperNamespace() + ".add";
		getSqlSessionTemplate().insert(statement, task);
	}

	public List<Long> getCandidateUsers(Long taskId) {
		List list = getBySqlKey("getCandidateUsers", taskId);
		return list;
	}

	public List<TaskEntity> getAllAgentTask(Long userId, QueryFilter filter) {
		String statmentName = "getAllAgent";
		filter.getFilters().put("userId", userId);
		return getBySqlKey(statmentName, filter);
	}

	public List<ProcessTask> getReminderTask() {
		Date curDate = new Date(System.currentTimeMillis());
		List list = getSqlSessionTemplate().selectList("getReminderTask",
				curDate);
		return list;
	}

	public List<ProcessTask> getTasksByRunId(Long runId) {
		List list = getBySqlKey("getTasksByRunId", runId);
		return list;
	}

	public void updateTaskAssignee(String taskId, String userId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		params.put("userId", userId);
		update("updateTaskAssignee", params);
	}

	public void updateTaskAssigneeNull(String taskId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		update("updateTaskAssigneeNull", params);
	}

	public void updateTaskOwner(String taskId, String userId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		params.put("userId", userId);
		update("updateTaskOwner", params);
	}

	public List<ProcessTask> getByTaskNameOrTaskIds(String userId,
			String taskName, String taskIds, PageBean pb) {
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("taskName", taskName);
		params.put("taskIds", taskIds);
		List list = getBySqlKey("getByTaskNameOrTaskIds", params, pb);
		return list;
	}

	public ProcessTask getByTaskId(String taskId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		return (ProcessTask) getOne("getByTaskId", params);
	}

	public List<TaskEntity> getAllAgentTask(Long userId, PageBean pb) {
		Map params = new HashMap();
		params.put("userId", userId);
		return getBySqlKey("getByAgent", params, pb);
	}

	public List<TaskEntity> getCcTasks(Long userId, QueryFilter queryFilter) {
		String statmentName = "getCcTasks";
		queryFilter.getFilters().put("userId", userId);
		return getBySqlKey(statmentName, queryFilter);
	}
}
