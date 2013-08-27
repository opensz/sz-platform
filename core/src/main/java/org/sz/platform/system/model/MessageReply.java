package org.sz.platform.system.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class MessageReply extends BaseModel {
	protected Long id;
	protected Long messageId;
	protected String content;
	protected Long replyId;
	protected String reply;
	protected Date replyTime;
	protected Short isPrivate;

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

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public Long getReplyId() {
		return this.replyId;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public Date getReplyTime() {
		return this.replyTime;
	}

	public void setIsPrivate(Short isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Short getIsPrivate() {
		return this.isPrivate;
	}

	public boolean equals(Object object) {
		if (!(object instanceof MessageReply)) {
			return false;
		}
		MessageReply rhs = (MessageReply) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.messageId, rhs.messageId)
				.append(this.content, rhs.content)
				.append(this.replyId, rhs.replyId)
				.append(this.reply, rhs.reply)
				.append(this.replyTime, rhs.replyTime)
				.append(this.isPrivate, rhs.isPrivate).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.messageId).append(this.content)
				.append(this.replyId).append(this.reply).append(this.replyTime)
				.append(this.isPrivate).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("messageId", this.messageId)
				.append("content", this.content)
				.append("replyId", this.replyId).append("reply", this.reply)
				.append("replyTime", this.replyTime)
				.append("isPrivate", this.isPrivate).toString();
	}
}
