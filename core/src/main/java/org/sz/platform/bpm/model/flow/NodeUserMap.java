package org.sz.platform.bpm.model.flow;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sz.platform.system.model.SysUser;

public class NodeUserMap {
	private String nodeId;
	private String nodeName;
	private Long setId;
	private List<BpmNodeUser> nodeUserList;
	private Set<SysUser> users = new HashSet<SysUser>();
	
	private Short nodeAssignMode; //add by huhao 2013.01.22 节点流转模式

	public NodeUserMap() {
	}

	public NodeUserMap(Long setId, String nodeId, String nodeName, List<BpmNodeUser> nodeUserList) {
		this.setId = setId;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeUserList = nodeUserList;
	}

	public NodeUserMap(String nodeId, String nodeName, Set<SysUser> users) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.users = users;
	}
	
	public NodeUserMap(String nodeId, String nodeName, Set<SysUser> users,Short nodeAssignMode) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.users = users;
		this.nodeAssignMode = nodeAssignMode;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<BpmNodeUser> getNodeUserList() {
		return this.nodeUserList;
	}

	public void setNodeUserList(List<BpmNodeUser> nodeUserList) {
		this.nodeUserList = nodeUserList;
	}

	public Long getSetId() {
		return this.setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Set<SysUser> getUsers() {
		return this.users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	public Short getNodeAssignMode() {
		return nodeAssignMode;
	}

	public void setNodeAssignMode(Short nodeAssignMode) {
		this.nodeAssignMode = nodeAssignMode;
	}
}
