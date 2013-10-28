package com.jalinsuara.android.news;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.news.NewsListFragment.OnNewsItemClickListener;
import com.jalinsuara.android.news.model.News;

public class NewsListActivity extends BaseFragmentActivity implements
		OnNewsItemClickListener {

	private boolean mMultiPane;
	private NewsListFragment mListFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_news_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		resetStatus();
		setStatusProgress(getResources().getString(R.string.loading), false);

		mListFragment = new NewsListFragment(this);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.activity_news_list_list_fragment, mListFragment)
				.commit();

		if (findViewById(R.id.activity_news_detail_fragment) == null) {
			setMultiPane(false);

		} else {
			setMultiPane(true);
			// getSupportFragmentManager().beginTransaction().replace(
			// R.id.activity_news_list_l, mListFragment).commit();
		}
		resetStatus();
		setStatusShowContent();
	}

	public boolean isMultiPane() {
		return mMultiPane;
	}

	public void setMultiPane(boolean multiPane) {
		mMultiPane = multiPane;
	}

	@Override
	public void onNewsItemClickListener(News news, int position) {
		if (mMultiPane) {

		} else {
			Intent intent = new Intent(this, NewsActivity.class);
			intent.putExtra(NewsActivity.EXTRA_ID, news.getId());
			startActivity(intent);
		}

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
}
