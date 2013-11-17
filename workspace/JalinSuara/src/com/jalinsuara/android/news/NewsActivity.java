package com.jalinsuara.android.news;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.news.model.News;

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
		long id = getIntent().getLongExtra(EXTRA_ID, -1);
		if (id != -1) {
			mNews = JalinSuaraSingleton.getInstance(this).findNewsById(id);
			if (mNews != null) {
				mNewsFragment = new NewsFragment(mNews);
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.activity_news_detail_fragment,
								mNewsFragment).commit();

				setTitle(mNews.getTitle());

				resetStatus();
				setStatusShowContent();
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
