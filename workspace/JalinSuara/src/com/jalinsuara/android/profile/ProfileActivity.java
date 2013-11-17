package com.jalinsuara.android.profile;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.R.layout;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Profile activity to show user's profile.
 * 
 * @author tonoman3g
 * @author gabriellewp
 * 
 */
public class ProfileActivity extends BaseFragmentActivity {

	private TextView mEmailTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		resetStatus();
		setStatusShowContent();

		mEmailTextView = (TextView) findViewById(R.id.activity_profile_emailContent_textview);
		mEmailTextView
				.setText(JalinSuaraSingleton.getInstance(this).getEmail());

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
