/**
 * 
 */
package com.hafidz.autoBrowser.record;

import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLElement;

import com.hafidz.autoBrowser.constant.RecordingConstant.Type;
import com.hafidz.autoBrowser.util.DomUtil;
import com.hafidz.autoBrowser.util.ListenerUtil;

/**
 * @author 608845362
 *
 */
public class ChangeRecording extends AbstractRecording {

	public ChangeRecording() {
		setType(Type.CHANGE);
	}

	
	@Override
	public void playback() throws Exception {
		System.out.println("playback change");

		ListenerUtil.updateListeners();

		// get Node
		Node node = DomUtil.getNode(getElementXpath());

		System.out.println("node = " + node);

		if (node == null) {
			throw new Exception("Element not found!");
		}

		// // debug
		// NamedNodeMap attributes = node.getAttributes();
		// for (int index = 0; index < attributes.getLength(); index++) {
		// Node attributeNode = attributes.item(index);
		// System.out.println("attribute name = " +
		// attributeNode.getNodeName());
		// System.out.println("attribute value = " +
		// attributeNode.getNodeValue());
		// }

		System.out.println("should changed to " + (String) getElementValue());

		// trigger set value (not working)
		// ((HTMLElementImpl) node).setNodeValue((String) getElementValue());

		// compare element tag
		String oriTag = getElementTag();
		String playbackTag = DomUtil.constructTag(node);
		if (!oriTag.equals(playbackTag)) {
			throw new Exception("Expected tag :\n[" + oriTag + "]\ndid'nt match observed tag\n[" + playbackTag + "]");
		}

		// readonly or disabled check
		if (!DomUtil.isReadOnlyOrDisabled((HTMLElement) node)) {

			// TODO : check maxlength if enough

			System.out.println("ori tag = " + getElementTag());
			System.out.println("change tag = " + DomUtil.constructTag(node));

			// Object evalReturn = ((HTMLElementImpl)
			// node).eval("this.style.borderColor='blue'");
			// System.out.println("evalReturn = " + evalReturn);

			// DomUtil.focus(node);
			// DomUtil.focus(getElementXpath());

			// ((HTMLElementImpl) node).setNodeValue((String)
			// getElementValue());

			// ((Element) node).setAttribute("value", (String)
			// getElementValue());

			// DomUtil.setValue(node, getElementValue());
			DomUtil.setValue((HTMLElement) node, getElementXpath(), getElementValue());

			// System.out.println("change tag 222 = " +
			// DomUtil.constructTag(node));

			// DomUtil.blur(getElementXpath());
			DomUtil.onChange(getElementXpath());

		} else {
			throw new Exception("Cannot change value becauseinput is readonly or disabled!");
		}

	}

}
