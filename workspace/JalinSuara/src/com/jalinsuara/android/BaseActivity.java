package com.jalinsuara.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * Base Activity used in the entire application
 * 
 * @author tonoman3g
 * 
 */
public abstract class BaseActivity extends SherlockActivity {

	public Logger log = LoggerFactory
			.getLogger(this.getClass().getSimpleName());

	/*
	 * initial -> error / progressing / content loaded
	 */
	public final static int STATUS_STATE_INITIAL = -2;
	public final static int STATUS_STATE_ERROR = -1;
	public final static int STATUS_STATE_PROGRESSING = 0;
	public final static int STATUS_STATE_CONTENT_LOADED = 1;

	/**
	 * Save status's state, whether it is error, progressing, content loaded.
	 */
	private int mStatusState = STATUS_STATE_INITIAL;

	/**
	 * Maximum value for progress percentage, if we don't use indeterminate
	 * progress bar
	 */
	protected int mMaxProgressPercentage = 100;

	/**
	 * Progress for determinate progress bar
	 */
	protected int mProgress = 0;

	/**
	 * Text View for Progress's Percentage
	 */
	protected TextView mProgressPercentageView;

	/**
	 * To save activity state whether {@link BaseActivity#onPause()} called or
	 * not.
	 */
	protected boolean mPaused;

	// override to add layout template progress error
	@Override
	public void setContentView(int layoutResId) {
		FrameLayout group = (FrameLayout) findViewById(android.R.id.content);
		View.inflate(this, R.layout.layout_template_progress_error_1, group);
		FrameLayout content = (FrameLayout) findViewById(R.id.template_content);
		View.inflate(this, layoutResId, content);
	}

	/**
	 * Reset all error, progress, and content to default. Content is visible,
	 * the others visibilities are gone.
	 * <p>
	 * Reset status to initial.
	 */
	public void resetStatus() {
		log.info("resetStatus()");
		// always checking for null pointer possibility

		// reset error template
		LinearLayout layout = (LinearLayout) findViewById(R.id.template_error);
		if (layout != null) {
			layout.setVisibility(View.GONE);
		}

		// reset status / progress template
		LinearLayout otherLayout = (LinearLayout) findViewById(R.id.template_status);
		if (otherLayout != null) {
			otherLayout.setVisibility(View.GONE);
		}

		// reset content view
		View contentLayout = findViewById(R.id.template_content);
		if (contentLayout != null) {
			contentLayout.setVisibility(View.GONE);
		}

		setStatusState(STATUS_STATE_INITIAL);
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
		LinearLayout layout = (LinearLayout) findViewById(R.id.template_error);
		if (layout != null) {
			TextView messageView = (TextView) findViewById(R.id.template_error_message);
			if (messageView != null && message != null) {
				messageView.setText(message);
			}
			layout.setVisibility(View.VISIBLE);
			setStatusState(STATUS_STATE_ERROR);
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
		View layout = findViewById(R.id.template_content);
		if (layout != null) {
			layout.setVisibility(View.VISIBLE);
			setStatusState(STATUS_STATE_CONTENT_LOADED);
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
		LinearLayout layout = (LinearLayout) findViewById(R.id.template_status);
		if (layout != null) {

			// set status message
			TextView messageView = (TextView) findViewById(R.id.template_status_message);
			if (messageView != null && message != null) {
				messageView.setText(message);
			}

			layout.setVisibility(View.VISIBLE);
			if (mProgressPercentageView == null) {
				mProgressPercentageView = (TextView) findViewById(R.id.template_status_percentage);
			}
			if (mProgressPercentageView != null) {
				mProgressPercentageView.setVisibility(percentage ? View.VISIBLE
						: View.GONE);
			}
			setStatusState(STATUS_STATE_PROGRESSING);

		}

	}

	/**
	 * Set only progress percentage, if it uses determinate progress bar
	 * 
	 * @param progress
	 */
	public void publishProgressPercentage(int progress) {
		if (getStatusState() == STATUS_STATE_PROGRESSING) {
			if (progress >= 0 && progress < mMaxProgressPercentage) {
				mProgress = progress;
			} else if (progress < 0) {
				mProgress = 0;
			} else {
				mProgress = mMaxProgressPercentage;
			}

			LinearLayout layout = (LinearLayout) findViewById(R.id.template_status);
			if (layout != null) {
				if (mProgressPercentageView == null) {
					mProgressPercentageView = (TextView) findViewById(R.id.template_status_percentage);
				}
				if (mProgressPercentageView != null) {
					mProgressPercentageView.setText(mProgress + "%");
				}
			}
		}
	}

	/**
	 * Set only progress message
	 * 
	 * @param message
	 */
	public void publishProgressMessage(String message) {
		if (getStatusState() == STATUS_STATE_PROGRESSING) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.template_status);
			if (layout != null) {
				TextView messageView = (TextView) findViewById(R.id.template_status_message);
				if (message != null) {
					messageView.setText(message);
				}

			}
		}
	}

	/**
	 * Shortcut functions to set visibility of menu item
	 * 
	 * @param show
	 * @param menu
	 * @param id
	 */
	public void showMenuItem(boolean show, Menu menu, int id) {
		MenuItem item = menu.findItem(id);
		if (item != null) {
			item.setVisible(show);
		}
	}

	/**
	 * Check whether activity state is paused or not
	 * 
	 * @return
	 */
	public synchronized boolean isPaused() {
		log.info("isPaused() " + mPaused);
		return mPaused;
	}

	/**
	 * Set {@link BaseActivity#mPaused} value
	 * 
	 * @param paused
	 */
	private synchronized void setPaused(boolean paused) {
		mPaused = paused;
	}

	@Override
	protected void onResume() {
		super.onResume();
		log.info("onResume()");

		// set activity state to be resumed
		setPaused(false);

	}

	@Override
	protected void onPause() {
		super.onPause();
		log.info("onPause()");
		setPaused(true);

	}

	/**
	 * Get status state
	 * 
	 * @return
	 */
	public synchronized int getStatusState() {
		return mStatusState;
	}

	/**
	 * Set status state
	 * 
	 * @param statusState
	 */
	public synchronized void setStatusState(int statusState) {
		mStatusState = statusState;
	}
}
