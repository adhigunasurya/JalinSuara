package com.jalinsuara.android.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.jalinsuara.android.BaseListActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.NetworkUtils;
import com.jalinsuara.android.news.NewsAdapter;

public class SearchableActivity extends BaseListActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		log.info("onCreate()");

		// Get the intent, verify the action and get the query
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doSearch(query);
		}
	}

	public void doSearch(String query) {
		// mTextView.setText(getString(R.string.no_results, new Object[]
		// {query}));

		
		// // Display the number of results
		// int count = cursor.getCount();
		// String countString =
		// getResources().getQuantityString(R.plurals.search_results,
		// count, new Object[] {count, query});
		// mTextView.setText(countString);

		resetStatus();
		setStatusProgress(
				getString(R.string.searching_for, new Object[] { query }),
				false);
		
		
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			 onSearchRequested();
			return true;
		default:

			return false;
		}

	}

	
	private class SearchTask extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			JalinSuaraSingleton.getInstance().setNewsList(
					NetworkUtils.getPosts());
			if (getBaseContext() != null) {				
				NetworkUtils.getSearch(params[0]);
//				mAdapter = new NewsAdapter(getBaseContext(),
//						JalinSuaraSingleton.getInstance().get
//						);
			}

			return E_OK;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
//			setListAdapter(mAdapter);
			if (result == E_OK) {

				resetStatus();
				setStatusShowContent();

			} else {

				resetStatus();
				setStatusError(getString(R.string.error));
			}
		}

	}
}
