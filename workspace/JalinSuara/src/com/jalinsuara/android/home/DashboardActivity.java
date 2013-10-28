package com.jalinsuara.android.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.profile.ProfileActivity;

public class DashboardActivity extends BaseFragmentActivity {

	private SlidingPaneLayout mSlidingLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		setTitle(R.string.title_dashboard);

		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
		mSlidingLayout.setPanelSlideListener(new SliderListener());
		

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		resetStatus();
		setStatusShowContent();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new DashboardFragment()).commit();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.

		if (item.getItemId() == android.R.id.home && !mSlidingLayout.isOpen()) {
			mSlidingLayout.smoothSlideOpen();
			return true;
		}

		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_profile: {

			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.action_settings: {
			// Intent intent = new Intent(this, ProfileActivity.class);
			// startActivity(intent);
			return true;
		}
		case R.id.action_about: {

			// Intent intent = new Intent(this, ProfileActivity.class);
			// startActivity(intent);
			return true;
		}

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_dashboard, menu);
		return true;

	};

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// boolean drawerOpen = isDrawerOpen();
		// showMenuItem(!drawerOpen, menu, R.id.action_settings);
		// showMenuItem(!drawerOpen, menu, R.id.action_about);
		// showMenuItem(!drawerOpen, menu, R.id.action_profile);
		return super.onPrepareOptionsMenu(menu);
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

}