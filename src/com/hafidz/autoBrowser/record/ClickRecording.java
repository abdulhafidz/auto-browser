/**
 * 
 */
package com.hafidz.autoBrowser.record;

import org.w3c.dom.Node;

import com.hafidz.autoBrowser.constant.RecordingConstant.Type;
import com.hafidz.autoBrowser.util.DomUtil;
import com.hafidz.autoBrowser.util.ListenerUtil;
import com.sun.webkit.dom.HTMLElementImpl;

/**
 * @author 608845362
 *
 */
public class ClickRecording extends AbstractRecording {

	public ClickRecording() {
		setType(Type.CLICK);
	}

	
	@Override
	public void playback() throws Exception {
		System.out.println("playback click");

		ListenerUtil.updateListeners();

		// get Node
		Node node = DomUtil.getNode(getElementXpath());

		System.out.println("node = " + node);

		if (node == null) {
			throw new Exception("Element not found!");
		}

		// compare element tag
		String oriTag = getElementTag();
		String playbackTag = DomUtil.constructTag(node);

		if (!oriTag.equals(playbackTag)) {
			throw new Exception("Expected tag :\n[" + oriTag + "]\ndid'nt match observed tag\n[" + playbackTag + "]");
		}

		// trigger click
		// ((HTMLElementImpl) node).eval("this.mouseover()");
		((HTMLElementImpl) node).click();

	}

}
