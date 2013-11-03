package com.jalinsuara.android.news;

import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.news.model.News;

public class CommentActivity extends BaseFragmentActivity {

	public final static String EXTRA_ID = "news_id";	

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
			News news = JalinSuaraSingleton.getInstance().findNewsById(id);
			if (news != null) {				

				setTitle(news.getTitle());

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
