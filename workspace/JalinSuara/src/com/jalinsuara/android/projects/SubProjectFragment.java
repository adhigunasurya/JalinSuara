package com.jalinsuara.android.projects;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.SubProject;
import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.R;

public class SubProjectFragment extends BaseFragment {
	private SubProject mSubProject;
	private ImageView mImageView;
	private TextView mTitleTextView;
	private TextView mDescriptionTextView;

	public SubProjectFragment(SubProject subproject) {
		mSubProject = subproject;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_sub_projects;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (mSubProject != null) {
			mImageView = (ImageView) getView().findViewById(
					R.id.fragment_sub_project_image_imageview);
			mTitleTextView = (TextView) getView().findViewById(
					R.id.fragment_sub_project_title_textview);
			mDescriptionTextView = (TextView) getView().findViewById(
					R.id.fragment_sub_project_description_textview);

			ImageLoader loader = new ImageLoader(getSherlockActivity());
			// loader.DisplayImage(url, imageView);
			mTitleTextView.setText(mSubProject.getName());

			if (mSubProject.getDescription() != null) {
				mDescriptionTextView.setText(Html.fromHtml(mSubProject
						.getDescription()));
			}

			resetStatus();
			setStatusShowContent();
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}

	}

}
