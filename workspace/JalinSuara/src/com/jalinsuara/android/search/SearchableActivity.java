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
import android.widget.BaseAdapter;
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

		listener = new EndlessScrollListener() {
			@Override
			public void load(int page) {
				SearchTask task = new SearchTask(getBaseContext());
				task.execute(page, getCurrentPage(), mQuery);
			}
		};
		mListView.setOnScrollListener(listener);

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
	private class SearchTask extends LoadItemTask<SearchResult> {

		Context mContext;
		String query;

		public SearchTask(Context context) {
			mContext = context;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (mContext != null && !isFinishing()) {
				int count = mAdapter.getCount();
				if (count > 0) {
					String countString = getResources().getQuantityString(
							R.plurals.search_results, count,
							new Object[] { count, query });
					mStatusTextView.setText(countString);
					mEmptyTextView.setVisibility(View.GONE);

				} else {
					mEmptyTextView.setText(getString(R.string.search_no_result,
							new Object[] { query }));
					mStatusTextView.setVisibility(View.GONE);
				}
				resetStatus();
				setStatusShowContent();
			}
		}

		@Override
		public void onFirstLoad() {
			mAdapter = new SearchResultAdapter(mContext, JalinSuaraSingleton
					.getInstance(mContext).getRecentSearchResultList());
		}

		@Override
		public ArrayList<SearchResult> loadFromNetwork(Object[] params) {
			int page = (Integer) params[1];
			query = (String) params[2];
			ArrayList<SearchResult> result = NetworkUtils
					.getSearch(query, page);

			return result;
		}

		@Override
		public ArrayList<SearchResult> getList() {
			return JalinSuaraSingleton.getInstance(mContext)
					.getRecentSearchResultList();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		log.info("onDestroy()");
	}

}
