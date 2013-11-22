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
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.home.DashboardActivity;
import com.jalinsuara.android.news.CommentAdapter;
import com.jalinsuara.android.news.model.Comment;

/**
 * Login to server
 * 
 * @author tonoman3g
 * @author gabriellewp
 */
public class LoginActivity extends BaseFragmentActivity {

	private Button mLoginButton;

	private TextView mRegisterTextView;

	private EditText mEmailEditText;

	private EditText mPasswordEditText;

	private String tokenLogin;
	
	private TextView mRegisterButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mLoginButton = (Button) findViewById(R.id.activity_login_login_button);
		mRegisterTextView = (TextView) findViewById(R.id.activity_login_sign_up_textview);
		mEmailEditText = (EditText) findViewById(R.id.activity_login_email_edittext);
		mPasswordEditText = (EditText) findViewById(R.id.activity_login_password_edittext);
		mRegisterButton =(TextView)findViewById(R.id.activity_login_sign_up_textview);
		mRegisterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
	            startActivity(intent);
			}
		});
		
		mLoginButton.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {

				if (mEmailEditText.getText().toString().length() > 0
						&& mPasswordEditText.getText().toString().length() > 0) {
					LoadTokens token = new LoadTokens();
					token.execute();
				} else {
					Toast.makeText(getBaseContext(),
							getString(R.string.error_fill_all_fields),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		mRegisterTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						SignUpActivity.class);
				startActivity(intent);
			}
		});

		resetStatus();
		setStatusShowContent();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Sign in request
	 * 
	 * @author tonoman3g
	 * @author gabriellewp
	 * 
	 */
	public class LoadTokens extends AsyncTask<String, Integer, Integer> {

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
			String token = NetworkUtils.signIn(mEmailEditText.getText()
					.toString(), mPasswordEditText.getText().toString());
			if (token != null) {
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
					resetStatus();
					setStatusShowContent();

					log.info("Signed in: "+tokenLogin, mEmailEditText.getText().toString());
					JalinSuaraSingleton.getInstance(getBaseContext()).signIn(
							tokenLogin, mEmailEditText.getText().toString());

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
