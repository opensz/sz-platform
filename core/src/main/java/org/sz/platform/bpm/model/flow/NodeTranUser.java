package org.sz.platform.bpm.model.flow;

import java.util.LinkedHashSet;
import java.util.Set;

public class NodeTranUser {
	private String nodeName;
	private String nodeId;
	Set<NodeUserMap> nodeUserMapSet = new LinkedHashSet();

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public NodeTranUser() {
	}

	public NodeTranUser(String nodeId, String nodeName,
			Set<NodeUserMap> nodeUserMapSet) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeUserMapSet = nodeUserMapSet;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Set<NodeUserMap> getNodeUserMapSet() {
		return this.nodeUserMapSet;
	}

	public void setNodeUserMapSet(Set<NodeUserMap> nodeUserMapSet) {
		this.nodeUserMapSet = nodeUserMapSet;
	}
}
