package com.jalinsuara.android.project;

import android.content.Intent;
import android.os.Bundle;

import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.news.NewsListFragment.OnNewsItemClickListener;
import com.jalinsuara.android.project.SubProjectListFragment.OnSubProjectItemClickListener;
import com.jalinsuara.android.project.subdistrict.SubDistrictListFragment;
import com.jalinsuara.android.projects.model.SubProject;
import com.actionbarsherlock.view.MenuItem;

public class SubProjectListActivity extends BaseFragmentActivity implements
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
		Bundle bundle = new Bundle();		
		long id = getIntent().getLongExtra(
				SubProjectListFragment.EXTRA_SUB_DISTRICT_ID, -1);
		
		if (id != -1) {
			bundle.putLong(SubProjectListFragment.EXTRA_SUB_DISTRICT_ID, id);
		}
		mListFragment.setArguments(bundle);

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
