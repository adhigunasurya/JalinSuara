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
import com.jalinsuara.android.helper.NetworkUtils;

public class ReplyCommentActivity extends BaseFragmentActivity {
	public static final String EXTRA_NEWS_ID = "news_id";
	public static final int ACTIVITY_REQUEST = 0;

	public static final int ACTIVITY_COMPLETE = 1;
	private EditText mCommentEditText;
	private EditText mNameEditText;
	private EditText mEmailEditText;
	private long mNewsId;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_reply_comment);

		mNewsId = getIntent().getLongExtra(EXTRA_NEWS_ID, -1);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(getString(R.string.reply));
		mCommentEditText = (EditText) findViewById(R.id.activity_reply_comment_edittext);
		mNameEditText = (EditText) findViewById(R.id.activity_reply_comment_name_edittext);
		mEmailEditText = (EditText) findViewById(R.id.activity_reply_comment_email_edittext);

		if (JalinSuaraSingleton.getInstance(this).isAuthenticated()) {
			mNameEditText.setVisibility(View.GONE);
			mEmailEditText.setVisibility(View.GONE);
		}
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
			boolean valid = true;

			if (JalinSuaraSingleton.getInstance(getBaseContext())
					.isAuthenticated()) {
				if (mCommentEditText.getText().toString().length() > 0) {
					valid = true;
				} else {
					valid = false;
				}
			} else {
				if (mCommentEditText.getText().toString().length() > 0
						&& mNameEditText.getText().toString().length() > 0
						&& mEmailEditText.getText().toString().length() > 0) {
					valid = true;
				} else {
					valid = false;
				}
			}
			if (valid) {
				// send data
				String name = mNameEditText.getText().toString();
				String email = mEmailEditText.getText().toString();
				String comment = mCommentEditText.getText().toString();

				if (JalinSuaraSingleton.getInstance(this).isAuthenticated()) {
					email = JalinSuaraSingleton.getInstance(this).getEmail();
					PostCommentTask commentTask = new PostCommentTask();
					commentTask.execute(String.valueOf(mNewsId), email, email,
							comment);
				} else {
					PostCommentTask commentTask = new PostCommentTask();
					commentTask.execute(String.valueOf(mNewsId), name, email,
							comment);
				}
			} else {
				Toast.makeText(getBaseContext(),
						getString(R.string.error_fill_all_fields),
						Toast.LENGTH_SHORT).show();
			}

			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Post comment task
	 * 
	 * @author hartono
	 * @author gabriellewp
	 * 
	 */
	private class PostCommentTask extends AsyncTask<String, Integer, Integer> {

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
			String postId = params[0];
			String name = params[1];
			String email = params[2];
			String comment = params[3];
			if (params.length == 4) {
				String token = NetworkUtils.postNewComment(Long
						.parseLong(postId), name, email, comment,
						JalinSuaraSingleton.getInstance(getBaseContext())
								.getToken(),
						JalinSuaraSingleton.getInstance(getBaseContext())
								.isAuthenticated());
				if (token != null) {
					return E_OK;
				}
			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {

					setResult(ACTIVITY_COMPLETE);
					finish();
				} else {
					// resetStatus();
					// setStatusShowContent();
					Toast.makeText(getBaseContext(), R.string.error,
							Toast.LENGTH_SHORT).show();
					//
					// }
				}
			}
		}
	}
}
