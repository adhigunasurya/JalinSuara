package com.jalinsuara.android.news;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
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

public class NewsFragment extends BaseFragment {

	private News mNews;
	private ImageView mImageView;
	private TextView mDateUpdatedView;
	private TextView mTitleTextView;
	private TextView mDescriptionTextView;
	private MapView mMapView;

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
			mMapView = (MapView) getView().findViewById(
					R.id.fragment_news_map_mapview);

			if (mNews.getPictureUrl() != null
					&& mNews.getPictureUrl().length() > 0) {
				ImageLoader loader = new ImageLoader(getSherlockActivity());
				loader.DisplayImage(mNews.getPictureUrl(), mImageView);
			}
			mMapView.onCreate(savedInstanceState);
			mTitleTextView.setText(mNews.getTitle());
			mDateUpdatedView.setText(com.jalinsuara.android.helper.DateUtils
					.toStringDateOnly(mNews.getUpdatedAt()));
			mDescriptionTextView.setText(Html.fromHtml(mNews.getDescription()));

			if (mNews.getLatitude() != 0 && mNews.getLongitude() != 0) {
				LatLng position = new LatLng(mNews.getLatitude(),
						mNews.getLongitude());
				if (mMapView.getMap() != null) {
					try {

						// force initialize
						MapsInitializer.initialize(getActivity());

						// move camera
						mMapView.getMap()
								.moveCamera(
										CameraUpdateFactory.newLatLngZoom(
												position, 10));

						// mark the map
						Marker marker = mMapView.getMap().addMarker(
								new MarkerOptions().position(position));

					} catch (GooglePlayServicesNotAvailableException impossible) {
						impossible.printStackTrace();
						mMapView.setVisibility(View.GONE);
					}

				}
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
		if (mMapView != null) {
			mMapView.onPause();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mMapView != null) {
			mMapView.onResume();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mMapView != null) {
			mMapView.onDestroy();
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (mMapView != null) {
			mMapView.onLowMemory();
		}
	}

}
