package com.jalinsuara.android.project.district;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.projects.model.District;

public class DistrictAdapter extends BaseAdapter {

	private ArrayList<District> mList;
	private ImageLoader mImageLoader;
	private Context mContext;

	public DistrictAdapter(Context context, ArrayList<District> list) {
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
		District object = mList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_item_district, null);

		}

		TextView titleTextview = ((TextView) convertView
				.findViewById(R.id.list_item_district_name_textview));

		TextView provinceTextview = ((TextView) convertView
				.findViewById(R.id.list_item_district_province_textview));

		convertView.setTag(object.getId());
		titleTextview.setText(object.getName());
		if (object.getProvince() != null) {
			provinceTextview.setText(object.getProvince().getName());
		} else {
			provinceTextview.setText("");
		}

		return convertView;
	}
}
