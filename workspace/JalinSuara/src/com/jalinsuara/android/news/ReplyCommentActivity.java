package com.jalinsuara.android.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.auth.LoginActivity.LoadTokens;
import com.jalinsuara.android.helper.NetworkUtils;

public class ReplyCommentActivity extends BaseFragmentActivity {
	private EditText mCommentEditText;
	private EditText mNameEditText;
	private EditText mEmailEditText;
	private Button mSubmitButton;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_reply_comment);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(getString(R.string.reply));
		mCommentEditText = (EditText) findViewById(R.id.activity_reply_comment_edittext);
		mNameEditText = (EditText) findViewById(R.id.activity_reply_comment_name_edittext);
		mEmailEditText = (EditText) findViewById(R.id.activity_reply_comment_email_edittext);
		mSubmitButton = (Button) findViewById(R.id.activity_reply_comment_submit_button);
		mSubmitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCommentEditText!=null) {
					LoadComment comment = new LoadComment();
					comment.execute();
				} else {
					Toast.makeText(getBaseContext(),
							getString(R.string.error_fill_all_fields),
							Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		resetStatus();
		setStatusShowContent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_reply_comment, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			return true;
		}
		case R.id.action_submit: {
			// send data
			
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class LoadComment extends AsyncTask<String, Integer, Integer> {

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
			String token = NetworkUtils.postNewComment(mNameEditText.getText().toString(), mEmailEditText.getText().toString(), mCommentEditText.getText().toString(),JalinSuaraSingleton.getInstance(getBaseContext()).getToken(), JalinSuaraSingleton.getInstance(getBaseContext()).isAuthenticated());
//			if (token != null) {
//				tokenLogin = token;
//				return E_OK;
//			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
//			if (!isFinishing()) {
//				if (result == E_OK) {
//					resetStatus();
//					setStatusShowContent();
//
//					JalinSuaraSingleton.getInstance(getBaseContext()).signIn(
//							tokenLogin, mEmailEditText.getText().toString());
//
//					finish();
//				} else {
//					resetStatus();
//					setStatusShowContent();
//					Toast.makeText(getBaseContext(),
//							R.string.error_login_failed, Toast.LENGTH_SHORT)
//							.show();
//
//				}
//			}
		}
	}
}
