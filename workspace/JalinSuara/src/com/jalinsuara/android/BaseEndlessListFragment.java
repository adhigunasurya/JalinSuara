package com.jalinsuara.android;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.project.SubProjectAdapter;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Base List Fragment used for endless scroll list entire application
 * 
 * @author tonoman3g
 * 
 */
public abstract class BaseEndlessListFragment extends BaseListFragment {

	protected boolean mAdapterSet = false;

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

	@Override
	public void setListAdapter(ListAdapter adapter) {
		super.setListAdapter(adapter);
		setAdapterSet(adapter != null);
	}

	/**
	 * Endless scroll listener
	 * 
	 * @author tonoman3g
	 * 
	 */
	public class EndlessScrollListener implements OnScrollListener {

		protected int currentPage = 0;
		private int previousTotal = 0;
		private boolean mFirst = true;

		public EndlessScrollListener() {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			log.info("on scroll");

			if (mFirst) {
				log.info("first time : " + totalItemCount);
				previousTotal = totalItemCount;
				mFirst = false;
			}

			if (isLoading()) {
				if (totalItemCount > previousTotal) {
					log.info("increase page");
					setLoading(false);
					previousTotal = totalItemCount;
					currentPage++;
				}
			}
			if (!isLoading()
					&& (firstVisibleItem + visibleItemCount) == totalItemCount) {
				// load the next page
				log.info("load next page " + (currentPage + 1));
				load(currentPage + 1);
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

	}

	public abstract class LoadItemTask<T> extends
			AsyncTask<Object, Integer, Integer> {

		protected final static int E_OK = 1;
		protected final static int E_OK_CHANGED = 3;
		protected final static int E_ERROR = 2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			getSherlockActivity().setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Integer doInBackground(Object... params) {
			loading = true;
			ArrayList<T> retval = loadFromNetwork(params);

			if (getSherlockActivity() != null) {
				if (getAdapter() == null) {
					if (retval != null && retval.size() > 0) {
						getList().clear();
						getList().addAll(retval);

						onFirstLoad();
					} else if (retval == null) {
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
						mLastScrollY = getListView().getFirstVisiblePosition();
						loading = false;
						return E_OK;
					} else {
						loading = false;						
					}
				}
			} else {
				return E_ERROR;
			}
			return E_OK;
		}

		/**
		 * Instantiate mAdapter here
		 */
		public abstract void onFirstLoad();

		public abstract ArrayList<T> loadFromNetwork(Object[] params);

		public abstract ArrayList<T> getList();

		public abstract BaseAdapter getAdapter();

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (getSherlockActivity() != null) {
				getSherlockActivity().setProgressBarIndeterminateVisibility(
						false);

				if (result == E_OK) {
					log.info("result ok");
					if (!isAdapterSet()) {
						setListAdapter(getAdapter());
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
				} else {
					loading = false;
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}

	}

}
