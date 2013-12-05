package com.jalinsuara.android.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.helper.NetworkUtils;
import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.ShareNewsActivity;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Fragment to show sub project details
 * 
 * @author tonoman3g
 * @author gabriellewp
 * 
 */
public class SubProjectFragment extends BaseFragment {

	private SubProject mSubProject;

	/**
	 * Related stories
	 */
	private ArrayList<News> mRelatedStoriesList;

	private ImageView mImageView;

	private TextView mTitleTextView;

	private TextView mSubTitle1;

	private TextView mInformationTitle1;

	private TextView mInformationContent1;
	private TextView mInformationContent2;
	private TextView mInformationContent4;
	private TextView mInformationContent5;
	private TextView mInformationContent6;

	private TextView mInformationTitle2;
	private TextView mInformationContent21;
	private TextView mInformationContent22;
	private TextView mInformationContent23;
	private TextView mInformationContent24;
	private TextView mInformationContent25;

	private TextView mSubTitle2;
	private TextView mSubTitle3;

	/**
	 * Layout for showing additional information
	 */
	private LinearLayout mAdditionalInformationLayout;

	/**
	 * Dynamic text view for showing additional information
	 */
	private TextView mTextViewDynamic;

	/**
	 * Layout for showing related stories
	 */
	private LinearLayout mRelatedStoriesLayout;

	public SubProjectFragment() {

	}
	
	public SubProjectFragment(SubProject subproject) {
		mSubProject = subproject;
		log.info("project: " + mSubProject);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_subprojects;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);

