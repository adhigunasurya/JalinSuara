package com.jalinsuara.android.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

/**
 * <p>
 * Helper class for showing confirmation dialog with yes no button
 * <p>
 * 
 * @author tonoman3g
 */
public class ConfirmationDialog extends SherlockDialogFragment {

	private ConfirmationDialog.OnConfirmationDialogListener mListener;
	private Builder mBuilder;
	private String mMessage;
	private String mYes;
	private String mNo;
	private String mTitle;

	/**
	 * @param listener
	 * @param title
	 *            dialog title
	 * @param yes
	 *            text for yes button
	 * @param no
	 *            text for no button
	 * @param message
	 *            dialog message
	 */
	public ConfirmationDialog(
			ConfirmationDialog.OnConfirmationDialogListener listener,
			String title, String yes, String no, String message) {
		mListener = listener;
		mMessage = message;
		mNo = no;
		mYes = yes;
		mTitle = title;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mBuilder = new Builder(getActivity());
		mBuilder.setMessage(mMessage)
				.setPositiveButton(mYes, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mListener.onPositiveClick();
					}
				}).setNegativeButton(mNo, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						mListener.onNegativeClick();
					}
				}).setTitle(mTitle);
		Dialog dialog = mBuilder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	/**
	 * <p>
	 * Listener for {@link ConfirmationDialog}
	 * <p>
	 * 
	 * @author HW
	 * @version 0.2 refactoring by HW 28/07/2013
	 * @since 28/07/13
	 */
	public static interface OnConfirmationDialogListener {
		/**
		 * On positive button clicked
		 */
		public void onPositiveClick();

		/**
		 * On negative button click
		 */
		public void onNegativeClick();

	}
}
