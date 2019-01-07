/**
 * 
 */
package com.hafidz.autoBrowser.util;

/**
 * @author 608845362
 *
 */
public class StringUtil {

	public static String removeMultiLines(String src) {
		if (src == null)
			return null;

		return src.replace("\n", "").replace("\r", "");
	}

	public static String shorten(String text, int maxLength) {

		if(text == null)
			return null;
		
		if (text.length() > maxLength) {
			return text.substring(0, maxLength) + "...";
		} else
			return text;
	}
}