		if (mSubProject != null) {

			mImageView = (ImageView) getView().findViewById(
					R.id.fragment_sub_project_image_imageview);
			mTitleTextView = (TextView) getView().findViewById(
					R.id.fragment_sub_project_title_textview);
			mSubTitle1 = (TextView) getView().findViewById(
					R.id.fragment_sub_project_title_textview);
			mInformationTitle1 = (TextView) getView().findViewById(
					R.id.fragment_sub_project_information_title_textview);
			mInformationContent1 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_maleproposalquantity_textview);
			mInformationContent2 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_femaleproposalquantity_textview);
			mInformationContent4 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_projectlength_textview);
			mInformationContent5 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_projectarea_textview);
			mInformationContent6 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_projectquantity_textview);
			mInformationTitle2 = (TextView) getView().findViewById(
					R.id.fragment_sub_project_information_title_2_textview);
			mInformationContent21 = (TextView) getView().findViewById(
					R.id.fragment_sub_project_information_content_BLM_textview);
			mInformationContent22 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_nongovernmental_textview);
			mInformationContent23 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_malebeneficiaries_textview);
			mInformationContent24 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_femalebeneficiaries_textview);
			mInformationContent25 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_content_poorbeneficiaries_textview);
			mSubTitle2 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_information_title_additionalinfo_textview);
			mSubTitle3 = (TextView) getView()
					.findViewById(
							R.id.fragment_sub_project_sub_title_relatedstory_title_textview);

			mRelatedStoriesLayout = (LinearLayout) getView().findViewById(
					R.id.fragment_sub_project_relatedstory_layout);
			mAdditionalInformationLayout = (LinearLayout) getView()
					.findViewById(
							R.id.fragment_sub_project_information_additional_layout);

			loadData();

			resetStatus();
			setStatusShowContent();
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}
	}

	/**
	 * Load data and set data to respected view
	 */
	private void loadData() {
		// load images
		if (mSubProject.getPictureUrl() != null
				&& mSubProject.getPictureUrl().length() > 0) {
			ImageLoader loader = new ImageLoader(getSherlockActivity());
			loader.DisplayImage(mSubProject.getPictureUrl(), mImageView);
		}

		mTitleTextView.setText(mSubProject.getName());

		if (mSubProject.getName() != null) {
			mTitleTextView.setText(mSubProject.getName());
		}

		mInformationContent1.setText("Jumlah Proposal(L): "
				+ mSubProject.getMaleProposal());
		mInformationContent2.setText("Jumlah Proposal(P): "
				+ mSubProject.getMaleProposal());
		mInformationContent4.setText("Panjang Proyek: "
				+ mSubProject.getProjectLength());
		mInformationContent5.setText("Panjang Proyek: "
				+ mSubProject.getProjectArea());
		mInformationContent6.setText("Panjang Proyek: "
				+ mSubProject.getProjectQuantity());
		mInformationContent21.setText("BLM: " + mSubProject.getBlmAmount());
		mInformationContent22.setText("Swadaya Masyarakat: "
				+ mSubProject.getSelfFundAmount());
		mInformationContent23.setText("Penerima Manfaat (L): "
				+ mSubProject.getMaleBeneficiary());
		mInformationContent24.setText("Penerima Manfaat (P): "
				+ mSubProject.getFemaleBeneficiary());
		mInformationContent25.setText("Penerima Manfaat (Miskin): "
				+ mSubProject.getPoorBeneficiary());

		initDynamicAttributes();
		initRelatedStories();
	}

	/**
	 * Load related stories
	 */
	private void initRelatedStories() {

		mRelatedStoriesLayout.setVisibility(View.GONE);
		LoadRelatedStory task = new LoadRelatedStory();
		task.execute(String.valueOf(mSubProject.getId()));
	}

	/**
	 * Populate related stories to view
	 */
	private void populateRelatedStories() {
		if (mRelatedStoriesList.size() > 0) {
			mRelatedStoriesLayout.setVisibility(View.VISIBLE);

			for (Iterator<News> i = mRelatedStoriesList.iterator(); i.hasNext();) {
				TextView tv = new TextView(getSherlockActivity());

				News news = i.next();

				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

				Resources r = getSherlockActivity().getResources();
				int px = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

				int px2 = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 2, r.getDisplayMetrics());

				if (!i.hasNext()) {
					params.setMargins(px, 0, 0, px);
				} else {
					params.setMargins(px, 0, 0, px2);
				}

				tv.setLayoutParams(params);
				tv.setText(news.getTitle());

				mRelatedStoriesLayout.addView(tv);
			}
		}

	}

	/**
	 * Load and show dynamic attributes in view
	 */
	private void initDynamicAttributes() {

		if (mSubProject.getDynamicAttributes() != null
				&& mSubProject.getDynamicAttributes().size() > 0) {

			Set keys = mSubProject.getDynamicAttributes().keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				String value = mSubProject.getDynamicAttributes().get(key);
				mTextViewDynamic = new TextView(getSherlockActivity());

				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				Resources r = getSherlockActivity().getResources();
				int px = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

				int px2 = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 2, r.getDisplayMetrics());

				if (!i.hasNext()) {
					params.setMargins(px, 0, 0, px);
				} else {
					params.setMargins(px, 0, 0, px2);
				}

				mTextViewDynamic.setLayoutParams(params);
				mTextViewDynamic.setText(key.replace("field_", "").replace("_",
						" ")
						+ " : " + value);
				// mTextViewDynamic.setText((String)i.next()+" : "+(String)mSubProject.getDynamicAttributes().get((String)i.next()));
				mAdditionalInformationLayout.addView(mTextViewDynamic);

			}

		} else {
			log.info("tak ada nilai dynamic attr");
			mAdditionalInformationLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_subproject, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_share_news) {
			Intent intent = new Intent(getSherlockActivity(),
					ShareNewsActivity.class);
			intent.putExtra(ShareNewsActivity.EXTRA_TRIGGERED_IN_TYPE,
					ShareNewsActivity.TYPE_SUB_PROJECT_DETAIL);
			intent.putExtra(ShareNewsActivity.EXTRA_ID, mSubProject.getId());
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Load related stories from server
	 * 
	 * @author hartono
	 * 
	 */
	private class LoadRelatedStory extends AsyncTask<String, Integer, Integer> {

		private final static int E_OK = 1;
		private final static int E_ERROR = 2;

		@Override
		protected Integer doInBackground(String... params) {
			String subprojectId = params[0];

			if (params.length == 1) {
				mRelatedStoriesList = NetworkUtils.getPosts(
						Long.parseLong(subprojectId), 0);
				if (mRelatedStoriesList != null) {
					return E_OK;
				}
			}
			return E_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (!getSherlockActivity().isFinishing()) {
				if (result == E_OK) {
					populateRelatedStories();
				} else {
					log.error("error when loading related stories");
				}
			}
		}
	}

}
