package com.jalinsuara.android.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.R;
import com.jalinsuara.android.news.NewsListActivity;
import com.jalinsuara.android.news.ShareNewsActivity;

public class DashboardFragment extends BaseFragment {

	private Button mNewsButton;
	private Button mMapsButton;
	private Button mShareNewsButton;
	private Button mSubProjectsButton;
	private Button mSearchButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		mNewsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_news_button);
		mMapsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_maps_button);
		mShareNewsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_share_news_button);
		mSubProjectsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_sub_projects_button);		
		mSearchButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_search_button);
		mNewsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				navigateToNewsList();
			}
		});
		mMapsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				navigateToMaps();
			}
		});
		mSubProjectsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				navigateToSubProjectList();
			}
		});
		mShareNewsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				navigateToShareNews();
			}
		});
		
		mSearchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				
			}
		});

		resetStatus();
		setStatusShowContent();

	}

	protected void navigateToNewsList() {
		Intent intent = new Intent(getSherlockActivity(),
				NewsListActivity.class);
		startActivity(intent);
	}

	protected void navigateToMaps() {
		// TODO Auto-generated method stub

	}

	protected void navigateToSubProjectList() {
		// TODO Auto-generated method stub

	}

	protected void navigateToShareNews() {
		Intent intent = new Intent(getSherlockActivity(),
				ShareNewsActivity.class);
		startActivity(intent);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_dashboard;
	}

}
