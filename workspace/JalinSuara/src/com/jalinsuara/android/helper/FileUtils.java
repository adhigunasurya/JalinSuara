package com.jalinsuara.android.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * File utils
 * 
 * @author tonoman3g
 * 
 */
public class FileUtils {

	/**
	 * Get real file path from uri
	 * 
	 * @param context
	 * @param contentURI
	 * @return
	 */
	public static String getRealPathFromURI(Context context, Uri contentURI) {
		Cursor cursor = context.getContentResolver().query(contentURI, null,
				null, null, null);
		if (cursor == null) {
			// Source is Dropbox or other similar local file path
			return contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(idx);
		}
	}

	/**
	 * Get extension from file path
	 * 
	 * @param file
	 * @return
	 */
	public static String getExtension(String file) {
		if (file != null) {
			int idx = file.lastIndexOf(".");
			if (idx != -1 && (idx + 1) < file.length()) {
				return file.substring(idx + 1).toLowerCase();
			}
		}
		return null;
	}
}
