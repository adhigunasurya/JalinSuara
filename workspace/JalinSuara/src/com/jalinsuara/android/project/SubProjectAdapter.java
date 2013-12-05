package com.jalinsuara.android.project;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.projects.model.SubProject;

public class SubProjectAdapter extends BaseAdapter {

	private ArrayList<SubProject> mList;
	private ImageLoader mImageLoader;
	private Context mContext;

	public SubProjectAdapter(Context context, ArrayList<SubProject> list) {
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
		SubProject object = mList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater
					.inflate(R.layout.list_item_subproject, null);

		}

		TextView namaKecamatan = ((TextView) convertView
				.findViewById(R.id.list_item_sub_project_kecamatan_textview));
		TextView deskripsiProyek = ((TextView) convertView
				.findViewById(R.id.list_item_sub_project_title_textview));

		namaKecamatan.setText(object.getSubdistrict().getName());
		deskripsiProyek.setText(object.getName());

		return convertView;
	}
}
