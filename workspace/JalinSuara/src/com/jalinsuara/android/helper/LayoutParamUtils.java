package com.jalinsuara.android.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Layout param utils
 * 
 * @author tonoman3g
 * 
 */
public class LayoutParamUtils {

	/**
	 * Convert from dp to px. Used in settings layout param programmaticaly
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int convertFromDpToPx(Context context, int dp) {
		Resources r = context.getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp, r.getDisplayMetrics());
		return px;
	}
}
