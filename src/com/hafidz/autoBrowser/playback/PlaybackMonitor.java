/**
 * 
 */
package com.hafidz.autoBrowser.playback;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.constant.RecordingConstant.TestResult;
import com.hafidz.autoBrowser.record.AbstractRecording;
import com.hafidz.autoBrowser.util.Recorder;
import com.hafidz.autoBrowser.util.StringUtil;

/**
 * @author 608845362
 *
 */
public class PlaybackMonitor {

	private static boolean gotError = false;
	private static boolean stopRequested = false;

	private static Map<Integer, AbstractRecording> playbacks = new LinkedHashMap<>();

	public static void startMonitoring(String[] rewRecordings)
			throws InstantiationException, IllegalAccessException, ParseException {

		// reset state
		gotError = false;
		stopRequested = false;
		playbacks.clear();

		// parse playback raw text to objects
		int count = 1;
		for (String rawRecord : rewRecordings) {
			if (!rawRecord.trim().isEmpty()) {
				AbstractRecording rec = Recorder.parse(rawRecord);

				rec.setOrder(count);

				// set all the value to waiting
				rec.setTestResult(TestResult.WAITING);

				playbacks.put(count, rec);

				count++;

			}
		}

	}

	public static void updateTestResultView(AbstractRecording rec) throws SAXException, IOException, ParserConfigurationException {

		NodeList nodeList = Main.testResultList.getEngine().getDocument().getElementsByTagName("body");

		rec.outputResult(nodeList.item(0));

		// // debug
		// try {
		// System.out.println("html = " +
		// DomUtil.format(Main.testResultList.getEngine().getDocument()));
		// } catch (ClassNotFoundException | InstantiationException |
		// IllegalAccessException | ClassCastException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		
		// auto scroll to last
		Main.testResultList.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");

	}

	public static void stopMonitoring() {
	}

	public static void startTest(PlaybackRunner runner) {
		System.out.println("- - - [START PLAYBACK] - - -");
	}

	public static void testPass(PlaybackRunner runner) {

		// update UI
		runner.getRecord().setTestResult(TestResult.PASS);
		try {
			updateTestResultView(runner.getRecord());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			fail(runner, e);
			endTest(runner);
		}

	}

	public static void endTest(PlaybackRunner runner) {
		System.out.println("- - - [END PLAYBACK] - - -\n");
	}

	public static void fail(PlaybackRunner runner, Exception e) {
		gotError = true;

		// update UI
		runner.getRecord().setTestResult(TestResult.FAILED);
		runner.getRecord().setFailedReason(StringUtil.removeMultiLines(e.toString()));

		try {
			updateTestResultView(runner.getRecord());
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
		}

	}

	public static boolean gotFailed() {
		return gotError;
	}

	public static void stopPlayback() {
		stopRequested = true;
	}

	public static boolean gotStopRequest() {
		return stopRequested;
	}

	public static Map<Integer, AbstractRecording> getPlaybacks() {
		return playbacks;
	}
}
