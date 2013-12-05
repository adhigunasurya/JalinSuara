package com.jalinsuara.android.project.province;

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
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.ShareNewsActivity;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Show list of provinces
 * 
 * @author hartono
 * 
 */
public class ProvinceListFragment extends BaseEndlessListFragment {

	private ProvinceAdapter mAdapter = null;
	private OnProvinceItemClickListener mListener;

	public ProvinceListFragment(OnProvinceItemClickListener listener) {
		log.info("ProvinceListFragment()");
		mListener = listener;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_provinces_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		log.info("onActivityCreated");

		setHasOptionsMenu(true);

		if (savedInstanceState == null) {
			resetStatus();
			setStatusProgress(getResources().getString(R.string.loading), false);
			listener = new EndlessScrollListener() {
				@Override
				public void load(int page) {

					// limit page to 1
					if (page == 1) {
						LoadProvince task = new LoadProvince();
						task.execute(page, getCurrentPage());
					}
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

						// limit page to 1
						if (page == 1 && getCurrentPage() != 1) {
							LoadProvince task = new LoadProvince();
							task.execute(page, getCurrentPage());
						}
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
		Province province = (Province) mAdapter.getItem(position);
		mListener.onProvinceItemClickListener(province, position);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_province_list, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_share_news) {
			Intent intent = new Intent(getSherlockActivity(),
					ShareNewsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class LoadProvince extends LoadItemTask<Province> {

		@Override
		public void onFirstLoad() {
			mAdapter = new ProvinceAdapter(getSherlockActivity(),
					JalinSuaraSingleton.getInstance(getSherlockActivity())
							.getProvincesList());
		}

		@Override
		public ArrayList<Province> loadFromNetwork(Object[] params) {
			ArrayList<Province> sProvinces = JalinSuaraSingleton.getInstance(
					getSherlockActivity()).getProvincesList();
			if (sProvinces.size() == 0) {
				ArrayList<Province> lProvinces = (ArrayList<Province>) NetworkUtils
						.getProvinces();

				// sort by province's name
				if (lProvinces != null) {
					Collections.sort(lProvinces, new Comparator<Province>() {

						@Override
						public int compare(Province lhs, Province rhs) {
							return lhs.getName().compareTo(rhs.getName());
						}
					});
				}

				return lProvinces;
			} else {
				ArrayList<Province> lProvinces = new ArrayList<Province>();
				lProvinces.addAll(sProvinces);
				return lProvinces;
			}

		}

		@Override
		public ArrayList<Province> getList() {
			return JalinSuaraSingleton.getInstance(getSherlockActivity())
					.getProvincesList();
		}

		@Override
		public BaseAdapter getAdapter() {
			return mAdapter;
		}

		@Override
		public ListView getListView() {
			return ProvinceListFragment.this.getListView();
		}

	}

	public interface OnProvinceItemClickListener {
		public void onProvinceItemClickListener(Province province, int position);
	}
}