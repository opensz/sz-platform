package org.sz.platform.bpm.model.flow;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmNodeMessage extends BaseModel implements Cloneable {
	public static final String TABLE_NAME = "BPM_NODE_MESSAGE";
	protected Long id;
	protected Long messageId;
	protected String actDefId;
	protected String nodeId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getMessageId() {
		return this.messageId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeMessage)) {
			return false;
		}
		BpmNodeMessage rhs = (BpmNodeMessage) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.messageId, rhs.messageId)
				.append(this.actDefId, rhs.actDefId)
				.append(this.nodeId, rhs.nodeId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.messageId).append(this.actDefId)
				.append(this.nodeId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("messageId", this.messageId)
				.append("actDefId", this.actDefId)
				.append("nodeId", this.nodeId).toString();
	}

	public Object clone() {
		BpmNodeMessage obj = null;
		try {
			obj = (BpmNodeMessage) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
