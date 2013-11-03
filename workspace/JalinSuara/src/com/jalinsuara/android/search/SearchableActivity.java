package com.jalinsuara.android.search;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.jalinsuara.android.BaseListActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.NetworkUtils;

public class SearchableActivity extends BaseListActivity {

	public SearchResultAdapter mAdapter;

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
		SearchTask task = new SearchTask();
		task.execute(query);

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
			ArrayList<SearchResult> result = NetworkUtils.getSearch(params[0]);
			JalinSuaraSingleton.getInstance().setRecentSearchResultList(result);
			if (getBaseContext() != null) {

				mAdapter = new SearchResultAdapter(getBaseContext(), result);
				if (mAdapter != null) {
					return E_OK;
				} else {
					return E_ERROR;
				}
			}

			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (getBaseContext() != null && !isFinishing()) {
				if (result == E_OK) {
					if (mAdapter != null) {
						setListAdapter(mAdapter);
						resetStatus();
						setStatusShowContent();
					} else {
						resetStatus();
						setStatusError("Null ");
					}
				} else {
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		log.info("onDestroy()");
	}
}
