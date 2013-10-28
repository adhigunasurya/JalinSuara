package com.jalinsuara.android.news;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.model.Comment;
import com.jalinsuara.android.news.model.News;

public class CommentAdapter extends BaseAdapter {

	private ArrayList<Comment> mList;
	private ImageLoader mImageLoader;
	private Context mContext;

	public CommentAdapter(Context context, ArrayList<Comment> list) {
		mList = list;
		mImageLoader = new ImageLoader(context);
		mContext = context;
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
		Comment object = mList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_item_news, null);

		}

//		TextView titleTextview = ((TextView) convertView
//				.findViewById(R.id.list_item_news_title_textview));
//		TextView descriptionTextview = ((TextView) convertView
//				.findViewById(R.id.list_item_news_content_textview));
//		
//		convertView.setTag(object.getId());
//		titleTextview.setText(object.getTitle());
//		descriptionTextview.setText(Html.fromHtml(object.getDescription()));
		
		

		return convertView;
	}
}
