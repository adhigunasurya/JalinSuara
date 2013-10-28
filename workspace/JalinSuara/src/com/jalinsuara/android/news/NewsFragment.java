package com.jalinsuara.android.news;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.model.News;

public class NewsFragment extends BaseFragment {

	private News mNews;
	private ImageView mImageView;
	private TextView mTitleTextView;
	private TextView mDescriptionTextView;

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

			ImageLoader loader = new ImageLoader(getSherlockActivity());
			// loader.DisplayImage(url, imageView);
			mTitleTextView.setText(mNews.getTitle());
			mDescriptionTextView.setText(Html.fromHtml(mNews.getDescription()));

			resetStatus();
			setStatusShowContent();
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}

	}

}
