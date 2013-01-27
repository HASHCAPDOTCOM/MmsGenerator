/*
 * Copyright (C) 2012-2013 Hashcap Pvt. Ltd.
 */
package com.hashcap.mmsgenerator;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

public class InputFilterMinMax implements InputFilter {

	private int mMin, mMax; // paramets that you send to class
	private Context mContext;

	public InputFilterMinMax(Context context, int min, int max) {
		mContext = context;
		this.mMin = min;
		this.mMax = max;
	}

	public InputFilterMinMax(Context context, String min, String max) {
		mContext = context;
		this.mMin = Integer.parseInt(min);
		this.mMax = Integer.parseInt(max);
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		try {
			String startString = dest.toString().substring(0, dstart);
			String insert = source.toString();
			String endString = dest.toString().substring(dend);
			String parseThis = startString + insert + endString;
			int input = Integer.parseInt(parseThis);
			if (isInRange(mMin, mMax, input)) {
				return null;
			}else{
				Toast.makeText(mContext,
						"( " + mMin + " - " + mMax + " ) can be acceptable.",
						Toast.LENGTH_SHORT).show();
			}

		} catch (NumberFormatException nfe) {
		}
		return "";
	}

	private boolean isInRange(int a, int b, int c) {
		return b > a ? c >= a && c <= b : c >= b && c <= a;
	}
}
