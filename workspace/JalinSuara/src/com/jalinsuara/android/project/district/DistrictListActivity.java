package com.jalinsuara.android.project.district;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.project.SubProjectActivity;
import com.jalinsuara.android.project.SubProjectListFragment;
import com.jalinsuara.android.project.SubProjectListFragment.OnSubProjectItemClickListener;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Show list of district
 * @author hartono
 *
 */
public class DistrictListActivity extends BaseFragmentActivity implements
		OnSubProjectItemClickListener {
	
	private boolean mMultiPane;
	private SubProjectListFragment mListFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_subproject_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		resetStatus();
		setStatusProgress(getResources().getString(R.string.loading), false);

		mListFragment = new SubProjectListFragment(this);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_subproject_list_list_fragment,
						mListFragment).commit();

		if (findViewById(R.id.activity_subproject_detail_fragment) == null) {
			setMultiPane(false);

		} else {
			setMultiPane(true);
			// getSupportFragmentManager().beginTransaction().replace(
			// R.id.activity_news_list_l, mListFragment).commit();
		}
		resetStatus();
		setStatusShowContent();
	}

	public boolean isMultiPane() {
		return mMultiPane;
	}

	public void setMultiPane(boolean multiPane) {
		mMultiPane = multiPane;
	}

	@Override
	public void onSubProjectItemClickListener(SubProject subproject,
			int position) {
		if (mMultiPane) {

		} else {
			Intent intent = new Intent(this, SubProjectActivity.class);
			intent.putExtra(SubProjectActivity.EXTRA_ID, subproject.getId());
			startActivity(intent);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}
}
