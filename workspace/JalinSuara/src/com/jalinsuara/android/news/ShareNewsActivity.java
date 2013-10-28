package com.jalinsuara.android.news;

import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ShareNewsActivity extends BaseFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_news);
		resetStatus();
		setStatusShowContent();
	}

}
