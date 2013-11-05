package com.jalinsuara.android.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.jalinsuara.android.LicenseActivity;
import com.jalinsuara.android.R;

/**
 * Dialog that will be used for viewing the application version and additional
 * information
 * 
 * @author tonoman3g
 */
public class AboutDialog extends AlertDialog {

	public AboutDialog(final Context context, final FragmentManager fm) {
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

		final Button licenseButton = new Button(context);
		licenseButton.setText(context.getString(R.string.see_license));
		licenseButton.setPadding(10, 0, 10, 0);
		licenseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, LicenseActivity.class);
				context.startActivity(intent);

			}
		});

		final Button googleMapsLegalButton = new Button(context);
		googleMapsLegalButton.setText(context
				.getString(R.string.see_google_maps_legal_notice));
		googleMapsLegalButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				GoogleMapLegalDialog dialog = new GoogleMapLegalDialog(
						GooglePlayServicesUtil
								.getOpenSourceSoftwareLicenseInfo(context));
				dialog.show(fm, context.getString(R.string.legal_notice));

			}
		});
		googleMapsLegalButton.setPadding(10, 0, 10, 0);

		final ScrollView screen = new ScrollView(context);

		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);

		layout.addView(message);
		layout.addView(licenseButton);
		layout.addView(googleMapsLegalButton);

		screen.addView(layout);

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