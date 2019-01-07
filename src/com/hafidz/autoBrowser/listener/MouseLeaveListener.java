package com.hafidz.autoBrowser.listener;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.hafidz.autoBrowser.Main;

public class MouseLeaveListener implements EventListener {

	@Override
	public void handleEvent(Event evt) {
		Main.componentInfo.setText("");
		Main.componentValue.setText("");
		Main.componentXpath.setText("");
	}

}