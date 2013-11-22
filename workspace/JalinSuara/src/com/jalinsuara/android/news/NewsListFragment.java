package com.jalinsuara.android.news;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jalinsuara.android.BaseEndlessListFragment;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.news.model.News;

public class NewsListFragment extends BaseEndlessListFragment {

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
		log.info("onActivityCreated()");
		resetStatus();
		setStatusProgress(getResources().getString(R.string.loading), false);

//		LoadPosts task = new LoadPosts();
//		task.execute(1);

		getListView().setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void load(int page) {
				LoadPosts task = new LoadPosts();
				task.execute(page);
			}

		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		News news = (News) mAdapter.getItem(position);
		mListener.onNewsItemClickListener(news, position);
	}

	private class LoadPosts extends LoadItemTask<News> {

		@Override
		public void onFirstLoad() {
			mAdapter = new NewsAdapter(getSherlockActivity(),
					JalinSuaraSingleton.getInstance(getSherlockActivity())
							.getNewsList());
		}

		@Override
		public ArrayList<News> loadFromNetwork(Object[] params) {
			ArrayList<News> retval = NetworkUtils.getPosts((Integer) params[0]);
			return retval;
		}

		@Override
		public ArrayList<News> getList() {
			return JalinSuaraSingleton.getInstance(getSherlockActivity())
					.getNewsList();
		}

		@Override
		public BaseAdapter getAdapter() {
			return mAdapter;
		}

		@Override
		public ListView getListView() {
			return NewsListFragment.this.getListView();
		}

	}
//
//	private class LoadPosts extends AsyncTask<Integer, Integer, Integer> {
//		private final static int E_OK = 1;
//		private final static int E_OK_CHANGED = 3;
//		private final static int E_ERROR = 2;
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			getSherlockActivity().setProgressBarIndeterminateVisibility(true);
//		}
//
//		@Override
//		protected Integer doInBackground(Integer... params) {
//			ArrayList<News> retval = NetworkUtils.getPosts(params[0]);
//
//			if (getSherlockActivity() != null) {
//
//				if (mAdapter == null) {
//					if (retval != null && retval.size() > 0) {
//						log.info("first load " + retval.size());
//						if (params[0] == 1) {
//							JalinSuaraSingleton
//									.getInstance(getSherlockActivity())
//									.getNewsList().clear();
//						}
//						JalinSuaraSingleton.getInstance(getSherlockActivity())
//								.getNewsList().addAll(retval);
//
//						mAdapter = new NewsAdapter(getSherlockActivity(),
//								JalinSuaraSingleton.getInstance(
//										getSherlockActivity()).getNewsList());
//					} else if (retval != null && retval.size() == 0) {
//						log.info("no data");
//					} else if (retval == null) {
//						return E_ERROR;
//					}
//				} else {
//					if (retval != null && retval.size() > 0) {
//						log.info("add new " + retval.size() + " items");
//						mLastScrollY = getListView().getFirstVisiblePosition();
//
//						JalinSuaraSingleton.getInstance(getSherlockActivity())
//								.getNewsList().addAll(retval);
//
//						return E_OK_CHANGED;
//					} else {
//						loading = false;
//					}
//				}
//			} else {
//				return E_ERROR;
//			}
//			return E_OK;
//		}
//
//		@Override
//		protected void onPostExecute(Integer result) {
//			super.onPostExecute(result);
//
//			if (getSherlockActivity() != null) {
//				getSherlockActivity().setProgressBarIndeterminateVisibility(
//						false);
//				if (result == E_OK) {
//
//					resetStatus();
//					setStatusShowContent();
//
//				} else if (result == E_OK_CHANGED) {
//					mAdapter.notifyDataSetChanged();
//					getListView().setSelectionFromTop(mLastScrollY, 0);
//				} else {
//					loading = false;
//					resetStatus();
//					setStatusError(getString(R.string.error));
//				}
//			}
//		}
//	}

	public interface OnNewsItemClickListener {
		public void onNewsItemClickListener(News news, int position);
	}
}
