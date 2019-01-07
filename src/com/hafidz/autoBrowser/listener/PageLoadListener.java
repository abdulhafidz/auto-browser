/**
 * 
 */
package com.hafidz.autoBrowser.listener;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.record.AbstractRecording;
import com.hafidz.autoBrowser.record.PageLoadedRecording;
import com.hafidz.autoBrowser.util.ListenerUtil;
import com.hafidz.autoBrowser.util.Recorder;
import com.hafidz.autoBrowser.util.WebUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;

/**
 * @author 608845362
 *
 */
public class PageLoadListener implements ChangeListener<Worker.State> {

	@Override
	public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue,
			Worker.State newValue) {

		if (newValue == Worker.State.SUCCEEDED) {

			AbstractRecording input = new PageLoadedRecording();
			input.setElementValue(Main.browser.getEngine().getLocation());
			Recorder.addActivity(input);

			// clear
			Main.componentInfo.setText("");
			Main.componentValue.setText("");
			Main.componentXpath.setText("");

			Main.stupidBT.setDisable(false);
			Main.stupidBT.setText("Analyze");

			// trigger listener registration, Javascript may not be loaded fully
			// at this time. Thats why we add the "Stupid" button.
			ListenerUtil.updateListeners();

		}

		else if (newValue == Worker.State.RUNNING) {
			WebUtil.loading();
		}

	}

}
