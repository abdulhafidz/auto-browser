package com.hafidz.autoBrowser.listener;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.hafidz.autoBrowser.record.AbstractRecording;
import com.hafidz.autoBrowser.record.ChangeRecording;
import com.hafidz.autoBrowser.util.DomUtil;
import com.hafidz.autoBrowser.util.Recorder;

public class ChangeListener implements EventListener {

	private long prevClickTime = -1;

	@Override
	public void handleEvent(Event evt) {

		long time = System.currentTimeMillis();

		// keji way to prevent double click trigger.. donno why this
		// happened
		long delay = time - prevClickTime;

		// if (prevClick != evt.getTarget() || prevClickTime == -1 || delay >
		// 10) {
		if (prevClickTime == -1 || delay > 50) {

			AbstractRecording input = new ChangeRecording();
			input.setElementTag(DomUtil.constructTag(evt));
			input.setElementValue(DomUtil.getValue(evt));
			input.setElementXpath(DomUtil.getXPath(evt));
			Recorder.addActivity(input);

		}

		// prevClick = evt.getTarget();
		prevClickTime = time;

	}

}