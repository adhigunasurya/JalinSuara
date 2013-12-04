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
import com.jalinsuara.android.helper.DateUtils;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.model.Comment;
import com.jalinsuara.android.news.model.News;

/**
 * Comment adapter for list of comments a post
 * 
 * @author tonoman3g
 * 
 */
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

			convertView = inflater.inflate(R.layout.list_item_comment, null);

		}

		TextView usernameTextview = ((TextView) convertView
				.findViewById(R.id.list_item_comment_user_name_textview));
		TextView dateTextview = ((TextView) convertView
				.findViewById(R.id.list_item_comment_user_date_textview));
		TextView bodyTextView = ((TextView) convertView
				.findViewById(R.id.list_item_comment_body_textview));

		dateTextview.setText(DateUtils.toStringDateOnly(object.getCreatedAt()));
		convertView.setTag(object.getId());

		if (object.getGuestName() == null) {
			if (object.getCommenterName() != null) {
				usernameTextview.setText(object.getCommenterName());
			}
		} else {
			usernameTextview.setText(object.getGuestName());
		}

		bodyTextView.setText(object.getBody());

		return convertView;
	}
}
