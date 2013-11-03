package com.jalinsuara.android.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jalinsuara.android.BaseListFragment;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helpers.NetworkUtils;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.SubProject;

public class NewsListFragment extends BaseListFragment {

	private NewsAdapter mAdapter;
	private OnNewsItemClickListener mListener;

	public NewsListFragment(OnNewsItemClickListener listener) {
		mListener = listener;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_news_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		resetStatus();
		setStatusProgress(getResources().getString(R.string.loading), false);

		LoadPosts task = new LoadPosts();
		task.execute();

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		News news = (News) mAdapter.getItem(position);
		mListener.onNewsItemClickListener(news, position);
	}

	private class LoadPosts extends AsyncTask<Integer, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(Integer... params) {
			JalinSuaraSingleton.getInstance().setNewsList(
					NetworkUtils.getPosts());
			if (getSherlockActivity() != null) {
				mAdapter = new NewsAdapter(getSherlockActivity(),
						JalinSuaraSingleton.getInstance().getNewsList());
			}

			return E_OK;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (getSherlockActivity() != null) {
				if (result == E_OK) {
					setListAdapter(mAdapter);
					resetStatus();
					setStatusShowContent();

				} else {

					resetStatus();
					setStatusError(getString(R.string.error));
				}
			}
		}

	}

	public interface OnNewsItemClickListener {
		public void onNewsItemClickListener(News news, int position);

	}
}
