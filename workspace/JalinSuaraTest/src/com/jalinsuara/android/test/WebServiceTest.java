package com.jalinsuara.android.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import android.util.Log;

import com.jalinsuara.android.helpers.NetworkUtils;
import com.jalinsuara.android.news.model.News;

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
}
