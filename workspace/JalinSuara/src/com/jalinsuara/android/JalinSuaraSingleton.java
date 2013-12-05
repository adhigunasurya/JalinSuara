package com.jalinsuara.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubDistrict;
import com.jalinsuara.android.projects.model.SubProject;
import com.jalinsuara.android.search.SearchResult;

/**
 * Singleton used in the entire application
 * 
 * @author tonoman3g
 * 
 */
public class JalinSuaraSingleton {

	/**
	 * Shared preference name for session
	 */
	private static final String SESSION = "session";

	private static final String KEY_TOKEN = "token";

	private static final String KEY_EMAIL = "email";

	private static Logger log = LoggerFactory
			.getLogger(JalinSuaraSingleton.class.getSimpleName());

	private static JalinSuaraSingleton ourInstance;

	/**
	 * Initialize the singleton, should called in {@link Application}
	 * 
	 * @param context
	 */
	public static void initialize(Context context) {
		getInstance(context);
	}

	/**
	 * Uninitialize the singleton
	 */
	public static void uninitialize() {
		ourInstance = null;
	}

	public static synchronized JalinSuaraSingleton getInstance(Context context) {
		if (ourInstance == null) {
			log.info("JalinSuaraSingleton null, initialize it.");
			ourInstance = new JalinSuaraSingleton(context);
		} else {
			if (ourInstance.getContext() == null) {
				ourInstance = new JalinSuaraSingleton(context);
			}
		}
		return ourInstance;
	}

	private Context mContext;

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

	/**
	 * User token for authenticated user
	 */
	private String mToken;

	/**
	 * User email
	 */
	private String mEmailUser;

	private JalinSuaraCache mCache;

	private JalinSuaraSingleton(Context context) {
		log.info("JalinSuaraSingleton()");
		setContext(context);

		// load token
		SharedPreferences pref = context.getSharedPreferences(SESSION,
				Context.MODE_PRIVATE);
		setToken(pref.getString(KEY_TOKEN, null));
		setEmail(pref.getString(KEY_EMAIL, null));
		log.info("Load email: " + getEmail());
		log.info("Load token: " + getToken());

		// init news list
		setNewsList(new ArrayList<News>());

		setSubProjectList(new ArrayList<SubProject>());

		setProvincesList(new ArrayList<Province>());

		mCache = new JalinSuaraCache();

	}

	public synchronized ArrayList<News> getNewsList() {
		return mNewsList;
	}

	public synchronized void setNewsList(ArrayList<News> newsList) {
		mNewsList = newsList;
	}

	public synchronized void setToken(String token) {
		mToken = token;
	}

	public synchronized String getToken() {
		return mToken;
	}

	public synchronized void setEmail(String email) {
		mEmailUser = email;
	}

	public synchronized String getEmail() {
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

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context context) {
		mContext = context;
	}

	public boolean isAuthenticated() {
		return getToken() != null;
	}

	/**
	 * Sign out, clear token in local app
	 */
	public void signOut() {
		setToken(null);
		setEmail(null);
		SharedPreferences pref = mContext.getSharedPreferences(SESSION,
				Context.MODE_PRIVATE);
		pref.edit().clear().commit();
	}

	/**
	 * Sign in to local app
	 * 
	 * @param token
	 * @param email
	 */
	public void signIn(String token, String email) {
		log.info("sign in " + token + ", " + email);
		setToken(token);
		setEmail(email);
		SharedPreferences pref = mContext.getSharedPreferences(SESSION,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(KEY_TOKEN, token);
		editor.putString(KEY_EMAIL, email);
		editor.commit();

	}

	public JalinSuaraCache getCache() {
		return mCache;
	}

}
