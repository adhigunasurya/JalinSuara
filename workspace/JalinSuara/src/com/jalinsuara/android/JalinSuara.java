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

	@Override
	public void onCreate() {
		super.onCreate();
		JalinSuaraSingleton.initialize();
	}
}
