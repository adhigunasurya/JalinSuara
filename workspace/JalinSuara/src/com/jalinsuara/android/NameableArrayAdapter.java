package com.jalinsuara.android;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ArrayAdapter for {@link Nameable} It is used for spinner. Simplicity is
 * everything.
 * 
 * @author tonoman3g
 * 
 */
public class NameableArrayAdapter extends ArrayAdapter<Nameable> {

	public NameableArrayAdapter(Context context,
			List<? extends Nameable> objects) {
		super(context, android.R.layout.simple_spinner_item);
		addAll(objects);
		setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View retval = super.getDropDownView(position, convertView, parent);
		
		// show only the name
		((TextView) retval).setText(this.getItem(position).getName());
		return retval;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View retval = super.getView(position, convertView, parent);
		
		// show only the name
		((TextView) retval).setText(this.getItem(position).getName());
		return retval;
	}

}
