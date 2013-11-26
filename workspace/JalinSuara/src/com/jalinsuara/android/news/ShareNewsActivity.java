package com.jalinsuara.android.news;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.project.province.ProvinceAdapter;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubDistrict;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Share news activity
 * 
 * @author tonoman3g
 * @author gabriellewp
 * 
 */
public class ShareNewsActivity extends BaseFragmentActivity {

	/**
	 * Code for starting activity to pick picture
	 */
	protected static final int PICKFILE_RESULT_CODE = 0;

	/**
	 * Upload picture for news
	 */
	private Button mInsertPictureButton;

	private EditText mPostTitleEditText;

	private EditText mPostBudgetEditText;

	private EditText mPostDimensionEditText;

	private EditText mPostManfaatEditText;

	private EditText mPostContentEditText;

	private Spinner mPostProvinceSpinner;

	private Spinner mPostDistrictSpinner;

	private Spinner mPostSubDistrictSpinner;

	private Spinner mPostSubProjectSpinner;

	private CheckBox mSaraCheckBox;

	private CheckBox mResponsibleCheckBox;

	private String mFilePath;

	private ImageView mImagePreview;

	private Province mProvince;

	private District mDistrict;

	private SubDistrict mSubDistrict;

	private SubProject mSubProject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("-----","muncul");
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
		mPostSubProjectSpinner = (Spinner) findViewById(R.id.activity_share_post_subproject_spinner);

		mPostProvinceSpinner.setVisibility(View.VISIBLE);
		mPostDistrictSpinner.setVisibility(View.GONE);
		mPostSubDistrictSpinner.setVisibility(View.GONE);
		mPostSubProjectSpinner.setVisibility(View.GONE);

		mPostProvinceSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Province province = (Province) mPostProvinceSpinner
								.getItemAtPosition(arg2);
						if (province.getId() != mProvince.getId()) {
							loadDistrict(province);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		mSaraCheckBox = (CheckBox) findViewById(R.id.activity_share_post_saraCheckBox);
		mResponsibleCheckBox = (CheckBox) findViewById(R.id.activity_share_post_responsibleCheckBox);

		mImagePreview = (ImageView) findViewById(R.id.activity_share_post_image_preview_imageview);

		mInsertPictureButton = (Button) findViewById(R.id.activity_share_post_sisipGambarButton);

		mInsertPictureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, PICKFILE_RESULT_CODE);
			}
		});
		
		//load province
		LoadProvinces province = new LoadProvinces();
		province.execute();
	}

	protected void loadDistrict(Province province) {
		// TODO Auto-generated method stub

	}

	protected void loadSubDistrict(District district) {
		// TODO Auto-generated method stub

	}

	protected void loadSubProject(SubDistrict subDistrict) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICKFILE_RESULT_CODE) {
			if (data != null) {
				Uri uri = data.getData();
				log.info("File Uri: " + uri.toString());
				// Get the path
				File file = new File(uri.getPath());
				log.info("File Path: " + file.toString());
				mFilePath = file.toString();

				mImagePreview.setVisibility(View.VISIBLE);
				mImagePreview.setImageURI(uri);
			}
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
	 * Load provinces data from server
	 */
	private class LoadProvinces extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;
		private ArrayList<Province> listProvince;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			resetStatus();
			setStatusProgress(getString(R.string.loading), false);
		}

		@Override
		protected Integer doInBackground(String... params) {
				listProvince = NetworkUtils.getProvinces();
				if (listProvince != null) {
					return E_OK;
				}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!isFinishing()) {
				if (result == E_OK) {
					
					ProvinceAdapter provinceAdapter = new ProvinceAdapter(getBaseContext(),listProvince);
					
					//finish();
				} else {
					// resetStatus();
					// setStatusShowContent();
					Toast.makeText(getBaseContext(), R.string.error,
							Toast.LENGTH_SHORT).show();
					//
					// }
				}
			}
		}
	}
	/**
	 * On submit button click
	 */
	private void onSubmit() {
		String title = mPostTitleEditText.getText().toString();
		String description = mPostContentEditText.getText().toString();
		String postable_type = News.POSTABLE_ACTIVITY;
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
						user_id, auth_token, mFilePath);
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
	 *         postable_id, auth_token,file
	 * 
	 */
	private class ShareNewPost extends AsyncTask<String, Integer, Integer> {

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
			String file = params[6];

			if (params.length == 6) {
				String retval = NetworkUtils.postShareNews(title, description,
						postable_type, postable_id, auth_token, file);

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

