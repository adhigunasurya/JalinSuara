package com.jalinsuara.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * Base Activity used in the entire application
 * 
 * @author tonoman3g
 * 
 */
public abstract class BaseEndlessListFragmentActivity extends
		BaseFragmentActivity {

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

		protected int currentPage = 1;
		private int previousTotal = 0;

		public EndlessScrollListener() {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (isLoading()) {
				if (totalItemCount > previousTotal) {
					setLoading(false);
					previousTotal = totalItemCount;
					currentPage++;
				}
			}
			if (!isLoading()
					&& (firstVisibleItem + visibleItemCount) == totalItemCount) {
				// load the next page
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
}
