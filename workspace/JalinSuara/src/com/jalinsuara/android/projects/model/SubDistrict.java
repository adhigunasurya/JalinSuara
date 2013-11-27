package com.jalinsuara.android.projects.model;

import java.util.Date;

import com.jalinsuara.android.Nameable;

/**
 * Model for subdisctrict
 * 
 * @author tonoman3g
 * 
 */
public class SubDistrict extends Nameable{

	private long id;

	private String name;

	private long districtId;

	private double latitude;

	private double longitude;

	private Date createdAt;

	private Date updatedAt;

	private boolean gmaps;

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
	 * @return the districtId
	 */
	public long getDistrictId() {
		return districtId;
	}

	/**
	 * @param districtId
	 *            the districtId to set
	 */
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubDistrict [id=" + id + ", name=" + name + ", districtId="
				+ districtId + ", latitude=" + latitude + ", longitude="
				+ longitude + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", gmaps=" + gmaps + "]";
	}

}
