package com.hafidz.autoBrowser.listener;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.html.HTMLElement;

import com.hafidz.autoBrowser.record.AbstractRecording;
import com.hafidz.autoBrowser.record.ClickRecording;
import com.hafidz.autoBrowser.record.InspectRecording;
import com.hafidz.autoBrowser.util.DomUtil;
import com.hafidz.autoBrowser.util.ListenerUtil;
import com.hafidz.autoBrowser.util.Recorder;

public class MouseClickListener implements EventListener {

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

			// because......
			ListenerUtil.updateListeners();

			if (evt.getTarget() instanceof com.sun.webkit.dom.HTMLInputElementImpl
					|| evt.getTarget() instanceof com.sun.webkit.dom.HTMLSelectElementImpl) {
				// com.sun.webkit.dom.HTMLInputElementImpl input =
				// (HTMLInputElementImpl) evt.getTarget();
				// boolean editable = input.getIsContentEditable();
				// System.out.println("editable = " + editable);

				// Node node = (Node) evt.getTarget();
				// String readonly = DomUtil.getAttributeValue(node,
				// "readonly");
				// if (readonly != null && (readonly.isEmpty() ||
				// readonly.equalsIgnoreCase("true"))) {

				if (DomUtil.isReadOnlyOrDisabled((HTMLElement) evt.getTarget())) {
					AbstractRecording input = new InspectRecording();
					input.setElementTag(DomUtil.constructTag(evt));
					input.setElementValue(DomUtil.getValue(evt));
					input.setElementXpath(DomUtil.getXPath(evt));
					Recorder.addActivity(input);

					// TODO : confirm the value to be inspect

				} else {
					// TODO : ask, want to inspect or type

					// do nothing because change of value already tracked by
					// ChangeListener

				}

			}

			// click is only for button, anchor and attribute with id only....
			else if (true || evt.getTarget() instanceof com.sun.webkit.dom.HTMLButtonElementImpl
					|| evt.getTarget() instanceof com.sun.webkit.dom.HTMLAnchorElementImpl
			/*
			 * || HtmlUtil.getAttributeValue((Node) evt.getTarget(), "id") !=
			 * null
			 */) {

				// System.out.println("class name , else , click listener = " +
				// evt.getTarget().getClass().getName());

				AbstractRecording input = new ClickRecording();
				input.setElementTag(DomUtil.constructTag(evt));
				input.setElementValue(DomUtil.getValue(evt));
				input.setElementXpath(DomUtil.getXPath(evt));
				Recorder.addActivity(input);
			}

		}

		// prevClick = evt.getTarget();
		prevClickTime = time;

	}

}