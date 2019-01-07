package com.hafidz.autoBrowser.eventHandler;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.playback.PlaybackMonitor;
import com.hafidz.autoBrowser.util.Recorder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StopButtonHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		PlaybackMonitor.stopPlayback();

		Main.stopBT.setDisable(true);
		Main.stopBT.setText("Stopping...");

		// enable recording
		Recorder.enableRecording();
	}

}
