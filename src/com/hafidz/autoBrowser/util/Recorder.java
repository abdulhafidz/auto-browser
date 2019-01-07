/**
 * 
 */
package com.hafidz.autoBrowser.util;

import java.text.ParseException;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.constant.RecordingConstant;
import com.hafidz.autoBrowser.constant.RecordingConstant.Type;
import com.hafidz.autoBrowser.record.AbstractRecording;

/**
 * @author 608845362
 *
 */
public class Recorder {

	public static AbstractRecording parse(String rawRecording)
			throws InstantiationException, IllegalAccessException, ParseException {

		AbstractRecording recording = null;

		String type = null;
		String tag = null;
		String xpath = null;
		String value = null;
		String delay = null;

		String[] rows = rawRecording.split("\n");
		for (String row : rows) {
			if (row.startsWith(RecordingConstant.KEY_TYPE) && row.split(RecordingConstant.KEY_TYPE).length > 0) {
				type = row.split(RecordingConstant.KEY_TYPE)[1];
			} else if (row.startsWith(RecordingConstant.KEY_TAG) && row.split(RecordingConstant.KEY_TAG).length > 0) {
				tag = row.split(RecordingConstant.KEY_TAG)[1];
			} else if (row.startsWith(RecordingConstant.KEY_XPATH)
					&& row.split(RecordingConstant.KEY_XPATH).length > 0) {
				xpath = row.split(RecordingConstant.KEY_XPATH)[1];
			} else if (row.startsWith(RecordingConstant.KEY_VALUE)
					&& row.split(RecordingConstant.KEY_VALUE).length > 0) {
				value = row.split(RecordingConstant.KEY_VALUE)[1];
			} else if (row.startsWith(RecordingConstant.KEY_DELAY)
					&& row.split(RecordingConstant.KEY_DELAY).length > 0) {
				delay = row.split(RecordingConstant.KEY_DELAY)[1];
			}

		}

		// determining class
		if (type != null) {
			Type typeObj = RecordingConstant.Type.valueOf(type);
			if (typeObj != null) {
				Class<? extends AbstractRecording> recClass = typeObj.getRecordingClass();
				if (recClass != null) {

					recording = (AbstractRecording) recClass.newInstance();

					System.out.println("recording class = " + recording.getClass().getName());

				}
			}
		}

		// populate the details
		if (recording != null) {
			recording.setElementTag(tag);
			recording.setElementXpath(xpath);
			recording.setElementValue(value);
			if (delay != null) {
				recording.setDelay(SystemTimeFormatter.parse(delay));
			}

		}

		return recording;
	}

	private static long prevTime = -1;

	public static void addActivity(AbstractRecording record) {

		if (Main.recording) {
			long time = System.currentTimeMillis();

			if (prevTime == -1)
				prevTime = time;

			record.setDelay(time - prevTime);

			prevTime = time;

			Main.recordingList.appendText(record.toString() + "\n\n");
		}
	}

	public static void disableRecording() {
		Main.recordBT.setDisable(true);
		Main.recordPauseBT.setDisable(true);
		Main.recordingList.setDisable(true);
		Main.recording = false;
		
		Main.recordingTab.setDisable(true);
	}

	public static void enableRecording() {
		Main.recordBT.setDisable(false);
		Main.recordPauseBT.setDisable(true);
		Main.recordingList.setDisable(false);
		Main.recording = false;
		
		Main.recordingTab.setDisable(false);
	}

}
