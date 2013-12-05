package com.jalinsuara.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;

/**
 * Base Fragment used in entire application
 * 
 * @author tonoman3g
 * 
 */
public abstract class BaseListFragment extends SherlockListFragment {

	public Logger log = LoggerFactory
			.getLogger(this.getClass().getSimpleName());

	/*
	 * initial -> error / progressing / content loaded
	 */
	public final static int STATUS_STATE_INITIAL = -2;
	public final static int STATUS_STATE_ERROR = -1;
	public final static int STATUS_STATE_PROGRESSING = 0;
	public final static int STATUS_STATE_CONTENT_LOADED = 1;

	private boolean mFragmentPaused;

	private int mStatusState;

	/**
	 * Text View for Progress's Percentage
	 */
	protected TextView mProgressPercentageView;

	/**
	 * Last time when app fetched data from server
	 */
	private long mLastUpdatedDate = -1;

	/**
	 * Maximum value for progress percentage, if we don't use indeterminate
	 * progress bar
	 */
	protected int mMaxProgressPercentage = 100;

	/**
	 * Progress for determinate progress bar
	 */
	protected int mProgress = 0;	

	public abstract int getLayoutId();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log.info("onCreate() , savedInstanceState:" + savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info("onCreateView()");
		FrameLayout layout = (FrameLayout) inflater.inflate(
				R.layout.layout_template_fragment_progress_error_1, container,
				false);

		View view = inflater.inflate(getLayoutId(), null);
		FrameLayout contentLayout = (FrameLayout) layout
				.findViewById(R.id.template_fragment_content);
		contentLayout.addView(view);
		return layout;
	}

	public boolean isFragmentPaused() {
		return mFragmentPaused;
	}

	public void setFragmentPaused(boolean fragmentPaused) {
		mFragmentPaused = fragmentPaused;
	}

	@Override
	public void onPause() {
		super.onPause();
		setFragmentPaused(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		setFragmentPaused(false);
	}

	/**
	 * Set only progress message
	 * 
	 * @param message
	 */
	public void publishProgressMessage(String message) {
		if (getStatusState() == STATUS_STATE_PROGRESSING) {
			LinearLayout layout = (LinearLayout) getView().findViewById(
					R.id.template_fragment_status);
			if (layout != null) {
				TextView messageView = (TextView) getView().findViewById(
						R.id.template_fragment_status_message);
				if (message != null) {
					messageView.setText(message);
				}

			}
		}
	}

	/**
	 * Reset all error, progress, and content to default. Content is visible,
	 * the others visibilities are gone.
	 * <p>
	 * Reset status to initial.
	 */
	public void resetStatus() {
		// always checking for null pointer possibility

		// reset error template
		if (getView() != null) {
			log.info("resetStatus()true");
			LinearLayout layout = (LinearLayout) getView().findViewById(
					R.id.template_fragment_error);
			if (layout != null) {
				layout.setVisibility(View.GONE);
			}

			// reset status / progress template
			LinearLayout otherLayout = (LinearLayout) getView().findViewById(
					R.id.template_fragment_status);
			if (otherLayout != null) {
				otherLayout.setVisibility(View.GONE);
			}

			// reset content view
			View contentLayout = getView().findViewById(
					R.id.template_fragment_content);
			if (contentLayout != null) {
				contentLayout.setVisibility(View.GONE);
			}
		} else {
			log.info("resetStatus()false");
		}

		setStatusState(STATUS_STATE_INITIAL);
	}

	/**
	 * Set max progress percentage
	 * 
	 * @param max
	 */
	public void setMaxProgressPercentage(int max) {
		if (max > 0) {
			mMaxProgressPercentage = max;
		}
	}

	/**
	 * Change state to error, previous state should be initial.
	 * 
	 * @param message
	 */
	public void setStatusError(String message) {
		log.error("showError() message: " + message);
		if (getStatusState() != STATUS_STATE_INITIAL) {
			throw new IllegalStateException();
		}
		LinearLayout layout = (LinearLayout) getView().findViewById(
				R.id.template_fragment_error);
		if (layout != null) {
			TextView messageView = (TextView) getView().findViewById(
					R.id.template_fragment_error_message);
			if (messageView != null && message != null) {
				messageView.setText(message);
			}
			layout.setVisibility(View.VISIBLE);
			setStatusState(STATUS_STATE_ERROR);
		}
	}

	/**
	 * Change state to progressing, previous state should be initial.
	 * 
	 * @param message
	 * @param percentage
	 */
	public void setStatusProgress(String message, boolean percentage) {
		log.info("showProgress() message: " + message);
		if (getStatusState() != STATUS_STATE_INITIAL) {
			throw new IllegalStateException();
		}
		if (getView() != null) {
			LinearLayout layout = (LinearLayout) getView().findViewById(
					R.id.template_fragment_status);
			if (layout != null) {

				// set status message
				TextView messageView = (TextView) getView().findViewById(
						R.id.template_fragment_status_message);
				if (messageView != null && message != null) {
					messageView.setText(message);
				}

				layout.setVisibility(View.VISIBLE);
				if (mProgressPercentageView == null) {
					mProgressPercentageView = (TextView) getView()
							.findViewById(
									R.id.template_fragment_status_percentage);
				}
				if (mProgressPercentageView != null) {
					mProgressPercentageView
							.setVisibility(percentage ? View.VISIBLE
									: View.GONE);
				}
				setStatusState(STATUS_STATE_PROGRESSING);

			}
		}
	}

	/**
	 * Change state to show content, previous state should be initial.
	 */
	public void setStatusShowContent() {
		log.info("showContent()");
		if (getStatusState() != STATUS_STATE_INITIAL) {
			throw new IllegalStateException();
		}
		if (getView() != null) {
			View layout = getView()
					.findViewById(R.id.template_fragment_content);
			if (layout != null) {
				layout.setVisibility(View.VISIBLE);
				setStatusState(STATUS_STATE_CONTENT_LOADED);
			}
		}
	}

	/**
	 * Set status state
	 * 
	 * @param statusState
	 */
	public synchronized void setStatusState(int statusState) {
		mStatusState = statusState;
	}

	/**
	 * Get status state
	 * 
	 * @return
	 */
	public synchronized int getStatusState() {
		return mStatusState;
	}

	public void setLastUpdatedNow() {
		mLastUpdatedDate = System.currentTimeMillis();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		log.info("onSaveInstanceState()");
		outState.putString("outstate", "value");
	}
		
		

	
}
