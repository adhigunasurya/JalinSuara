package com.jalinsuara.android.project;

<<<<<<< HEAD
import com.jalinsuara.android.BaseFragment;

=======
import android.content.Intent;
>>>>>>> b0c306d8afb7cadd35d64bd08609629dfd672004
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.project.SubProjectListFragment.OnSubProjectItemClickListener;
import com.jalinsuara.android.project.subdistrict.SubDistrictListFragment;
import com.jalinsuara.android.projects.model.SubProject;

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

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
<<<<<<< HEAD
		public SubProjectListActivity getItem(int position) {
			switch (position) {
			case 0:
				return new SubProjectListActivity();
			case 1:
				return new ImageFragment(R.drawable.ic_launcher);
			case 2:
				return new ImageFragment(R.drawable.ic_launcher);

			default:
				return null;
=======
		public Fragment getItem(int arg0) {
			switch (arg0) {
			case 0: {
				if (mSubProjectListFragment==null){
					mSubProjectListFragment=new SubProjectListFragment(ViewPagerProjectActivity.this);
				}
				return mSubProjectListFragment;
			}
			case 1: {
				// return new SubProvinceListFragment();
>>>>>>> b0c306d8afb7cadd35d64bd08609629dfd672004
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
	}

	@Override
	public void onSubProjectItemClickListener(SubProject subproject,
			int position) {

		Intent intent = new Intent(this, SubProjectActivity.class);
		intent.putExtra(SubProjectActivity.EXTRA_ID, subproject.getId());
		startActivity(intent);

	}
}
