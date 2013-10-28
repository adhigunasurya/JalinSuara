package com.jalinsuara.android.auth;

import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.R.id;
import com.jalinsuara.android.R.layout;
import com.jalinsuara.android.home.DashboardActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends BaseFragmentActivity {

	private Button mLoginButton;
	private TextView mRegisterTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		

		mLoginButton = (Button) findViewById(R.id.activity_login_login_button);
		mRegisterTextView = (TextView) findViewById(R.id.activity_login_sign_up_textview);

		mLoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						DashboardActivity.class);
				startActivity(intent);
				finish();

			}
		});

		mRegisterTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						SignUpActivity.class);
				startActivity(intent);
				finish();

			}
		});
		

		resetStatus();
		setStatusShowContent();
	}
}
