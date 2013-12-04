package com.jalinsuara.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.actionbarsherlock.view.MenuItem;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Showing license
 * 
 * @author tonoman3g
 */
public class LicenseActivity extends BaseFragmentActivity {

	private TextView mLicenseTextView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_license);

		mLicenseTextView = (TextView) findViewById(R.id.activity_license_textview);
		resetStatus();
		setStatusProgress(getString(R.string.loading), false);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// reading apache license
		AssetManager assetManager = getAssets();
		InputStream ims;
		try {
			ims = assetManager.open("apache_license.txt");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ims));
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = reader.readLine();
			}
			reader.close();
			ims.close();

			mLicenseTextView.setText(sb.toString().replace("\n",
					System.getProperty("line.separator")));

			resetStatus();
			setStatusShowContent();
		} catch (IOException e) {
			e.printStackTrace();

			resetStatus();
			setStatusError(getString(R.string.error));
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}