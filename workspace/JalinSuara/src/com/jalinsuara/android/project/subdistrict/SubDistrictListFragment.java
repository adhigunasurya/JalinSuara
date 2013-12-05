package com.jalinsuara.android.project.subdistrict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseEndlessListFragment;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.ShareNewsActivity;
import com.jalinsuara.android.project.ViewPagerProjectActivity;
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

	public final static String EXTRA_DISTRICT_ID = "district_id";

	private SubDistrictAdapter mAdapter = null;

	private OnSubDistrictItemClickListener mListener;

	public ArrayList<SubDistrict> mList;

	protected long mDistrictId = -1;
	
	public SubDistrictListFragment() {

	}

	public SubDistrictListFragment(OnSubDistrictItemClickListener listener) {
		log.info("DistrictListFragment()");
		mListener = listener;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_subdistricts_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		log.info("onActivityCreated");

		setHasOptionsMenu(true);

		if (getArguments() != null) {
			mDistrictId = getArguments().getLong(EXTRA_DISTRICT_ID, -1);
			log.info("subDistrictId " + mDistrictId);
		}

		if (savedInstanceState == null) {
			mList = new ArrayList<SubDistrict>();

			resetStatus();
			setStatusProgress(getResources().getString(R.string.loading), false);
			listener = new EndlessScrollListener() {
				@Override
				public void load(int page) {
					LoadSubDistrict task = new LoadSubDistrict();
					task.execute(page, getCurrentPage(), mDistrictId);

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
						task.execute(page, getCurrentPage(), mDistrictId);
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_subdistrict_list, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_share_news) {
			Intent intent = new Intent(getSherlockActivity(),
					ShareNewsActivity.class);

			if (getSherlockActivity() instanceof ViewPagerProjectActivity) {

			} else {
				intent.putExtra(ShareNewsActivity.EXTRA_TRIGGERED_IN_TYPE,
						ShareNewsActivity.TYPE_SUB_DISTRICT_LIST);
				intent.putExtra(ShareNewsActivity.EXTRA_ID, mDistrictId);
			}

			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
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