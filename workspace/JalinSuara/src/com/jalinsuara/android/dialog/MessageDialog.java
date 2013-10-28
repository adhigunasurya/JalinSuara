
package com.jalinsuara.android.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

/**
 * <p>
 * Helper class for showing message dialog
 * <p>
 * 
 * @author tonoman3g

 */
public class MessageDialog extends SherlockDialogFragment {

    private MessageDialog.OnMessageDialogListener mListener;
    private Builder mBuilder;
    private String mTitle;
    private String mMessageTrue;
    private String mMessageNo;
    private String mButtonText;
    private boolean mConfirmed;

    /**
     * @param listener dialog listener
     * @param title message title
     * @param messageTrue if content is positive
     * @param messageNo if content is negative
     * @param buttonText button text
     * @param confirmed content's context
     */
    public MessageDialog(
            MessageDialog.OnMessageDialogListener listener, String title, String messageTrue,
            String messageNo,
            String buttonText, boolean confirmed) {
        mListener = listener;
        mTitle = title;
        mMessageTrue = messageTrue;
        mMessageNo = messageNo;
        mButtonText = buttonText;
        mConfirmed = confirmed;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBuilder = new Builder(getActivity());
        mBuilder
                .setNeutralButton(mButtonText, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mListener.onMessageDialogClicked(mConfirmed);
                    }
                }).setTitle(mTitle).setMessage(mConfirmed ? mMessageTrue : mMessageNo);
        Dialog dialog = mBuilder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mListener.onMessageDialogClicked(mConfirmed);
    }

    /**
     * <p>
     * Listener for {@link MessageDialog}
     * <p>
     * 
     * @author HW
     * @version 0.2 refactoring by HW 28/07/2013
     * @since 28/07/13
     */
    public static interface OnMessageDialogListener {
        public void onMessageDialogClicked(boolean confirmed);

    }
}
