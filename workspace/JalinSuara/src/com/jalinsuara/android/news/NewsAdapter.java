package com.jalinsuara.android.news;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.DateUtils;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.model.News;

public class NewsAdapter extends BaseAdapter {

	private ArrayList<News> mList;
	private ImageLoader mImageLoader;
	private Context mContext;
	private LayoutInflater inflater;

	public NewsAdapter(Context context, ArrayList<News> list) {
		mList = list;
		mImageLoader = new ImageLoader(context);
		mContext = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return mList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		News object = mList.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_news, null);

		}

		TextView titleTextview = ((TextView) convertView
				.findViewById(R.id.list_item_news_title_textview));
		TextView descriptionTextview = ((TextView) convertView
				.findViewById(R.id.list_item_news_content_textview));
		TextView dateTextView = ((TextView) convertView
				.findViewById(R.id.list_item_news_date_textview));

		dateTextView.setText(DateUtils.toStringDateOnly(object.getUpdatedAt()));
		convertView.setTag(object.getId());
		titleTextview.setText(object.getTitle());
		descriptionTextview.setText(Html.fromHtml(object.getDescription()));

		return convertView;
	}
}
