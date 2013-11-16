package com.jalinsuara.android;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.search.SearchResult;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Singleton used in the entire application
 * 
 * @author tonoman3g
 * 
 */
public class JalinSuaraSingleton {

	private static Logger log = LoggerFactory
			.getLogger(JalinSuaraSingleton.class.getSimpleName());

	private static JalinSuaraSingleton ourInstance;

	public static void initialize() {
		getInstance();
	}

	public static void uninitialize() {
		ourInstance = null;
	}

	public static synchronized JalinSuaraSingleton getInstance() {
		if (ourInstance == null) {
			log.info("JalinSuaraSingleton null, initialize it.");
			ourInstance = new JalinSuaraSingleton();
		}
		return ourInstance;
	}

	/**
	 * Gson for deserialize and serialize json
	 */
	private Gson mGson;

	/**
	 * News list
	 */
	private ArrayList<News> mNewsList;

	/**
	 * Sub project list
	 */
	private ArrayList<SubProject> mSubProjectList;

	/**
	 * Provinces list
	 */
	private ArrayList<Province> mProvincesList;

	/**
	 * Subdistricts list
	 */
	private ArrayList<Province> mSubDistrictsList;

	/**
	 * Districts lsit
	 */
	private ArrayList<District> mDistrictsList;

	/**
	 * Recent search list, always check for null value whenever using this field
	 */
	private ArrayList<SearchResult> mRecentSearchResultList;
	private String mLoginToken;
	private String mEmailUser;
	private JalinSuaraSingleton() {
		log.info("JalinSuaraSingleton()");

		// init gson
		GsonBuilder builder = new GsonBuilder();
		builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		setGson(builder.create());

		// init news list
		setNewsList(new ArrayList<News>());
		setSubProjectList(new ArrayList<SubProject>());
	}

	public Gson getGson() {
		return mGson;
	}

	public void setGson(Gson gson) {
		mGson = gson;
	}

	public synchronized ArrayList<News> getNewsList() {
		return mNewsList;
	}

	public synchronized void setNewsList(ArrayList<News> newsList) {
		mNewsList = newsList;
	}
	
	public synchronized void setToken(String token){
		mLoginToken = token;
	}
	
	public synchronized String getToken(){
		return mLoginToken;
	}
	
	public synchronized void setEmail(String email){
		mEmailUser = email;
	}
	
	public synchronized String getEmail(){
		return mEmailUser;
	}

	/**
	 * Find news from news list loaded from server or from recent search results
	 * 
	 * @param id
	 * @return
	 */
	public synchronized News findNewsById(long id) {
		if (mNewsList == null) {
			if (mRecentSearchResultList == null) {
				return null;
			} else {
				for (SearchResult result : mRecentSearchResultList) {
					if (result.isNews()) {
						if (result.getNews().getId() == id) {
							return result.getNews();
						}
					}
				}
				return null;
			}
		}
		for (News news : mNewsList) {
			if (news.getId() == id) {
				return news;
			}
		}
		if (mRecentSearchResultList == null) {
			return null;
		}
		for (SearchResult result : mRecentSearchResultList) {
			if (result.isNews()) {
				if (result.getNews().getId() == id) {
					return result.getNews();
				}
			}
		}
		return null;
	}

	public synchronized ArrayList<SubProject> getSubProjectList() {
		return mSubProjectList;
	}

	public synchronized void setSubProjectList(ArrayList<SubProject> subProject) {
		mSubProjectList = subProject;
	}

	/**
	 * Find sub project from sub project list that has been loaded from server
	 * 
	 * @param id
	 * @return
	 */
	public synchronized SubProject findSubProjectById(long id) {
		if (mSubProjectList == null) {
			return null;
		}
		for (SubProject subproject : mSubProjectList) {
			if (subproject.getId() == id) {
				return subproject;
			}
		}
		return null;
	}

	public synchronized ArrayList<SearchResult> getRecentSearchResultList() {
		return mRecentSearchResultList;
	}

	public synchronized void setRecentSearchResultList(
			ArrayList<SearchResult> recentSearchResultList) {
		mRecentSearchResultList = recentSearchResultList;
	}

	public synchronized ArrayList<Province> getSubDistrictsList() {
		return mSubDistrictsList;
	}

	public synchronized void setSubDistrictsList(
			ArrayList<Province> subDistrictsList) {
		mSubDistrictsList = subDistrictsList;
	}

	public synchronized ArrayList<Province> getProvincesList() {
		return mProvincesList;
	}

	public synchronized void setProvincesList(ArrayList<Province> provincesList) {
		mProvincesList = provincesList;
	}

}
