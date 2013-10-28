package com.jalinsuara.android;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jalinsuara.android.news.model.News;

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

	private Gson mGson;

	private ArrayList<News> mNewsList;

	private JalinSuaraSingleton() {
		log.info("JalinSuaraSingleton()");

		// init gson
		GsonBuilder builder = new GsonBuilder();
		builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		setGson(builder.create());

		// init news list
		setNewsList(new ArrayList<News>());
	}

	public Gson getGson() {
		return mGson;
	}

	public void setGson(Gson gson) {
		mGson = gson;
	}

	public ArrayList<News> getNewsList() {
		return mNewsList;
	}

	public void setNewsList(ArrayList<News> newsList) {
		mNewsList = newsList;
	}

	public News findNewsById(long id) {
		if (mNewsList == null) {
			return null;
		}
		for (News news : mNewsList) {
			if (news.getId() == id) {
				return news;
			}
		}
		return null;
	}

}
