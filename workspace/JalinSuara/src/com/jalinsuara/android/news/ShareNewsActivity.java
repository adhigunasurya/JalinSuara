package com.jalinsuara.android.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;

/**
 * Share news activity
 * 
 * @author tonoman3g
 * @author gabriellewp
 * 
 */
public class ShareNewsActivity extends BaseFragmentActivity {

	protected static final int PICKFILE_RESULT_CODE = 0;
	private Button minsertPic;
	private EditText mjudulPost;
	private EditText mbudgetPost;
	private EditText mdimensiPost;
	private EditText mmanfaatPost;
	private EditText misiPost;
	private Spinner mpropinsiPost;
	private Spinner mkabupatenPost;
	private Spinner mkecamatanPost;
	private Spinner mjejaringsosialPost;
	private Button msubmitButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_share_news);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		resetStatus();
		setStatusShowContent();
		
		mjudulPost = (EditText)findViewById(R.id.activity_share_post_judul_edittext);
		mbudgetPost = (EditText)findViewById(R.id.activity_share_post_judul_edittext);
		mdimensiPost = (EditText)findViewById(R.id.activity_share_post_judul_edittext);
		mmanfaatPost = (EditText)findViewById(R.id.activity_share_post_judul_edittext);
		misiPost  = (EditText)findViewById(R.id.activity_share_post_judul_edittext);
		mpropinsiPost = (Spinner)findViewById(R.id.activity_share_post_judul_edittext);
		mkecamatanPost = (Spinner)findViewById(R.id.activity_share_post_judul_edittext);
		mjejaringsosialPost = (Spinner)findViewById(R.id.activity_share_post_judul_edittext);
		msubmitButton = (Button)findViewById(R.id.activity_share_post_judul_edittext);
		
		msubmitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onSubmit();
			}
		});
		minsertPic = (Button) findViewById(R.id.activity_share_post_sisipGambarButton);
		
		minsertPic.setOnClickListener(new View.OnClickListener() {
		
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
		if(mjudulPost.getText().toString()!=null && misiPost.getText().toString()!=null){
			LoadNewPost newpost = new LoadNewPost();
			newpost.execute();
		}
	}
	
	private class LoadNewPost extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;
		
		@Override
		protected Integer doInBackground(String... params) {
			String token = NetworkUtils.postShareNews(mjudulPost.getText().toString(), misiPost.getText().toString(), "Activity","1228", "5", "yiXpsRyidaqWjq89JgGX");
			if (token != null) {
				return E_OK;
			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {
					finish();
				} else {
					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}
	}
}
