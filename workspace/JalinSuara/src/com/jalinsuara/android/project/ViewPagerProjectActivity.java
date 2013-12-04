package com.jalinsuara.android.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.project.SubProjectListFragment.OnSubProjectItemClickListener;
import com.jalinsuara.android.project.subdistrict.SubDistrictListFragment;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Show list of project
 * 
 * @author hartono
 * 
 */
public class ViewPagerProjectActivity extends BaseFragmentActivity implements
		OnSubProjectItemClickListener {
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private SubProjectListFragment mSubProjectListFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager_project);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		mPager = (ViewPager) findViewById(R.id.view_pager);

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager.setAdapter(mAdapter);

		resetStatus();
		setStatusShowContent();
	}

	public class MyAdapter extends FragmentStatePagerAdapter {

		private Logger log = LoggerFactory.getLogger(this.getClass()
				.getSimpleName());

		private final String[] TITLE = { "Aktivitas", "Provinsi", "Distrik",
				"Subdistrik" };

		public MyAdapter(FragmentManager fm) {
			super(fm);
			log.info("MyAdapter");
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Fragment getItem(int arg0) {
			log.info("getItem " + arg0);
			switch (arg0) {
			case 0: {
				if (mSubProjectListFragment == null) {
					log.info("instantiate fragment");
					mSubProjectListFragment = new SubProjectListFragment(
							ViewPagerProjectActivity.this);
				}
				return mSubProjectListFragment;
			}
			case 1: {
				// return new SubProvinceListFragment();
			}
			case 2: {
				// return new SubDistrictListFragment();
			}
			case 3: {
				// return new Sub
				return new SubDistrictListFragment();
			}
			}
			return new SubProjectListFragment(null);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE[position];
		}
	}

	@Override
	public void onSubProjectItemClickListener(SubProject subproject,
			int position) {

		Intent intent = new Intent(this, SubProjectActivity.class);
		intent.putExtra(SubProjectActivity.EXTRA_ID, subproject.getId());
		startActivity(intent);

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
