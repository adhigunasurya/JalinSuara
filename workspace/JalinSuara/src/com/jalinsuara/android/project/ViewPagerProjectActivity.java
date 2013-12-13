package com.jalinsuara.android.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.project.SubProjectListFragment.OnSubProjectItemClickListener;
import com.jalinsuara.android.project.district.DistrictListActivity;
import com.jalinsuara.android.project.district.DistrictListFragment;
import com.jalinsuara.android.project.district.DistrictListFragment.OnDistrictItemClickListener;
import com.jalinsuara.android.project.province.ProvinceListFragment;
import com.jalinsuara.android.project.province.ProvinceListFragment.OnProvinceItemClickListener;
import com.jalinsuara.android.project.subdistrict.SubDistrictListActivity;
import com.jalinsuara.android.project.subdistrict.SubDistrictListFragment;
import com.jalinsuara.android.project.subdistrict.SubDistrictListFragment.OnSubDistrictItemClickListener;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubDistrict;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Show list of project
 * 
 * @author hartono
 * 
 */
public class ViewPagerProjectActivity extends BaseFragmentActivity implements
		OnSubProjectItemClickListener, OnProvinceItemClickListener,
		OnDistrictItemClickListener, OnSubDistrictItemClickListener {
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private SubProjectListFragment mSubProjectListFragment;
	public ProvinceListFragment mProvinceListFragment;
	public DistrictListFragment mDistrictListFragment;
	public SubDistrictListFragment mSubDistrictListFragment;

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
				if (mProvinceListFragment == null) {
					log.info("instantiate fragment");
					mProvinceListFragment = new ProvinceListFragment(
							ViewPagerProjectActivity.this);
				}
				return mProvinceListFragment;
			}
			case 2: {
				if (mDistrictListFragment == null) {
					log.info("instantiate fragment");
					mDistrictListFragment = new DistrictListFragment(
							ViewPagerProjectActivity.this);
				}
				return mDistrictListFragment;
			}
			case 3: {
				if (mSubDistrictListFragment == null) {
					log.info("instantiate fragment");
					mSubDistrictListFragment = new SubDistrictListFragment(
							ViewPagerProjectActivity.this);
				}
				return mSubDistrictListFragment;
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

	@Override
	public void onProvinceItemClickListener(Province province, int position) {

		Intent intent = new Intent(this, DistrictListActivity.class);
		intent.putExtra(DistrictListFragment.EXTRA_PROVINCE_ID,
				province.getId());
		startActivity(intent);
	}

	@Override
	public void onDistrictItemClickListener(District district, int position) {

		Intent intent = new Intent(this, SubDistrictListActivity.class);
		intent.putExtra(SubDistrictListFragment.EXTRA_DISTRICT_ID,
				district.getId());
		startActivity(intent);
	}

	@Override
	public void onSubDistrictItemClickListener(SubDistrict subdistrict,
			int position) {

		Intent intent = new Intent(this, SubProjectListActivity.class);
		intent.putExtra(SubProjectListFragment.EXTRA_SUB_DISTRICT_ID,
				subdistrict.getId());
		startActivity(intent);
	}
}
