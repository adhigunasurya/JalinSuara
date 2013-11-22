package com.jalinsuara.android.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
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

	private Button mInsertPictureButton;
	private EditText mPostTitleEditText;
	private EditText mPostBudgetEditText;
	private EditText mPostDimensionEditText;
	private EditText mPostManfaatEditText;
	private EditText mPostContentEditText;
	private Spinner mPostProvinceSpinner;
	private Spinner mPostDistrictSpinner;
	private Spinner mPostSubDistrictSpinner;
	private Spinner mPostSocialMediaSpinner;
	private CheckBox mSaraCheckBox;
	private CheckBox mResponsibleCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_share_news);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		resetStatus();
		setStatusShowContent();

		mPostTitleEditText = (EditText) findViewById(R.id.activity_share_post_judul_edittext);
		mPostBudgetEditText = (EditText) findViewById(R.id.activity_share_post_budget_edittext);
		mPostDimensionEditText = (EditText) findViewById(R.id.activity_share_post_dimensi_edittext);
		mPostManfaatEditText = (EditText) findViewById(R.id.activity_share_post_manfaat_edittext);
		mPostContentEditText = (EditText) findViewById(R.id.activity_share_post_isi_edittext);

		mPostProvinceSpinner = (Spinner) findViewById(R.id.activity_share_post_propinsiSpinner);
		mPostDistrictSpinner = (Spinner) findViewById(R.id.activity_share_post_kabupatenSpinner);
		mPostSubDistrictSpinner = (Spinner) findViewById(R.id.activity_share_post_kecamatanSpinner);
		mPostSocialMediaSpinner = (Spinner) findViewById(R.id.activity_share_post_jejaringSosialSpinner);

		mSaraCheckBox = (CheckBox) findViewById(R.id.activity_share_post_saraCheckBox);
		mResponsibleCheckBox = (CheckBox) findViewById(R.id.activity_share_post_responsibleCheckBox);

		mInsertPictureButton = (Button) findViewById(R.id.activity_share_post_sisipGambarButton);

		mInsertPictureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
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
		String title = mPostTitleEditText.getText().toString();
		String description = mPostContentEditText.getText().toString();
		String postable_type = ShareNewPost.POSTABLE_ACTIVITY;
		String postable_id = "5";
		// FIXME
		String user_id = "5";
		String auth_token = JalinSuaraSingleton.getInstance(this).getToken();
		boolean valid = true;

		if (mSaraCheckBox.isChecked() && mResponsibleCheckBox.isChecked()) {
			if (title.length() > 0 && description.length() > 0) {
				valid = true;
			} else {
				valid = false;
			}

			if (valid) {
				ShareNewPost newpost = new ShareNewPost();
				newpost.execute(title, description, postable_type, postable_id,
						user_id, auth_token);
			} else {
				Toast.makeText(this, "Isi semua fields", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			valid = false;
			Toast.makeText(
					this,
					"Kontent tidak boleh mengandung SARA dan penulis harus bertanggung jawab terhadap isi konten.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Share new post
	 * 
	 * @author hartono parameters: title, description postable_type,
	 *         postable_id, user_id, auth_token
	 * 
	 */
	private class ShareNewPost extends AsyncTask<String, Integer, Integer> {

		/**
		 * Constant used for postable
		 */
		public final static String POSTABLE_ACTIVITY = "Activity";

		/**
		 * Constant used for postable
		 */
		public final static String POSTABLE_SUB_DISTRICT = "Subdistrict";

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			String title = params[0];
			String description = params[1];
			String postable_type = params[2];
			String postable_id = params[3];
			String user_id = params[4];
			String auth_token = params[5];

			if (params.length == 6) {
				String retval = NetworkUtils.postShareNews(title, description,
						postable_type, postable_id, user_id, auth_token);

				if (retval != null) {
					return E_OK;
				}
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
