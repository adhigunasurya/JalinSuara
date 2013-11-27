package com.jalinsuara.android.projects.model;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.jalinsuara.android.Nameable;

/**
 * Model for sub project
 * 
 * @author tonoman3g
 * 
 */
public class SubProject extends Nameable {

	private long id;

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

	private String name;
	private double latitude;
	private double longitude;
	private Date createdAt;
	private Date updatedAt;
	private String pictureFileName;
	private String pictureUrl;
	private String pictureContentType;
	private long pictureFileSize;
	private Date pictureUpdatedAt;
	private boolean gmaps;
	private String description;
	private int projectLength;
	private int projectArea;
	private int projectQuantity;
	private long blmAmount;
	private long selfFundAmount;
	private int maleProposal;
	private int maleBeneficiary;
	private int femaleBeneficiary;
	private int poorBeneficiary;
	private long subdistrictId;
	private long categoryId;
	private HashMap<String, String> dynamicAttributes;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the pictureUpdatedAt
	 */
	public Date getPictureUpdatedAt() {
		return pictureUpdatedAt;
	}

	/**
	 * @param pictureUpdatedAt
	 *            the pictureUpdatedAt to set
	 */
	public void setPictureUpdatedAt(Date pictureUpdatedAt) {
		this.pictureUpdatedAt = pictureUpdatedAt;
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
	 * @return the projectLength
	 */
	public int getProjectLength() {
		return projectLength;
	}

	/**
	 * @param projectLength
	 *            the projectLength to set
	 */
	public void setProjectLength(int projectLength) {
		this.projectLength = projectLength;
	}

	/**
	 * @return the projectArea
	 */
	public int getProjectArea() {
		return projectArea;
	}

	/**
	 * @param projectArea
	 *            the projectArea to set
	 */
	public void setProjectArea(int projectArea) {
		this.projectArea = projectArea;
	}

	/**
	 * @return the projectQuantity
	 */
	public int getProjectQuantity() {
		return projectQuantity;
	}

	/**
	 * @param projectQuantity
	 *            the projectQuantity to set
	 */
	public void setProjectQuantity(int projectQuantity) {
		this.projectQuantity = projectQuantity;
	}

	/**
	 * @return the maleProposal
	 */
	public int getMaleProposal() {
		return maleProposal;
	}

	/**
	 * @param maleProposal
	 *            the maleProposal to set
	 */
	public void setMaleProposal(int maleProposal) {
		this.maleProposal = maleProposal;
	}

	/**
	 * @return the maleBeneficiary
	 */
	public int getMaleBeneficiary() {
		return maleBeneficiary;
	}

	/**
	 * @param maleBeneficiary
	 *            the maleBeneficiary to set
	 */
	public void setMaleBeneficiary(int maleBeneficiary) {
		this.maleBeneficiary = maleBeneficiary;
	}

	/**
	 * @return the femaleBeneficiary
	 */
	public int getFemaleBeneficiary() {
		return femaleBeneficiary;
	}

	/**
	 * @param femaleBeneficiary
	 *            the femaleBeneficiary to set
	 */
	public void setFemaleBeneficiary(int femaleBeneficiary) {
		this.femaleBeneficiary = femaleBeneficiary;
	}

	/**
	 * @return the poorBeneficiary
	 */
	public int getPoorBeneficiary() {
		return poorBeneficiary;
	}

	/**
	 * @param poorBeneficiary
	 *            the poorBeneficiary to set
	 */
	public void setPoorBeneficiary(int poorBeneficiary) {
		this.poorBeneficiary = poorBeneficiary;
	}

	/**
	 * @return the blmAmount
	 */
	public long getBlmAmount() {
		return blmAmount;
	}

	/**
	 * @param blmAmount
	 *            the blmAmount to set
	 */
	public void setBlmAmount(long blmAmount) {
		this.blmAmount = blmAmount;
	}

	/**
	 * @return the selfFundAmount
	 */
	public long getSelfFundAmount() {
		return selfFundAmount;
	}

	/**
	 * @param selfFundAmount
	 *            the selfFundAmount to set
	 */
	public void setSelfFundAmount(long selfFundAmount) {
		this.selfFundAmount = selfFundAmount;
	}

	/**
	 * @return the subdistrictId
	 */
	public long getSubdistrictId() {
		return subdistrictId;
	}

	/**
	 * @param subdistrictId
	 *            the subdistrictId to set
	 */
	public void setSubdistrictId(long subdistrictId) {
		this.subdistrictId = subdistrictId;
	}

	/**
	 * @return the categoryId
	 */
	public long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the dynamicAttributes
	 */
	public HashMap<String, String> getDynamicAttributes() {
		return dynamicAttributes;
	}

	/**
	 * @param dynamicAttributes
	 *            the dynamicAttributes to set
	 */
	public void setDynamicAttributes(HashMap<String, String> dynamicAttributes) {
		this.dynamicAttributes = dynamicAttributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubProject [id=" + id + ", name=" + name + ", latitude="
				+ latitude + ", longitude=" + longitude + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", pictureFileName="
				+ pictureFileName + ", pictureContentType="
				+ pictureContentType + ", pictureFileSize=" + pictureFileSize
				+ ", pictureUpdatedAt=" + pictureUpdatedAt + ", gmaps=" + gmaps
				+ ", description=" + description + ", projectLength="
				+ projectLength + ", projectArea=" + projectArea
				+ ", projectQuantity=" + projectQuantity + ", blmAmount="
				+ blmAmount + ", selfFundAmount=" + selfFundAmount
				+ ", maleProposal=" + maleProposal + ", maleBeneficiary="
				+ maleBeneficiary + ", femaleBeneficiary=" + femaleBeneficiary
				+ ", poorBeneficiary=" + poorBeneficiary + ", subdistrictId="
				+ subdistrictId + ", categoryId=" + categoryId
				+ ", dynamicAttributes=" + dynamicAttributes + "]";
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

}
