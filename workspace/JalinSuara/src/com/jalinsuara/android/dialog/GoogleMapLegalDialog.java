package com.jalinsuara.android.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.jalinsuara.android.R;

/**
 * Dialog that will be used to show legal notice
 * 
 * @author tonoman3g
 */
public class GoogleMapLegalDialog extends SherlockDialogFragment {

	private Builder mBuilder;
	private String mLegal;

	public GoogleMapLegalDialog(String legal) {
		mLegal = legal;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mBuilder = new Builder(getActivity());
		mBuilder.setMessage(mLegal)
				.setNeutralButton(getString(R.string.close),
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setTitle(getString(R.string.legal_notice));

		Dialog dialog = mBuilder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}
}