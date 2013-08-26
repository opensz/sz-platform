package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class MessageReceiver extends BaseModel {
	public static final Short TYPE_USER = 0;
	public static final Short TYPE_ORG = 1;
	protected Long id;
	protected Long messageId;
	protected Short receiveType;
	protected Long receiverId;
	protected String receiver;

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

	public void setReceiveType(Short receiveType) {
		this.receiveType = receiveType;
	}

	public Short getReceiveType() {
		return this.receiveType;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReceiverId() {
		return this.receiverId;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public boolean equals(Object object) {
		if (!(object instanceof MessageReceiver)) {
			return false;
		}
		MessageReceiver rhs = (MessageReceiver) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.messageId, rhs.messageId)
				.append(this.receiveType, rhs.receiveType)
				.append(this.receiverId, rhs.receiverId)
				.append(this.receiver, rhs.receiver).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.messageId).append(this.receiveType)
				.append(this.receiverId).append(this.receiver).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("messageId", this.messageId)
				.append("receiveType", this.receiveType)
				.append("receiverId", this.receiverId)
				.append("receiver", this.receiver).toString();
	}
}
