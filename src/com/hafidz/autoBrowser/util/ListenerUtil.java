package com.hafidz.autoBrowser.util;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.listener.ChangeListener;
import com.hafidz.autoBrowser.listener.EnterPressedListener;
import com.hafidz.autoBrowser.listener.MouseClickListener;
import com.hafidz.autoBrowser.listener.MouseLeaveListener;
import com.hafidz.autoBrowser.listener.MouseOverListener;
import com.hafidz.autoBrowser.record.ChangeRecording;
import com.hafidz.autoBrowser.record.ClickRecording;

import javafx.scene.web.WebView;

public class ListenerUtil {

	// to make sure no duplication of event listener addition happen
	private static Set<EventTarget> eventTargets = new HashSet<>();

	private static EventListener clickListener = new MouseClickListener();
	private static MouseOverListener hoverListener = new MouseOverListener();
	private static MouseLeaveListener mouseLeaveListener = new MouseLeaveListener();
	// private static KeyPressListener keyPressListener = new
	// KeyPressListener();
	private static EventListener changeListener = new ChangeListener();
	// private static FocusBlurListener focusBlurListener = new
	// FocusBlurListener();

	private static EventListener keyPressListener = new EnterPressedListener();

	public static void clearListeners() {
		eventTargets.clear();
	}

	// TODO : also add form submit listener (when user press enter in text
	// field)
	private static void addListeners(Node node) {
		EventTarget target = (EventTarget) node;
		if (!eventTargets.contains(target)) {

			// System.out.println(node.getNodeName());

			target.addEventListener("mouseover", hoverListener, false);
			target.addEventListener("mouseleave", mouseLeaveListener, false);

			// input and select should be tracked by change
			// if (trackClick) {
			target.addEventListener("click", clickListener, true);
			// }

			// if (trackChange)
			target.addEventListener("change", changeListener, true);

			// FocusBlurListener focusBlurListener = new FocusBlurListener();
			// target.addEventListener("focus", focusBlurListener, false);
			// target.addEventListener("blur", focusBlurListener, false);

			target.addEventListener("keypress", keyPressListener, true);

			eventTargets.add(target);
		}
	}

	public static void updateListeners() {

		System.out.println("update listeners called");

		WebView browser = Main.browser;

		if (browser == null)
			return;

		if (browser.getEngine().getDocument() != null) {

			NodeList buttons = browser.getEngine().getDocument().getElementsByTagName("button");
			for (int index = 0; index < buttons.getLength(); index++) {
				Node node = buttons.item(index);
				addListeners(node);

			}

			NodeList inputs = browser.getEngine().getDocument().getElementsByTagName("input");
			for (int index = 0; index < inputs.getLength(); index++) {
				Node node = inputs.item(index);
				addListeners(node);
			}

			NodeList links = browser.getEngine().getDocument().getElementsByTagName("a");
			for (int index = 0; index < links.getLength(); index++) {
				Node node = links.item(index);
				addListeners(node);
			}

			NodeList divs = browser.getEngine().getDocument().getElementsByTagName("div");
			for (int index = 0; index < divs.getLength(); index++) {
				Node node = divs.item(index);
				addListeners(node);
			}

			NodeList spans = browser.getEngine().getDocument().getElementsByTagName("span");
			for (int index = 0; index < spans.getLength(); index++) {
				Node node = spans.item(index);
				addListeners(node);
			}

			NodeList lis = browser.getEngine().getDocument().getElementsByTagName("li");
			for (int index = 0; index < lis.getLength(); index++) {
				Node node = lis.item(index);
				addListeners(node);
			}

			NodeList selects = browser.getEngine().getDocument().getElementsByTagName("select");
			for (int index = 0; index < selects.getLength(); index++) {
				Node node = selects.item(index);
				addListeners(node);
			}

			// NodeList options =
			// browser.getEngine().getDocument().getElementsByTagName("option");
			// for (int index = 0; index < options.getLength(); index++) {
			// Node node = options.item(index);
			// addListeners(node);
			// }

		}
	}
}
