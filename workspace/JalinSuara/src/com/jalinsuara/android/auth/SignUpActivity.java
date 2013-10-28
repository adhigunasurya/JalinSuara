package com.jalinsuara.android.auth;

import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SignUpActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setTitle(R.string.title_register);
		resetStatus();
		setStatusShowContent();
	}

}
