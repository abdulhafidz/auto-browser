package com.hafidz.autoBrowser.listener;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.hafidz.autoBrowser.Main;
import com.hafidz.autoBrowser.util.DomUtil;
import com.hafidz.autoBrowser.util.StringUtil;

public class MouseOverListener implements EventListener {

	@Override
	public void handleEvent(Event evt) {

		// update component info
		Main.componentInfo.setText(StringUtil.shorten(StringUtil.removeMultiLines(DomUtil.constructTag(evt)), 75));
		Object value = DomUtil.getValue(evt);
		if (value != null)
			Main.componentValue.setText(StringUtil.removeMultiLines(value.toString()));
		Main.componentXpath.setText(StringUtil.removeMultiLines(DomUtil.getXPath(evt)));

	}

}