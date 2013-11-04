package com.jalinsuara.android.helpers;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Helper class that used for date conversion
 * 
 * @author tonoman3g
 */

@SuppressLint("SimpleDateFormat")
public class DateUtils {

	/**
	 * Date format that match the database
	 */
	public static SimpleDateFormat datebaseFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat dateOnlyFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * User friendly date format
	 */
	public static SimpleDateFormat userFriendlyDateFormat = new SimpleDateFormat(
			"dd/MM/yyyy   HH:mm");

	public static SimpleDateFormat userFriendlyTimeOnlyFormat = new SimpleDateFormat(
			"HH:mm");

	/**
	 * Helper functions to convert datestring returned from database to java
	 * date
	 * 
	 * @param dateString
	 * @return
	 */
	/**
	 * @param dateString
	 * @return
	 */
	public static Date fromDatabaseString(String dateString) {
		try {
			Date convertedDate = datebaseFormat.parse(dateString);
			return convertedDate;
		} catch (ParseException e) {
		}
		return new Date();
	}

	/**
	 * Helper functions to convert date to string that matched the database
	 * format
	 * 
	 * @param date
	 * @return
	 */
	public static String toDatabaseString(Date date) {
		if (date != null)
			return datebaseFormat.format(date);
		else
			return "";
	}

	/**
	 * Format date to "dd/MM/yyyy   HH:mm"
	 * 
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		return userFriendlyDateFormat.format(date);
	}

	/**
	 * Format date to "HH:mm"
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringTimeOnly(Date date) {
		return userFriendlyTimeOnlyFormat.format(date);
	}

	/**
	 * Format date to "yyyy-MM-dd"
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringDateOnly(Date date) {
		return dateOnlyFormat.format(date);
	}

	/**
	 * Helper for formatting time in duration "00 : 00 : 00"
	 * 
	 * @param millis
	 * @return
	 */
	public static String formatTime(long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		String output = "00:00:00";
		long seconds = c.get(Calendar.SECOND);
		long minutes = c.get(Calendar.MINUTE);
		long hours = c.get(Calendar.HOUR_OF_DAY);
		String secondsD = String.valueOf(seconds);
		String minutesD = String.valueOf(minutes);
		String hoursD = String.valueOf(hours);
		if (seconds < 10)
			secondsD = "0" + seconds;
		if (minutes < 10)
			minutesD = "0" + minutes;
		if (hours < 10)
			hoursD = "0" + hours;
		output = hoursD + " : " + minutesD + " : " + secondsD;
		return output;
	}

	/**
	 * Helper for formatting time in days - hours - minutes - seconds
	 * 
	 * @param millis
	 * @return
	 */
	public static String formatTimeString(long _seconds) {
		String output = "";
		long seconds = _seconds;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		seconds = seconds % 60;
		minutes = minutes % 60;
		hours = hours % 24;
		String secondsD = String.valueOf(seconds);
		String minutesD = String.valueOf(minutes);
		String hoursD = String.valueOf(hours);
		String daysD = String.valueOf(days);

		if (days != 0) {
			output = output + daysD;
			if (days == 1)
				output = output + " day ";
			else
				output = output + " days ";
		}

		if (hours != 0) {
			output = output + hoursD;
			if (hours == 1)
				output = output + " hour ";
			else
				output = output + " hours ";
		}

		if (minutes != 0) {
			output = output + minutesD;
			if (minutes == 1)
				output = output + " minute ";
			else
				output = output + " minutes ";
		}

		if (seconds != 0) {
			output = output + secondsD;
			if (seconds == 1)
				output = output + " second ";
			else
				output = output + " seconds ";
		}

		if ((seconds == 0) && (minutes == 0) && (hours == 0) && (days == 0))
			output = "-";
		return output;
	}

	/**
	 * @param minutes
	 *            duration in minutes
	 * @return "HH : mm"
	 */
	public static String formatDurationTime(int minutes) {
		String output = "00:00";
		int hours = minutes / 60;
		minutes = minutes % 60;
		String minutesD = String.valueOf(minutes);
		String hoursD = String.valueOf(hours);
		if (minutes < 10)
			minutesD = "0" + minutes;
		if (hours < 10)
			hoursD = "0" + hours;
		output = hoursD + " : " + minutesD;
		return output;
	}

	/**
	 * @param hours
	 * @param minutes
	 * @return "HH : mm"
	 */
	public static String formatDurationTime(int hours, int minutes) {
		String output = "00:00";
		String minutesD = String.valueOf(minutes);
		String hoursD = String.valueOf(hours);
		if (minutes < 10)
			minutesD = "0" + minutes;
		if (hours < 10)
			hoursD = "0" + hours;
		output = hoursD + " : " + minutesD;
		return output;
	}
}
