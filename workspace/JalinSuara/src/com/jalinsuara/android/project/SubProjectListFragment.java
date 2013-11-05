package com.jalinsuara.android.project;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.jalinsuara.android.BaseListFragment;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.projects.model.SubProject;

public class SubProjectListFragment extends BaseListFragment {

	private SubProjectAdapter mAdapter = null;
	private OnSubProjectItemClickListener mListener;

	public SubProjectListFragment(OnSubProjectItemClickListener listener) {
		mListener = listener;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_sub_projects_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		resetStatus();
		setStatusProgress(getResources().getString(R.string.loading), false);

		LoadProject task = new LoadProject();
		task.execute(1);

		getListView().setOnScrollListener(new EndlessScrollListener());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		SubProject subproject = (SubProject) mAdapter.getItem(position);
		mListener.onSubProjectItemClickListener(subproject, position);
	}

	private class LoadProject extends AsyncTask<Integer, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_OK_CHANGED = 3;
		private final static int E_ERROR = 2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			getSherlockActivity().setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			loading = true;
			ArrayList<SubProject> retval = NetworkUtils
					.getSubProject(params[0]);
			if (getSherlockActivity() != null) {
				if (mAdapter == null) {

					if (retval != null && retval.size() > 0) {
						log.info("first load " + retval.size());
						if (params[0] == 1) {
							JalinSuaraSingleton.getInstance()
									.getSubProjectList().clear();
						}
						JalinSuaraSingleton.getInstance().getSubProjectList()
								.addAll(retval);
						mAdapter = new SubProjectAdapter(getSherlockActivity(),
								JalinSuaraSingleton.getInstance()
										.getSubProjectList());
					} else if (retval == null) {

						return E_ERROR;

					}

				} else {
					if (retval != null && retval.size() > 0) {
						log.info("add new " + retval.size() + " items");
						mLastScrollY = getListView().getFirstVisiblePosition();
						JalinSuaraSingleton.getInstance().getSubProjectList()
								.addAll(retval);

						return E_OK_CHANGED;
					} else {
						loading = false;
					}
				}
			} else {
				return E_ERROR;
			}

			return E_OK;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			getSherlockActivity().setProgressBarIndeterminateVisibility(false);
			setListAdapter(mAdapter);
			if (result == E_OK) {

				resetStatus();
				setStatusShowContent();

			} else if (result == E_OK_CHANGED) {
				mAdapter.notifyDataSetChanged();
				getListView().setSelectionFromTop(mLastScrollY, 0);
			} else {
				loading = false;
				resetStatus();
				setStatusError(getString(R.string.error));
			}
		}
	}

	private int mLastScrollY;
	private boolean loading;

	/**
	 * @return the loading
	 */
	public synchronized boolean isLoading() {
		return loading;
	}

	/**
	 * @param loading
	 *            the loading to set
	 */
	public synchronized void setLoading(boolean loading) {
		this.loading = loading;
	}

	public interface OnSubProjectItemClickListener {
		public void onSubProjectItemClickListener(SubProject subproject,
				int position);

	}

	public class EndlessScrollListener implements OnScrollListener {

		private int currentPage = 1;
		private int previousTotal = 0;

		public EndlessScrollListener() {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (isLoading()) {
				if (totalItemCount > previousTotal) {
					setLoading(false);
					previousTotal = totalItemCount;
					currentPage++;
				}
			}
			// log.info("show index " + firstVisibleItem + "-"
			// + (firstVisibleItem + visibleItemCount) + " :"
			// + (firstVisibleItem + visibleItemCount) + "/"
			// + totalItemCount);
			if (!isLoading()
					&& (firstVisibleItem + visibleItemCount) == totalItemCount) {
				// load the next page
				log.info("load for project next page :" + (currentPage + 1));
				new LoadProject().execute(currentPage + 1);
				setLoading(loading);
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}

	}
}
