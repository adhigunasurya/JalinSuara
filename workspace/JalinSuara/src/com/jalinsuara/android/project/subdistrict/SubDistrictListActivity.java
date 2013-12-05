package com.jalinsuara.android.project.subdistrict;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.project.SubProjectListActivity;
import com.jalinsuara.android.project.SubProjectListFragment;
import com.jalinsuara.android.project.district.DistrictListFragment;
import com.jalinsuara.android.project.subdistrict.SubDistrictListFragment.OnSubDistrictItemClickListener;
import com.jalinsuara.android.projects.model.SubDistrict;

public class SubDistrictListActivity extends BaseFragmentActivity implements
		OnSubDistrictItemClickListener {

	private boolean mMultiPane;
	private SubDistrictListFragment mListFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_subdistrict_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		resetStatus();
		setStatusProgress(getResources().getString(R.string.loading), false);

		mListFragment = new SubDistrictListFragment(this);
		
		Bundle bundle = new Bundle();		
		long id = getIntent().getLongExtra(
				SubDistrictListFragment.EXTRA_DISTRICT_ID, -1);
		
		if (id != -1) {
			bundle.putLong(SubDistrictListFragment.EXTRA_DISTRICT_ID, id);
		}
		mListFragment.setArguments(bundle);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_subdistrict_list_list_fragment,
						mListFragment).commit();

		if (findViewById(R.id.activity_subdistrict_detail_fragment) == null) {
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
	public void onSubDistrictItemClickListener(SubDistrict subdistrict,
			int position) {
		if (mMultiPane) {

		} else {
			Intent intent = new Intent(this, SubProjectListActivity.class);
			intent.putExtra(SubProjectListFragment.EXTRA_SUB_DISTRICT_ID,
					subdistrict.getId());
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
