/**
 * 
 */
package com.hafidz.autoBrowser.record;

import com.hafidz.autoBrowser.constant.RecordingConstant.Type;
import com.hafidz.autoBrowser.util.WebUtil;

/**
 * @author 608845362
 *
 */
public class PageOpenRecording extends AbstractRecording {

	public PageOpenRecording() {
		setType(Type.PAGE_OPEN);
	}


	@Override
	public void playback() {
		System.out.println("playback : loading page - " + getElementValue());

		WebUtil.openURL((String) getElementValue());
	}

}
