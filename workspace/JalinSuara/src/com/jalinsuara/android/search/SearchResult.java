package com.jalinsuara.android.search;

import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.SubProject;

public class SearchResult {

	/**
	 * Is news, if true it is a news, if false it is an activity
	 */
	private boolean mIsNews;

	private News mNews;

	private SubProject mProjects;

	public boolean isNews() {
		return mIsNews;
	}

	public void setNews(boolean isNews) {
		mIsNews = isNews;
	}

	public SubProject getProjects() {
		return mProjects;
	}

	public void setProjects(SubProject projects) {
		mProjects = projects;
	}

	public News getNews() {
		return mNews;
	}

	public void setNews(News news) {
		mNews = news;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchResult [mIsNews=" + mIsNews + ", mNews=" + mNews
				+ ", mProjects=" + mProjects + "]";
	}
	
	

}
