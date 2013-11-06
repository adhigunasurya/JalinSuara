package com.jalinsuara.android.project;

import java.util.Iterator;
import java.util.Set;

import android.os.Bundle;
import android.text.Html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalinsuara.android.helpers.lazylist.ImageLoader;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.SubProject;
import com.jalinsuara.android.BaseFragment;
import com.jalinsuara.android.R;

public class SubProjectFragment extends BaseFragment {

	private static final String TAG = null;
	private SubProject mSubProject;
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
	private LinearLayout mAdditionalInformation;
	private TextView mTextViewDynamic;

	public SubProjectFragment(SubProject subproject) {
		mSubProject = subproject;
		log.info("project: " + mSubProject);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_sub_projects;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
			ImageLoader loader = new ImageLoader(getSherlockActivity());
			// loader.DisplayImage(url, imageView);
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
			if (mSubProject.getDynamicAttributes() != null) {
				mAdditionalInformation = (LinearLayout) getView()
						.findViewById(
								R.id.fragment_sub_project_information_additional_layout);
				Set keys = mSubProject.getDynamicAttributes().keySet();
				for (Iterator i = keys.iterator(); i.hasNext();) {
					String key = (String) i.next();
					String value = mSubProject.getDynamicAttributes().get(key);
					mTextViewDynamic = new TextView(getSherlockActivity());
					mTextViewDynamic.setText(key.replace("field_", "").replace("_", " ") + " : " + value);
					// mTextViewDynamic.setText((String)i.next()+" : "+(String)mSubProject.getDynamicAttributes().get((String)i.next()));
					mAdditionalInformation.addView(mTextViewDynamic);
					
				}

			} else {
				log.info("tak ada nilai dynamic attr");
			}

			resetStatus();
			setStatusShowContent();
		} else {
			resetStatus();
			setStatusError(getString(R.string.error));
		}

	}

}
