package com.jalinsuara.android.project.subdistrict;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.projects.model.SubDistrict;

public class SubDistrictAdapter extends BaseAdapter {

	private ArrayList<SubDistrict> mList;
	private ImageLoader mImageLoader;
	private Context mContext;

	public SubDistrictAdapter(Context context, ArrayList<SubDistrict> list) {
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
		SubDistrict object = mList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_item_subdistrict,
					null);

		}

		TextView titleTextview = ((TextView) convertView
				.findViewById(R.id.list_item_subdistrict_name_textview));
		TextView districtTextview = ((TextView) convertView
				.findViewById(R.id.list_item_subdistrict_district_textview));

		convertView.setTag(object.getId());
		titleTextview.setText(object.getName());
		if (object.getDistrict() != null) {
				StringBuilder sb= new StringBuilder();
				if (object.getDistrict().getProvince()!=null){
					sb.append(object.getDistrict().getProvince().getName());
					sb.append(" - ");
				}								
			
			sb.append(object.getDistrict().getName());
			districtTextview.setText(sb.toString());
		} else {
			districtTextview.setText("");
		}

		return convertView;
	}
}
