package com.jalinsuara.android.news.model;

/**
 * Param for sending news
 * 
 * @author hartono
 * 
 */
public class ShareNewsParams {
	String title;
	String description;
	String postable_type;
	String postable_id;
	String user_id;
	String auth_token;
	String file;
	String budget;
	String beneficiary;
	String dimensions;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPostable_type() {
		return postable_type;
	}

	public void setPostable_type(String postable_type) {
		this.postable_type = postable_type;
	}

	public String getPostable_id() {
		return postable_id;
	}

	public void setPostable_id(String postable_id) {
		this.postable_id = postable_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

}
