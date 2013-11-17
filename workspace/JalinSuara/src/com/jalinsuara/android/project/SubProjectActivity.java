package com.jalinsuara.android.project;

import android.os.Bundle;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.projects.model.SubProject;

public class SubProjectActivity extends BaseFragmentActivity {
	public final static String EXTRA_ID = "subproject_id";
	private SubProjectFragment mSubProjectFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_subproject);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// getSupportActionBar().setDisplayShowHomeEnabled(true);

		resetStatus();
		setStatusProgress(getString(R.string.loading), false);
		long id = getIntent().getLongExtra(EXTRA_ID, -1);
		if (id != -1) {
			SubProject subproject = JalinSuaraSingleton.getInstance(this)
					.findSubProjectById(id);
			if (subproject != null) {
				mSubProjectFragment = new SubProjectFragment(subproject);
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.activity_subproject_detail_fragment,
								mSubProjectFragment).commit();

				setTitle(subproject.getName());

				resetStatus();
				setStatusShowContent();
			} else {
				resetStatus();
				setStatusError(getString(R.string.error));
			}
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
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
