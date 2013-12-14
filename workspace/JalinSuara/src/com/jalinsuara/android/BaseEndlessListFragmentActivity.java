package com.jalinsuara.android;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseEndlessListFragment.EndlessScrollListener;

/**
 * Base Activity used in the entire application
 * 
 * @author tonoman3g
 * 
 */
public abstract class BaseEndlessListFragmentActivity extends
		BaseFragmentActivity {

	public static final String CURRENT_PAGE = "current_page";

	/**
	 * endless scroll listener
	 */
	protected EndlessScrollListener listener;

	protected TextView mEmptyTextView;
	protected boolean mAdapterSet = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

	}

	/**
	 * @return the adapterSet
	 */
	public boolean isAdapterSet() {
		return mAdapterSet;
	}

	/**
	 * @param adapterSet
	 *            the adapterSet to set
	 */
	public void setAdapterSet(boolean adapterSet) {
		mAdapterSet = adapterSet;
	}

	/**
	 * Last scroll y
	 */
	protected int mLastScrollY;

	/**
	 * Indicate for Loading data from server
	 */
	protected boolean loading;

	/**
	 * @return the loading
	 */
	public synchronized boolean isLoading() {
		return loading;
	}

	/**
	 * @param loading
	 *            the loading to set
	 */
	public synchronized void setLoading(boolean loading) {
		this.loading = loading;
	}

	/**
	 * Endless scroll listener
	 * 
	 * @author tonoman3g
	 * 
	 */
	public class EndlessScrollListener implements OnScrollListener {

		private Logger log = LoggerFactory.getLogger(this.getClass()
				.getSimpleName());

		private int currentPage = 0;

		private int previousTotal = 0;

		private boolean mFirst = true;

		public EndlessScrollListener() {
			log.info("EndlessScrollListener()");
			this.setCurrentPage(0);
		}

		public EndlessScrollListener(int currentPage) {
			log.info("EndlessScrollListener() " + currentPage);
			this.setCurrentPage(currentPage);
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			if (mFirst) {
				log.info("first time : " + totalItemCount);
				previousTotal = totalItemCount;
				mFirst = false;
			}

			if (isLoading()) {
				if (totalItemCount > previousTotal) {
					log.info("increase page from " + getCurrentPage() + " to "
							+ (getCurrentPage() + 1));
					setLoading(false);
					previousTotal = totalItemCount;
					setCurrentPage(getCurrentPage() + 1);
				}
			}
			if (!isLoading()
					&& (firstVisibleItem + visibleItemCount) == totalItemCount) {
				// load the next page
				log.info("load next page " + (getCurrentPage() + 1));
				load(getCurrentPage() + 1);
				setLoading(loading);
			}
		}

		/**
		 * Override this to load page
		 * 
		 * @param page
		 */
		public void load(int page) {

		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}

	}

	/**
	 * Load item task abstract class,used for load data from server
	 * 
	 * @author tonoman3g
	 * 
	 * @param <T>
	 *            1st element is page to load , 2nd element is current page
	 */
	public abstract class LoadItemTask<T> extends
			AsyncTask<Object, Integer, Integer> {

		protected final static int E_OK = 1;
		protected final static int E_OK_CHANGED = 3;
		protected final static int E_OK_NO_CHANGES = 4;
		protected final static int E_ERROR = 2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Integer doInBackground(Object... params) {
			if (params.length > 1) {
				int pageToLoad = (Integer) params[0];
				int currPage = (Integer) params[1];
				if (currPage >= pageToLoad) {
					log.info("no changes");
					return E_OK_NO_CHANGES;
				}
			}
			loading = true;
			ArrayList<T> retval = loadFromNetwork(params);

			if (!isFinishing()) {
				if (getAdapter() == null) {
					onFirstLoad();
					if (retval != null && retval.size() > 0) {
						getList().clear();
						getList().addAll(retval);
						log.info("load " + retval.size() + " data");
					} else if (retval == null) {
						log.error("no response from server");
						return E_ERROR;
					}
				} else {
					if (retval != null && retval.size() > 0) {

						mLastScrollY = getListView().getFirstVisiblePosition();
						log.info("add new " + retval.size()
								+ " items, at scroll Y " + mLastScrollY);
						getList().addAll(retval);
						return E_OK_CHANGED;
					} else if (retval != null && retval.size() == 0) {
						loading = false;
						return E_OK_NO_CHANGES;
					} else {
						loading = false;
					}
				}
			} else {
				log.error("activity null");
				return E_ERROR;
			}
			return E_OK;
		}

		/**
		 * Instantiate mAdapter here
		 */
		public abstract void onFirstLoad();

		/**
		 * @param params
		 *            , params[0] = page to load, params[1] = current page
		 * @return
		 */
		public abstract ArrayList<T> loadFromNetwork(Object[] params);

		public abstract ArrayList<T> getList();

		public abstract BaseAdapter getAdapter();

		public abstract ListView getListView();

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				setProgressBarIndeterminateVisibility(false);

				if (result == E_OK) {
					log.info("result ok");
					if (!isAdapterSet()) {
						setAdapterSet(true);
						getListView().setAdapter(getAdapter());
						if (getAdapter().getCount() > 0) {
							if (mEmptyTextView != null) {
								mEmptyTextView.setVisibility(View.GONE);
							}
						}
					} else {
						getListView().setSelectionFromTop(mLastScrollY, 0);
					}
					resetStatus();
					setStatusShowContent();

				} else if (result == E_OK_CHANGED) {
					log.info("data changed");
					getAdapter().notifyDataSetChanged();
					log.info("set selection from top " + mLastScrollY);
					getListView().setSelectionFromTop(mLastScrollY, 0);
				} else if (result == E_OK_NO_CHANGES) {

				} else {
					log.error("there is error");
					loading = false;
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (listener != null) {
			outState.putInt(CURRENT_PAGE, listener.getCurrentPage());
		} else {
			outState.putInt(CURRENT_PAGE, 0);
		}
	}
}
