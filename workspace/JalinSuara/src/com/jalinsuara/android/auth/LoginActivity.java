package com.jalinsuara.android.auth;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.home.DashboardActivity;
import com.jalinsuara.android.news.CommentAdapter;
import com.jalinsuara.android.news.model.Comment;

public class LoginActivity extends BaseFragmentActivity {

	private Button mLoginButton;
	private TextView mRegisterTextView;
	private EditText mEmailEditText;
	private EditText mPasswordEditText;
	private String tokenLogin;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		

		mLoginButton = (Button) findViewById(R.id.activity_login_login_button);
		mRegisterTextView = (TextView) findViewById(R.id.activity_login_sign_up_textview);
		mEmailEditText = (EditText) findViewById (R.id.activity_login_email_edittext);
		mPasswordEditText = (EditText) findViewById(R.id.activity_login_password_edittext);
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
				
			}
		});
		

		resetStatus();
		setStatusShowContent();
	}
	private class LoadTokens extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			String token = NetworkUtils.getTokenLogin(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
			if(token!=null){
				JalinSuaraSingleton.getInstance().setToken(token);
				tokenLogin = token;
				return E_OK;
			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {
					Intent intent = new Intent(getBaseContext(),
							SignUpActivity.class);				
					startActivity(intent);
					finish();
				} else {
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}
	}
}
