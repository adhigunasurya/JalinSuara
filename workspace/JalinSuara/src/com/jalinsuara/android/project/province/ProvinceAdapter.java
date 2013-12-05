package com.jalinsuara.android.project.province;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.projects.model.Province;

/**
 * Adapter for provinces list
 * 
 * @author hartono
 * 
 */
public class ProvinceAdapter extends BaseAdapter {

	private ArrayList<Province> mList;
	private ImageLoader mImageLoader;
	private Context mContext;

	public ProvinceAdapter(Context context, ArrayList<Province> list) {
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
		Province object = mList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_item_province, null);

		}

		TextView titleTextview = ((TextView) convertView
				.findViewById(R.id.list_item_province_name_textview));

		convertView.setTag(object.getId());
		titleTextview.setText(object.getName());

		return convertView;
	}
}
