package com.jalinsuara.android.project;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jalinsuara.android.BaseEndlessListFragment;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Show list of subproject
 * 
 * @author hartono
 * 
 */
public class SubProjectListFragment extends BaseEndlessListFragment {

	public final static String EXTRA_SUB_DISTRICT_ID = "subdistrict_id";

	private SubProjectAdapter mAdapter = null;
	private OnSubProjectItemClickListener mListener;

	/**
	 * subdistrict id, if the value is -1, load all project,
	 * <p>
	 * else load subproject specific to subdistrict id
	 */
	private long mSubDistrictId = -1;

	public SubProjectListFragment(OnSubProjectItemClickListener listener) {
		log.info("SubProjectListFragment()");
		mListener = listener;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_subprojects_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		log.info("onActivityCreated");

		if (getArguments() != null) {
			mSubDistrictId = getArguments().getLong(EXTRA_SUB_DISTRICT_ID, -1);
		}

		if (savedInstanceState == null) {
			resetStatus();
			setStatusProgress(getResources().getString(R.string.loading), false);
			listener = new EndlessScrollListener() {
				@Override
				public void load(int page) {
					LoadProject task = new LoadProject();
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
						LoadProject task = new LoadProject();
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
		SubProject subproject = (SubProject) mAdapter.getItem(position);
		mListener.onSubProjectItemClickListener(subproject, position);
	}

	private class LoadProject extends LoadItemTask<SubProject> {

		@Override
		public void onFirstLoad() {
			mAdapter = new SubProjectAdapter(getSherlockActivity(),
					JalinSuaraSingleton.getInstance(getSherlockActivity())
							.getSubProjectList());
		}

		@Override
		public ArrayList<SubProject> loadFromNetwork(Object[] params) {
			long subDistrictId = (Long) params[2];
			ArrayList<SubProject> lSubProjects = null;

			if (subDistrictId == -1) {
				lSubProjects = (ArrayList<SubProject>) NetworkUtils
						.getSubProjects((Integer) params[0]);
			} else {
				lSubProjects = (ArrayList<SubProject>) NetworkUtils
						.getSubProjects(subDistrictId, (Integer) params[0]);
			}
			return lSubProjects;
		}

		@Override
		public ArrayList<SubProject> getList() {
			return JalinSuaraSingleton.getInstance(getSherlockActivity())
					.getSubProjectList();
		}

		@Override
		public BaseAdapter getAdapter() {
			return mAdapter;
		}

		@Override
		public ListView getListView() {
			return SubProjectListFragment.this.getListView();
		}

	}

	// private class LoadProject extends AsyncTask<Integer, Integer, Integer> {
	//
	// private final static int E_OK = 1;
	// private final static int E_OK_CHANGED = 3;
	// private final static int E_ERROR = 2;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// getSherlockActivity().setProgressBarIndeterminateVisibility(true);
	// }
	//
	// @Override
	// protected Integer doInBackground(Integer... params) {
	// loading = true;
	// ArrayList<SubProject> retval = NetworkUtils
	// .getSubProjects(params[0]);
	//
	// if (getSherlockActivity() != null) {
	// if (mAdapter == null) {
	// if (retval != null && retval.size() > 0) {
	// log.info("first load " + retval.size());
	// if (params[0] == 1) {
	// JalinSuaraSingleton
	// .getInstance(getSherlockActivity())
	// .getSubProjectList().clear();
	// }
	// JalinSuaraSingleton.getInstance(getSherlockActivity())
	// .getSubProjectList().addAll(retval);
	// mAdapter = new SubProjectAdapter(getSherlockActivity(),
	// JalinSuaraSingleton.getInstance(
	// getSherlockActivity())
	// .getSubProjectList());
	// } else if (retval == null) {
	// return E_ERROR;
	// }
	// } else {
	// if (retval != null && retval.size() > 0) {
	// log.info("add new " + retval.size() + " items");
	// mLastScrollY = getListView().getFirstVisiblePosition();
	// JalinSuaraSingleton.getInstance(getSherlockActivity())
	// .getSubProjectList().addAll(retval);
	//
	// return E_OK_CHANGED;
	// } else {
	// loading = false;
	// }
	// }
	// } else {
	// return E_ERROR;
	// }
	// return E_OK;
	// }
	//
	// @Override
	// protected void onPostExecute(Integer result) {
	// super.onPostExecute(result);
	//
	// if (getSherlockActivity() != null) {
	// getSherlockActivity().setProgressBarIndeterminateVisibility(
	// false);
	// setListAdapter(mAdapter);
	// if (result == E_OK) {
	//
	// resetStatus();
	// setStatusShowContent();
	//
	// } else if (result == E_OK_CHANGED) {
	// mAdapter.notifyDataSetChanged();
	// getListView().setSelectionFromTop(mLastScrollY, 0);
	// } else {
	// loading = false;
	// resetStatus();
	// setStatusError(getString(R.string.error));
	// }
	// }
	// }
	// }

	public interface OnSubProjectItemClickListener {
		public void onSubProjectItemClickListener(SubProject subproject,
				int position);
	}

}
