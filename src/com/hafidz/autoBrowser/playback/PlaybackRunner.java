/**
 * 
 */
package com.hafidz.autoBrowser.playback;

import com.hafidz.autoBrowser.record.AbstractRecording;

/**
 * @author 608845362
 *
 */
public class PlaybackRunner implements Runnable {

	private AbstractRecording record;

	public PlaybackRunner(AbstractRecording record) {
		super();
		this.record = record;
	}

	@Override
	public void run() {

		PlaybackMonitor.startTest(this);

		try {
			record.playback();

			PlaybackMonitor.testPass(this);

		} catch (Exception e) {

			e.printStackTrace();
			
			PlaybackMonitor.fail(this, e);
		} finally {
			PlaybackMonitor.endTest(this);
		}

	}

	public AbstractRecording getRecord() {
		return record;
	}

}
