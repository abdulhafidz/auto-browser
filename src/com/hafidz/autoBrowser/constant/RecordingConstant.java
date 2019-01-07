/**
 * 
 */
package com.hafidz.autoBrowser.constant;

import com.hafidz.autoBrowser.record.AbstractRecording;
import com.hafidz.autoBrowser.record.ChangeRecording;
import com.hafidz.autoBrowser.record.ClickRecording;
import com.hafidz.autoBrowser.record.EnterKeyPressRecording;
import com.hafidz.autoBrowser.record.InspectRecording;
import com.hafidz.autoBrowser.record.PageLoadedRecording;
import com.hafidz.autoBrowser.record.PageOpenRecording;

/**
 * @author 608845362
 *
 */
public class RecordingConstant {

	public static enum Type {
		PAGE_OPEN(PageOpenRecording.class), PAGE_LOADED(PageLoadedRecording.class), CLICK(ClickRecording.class), CHANGE(
				ChangeRecording.class), INSPECT(InspectRecording.class), ENTER_KEY_PRESS(EnterKeyPressRecording.class);

		private Class<? extends AbstractRecording> recordingClass;

		Type(Class<? extends AbstractRecording> recordingClass) {
			this.recordingClass = recordingClass;
		}

		public Class<? extends AbstractRecording> getRecordingClass() {
			return recordingClass;
		}

	}

	public static final String KEY_TYPE = "type : ";
	public static final String KEY_TAG = "tag : ";
	public static final String KEY_XPATH = "xpath : ";
	public static final String KEY_VALUE = "value : ";
	public static final String KEY_DELAY = "delay : ";
	public static final String KEY_TEST_RESULT = "result : ";
	public static final Object KEY_FAILED_REASON = "failed reason : ";

	public static final String KEY_TYPE_HTML = "type";
	public static final String KEY_TAG_HTML = "tag";
	public static final String KEY_XPATH_HTML = "xpath";
	public static final String KEY_VALUE_HTML = "value";
	public static final String KEY_DELAY_HTML = "delay";
	public static final String KEY_TEST_RESULT_HTML = "result";
	public static final Object KEY_FAILED_REASON_HTML = "reason";

	public static enum TestResult {
		PASS, FAILED, WAITING
	}
}
