package com.jalinsuara.android.news;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.model.News;

/**
 * Show news
 * 
 * @author tonoman3g
 * 
 */
public class NewsActivity extends BaseFragmentActivity {

	public final static String EXTRA_ID = "news_id";
	private NewsFragment mNewsFragment;
	private News mNews;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_news);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// getSupportActionBar().setDisplayShowHomeEnabled(true);

		resetStatus();
		setStatusProgress(getString(R.string.loading), false);

		long id = -1;

		if (getIntent().getData() != null) {
			Uri data = getIntent().getData();
			String scheme = data.getScheme(); // "jalinsuara"
			String host = data.getHost(); // "posts"
			List<String> params = data.getPathSegments();
			String idString = params.get(0); // "id"
			id = Long.parseLong(idString);
			log.info("scheme:" + scheme + ", host:" + host + ", params:"
					+ params);
		} else {
			id = getIntent().getLongExtra(EXTRA_ID, -1);
		}
		if (id != -1) {
			mNews = JalinSuaraSingleton.getInstance(this).findNewsById(id);
			if (mNews != null) {
				
			} else {
				AsyncTask<Long, Integer, Integer> task = new AsyncTask<Long, Integer, Integer>() {

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						resetStatus();
						setStatusProgress(getString(R.string.loading), false);
					}

					@Override
					protected Integer doInBackground(Long... params) {
						mNews = NetworkUtils.getPosts(params[0]);
						return null;
					}

					@Override
					protected void onPostExecute(Integer result) {
						if (mNews != null) {
							mNewsFragment = new NewsFragment(mNews);
							getSupportFragmentManager()
									.beginTransaction()
									.replace(
											R.id.activity_news_detail_fragment,
											mNewsFragment).commit();

							setTitle(mNews.getTitle());

							resetStatus();
							setStatusShowContent();
						} else {
							resetStatus();
							setStatusError(getString(R.string.error));
						}
					}

				};
				task.execute(id);
			}

			if (mNews != null) {
				mNewsFragment = new NewsFragment(mNews);
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.activity_news_detail_fragment,
								mNewsFragment).commit();

				setTitle(mNews.getTitle());

				resetStatus();
				setStatusShowContent();
			}
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_news, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			return true;
		}
		case R.id.action_comment: {
			Intent intent = new Intent(this, CommentActivity.class);
			intent.putExtra(CommentActivity.EXTRA_ID, mNews.getId());
			startActivity(intent);
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

}
