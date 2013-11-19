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

	public String tokenLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setTitle(R.string.title_register);
		resetStatus();
		setStatusShowContent();
		mNewEmail = (EditText) findViewById(R.id.editNewEmail);
		mCompleteName = (EditText) findViewById(R.id.editTextCompleteName);
		mNewPassword = (EditText) findViewById(R.id.editNewPassword);
		mSignUp = (Button) findViewById(R.id.buttonSignUp);
		mSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mNewEmail != null && mCompleteName != null
						&& mNewPassword != null) {
					String email = mNewEmail.getText().toString();
					String name = mCompleteName.getText().toString();
					String password = mNewPassword.getText().toString();
					if (email.length() > 0 && !email.contains(" ")
							&& name.length() > 0 && !name.contains(" ")
							&& password.length() > 0) {
						LoadRegister register = new LoadRegister();
						register.execute();

						resetStatus();
						setStatusProgress(getString(R.string.loading), false);
					} else {
						Toast.makeText(getBaseContext(), "Invalid fields",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getBaseContext(), "Complete all fields",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * Register task to server. automatically load token after registration is
	 * success
	 * 
	 * @author tonoman3g
	 * @author gabriellewp
	 * 
	 */
	private class LoadRegister extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			log.info("register new user");
			String username = NetworkUtils.registerNewUser(mCompleteName
					.getText().toString(), mNewEmail.getText().toString(),
					mNewPassword.getText().toString());
			if (username != null) {
				JalinSuaraSingleton singleton = JalinSuaraSingleton
						.getInstance(getBaseContext());
				if (singleton != null) {
				}
				log.info("success: " + username + " is registered");
				return E_OK;

			}
			log.info("registration failed");
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {
					LoadTokens token = new LoadTokens();
					token.execute();
					log.info("User is signing in..");
				} else {
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}
	}

	/**
	 * Login / load token after sign up is success
	 * 
	 * @author tonoman3g
	 * @author gabriellewp
	 * 
	 */
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
			String token = NetworkUtils.signIn(mNewEmail.getText().toString(),
					mNewPassword.getText().toString());
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

					JalinSuaraSingleton singleton = JalinSuaraSingleton
							.getInstance(getBaseContext());
					if (singleton != null) {
						singleton.signIn(tokenLogin, mNewEmail.getText()
								.toString());
					}

					Intent intent = new Intent(getBaseContext(),
							DashboardActivity.class);
					startActivity(intent);

					finish();
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
