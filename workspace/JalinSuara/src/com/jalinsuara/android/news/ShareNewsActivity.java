package com.jalinsuara.android.news;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Share news activity
 * 
 * @author tonoman3g
 * @author gabriellewp
 * 
 */
public class ShareNewsActivity extends BaseFragmentActivity {

	protected static final int PICKFILE_RESULT_CODE = 0;
	private Button insertPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_share_news);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		resetStatus();
		setStatusShowContent();
		insertPic = (Button) findViewById(R.id.activity_share_post_sisipGambarButton);
		insertPic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("file/*");
				startActivityForResult(intent, PICKFILE_RESULT_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICKFILE_RESULT_CODE) {
			Log.d("sukses", "---" + data.getData().getLastPathSegment());

		}
		// int targetW = mImageView.getWidth();
		// int targetH = mImageView.getHeight();
		//
		// BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		// bmOptions.inJustDecodeBounds = true;
		// BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		// int photoW = bmOptions.outWidth;
		// int photoH = bmOptions.outHeight;
		//
		// int scaleFactor = 1;
		// if ((targetW > 0) || (targetH > 0)) {
		// scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		// }
		//
		// bmOptions.inJustDecodeBounds = false;
		// bmOptions.inSampleSize = scaleFactor;
		// bmOptions.inPurgeable = true;
		//
		// Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,
		// bmOptions);
		//
		// mImageView.setImageBitmap(bitmap);
		// mImageView.setVisibility(View.VISIBLE);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_share_news, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			return true;
		}
		case R.id.action_submit: {
			onSubmit();
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * On submit button click
	 */
	private void onSubmit() {

	}

}
