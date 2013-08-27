package org.sz.platform.bpm.service.flow;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.sz.platform.bpm.model.flow.ForkUser;

public interface TaskUserAssignService {

	void addForkUser(ForkUser forkTask);

	ForkUser getForkUser();

	void clearForkUser();

	void addNodeUserMap(String[] nodeIds, String[] userIds);

	Map<String, List<String>> getNodeUserMap();

	void clearNodeUserMap();
	
	void addCcUserIds(String userIds);
	
	Long[] getCcUserIds();
	
	void clearCcUserIds();

	void addNodeUser(String nodeId, String userIds);

	List<String> getSignUser(ActivityExecution execution);

	void setSignUser(List<String> users);

	List<String> getSignUser();

	void clearSignUser();

}