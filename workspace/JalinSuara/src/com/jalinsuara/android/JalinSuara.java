package com.jalinsuara.android;

import android.app.Application;

/**
 * 
 * Jalin Suara Application
 * 
 * @author tonoman3g
 * 
 */
public class JalinSuara extends Application {

	// api key for debug key store
	public final static String API_KEY = "AIzaSyC_G9i3dwBrlGD0JVBvfrVZ5rYvpGFVMy0";

	@Override
	public void onCreate() {
		super.onCreate();
		JalinSuaraSingleton.initialize();
	}
}
