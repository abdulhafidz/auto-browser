/**
 * 
 */
package com.hafidz.autoBrowser.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author 608845362
 *
 */
public class SystemTimeFormatter {

	private static DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
	private static DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

	public static String format(Long time) {

		if (time != null) {
			symbols.setGroupingSeparator(',');
			formatter.setDecimalFormatSymbols(symbols);

			return formatter.format(time) + " ms";
		}

		return null;
	}

	public static Long parse(String rawString) throws ParseException {
		if (rawString != null && rawString.endsWith(" ms")) {
			//rawString = rawString.substring(rawString.length() - 4, rawString.length() - 1);
			return formatter.parse(rawString).longValue();
		}
		return null;
	}

	// test
	public static void main(String[] args) {
		String s = format(System.currentTimeMillis());
		System.out.println("s = " + s);

		try {
			Long time = parse(s);
			System.out.println("time = " + time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
