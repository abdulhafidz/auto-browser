/**
 * 
 */
package com.hafidz.autoBrowser.record;

import org.w3c.dom.Node;

import com.hafidz.autoBrowser.constant.RecordingConstant.Type;
import com.hafidz.autoBrowser.util.DomUtil;
import com.hafidz.autoBrowser.util.ListenerUtil;
import com.hafidz.autoBrowser.util.Recorder;

/**
 * @author 608845362
 *
 */
public class InspectRecording extends AbstractRecording {

	public InspectRecording() {
		setType(Type.INSPECT);
	}


	@Override
	public void playback() throws Exception {
		System.out.println("inspect playback");

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

		Object currentValue = DomUtil.getValue(node);
		Object oriValue = getElementValue();

		System.out.println("expected value = " + oriValue);
		System.out.println("current value = " + currentValue);

		if (!oriValue.equals(currentValue))
			throw new Exception("Value didn't match. Expecting [" + oriValue + "] but getting [" + currentValue + "]!");

		AbstractRecording input = new InspectRecording();
		input.setElementTag(DomUtil.constructTag(node));
		input.setElementValue(DomUtil.getValue(node));
		input.setElementXpath(DomUtil.getXPath(node));
		Recorder.addActivity(input);

	}

}
