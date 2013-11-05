package com.jalinsuara.android.news.model;

import java.util.Date;

/**
 * Model for post / news
 * 
 * @author tonoman3g
 * 
 */
public class News {

	private long id;

	private long activityId;

	private String title;

	private long budget;

	private long beneficiary;

	private boolean gmaps;

	private String description;

	private Date createdAt;

	private long dimension;

	private String pictureContentType;

	private long postableId;

	private String postableType;

	private Date updatedAt;

	private long userId;

	private double latitude;

	private double longitude;

	private String pictureFileName;

	private long pictureFileSize;

	private Date pictureUpdatedAt;

	private int status;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ldescription = description;
		if (description.length() > 10) {
			ldescription = description.substring(0, 9) + "...";
		}

		return "News [id=" + id + ", title=" + title + ", budget=" + budget
				+ ", beneficiary=" + beneficiary + ", gmaps=" + gmaps
				+ ", description=" + ldescription + ", createdAt=" + createdAt
				+ ", dimension=" + dimension + ", pictureContentType="
				+ pictureContentType + ", postableId=" + postableId
				+ ", postableType=" + postableType + ", updatedAt=" + updatedAt
				+ ", userId=" + userId + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", pictureFileName="
				+ pictureFileName + ", pictureFileSize=" + pictureFileSize
				+ ", pictureUpdateAt=" + pictureUpdatedAt + ", status="
				+ status + "]";
	}

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the budget
	 */
	public long getBudget() {
		return budget;
	}

	/**
	 * @param budget
	 *            the budget to set
	 */
	public void setBudget(long budget) {
		this.budget = budget;
	}

	/**
	 * @return the beneficiary
	 */
	public long getBeneficiary() {
		return beneficiary;
	}

	/**
	 * @param beneficiary
	 *            the beneficiary to set
	 */
	public void setBeneficiary(long beneficiary) {
		this.beneficiary = beneficiary;
	}

	/**
	 * @return the gmaps
	 */
	public boolean isGmaps() {
		return gmaps;
	}

	/**
	 * @param gmaps
	 *            the gmaps to set
	 */
	public void setGmaps(boolean gmaps) {
		this.gmaps = gmaps;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the dimension
	 */
	public long getDimension() {
		return dimension;
	}

	/**
	 * @param dimension
	 *            the dimension to set
	 */
	public void setDimension(long dimension) {
		this.dimension = dimension;
	}

	/**
	 * @return the pictureContentType
	 */
	public String getPictureContentType() {
		return pictureContentType;
	}

	/**
	 * @param pictureContentType
	 *            the pictureContentType to set
	 */
	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	/**
	 * @return the postableId
	 */
	public long getPostableId() {
		return postableId;
	}

	/**
	 * @param postableId
	 *            the postableId to set
	 */
	public void setPostableId(long postableId) {
		this.postableId = postableId;
	}

	/**
	 * @return the postableType
	 */
	public String getPostableType() {
		return postableType;
	}

	/**
	 * @param postableType
	 *            the postableType to set
	 */
	public void setPostableType(String postableType) {
		this.postableType = postableType;
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
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the pictureFileName
	 */
	public String getPictureFileName() {
		return pictureFileName;
	}

	/**
	 * @param pictureFileName
	 *            the pictureFileName to set
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
	 * @param pictureFileSize
	 *            the pictureFileSize to set
	 */
	public void setPictureFileSize(long pictureFileSize) {
		this.pictureFileSize = pictureFileSize;
	}

	/**
	 * @return the pictureUpdateAt
	 */
	public Date getPictureUpdatedAt() {
		return pictureUpdatedAt;
	}

	/**
	 * @param pictureUpdateAt
	 *            the pictureUpdateAt to set
	 */
	public void setPictureUpdatedAt(Date pictureUpdatedAt) {
		this.pictureUpdatedAt = pictureUpdatedAt;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

}
