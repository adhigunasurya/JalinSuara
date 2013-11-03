package com.jalinsuara.android.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import android.util.Log;

import com.jalinsuara.android.helpers.NetworkUtils;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.SubProject;
import com.jalinsuara.android.search.SearchResult;

public class WebServiceTest extends TestCase {
	
	private static String TAG = WebServiceTest.class.getSimpleName();

	public void testGetPosts() {
		ArrayList<News> list = NetworkUtils.getPosts();
		assertTrue(list != null);
		
		if (list!=null){
			for (News news : list){
				Log.i(TAG, news.toString());	
			}
			
		}
	}
	public void testGetSubProject(){
		ArrayList<SubProject> list = NetworkUtils.getSubProject();
		assertTrue(list != null);
		
		if (list!=null){
			for (SubProject subproject : list){
				Log.i("---------", subproject.toString());	
			}
			
		}
		
	}
	
	public void testGetSearch(){
		ArrayList<SearchResult> list = NetworkUtils.getSearch("air");
		assertTrue(list != null);
		
		if (list!=null){
			for (SearchResult item: list){
				Log.i(TAG, item.toString());	
			}
			
		}
	}
}
