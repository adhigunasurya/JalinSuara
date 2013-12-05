package com.jalinsuara.android.project.district;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.project.district.DistrictListFragment.OnDistrictItemClickListener;
import com.jalinsuara.android.project.subdistrict.SubDistrictListActivity;
import com.jalinsuara.android.project.subdistrict.SubDistrictListFragment;
import com.jalinsuara.android.projects.model.District;

/**
 * Show list of district
 * 
 * @author hartono
 * 
 */
public class DistrictListActivity extends BaseFragmentActivity implements
		OnDistrictItemClickListener {

	private boolean mMultiPane;
	private DistrictListFragment mListFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_district_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		resetStatus();
		setStatusProgress(getResources().getString(R.string.loading), false);

		mListFragment = new DistrictListFragment(this);
		
		Bundle bundle = new Bundle();		
		long id = getIntent().getLongExtra(
				DistrictListFragment.EXTRA_PROVINCE_ID, -1);
		
		if (id != -1) {
			bundle.putLong(DistrictListFragment.EXTRA_PROVINCE_ID, id);
		}
		mListFragment.setArguments(bundle);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_district_list_list_fragment,
						mListFragment).commit();

		if (findViewById(R.id.activity_district_detail_fragment) == null) {
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
	public void onDistrictItemClickListener(District district, int position) {
		if (mMultiPane) {

		} else {
			Intent intent = new Intent(this, SubDistrictListActivity.class);
			intent.putExtra(SubDistrictListFragment.EXTRA_DISTRICT_ID,
					district.getId());
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
