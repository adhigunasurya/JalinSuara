package com.jalinsuara.android.auth;

import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.R.layout;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.home.DashboardActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends BaseFragmentActivity {
	
	private EditText mNewEmail;
	
	private EditText mCompleteName;
	
	private EditText mNewPassword;
	
	private Button mSignUp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setTitle(R.string.title_register);
		resetStatus();
		setStatusShowContent();
		mNewEmail = (EditText)findViewById(R.id.editNewEmail);
		mCompleteName = (EditText)findViewById(R.id.editTextCompleteName);
		mNewPassword = (EditText)findViewById(R.id.editNewPassword);
		mSignUp = (Button) findViewById(R.id.buttonSignUp);
		mSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mNewEmail!=null && mCompleteName != null && mNewPassword!=null){
					LoadRegister register = new LoadRegister();
					register.execute();
				}else {
					Toast.makeText(getBaseContext(),"complete your biodata",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private class LoadRegister extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			String username = NetworkUtils.registerNewUser(mCompleteName.getText()
					.toString(), mNewEmail.getText().toString(),mNewPassword.getText().toString());
			if (username != null) {
				LoadTokens token = new LoadTokens();
				token.execute();
			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {
					finish();
				} else {
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}
	}
	
	private class LoadTokens extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
			resetStatus();
			setStatusProgress(getString(R.string.loading), false);
		}

		@Override
		protected Integer doInBackground(String... params) {
			String token = NetworkUtils.signIn(mNewEmail.getText().toString(), mNewPassword.getText().toString());
			if (token != null) {

				return E_OK;
			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {
					resetStatus();
					setStatusShowContent();


					finish();
					Intent intent = new Intent(getBaseContext(),
							DashboardActivity.class);
					startActivity(intent);
				} else {
					resetStatus();
					setStatusShowContent();
					Toast.makeText(getBaseContext(),
							R.string.error_login_failed, Toast.LENGTH_SHORT)
							.show();

				}
			}
		}
	}

}
