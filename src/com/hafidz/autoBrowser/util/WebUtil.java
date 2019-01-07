/**
 * 
 */
package com.hafidz.autoBrowser.util;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.record.AbstractRecording;
import com.hafidz.autoBrowser.record.PageOpenRecording;

/**
 * @author 608845362
 *
 */
public class WebUtil {
	public static void openURL(String url) {

		loading();

		AbstractRecording input = new PageOpenRecording();
		input.setElementValue(url);
		Recorder.addActivity(input);

		ListenerUtil.clearListeners();

		Main.browser.getEngine().load(url);

	}

	public static void loading() {
		Main.stupidBT.setDisable(true);
		Main.stupidBT.setText("Loading...");

		// clear
		Main.componentInfo.setText("");
		Main.componentValue.setText("");
		Main.componentXpath.setText("");
	}

}
