package com.jalinsuara.android.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.R;
import com.jalinsuara.android.maps.ShowMapActivity;
import com.jalinsuara.android.news.NewsListActivity;
import com.jalinsuara.android.news.ShareNewsActivity;
import com.jalinsuara.android.projects.SubProjectListActivity;

public class DashboardFragment extends BaseFragment {

	private Button mNewsButton;
	private Button mMapsButton;
	private Button mShareNewsButton;
	private Button mSubProjectsButton;
	private TextView mFbJalinSuara;
	private TextView mFbPsf;
	private TextView mTwitterPnpm;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setHasOptionsMenu(true);

		mNewsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_news_button);
		mMapsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_maps_button);
		mShareNewsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_share_news_button);
		mSubProjectsButton = (Button) getView().findViewById(
				R.id.fragment_dashboard_sub_projects_button);
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

		mFbJalinSuara = (TextView) getView().findViewById(
				R.id.link_fb_jalin_suara);
		mFbPsf = (TextView) getView().findViewById(R.id.link_fb_jalin_suara);
		mTwitterPnpm = (TextView) getView().findViewById(
				R.id.link_fb_jalin_suara);

		mFbJalinSuara.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getString(R.string.link_fb_jalin_suara)));
				startActivity(browserIntent);

			}
		});
		mFbPsf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getString(R.string.link_fb_psf)));
				startActivity(browserIntent);

			}
		});
		mTwitterPnpm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getString(R.string.link_twitter_pnpm_support)));
				startActivity(browserIntent);

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
		Intent intent = new Intent(getSherlockActivity(), ShowMapActivity.class);
		startActivity(intent);
	}

	protected void navigateToSubProjectList() {
		Intent intent = new Intent(getSherlockActivity(),
				SubProjectListActivity.class);
		startActivity(intent);

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
