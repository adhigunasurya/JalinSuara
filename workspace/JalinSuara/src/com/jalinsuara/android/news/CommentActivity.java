package com.jalinsuara.android.news;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.news.model.Comment;
import com.jalinsuara.android.news.model.News;

public class CommentActivity extends BaseFragmentActivity {

	public final static String EXTRA_ID = "news_id";
	private ListView mListView;
	private TextView mEmptyTextView;
	private CommentAdapter mAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_comment);

		mListView = (ListView) findViewById(android.R.id.list);
		mEmptyTextView = (TextView) findViewById(android.R.id.empty);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		

		resetStatus();
		setStatusProgress(getString(R.string.loading), false);

		long id = getIntent().getLongExtra(EXTRA_ID, -1);
		if (id != -1) {
			News news = JalinSuaraSingleton.getInstance().findNewsById(id);
			if (news != null) {
				setTitle(news.getTitle());

				LoadComments task = new LoadComments();
				task.execute();

			} else {
				resetStatus();
				setStatusError(getString(R.string.error));
			}
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_comment, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			return true;
		}
		case R.id.action_reply: {
			Intent intent = new Intent(this, ReplyCommentActivity.class);
			startActivity(intent);
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private class LoadComments extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			ArrayList<Comment> comments = new ArrayList<Comment>();
			Comment comment = new Comment();
			comment.setBody("body");
			comment.setCreatedAt(new Date());
			comment.setId(1);
			comment.setGuestName("tono");
			comments.add(comment);
			if (!isFinishing()) {
				mAdapter = new CommentAdapter(getBaseContext(), comments);
			}

			return E_OK;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {
					mListView.setAdapter(mAdapter);
					if (mAdapter.getCount() > 0) {
						mEmptyTextView.setVisibility(View.GONE);
					} else {
						mEmptyTextView.setVisibility(View.VISIBLE);
					}
					resetStatus();
					setStatusShowContent();
				} else {
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}
	}
}
