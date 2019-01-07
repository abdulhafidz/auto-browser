/**
 * 
 */
package com.hafidz.autoBrowser.eventHandler;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.constant.ResultConstant;
import com.hafidz.autoBrowser.playback.PlaybackMonitor;
import com.hafidz.autoBrowser.playback.PlaybackRunner;
import com.hafidz.autoBrowser.record.AbstractRecording;
import com.hafidz.autoBrowser.util.Recorder;
import com.hafidz.autoBrowser.util.SystemTimeFormatter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

/**
 * @author 608845362
 *
 */
public class PlayButtonHandler implements EventHandler<ActionEvent> {

	// private TextArea playbackList;

	public PlayButtonHandler(TextArea playbackList, WebView testResultView) {
		// this.playbackList = playbackList;

		testResultView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {

			@Override
			public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue,
					Worker.State newValue) {

				if (newValue == Worker.State.SUCCEEDED) {

					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {

							Platform.runLater(new Runnable() {

								@Override
								public void run() {

									Main.playBT.setDisable(true);
									Main.playBT.setText("Playing...");
									Main.stopBT.setDisable(false);
									Main.playbackList.setEditable(false);

									// disable recording
									Recorder.disableRecording();

									// enable Test Results tab and focus
									Main.testResultTab.setDisable(false);
									Main.activityTabPanel.getSelectionModel().select(Main.testResultTab);

								}
							});

							String rawRecordings[] = playbackList.getText().split("\n\n");

							try {

								PlaybackMonitor.startMonitoring(rawRecordings);

								for (AbstractRecording record : PlaybackMonitor.getPlaybacks().values()) {

									if (PlaybackMonitor.gotFailed()) {
										System.out.println("stopping playback because test failed.");
										break;
									}

									if (PlaybackMonitor.gotStopRequest()) {
										System.out.println("stopping playback by user.");
										break;
									}

									// currRawRecord = rawRecord;

									// apply delay
									if (record.getDelay() != null) {
										System.out.println(
												"sleeping for " + SystemTimeFormatter.format(record.getDelay()));
										Thread.sleep(record.getDelay());
									}

									// run the test
									Platform.runLater(new PlaybackRunner(record));

								}

								PlaybackMonitor.stopMonitoring();

							} catch (Exception e) {
								e.printStackTrace();
							}

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									Main.playBT.setDisable(false);
									Main.playBT.setText("Play");

									Main.stopBT.setDisable(true);
									Main.stopBT.setText("Stop");

									Main.playbackList.setEditable(true);

									Recorder.enableRecording();

								}
							});

						}
					});
					thread.start();

					System.out.println("testing thread started");

				}

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {

		// this will trigger the load listener (init in this class constructor),
		// that will eventually trigger the
		// test run
		Main.testResultList.getEngine().loadContent(ResultConstant.RESULT_HTML_TEMPLATE);

	}

	// private class ExceptionAlert implements Runnable {
	//
	// String recording;
	// Exception e;
	//
	// public ExceptionAlert(String recording, Exception e) {
	// this.recording = recording;
	// this.e = e;
	// }
	//
	// public void run() {
	// Alert alert = new Alert(AlertType.ERROR);
	// alert.setTitle("Test failed!");
	// alert.setContentText(
	// "Recording\n- - - - - - - - - -\n" + recording + "\n\nError\n- - - - - -
	// - - - -\n" + e.toString());
	// alert.showAndWait();
	// }
	// }

}
