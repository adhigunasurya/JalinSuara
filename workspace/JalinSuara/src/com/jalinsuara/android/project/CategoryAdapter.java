package com.jalinsuara.android.project;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.profile.model.User;
import com.jalinsuara.android.projects.model.Category;

public class CategoryAdapter extends BaseAdapter {

	private ArrayList<Category> mList;
	private ImageLoader mImageLoader;
	private Context mContext;

	public CategoryAdapter(Context context, ArrayList<Category> list) {
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
		Category object = mList.get(position);
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
