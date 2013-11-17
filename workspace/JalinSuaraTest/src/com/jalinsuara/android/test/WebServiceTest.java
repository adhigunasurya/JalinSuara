package com.jalinsuara.android.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import android.util.Log;

import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.model.Comment;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubDistrict;
import com.jalinsuara.android.projects.model.SubProject;
import com.jalinsuara.android.search.SearchResult;

public class WebServiceTest extends TestCase {

	private static String TAG = WebServiceTest.class.getSimpleName();

	public void testGetPosts() {
		ArrayList<News> list = NetworkUtils.getPosts(1);
		assertTrue(list != null);
		if (list != null) {
			assertTrue(list.size() > 0);
		}

		if (list != null) {
			for (News news : list) {
				Log.i(TAG, news.toString());
			}

		}
	}

	public void testGetSubProject() {
		ArrayList<SubProject> list = NetworkUtils.getSubProjects(1);
		assertTrue(list != null);
		if (list != null) {
			assertTrue(list.size() > 0);
		}

		if (list != null) {
			for (SubProject subproject : list) {
				Log.i("---------", subproject.toString());
			}

		}

	}

	public void testGetSearch() {
		ArrayList<SearchResult> list = NetworkUtils.getSearch("air", 1);
		assertTrue(list != null);
		if (list != null) {
			assertTrue(list.size() > 0);
		}

		if (list != null) {
			for (SearchResult item : list) {
				Log.i(TAG, item.toString());
			}

		}
	}

	public void testGetComment() {
		ArrayList<Comment> list = NetworkUtils.getComment(15, 1);
		assertTrue(list != null);
		if (list != null) {
			assertTrue(list.size() > 0);
		}

		if (list != null) {
			for (Comment comment : list) {
				Log.i(TAG, comment.toString());
			}
		}
	}

	public void testGetProvince() {
		ArrayList<Province> list = NetworkUtils.getProvinces();
		assertTrue(list != null);
		if (list != null) {
			assertTrue(list.size() > 0);
		}

		if (list != null) {
			for (Province item : list) {
				Log.i(TAG, item.toString());
			}
		}
	}

	public void testGetDistrict() {
		ArrayList<District> list = NetworkUtils.getDistricts(1);
		assertTrue(list != null);
		if (list != null) {
			assertTrue(list.size() > 0);
		}

		if (list != null) {
			for (District item : list) {
				Log.i(TAG, item.toString());
			}
		}
	}

	public void testGetSubdistrict() {
		ArrayList<SubDistrict> list = NetworkUtils.getSubdistricts(1);
		assertTrue(list != null);
		if (list != null) {
			assertTrue(list.size() > 0);
		}

		if (list != null) {
			for (SubDistrict item : list) {
				Log.i(TAG, item.toString());
			}
		}
	}
}
