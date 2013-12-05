package com.jalinsuara.android.search;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.jalinsuara.android.BaseEndlessListFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.NewsActivity;

/**
 * Searchable and search result activity
 * 
 * @author tonoman3g
 * 
 */
public class SearchableActivity extends BaseEndlessListFragmentActivity {

	private SearchResultAdapter mAdapter;
	private String mQuery;
	private ListView mListView;
	private TextView mEmptyTextView;
	private TextView mStatusTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);
		mListView = (ListView) findViewById(android.R.id.list);
		mStatusTextView = (TextView) findViewById(R.id.activity_search_result_status_textview);
		mEmptyTextView = (TextView) findViewById(android.R.id.empty);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		log.info("onCreate()");

		// Get the intent, verify the action and get the query
		handleIntent(getIntent());

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SearchResult item = (SearchResult) mAdapter.getItem(arg2);
				if (item.isNews()) {
					Intent intent = new Intent(getBaseContext(),
							NewsActivity.class);
					intent.putExtra(NewsActivity.EXTRA_ID, item.getId());
					startActivity(intent);
				}
			}
		});

		mListView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void load(int page) {
				SearchTask task = new SearchTask(getBaseContext());
				task.execute(mQuery, String.valueOf(page));
			}
		});
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

		resetStatus();
		setStatusProgress(
				getString(R.string.searching_for, new Object[] { query }),
				false);
		mQuery = query;
		SearchTask task = new SearchTask(this);
		task.execute(query, String.valueOf(1));

	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			onSearchRequested();
			return true;
		case android.R.id.home:
			finish();
			return true;
		default:

			return false;
		}

	}

	/**
	 * Call Search API
	 * 
	 * @author tonoman3g
	 * 
	 */
	private class SearchTask extends AsyncTask<String, Integer, Integer> {

		Context mContext;
		String query;

		public SearchTask(Context context) {
			mContext = context;
		}

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			query = params[0];
			ArrayList<SearchResult> result = NetworkUtils.getSearch(params[0],
					Integer.parseInt(params[1]));
			JalinSuaraSingleton.getInstance(mContext)
					.setRecentSearchResultList(result);
			if (mContext != null) {

				mAdapter = new SearchResultAdapter(mContext, result);
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
			if (mContext != null && !isFinishing()) {
				if (result == E_OK) {
					if (mAdapter != null) {
						mListView.setAdapter(mAdapter);
					}
					int count = mAdapter.getCount();
					if (count > 0) {
						String countString = getResources().getQuantityString(
								R.plurals.search_results, count,
								new Object[] { count, query });
						mStatusTextView.setText(countString);
						mEmptyTextView.setVisibility(View.GONE);

					} else {
						mEmptyTextView.setText(getString(
								R.string.search_no_result,
								new Object[] { query }));
						mStatusTextView.setVisibility(View.GONE);
					}
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
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		log.info("onDestroy()");
	}

}
