package com.jalinsuara.android.profile.model;

import java.util.Date;

public class User {

	private long id;
	private String email;
	private String username;
	private String pictureContentType;
	private String pictureFileName;
	private long pictureFileSize;
	private Date pictureUpdatedAt;
	private Date updatedAt;
	private Date createdAt;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the pictureContentType
	 */
	public String getPictureContentType() {
		return pictureContentType;
	}
	/**
	 * @param pictureContentType the pictureContentType to set
	 */
	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}
	/**
	 * @return the pictureFileName
	 */
	public String getPictureFileName() {
		return pictureFileName;
	}
	/**
	 * @param pictureFileName the pictureFileName to set
	 */
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	/**
	 * @return the pictureFileSize
	 */
	public long getPictureFileSize() {
		return pictureFileSize;
	}
	/**
	 * @param pictureFileSize the pictureFileSize to set
	 */
	public void setPictureFileSize(long pictureFileSize) {
		this.pictureFileSize = pictureFileSize;
	}
	/**
	 * @return the pictureUpdatedAt
	 */
	public Date getPictureUpdatedAt() {
		return pictureUpdatedAt;
	}
	/**
	 * @param pictureUpdatedAt the pictureUpdatedAt to set
	 */
	public void setPictureUpdatedAt(Date pictureUpdatedAt) {
		this.pictureUpdatedAt = pictureUpdatedAt;
	}
	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username
				+ ", pictureContentType=" + pictureContentType
				+ ", pictureFileName=" + pictureFileName + ", pictureFileSize="
				+ pictureFileSize + ", pictureUpdatedAt=" + pictureUpdatedAt
				+ ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + "]";
	}
	
	
}
