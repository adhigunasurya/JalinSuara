package com.jalinsuara.android.news;

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

public class ShareNewsActivity extends BaseFragmentActivity {
	protected static final int PICKFILE_RESULT_CODE = 0;
	private Button insertPic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_news);
		resetStatus();
		setStatusShowContent();
		insertPic = (Button)findViewById(R.id.activity_share_post_sisipGambarButton);
		insertPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			       intent.setType("file/*");
			       startActivityForResult(intent,PICKFILE_RESULT_CODE);
				}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == PICKFILE_RESULT_CODE){
			Log.d("sukses","---"+data.getData().getLastPathSegment());
			
		}	
//		int targetW = mImageView.getWidth();
//			int targetH = mImageView.getHeight();
//
//			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//			bmOptions.inJustDecodeBounds = true;
//			BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//			int photoW = bmOptions.outWidth;
//			int photoH = bmOptions.outHeight;
//
//			int scaleFactor = 1;
//			if ((targetW > 0) || (targetH > 0)) {
//				scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//			}
//
//			bmOptions.inJustDecodeBounds = false;
//			bmOptions.inSampleSize = scaleFactor;
//			bmOptions.inPurgeable = true;
//
//			Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//
//			mImageView.setImageBitmap(bitmap);
//			mImageView.setVisibility(View.VISIBLE);

		
		
	}
	

}
