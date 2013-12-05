package com.jalinsuara.android.project.subdistrict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jalinsuara.android.BaseEndlessListFragment;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.project.district.DistrictAdapter;
import com.jalinsuara.android.project.district.DistrictListFragment.OnDistrictItemClickListener;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.SubDistrict;

/**
 * Show list of sub districts.
 * <p>
 * if district id provided, show all subdistricts based on district id
 * 
 * @author hartono
 * 
 */
public class SubDistrictListFragment extends BaseEndlessListFragment {

	public final static String EXTRA_SUB_DISTRICT_ID = "district_id";

	private SubDistrictAdapter mAdapter = null;

	private OnSubDistrictItemClickListener mListener;

	public ArrayList<SubDistrict> mList;

	protected long mSubDistrictId = -1;

	public SubDistrictListFragment(OnSubDistrictItemClickListener listener) {
		log.info("DistrictListFragment()");
		mListener = listener;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_sub_districts_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		log.info("onActivityCreated");

		if (getArguments() != null) {
			mSubDistrictId = getArguments().getLong(EXTRA_SUB_DISTRICT_ID, -1);
			log.info("subDistrictId " + mSubDistrictId);
		}

		if (savedInstanceState == null) {
			mList = new ArrayList<SubDistrict>();

			resetStatus();
			setStatusProgress(getResources().getString(R.string.loading), false);
			listener = new EndlessScrollListener() {
				@Override
				public void load(int page) {
					LoadSubDistrict task = new LoadSubDistrict();
					task.execute(page, getCurrentPage(), mSubDistrictId);

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
						LoadSubDistrict task = new LoadSubDistrict();
						task.execute(page, getCurrentPage(), mSubDistrictId);
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
		SubDistrict subdistrict = (SubDistrict) mAdapter.getItem(position);
		mListener.onSubDistrictItemClickListener(subdistrict, position);
	}

	/**
	 * Param 0 - page, 1 - current page , 2 parent id
	 * 
	 * @author hartono
	 * 
	 */
	private class LoadSubDistrict extends LoadItemTask<SubDistrict> {

		@Override
		public void onFirstLoad() {
			mAdapter = new SubDistrictAdapter(getSherlockActivity(), mList);
		}

		@Override
		public ArrayList<SubDistrict> loadFromNetwork(Object[] params) {

			long districtId = (Long) params[2];
			int page = (Integer) params[0];
			ArrayList<SubDistrict> lDistricts = null;
			log.info("Load district page " + page + ", provinceId "
					+ districtId);

			if (districtId == -1) {
				lDistricts = (ArrayList<SubDistrict>) NetworkUtils
						.getSubdistricts(page);
			} else {
				lDistricts = (ArrayList<SubDistrict>) NetworkUtils
						.getSubdistricts(districtId, page);
			}

			// sort by subdistrict's and district's name
			if (lDistricts != null) {
				Collections.sort(lDistricts, new Comparator<SubDistrict>() {

					@Override
					public int compare(SubDistrict lhs, SubDistrict rhs) {
						if (lhs.getDistrict() != null
								&& rhs.getDistrict() != null) {
							return lhs.getDistrict().getName()
									.compareTo(rhs.getDistrict().getName());
						}
						return lhs.getName().compareTo(rhs.getName());
					}
				});
			}
			return lDistricts;

		}

		@Override
		public ArrayList<SubDistrict> getList() {
			return mList;
		}

		@Override
		public BaseAdapter getAdapter() {
			return mAdapter;
		}

		@Override
		public ListView getListView() {
			return SubDistrictListFragment.this.getListView();
		}

	}

	public interface OnSubDistrictItemClickListener {
		public void onSubDistrictItemClickListener(SubDistrict subdistrict,
				int position);
	}
}