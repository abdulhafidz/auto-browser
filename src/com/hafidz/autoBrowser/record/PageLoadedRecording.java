/**
 * 
 */
package com.hafidz.autoBrowser.record;

import com.hafidz.autoBrowser.constant.RecordingConstant.Type;

/**
 * @author 608845362
 *
 */
public class PageLoadedRecording extends AbstractRecording {

	public PageLoadedRecording() {
		setType(Type.PAGE_LOADED);
	}


	@Override
	public void playback() {
		System.out.println("page should be loaded by now");

		// TODO : verify ori page loaded and current page loaded

	}

}
