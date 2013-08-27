package org.sz.platform.system.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class MessageRead extends BaseModel {
	protected Long id;
	protected Long messageId;
	protected Long receiverId;
	protected String receiver;
	protected Date receiveTime;

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

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public boolean equals(Object object) {
		if (!(object instanceof MessageRead)) {
			return false;
		}
		MessageRead rhs = (MessageRead) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.messageId, rhs.messageId)
				.append(this.receiverId, rhs.receiverId)
				.append(this.receiver, rhs.receiver)
				.append(this.receiveTime, rhs.receiveTime).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.messageId).append(this.receiverId)
				.append(this.receiver).append(this.receiveTime).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("messageId", this.messageId)
				.append("receiverId", this.receiverId)
				.append("receiver", this.receiver)
				.append("receiveTime", this.receiveTime).toString();
	}
}
