package com.jalinsuara.android.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jalinsuara.android.R;

/**
 * Dialog that will be used for viewing the application version and additional
 * information
 * 
 * 
 * @author tonoman3g
 */
public class AboutDialog extends AlertDialog {

	public AboutDialog(Context context) {
		super(context);

		final SpannableString s = new SpannableString(context
				.getText(R.string.about_content)
				.toString()
				.replace("${VERSION}", getVersion(context))
				.replace("${APP_NAME}",
						context.getText(R.string.app_name).toString()));
		Linkify.addLinks(s, Linkify.WEB_URLS);

		final TextView message = new TextView(context);
		message.setText(s);
		message.setMovementMethod(LinkMovementMethod.getInstance());
		message.setLinksClickable(true);
		message.setTextAppearance(context,
				android.R.style.TextAppearance_Medium);
		message.setPadding(10, 0, 10, 0);
		message.setLinkTextColor(Color.BLUE);

		final ScrollView screen = new ScrollView(context);
		screen.addView(message);

		setView(screen);
		setTitle(R.string.action_about);
		setIcon(R.drawable.ic_about);
		setCancelable(true);
		setButton(BUTTON_POSITIVE, context.getText(R.string.close),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						AboutDialog.this.cancel();
					}
				});
	}

	public static String getVersion(Context context) {
		final String unknown = "-";

		if (context == null) {
			return unknown;
		}

		try {
			String ret = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
			if (ret.contains(" + "))
				ret = ret.substring(0, ret.indexOf(" + ")) + "b";

			return ret;
		} catch (NameNotFoundException ex) {
			return unknown;
		}
	}
}