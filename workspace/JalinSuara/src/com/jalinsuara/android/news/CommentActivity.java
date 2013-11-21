package com.jalinsuara.android.news;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.BaseEndlessListFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.model.Comment;
import com.jalinsuara.android.news.model.News;

/**
 * Show comments of a news
 * 
 * @author tonoman3g
 * 
 */
public class CommentActivity extends BaseEndlessListFragmentActivity {

	public final static String EXTRA_ID = "news_id";

	private ListView mListView;

	private ArrayList<Comment> mList;

	private News mNews;

	private CommentAdapter mAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_comment);

		mListView = (ListView) findViewById(android.R.id.list);
		mEmptyTextView = (TextView) findViewById(android.R.id.empty);

		mList = new ArrayList<Comment>();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		resetStatus();
		setStatusProgress(getString(R.string.loading), false);

		long id = getIntent().getLongExtra(EXTRA_ID, -1);
		if (id != -1) {
			mNews = JalinSuaraSingleton.getInstance(this).findNewsById(id);
			if (mNews != null) {
				setTitle(mNews.getTitle());
			} else {
				resetStatus();
				setStatusError(getString(R.string.error));
			}
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}

		mListView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void load(int page) {
				LoadComments task = new LoadComments();
				task.execute(page);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0==ReplyCommentActivity.ACTIVITY_REQUEST && arg1==ReplyCommentActivity.ACTIVITY_COMPLETE){
			mList.clear();		
			mAdapter=null;
			mListView.setOnScrollListener(new EndlessScrollListener() {
				@Override
				public void load(int page) {
					LoadComments task = new LoadComments();
					task.execute(page);
				}
			});
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
			intent.putExtra(ReplyCommentActivity.EXTRA_NEWS_ID, mNews.getId());
			startActivityForResult(intent,ReplyCommentActivity.ACTIVITY_REQUEST);
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Load comment from server for a certain post
	 * 
	 * @author tonoman3g
	 * 
	 */
	public class LoadComments extends LoadItemTask<Comment> {

		@Override
		public void onFirstLoad() {
			mAdapter = new CommentAdapter(getBaseContext(), mList);
		}

		@Override
		public ArrayList<Comment> loadFromNetwork(Object[] params) {
			ArrayList<Comment> retval = NetworkUtils.getComment(mNews.getId(),
					(Integer) params[0]);
			return retval;
		}

		@Override
		public ArrayList<Comment> getList() {
			return mList;
		}

		@Override
		public BaseAdapter getAdapter() {
			return mAdapter;
		}

		@Override
		public ListView getListView() {
			return mListView;
		}

	}

	/*
	 * private class LoadComments extends AsyncTask<Integer, Integer, Integer> {
	 * 
	 * private final static int E_OK = 1; private final static int E_ERROR = 2;
	 * 
	 * @Override protected Integer doInBackground(Integer... params) {
	 * ArrayList<Comment> comments = NetworkUtils.getComment( mNews.getId(),
	 * params[0]); if (!isFinishing()) { if (comments != null) { mAdapter = new
	 * CommentAdapter(getBaseContext(), comments); } else { return E_ERROR; }
	 * 
	 * } return E_OK; }
	 * 
	 * @Override protected void onPostExecute(Integer result) {
	 * super.onPostExecute(result); if (!isFinishing()) { if (result == E_OK) {
	 * mListView.setAdapter(mAdapter); if (mAdapter.getCount() > 0) {
	 * mEmptyTextView.setVisibility(View.GONE); } else {
	 * mEmptyTextView.setVisibility(View.VISIBLE); } resetStatus();
	 * setStatusShowContent(); } else { resetStatus();
	 * setStatusError(getString(R.string.error)); } } } }
	 */
}
