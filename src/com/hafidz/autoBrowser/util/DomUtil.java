/**
 * 
 */
package com.hafidz.autoBrowser.util;

import static org.joox.JOOX.$;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.events.Event;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLInputElement;
import org.w3c.dom.html.HTMLSelectElement;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hafidz.autoBrowser.Main;
import com.sun.webkit.dom.HTMLElementImpl;

/**
 * @author 608845362
 *
 */
public class DomUtil {

	public static String getAttributeValue(Node node, String attributeName) {

		return ((Element) node).getAttribute(attributeName);

	}

	public static String format(Document document)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {

		final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
		final LSSerializer writer = impl.createLSSerializer();

		writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
		writer.getDomConfig().setParameter("xml-declaration", false);

		return writer.writeToString(document);

	}

	public static String getXPath(Event evt) {

		Node node = (Node) evt.getTarget();

		return getXPath(node);
	}

	public static Object getValue(Node node) {

		Object value = null;

		if (node instanceof HTMLInputElement && ("checkbox".equalsIgnoreCase(((HTMLInputElement) node).getType())
				|| "radio".equalsIgnoreCase(((HTMLInputElement) node).getType()))) {
			Object obj = ((HTMLElementImpl) node).eval("this.checked");
			if (obj != null && !"undefined".equals(obj)) {
				value = obj;
			}
		} else {
			Object obj = ((HTMLElementImpl) node).eval("this.value");
			if (obj != null && !"undefined".equals(obj)) {
				value = obj;
			}
		}

		return value;

	}

	public static Object getValue(Event evt) {
		Node node = (Node) evt.getTarget();
		return getValue(node);
	}

	public static void setValue(HTMLElement element, String xpath, Object value) {

		String script = "var cstBrowserSetValueElement = document.evaluate('" + xpath
				+ "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;";

		// debug
		if (element instanceof HTMLInputElement) {
			System.out.println("input type = " + ((HTMLInputElement) element).getType());
		}

		// radio button handling
		if (element instanceof HTMLInputElement && (("radio".equalsIgnoreCase(((HTMLInputElement) element).getType()))
				|| "checkbox".equalsIgnoreCase(((HTMLInputElement) element).getType()))) {
			script += "\ncstBrowserSetValueElement.checked=" + value + ";";

		}
		// other elements handling
		else {
			script += "\ncstBrowserSetValueElement.value='" + value + "';";
		}

		System.out.println("set value script = " + script);
		Main.browser.getEngine().executeScript(script);
	}

	public static void focus(String xpath) {
		String script = "var cstBrowserFocusElement = document.evaluate('" + xpath
				+ "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n"
				+ "cstBrowserFocusElement.focus();";
		System.out.println("focus script = " + script);
		Main.browser.getEngine().executeScript(script);
	}

	public static void blur(String xpath) {
		String script = "var cstBrowserBlurElement = document.evaluate('" + xpath
				+ "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n"
				+ "cstBrowserBlurElement.blur();";
		System.out.println("blur script = " + script);
		Main.browser.getEngine().executeScript(script);
	}

	public static void onChange(String xpath) {
		String script = "var cstBrowserChangeElement = document.evaluate('" + xpath
				+ "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n"
				+ "if (\"createEvent\" in document) {" + "var evt = document.createEvent(\"HTMLEvents\");"
				+ "evt.initEvent(\"change\", false, true);" + "cstBrowserChangeElement.dispatchEvent(evt);" + "}"
				+ "else{cstBrowserChangeElement.fireEvent(\"onchange\");}";
		System.out.println("onchange script = " + script);
		Main.browser.getEngine().executeScript(script);
	}

	public static String constructTag(Event evt) {
		Node node = (Node) evt.getTarget();
		return constructTag(node);
	}

	public static String constructTag(Node node) {

		StringBuilder sb = new StringBuilder();

		sb.append("<").append(node.getNodeName());

		String type = getAttributeValue(node, "type");
		if (type != null) {
			// System.out.println("type = " + type);
			sb.append(" type = '").append(type).append("'");
		}

		String name = getAttributeValue(node, "name");
		if (name != null)
			sb.append(" name = '").append(name).append("'");

		String id = getAttributeValue(node, "id");
		if (id != null)
			sb.append(" id = '").append(id).append("'");

		sb.append(">");

		String text = node.getTextContent();
		if (text != null)

			sb.append(text);

		sb.append("</").append(node.getNodeName()).append(">");

		// return StringUtil.shorten(sb.toString(), 75);
		return sb.toString();
	}

	public static String constructTag(HTMLElement node) {

		StringBuilder sb = new StringBuilder();

		sb.append("<").append(node.getNodeName());

		String type = getAttributeValue(node, "type");
		if (type != null) {
			// System.out.println("type = " + type);
			sb.append(" type = '").append(type).append("'");
		}

		String name = getAttributeValue(node, "name");
		if (name != null)
			sb.append(" name = '").append(name).append("'");

		String id = getAttributeValue(node, "id");
		if (id != null)
			sb.append(" id = '").append(id).append("'");

		sb.append(">");

		String text = node.getTextContent();
		if (text != null) {

			// substring
			if (text.length() > 50) {
				text = text.substring(0, 50) + "...";
			}

			sb.append(text);
		}

		sb.append("</").append(node.getNodeName()).append(">");

		return sb.toString();
	}

	public static String getXPath(Node node) {
		try {
			Element elem = (Element) node;
			return $(elem).xpath();
		} catch (Exception e) {
			System.err.println("fakkk");
			// e.printStackTrace();
		}

		return null;
	}

	public static Document toDoc(String xmlString) throws SAXException, IOException, ParserConfigurationException {

		StringReader reader = null;

		try {
			reader = new StringReader(xmlString);
			InputSource src = new InputSource(reader);
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);
			return document;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public static void removeDocType(Document doc) throws SAXException, IOException, ParserConfigurationException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		// String html = format(doc).replaceAll("<!DOCTYPE[^>]*>\n", "");
		//
		// return toDoc(html);

		DocumentType docType = doc.getDoctype();
		if (docType != null) {
			doc.removeChild(docType);
		}
	}

	public static Document removeDocTypeInNewCopy(Document doc)
			throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, ClassCastException {
		String html = format(doc).replaceAll("<!DOCTYPE[^>]*>\n", "");

		return toDoc(html);

	}

	public static Document fixDocType(Document doc) throws SAXException, IOException, ParserConfigurationException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {

		String html = format(doc).replaceAll("<!DOCTYPE[^>]*>\n", "<!DOCTYPE html>");

		return toDoc(html);

	}

	public static Document getDocument(File file) throws ParserConfigurationException, SAXException, IOException {

		if (!file.exists() || file.isDirectory())
			return null;

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);

		doc.getDocumentElement().normalize();

		return doc;

	}

	public static HTMLElement getNode(String xpath) {
		String script = "document.evaluate('" + xpath
				+ "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue";
		System.out.println("get node script = " + script);
		return (HTMLElement) Main.browser.getEngine().executeScript(script);
	}

	public static boolean isReadOnlyOrDisabled(HTMLElement element) {
		if (element instanceof HTMLInputElement) {
			return ((HTMLInputElement) element).getDisabled() || ((HTMLInputElement) element).getReadOnly();
		} else if (element instanceof HTMLSelectElement) {
			return ((HTMLSelectElement) element).getDisabled();
		}

		return false;
	}

	public static Node toNode(String xmlString) throws SAXException, IOException, ParserConfigurationException {

		StringReader reader = null;

		try {
			reader = new StringReader(xmlString);
			InputSource src = new InputSource(reader);
			Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
			return document;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
