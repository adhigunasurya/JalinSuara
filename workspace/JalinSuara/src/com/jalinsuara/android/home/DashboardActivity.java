package com.jalinsuara.android.home;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.auth.LoginActivity;
import com.jalinsuara.android.auth.SignUpActivity;
import com.jalinsuara.android.dialog.AboutDialog;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.profile.ProfileActivity;

/**
 * Jalin suara's dashboard screen
 * 
 * @author tonoman3g
 * 
 */
public class DashboardActivity extends BaseFragmentActivity {

	public final static int DIALOG_ABOUT = 1;

	/**
	 * Slideable Left panel
	 */
	private SlidingPaneLayout mSlidingLayout;

	/**
	 * Link in left panel
	 */
	private TextView mFbJalinSuara;

	/**
	 * Link in left panel
	 */
	private TextView mFbPsf;

	/**
	 * Link in left panel
	 */
	private TextView mTwitterPnpm;

	private DashboardFragment mDashboardFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_dashboard);
		setTitle(R.string.title_dashboard);

		mDashboardFragment = new DashboardFragment();

		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
		mSlidingLayout.setPanelSlideListener(new SliderListener());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		resetStatus();
		setStatusShowContent();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mDashboardFragment).commit();

		mFbJalinSuara = (TextView) findViewById(R.id.link_fb_jalin_suara);
		mFbPsf = (TextView) findViewById(R.id.link_fb_psf);
		mTwitterPnpm = (TextView) findViewById(R.id.link_twitter_pnpm_support);

		mFbJalinSuara.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getString(R.string.link_fb_jalin_suara)));
				startActivity(browserIntent);

			}
		});
		mFbPsf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getString(R.string.link_fb_psf)));
				startActivity(browserIntent);

			}
		});
		mTwitterPnpm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getString(R.string.link_twitter_pnpm_support)));
				startActivity(browserIntent);

			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home && !mSlidingLayout.isOpen()) {
			mSlidingLayout.smoothSlideOpen();
			return true;
		} else {
			mSlidingLayout.smoothSlideClosed();
		}

		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_profile: {

			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
			return true;
		}

		// FIXME add settings if needed
		// case R.id.action_settings: {
		// return true;
		// }

		case R.id.action_about: {
			showDialog(DIALOG_ABOUT);

			return true;
		}
		case R.id.action_search: {

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
				onSearchRequested();

			return true;
		}

		case R.id.action_sign_out: {
			SignOutTask task = new SignOutTask();
			task.execute();
			return true;
		}
		case R.id.action_sign_in: {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_dashboard, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();

		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		// Do not iconify the widget; expand it by default
		searchView.setIconifiedByDefault(false);

		return true;

	};

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (JalinSuaraSingleton.getInstance(this).isAuthenticated()) {
			showMenuItem(true, menu, R.id.action_profile);
			showMenuItem(true, menu, R.id.action_sign_out);
			showMenuItem(false, menu, R.id.action_sign_in);
		} else {
			showMenuItem(false, menu, R.id.action_profile);
			showMenuItem(false, menu, R.id.action_sign_out);
			showMenuItem(true, menu, R.id.action_sign_in);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ABOUT: {
			AboutDialog dialog = new AboutDialog(this,
					getSupportFragmentManager());

			return dialog;
		}
		}
		return super.onCreateDialog(id);
	}

	/**
	 * This panel slide listener updates the action bar accordingly for each
	 * panel state.
	 */
	private class SliderListener extends
			SlidingPaneLayout.SimplePanelSlideListener {
		@Override
		public void onPanelOpened(View panel) {

		}

		@Override
		public void onPanelClosed(View panel) {

		}
	}

	/**
	 * Sign out request to server
	 * 
	 * @author tonoman3g
	 * @author gabriellewp
	 */
	private class SignOutTask extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Integer doInBackground(String... params) {
			boolean success = NetworkUtils.signOut(JalinSuaraSingleton
					.getInstance(getBaseContext()).getEmail().toString());
			if (success == true) {
				return E_OK;
			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				setProgressBarIndeterminateVisibility(false);
				if (result == E_OK) {

					Toast.makeText(getBaseContext(),
							R.string.msg_logout_success, Toast.LENGTH_SHORT)
							.show();
					JalinSuaraSingleton.getInstance(getBaseContext()).signOut();
					refreshOptionMenu();
					mDashboardFragment.refreshState();
				} else {
					Toast.makeText(getBaseContext(),
							R.string.error_logout_failed, Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshOptionMenu();
	}

}