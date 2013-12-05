package com.jalinsuara.android.project.district;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jalinsuara.android.BaseEndlessListFragment;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;

/**
 * Show list of districts
 * <p>
 * 
 * if province id is provided, load all district based on province id
 * 
 * @author hartono
 * 
 */
public class DistrictListFragment extends BaseEndlessListFragment {

	public final static String EXTRA_PROVINCE_ID = "province_id";

	private DistrictAdapter mAdapter = null;

	private OnDistrictItemClickListener mListener;

	public ArrayList<District> mList;

	protected long mProvinceId = -1;

	public DistrictListFragment(OnDistrictItemClickListener listener) {
		log.info("DistrictListFragment()");
		mListener = listener;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_districts_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		log.info("onActivityCreated");

		if (getArguments() != null) {
			mProvinceId = getArguments().getLong(EXTRA_PROVINCE_ID, -1);
			log.info("provinceId " + mProvinceId);
		}

		if (savedInstanceState == null) {
			mList = new ArrayList<District>();

			resetStatus();
			setStatusProgress(getResources().getString(R.string.loading), false);
			listener = new EndlessScrollListener() {
				@Override
				public void load(int page) {
					LoadDistrict task = new LoadDistrict();
					task.execute(page, getCurrentPage(), mProvinceId);

				}
			};
			getListView().setOnScrollListener(listener);
		} else {
			if (mAdapter != null) {
				log.info("adapter size " + mAdapter.getCount());

				int currentPage = savedInstanceState.getInt(CURRENT_PAGE);

				listener = new EndlessScrollListener(currentPage) {
					@Override
					public void load(int page) {
						LoadDistrict task = new LoadDistrict();
						task.execute(page, getCurrentPage(), mProvinceId);
					}
				};
				getListView().setOnScrollListener(listener);

				setListAdapter(mAdapter);
				resetStatus();
				setStatusShowContent();
			}
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		District district = (District) mAdapter.getItem(position);
		mListener.onDistrictItemClickListener(district, position);
	}

	/**
	 * Param 0 - page, 1 - current page , 2 parent id
	 * 
	 * @author hartono
	 * 
	 */
	private class LoadDistrict extends LoadItemTask<District> {

		@Override
		public void onFirstLoad() {
			mAdapter = new DistrictAdapter(getSherlockActivity(), mList);
		}

		@Override
		public ArrayList<District> loadFromNetwork(Object[] params) {

			long provinceId = (Long) params[2];
			int page = (Integer) params[0];
			ArrayList<District> lDistricts = null;
			log.info("Load district page " + page + ", provinceId "
					+ provinceId);

			if (provinceId == -1) {
				lDistricts = (ArrayList<District>) NetworkUtils
						.getDistricts(page);
			} else {
				lDistricts = (ArrayList<District>) NetworkUtils.getDistricts(
						provinceId, page);
			}

			// sort by district's and province's name
			if (lDistricts != null) {
				Collections.sort(lDistricts, new Comparator<District>() {

					@Override
					public int compare(District lhs, District rhs) {
						if (lhs.getProvince() != null
								&& rhs.getProvince() != null) {
							return lhs.getProvince().getName()
									.compareTo(rhs.getProvince().getName());
						}
						return lhs.getName().compareTo(rhs.getName());
					}
				});
			}
			return lDistricts;

		}

		@Override
		public ArrayList<District> getList() {
			return mList;
		}

		@Override
		public BaseAdapter getAdapter() {
			return mAdapter;
		}

		@Override
		public ListView getListView() {
			return DistrictListFragment.this.getListView();
		}

	}

	public interface OnDistrictItemClickListener {
		public void onDistrictItemClickListener(District district, int position);
	}
}