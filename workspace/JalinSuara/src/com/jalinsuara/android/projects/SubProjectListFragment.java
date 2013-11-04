package com.jalinsuara.android.projects;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jalinsuara.android.BaseListFragment;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.NetworkUtils;

import com.jalinsuara.android.projects.model.SubProject;

public class SubProjectListFragment extends BaseListFragment {

	private SubProjectAdapter mAdapter;
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
		task.execute();

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		SubProject subproject = (SubProject) mAdapter.getItem(position);
		mListener.onSubProjectItemClickListener(subproject, position);
	}

	private class LoadProject extends AsyncTask<Integer, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(Integer... params) {
			JalinSuaraSingleton.getInstance().setSubProjectList(
					NetworkUtils.getSubProject());
			if (getSherlockActivity() != null) {
				mAdapter = new SubProjectAdapter(getSherlockActivity(),
						JalinSuaraSingleton.getInstance().getSubProjectList());
			}

			return E_OK;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			setListAdapter(mAdapter);
			if (result == E_OK) {

				resetStatus();
				setStatusShowContent();

			} else {

				resetStatus();
				setStatusError(getString(R.string.error));
			}
		}

	}

	public interface OnSubProjectItemClickListener {
		public void onSubProjectItemClickListener(SubProject subproject,
				int position);

	}

}
