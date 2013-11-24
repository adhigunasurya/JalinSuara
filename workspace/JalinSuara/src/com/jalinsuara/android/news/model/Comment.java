package com.jalinsuara.android.news.model;

import java.util.Date;

/**
 * Model for comment
 * 
 * @author tonoman3g
 * 
 */
public class Comment {
	
	public final static String COMMENTABLE_TYPE_POST = "Post";

	private long id;

	private long ownerId;

	private long commentableId;

	private String commentableType;

	private String body;

	private Date createdAt;

	private Date updatedAt;

	/**
	 * null if the owner is a registered user
	 */
	private String guestName;

	private String guestEmail;
	
	/**
	 * null if the owner is a guest user
	 */
	private String commenterName;
	
	private String commenterEmail;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the commentableId
	 */
	public long getCommentableId() {
		return commentableId;
	}

	/**
	 * @param commentableId
	 *            the commentableId to set
	 */
	public void setCommentableId(long commentableId) {
		this.commentableId = commentableId;
	}

	/**
	 * @return the commentableType
	 */
	public String getCommentableType() {
		return commentableType;
	}

	/**
	 * @param commentableType
	 *            the commentableType to set
	 */
	public void setCommentableType(String commentableType) {
		this.commentableType = commentableType;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the guestName
	 */
	public String getGuestName() {
		return guestName;
	}

	/**
	 * @param guestName
	 *            the guestName to set
	 */
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	/**
	 * @return the guestEmail
	 */
	public String getGuestEmail() {
		return guestEmail;
	}

	/**
	 * @param guestEmail
	 *            the guestEmail to set
	 */
	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Comment [id=" + id + ", ownerId=" + ownerId
				+ ", commentableId=" + commentableId + ", commentableType="
				+ commentableType + ", body=" + body + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", guestName="
				+ guestName + ", guestEmail=" + guestEmail + ", commenterName="
				+ commenterName + ", commenterEmail=" + commenterEmail + "]";
	}

	public String getCommenterEmail() {
		return commenterEmail;
	}

	public void setCommenterEmail(String commenterEmail) {
		this.commenterEmail = commenterEmail;
	}

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

}
