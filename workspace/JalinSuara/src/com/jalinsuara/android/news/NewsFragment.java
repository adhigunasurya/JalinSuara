package com.jalinsuara.android.news;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.model.News;

/**
 * Fragment to show news detail
 * 
 * @author tonoman3g
 * 
 */
public class NewsFragment extends BaseFragment {

	private News mNews;
	private ImageView mImageView;
	private TextView mDateUpdatedView;
	private TextView mTitleTextView;
	private TextView mDescriptionTextView;

	public NewsFragment() {

	}

	public NewsFragment(News news) {
		mNews = news;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_news;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (mNews != null) {
			mImageView = (ImageView) getView().findViewById(
					R.id.fragment_news_image_imageview);
			mTitleTextView = (TextView) getView().findViewById(
					R.id.fragment_news_title_textview);
			mDescriptionTextView = (TextView) getView().findViewById(
					R.id.fragment_news_description_textview);
			mDateUpdatedView = (TextView) getView().findViewById(
					R.id.fragment_news_date_textview);

			if (mNews.getPictureUrl() != null
					&& mNews.getPictureUrl().length() > 0) {
				ImageLoader loader = new ImageLoader(getSherlockActivity());
				loader.DisplayImage(mNews.getPictureUrl(), mImageView);
			}

			mTitleTextView.setText(mNews.getTitle());
			if (mNews.getUpdatedAt() != null) {
				mDateUpdatedView
						.setText(com.jalinsuara.android.helper.DateUtils
								.toStringDateOnly(mNews.getUpdatedAt()));
			}
			if (mNews.getDescription() != null) {
				mDescriptionTextView.setText(Html.fromHtml(mNews
						.getDescription()));
			}

			resetStatus();
			setStatusShowContent();
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}

	}

	@Override
	public void onPause() {
		super.onPause();		
	}

	@Override
	public void onResume() {
		super.onResume();		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();		
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();		
	}

}
