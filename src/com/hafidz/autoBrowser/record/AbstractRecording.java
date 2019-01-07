/**
 * 
 */
package com.hafidz.autoBrowser.record;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.hafidz.autoBrowser.constant.RecordingConstant;
import com.hafidz.autoBrowser.constant.RecordingConstant.TestResult;
import com.hafidz.autoBrowser.constant.RecordingConstant.Type;
import com.hafidz.autoBrowser.util.StringUtil;
import com.hafidz.autoBrowser.util.SystemTimeFormatter;

/**
 * @author 608845362
 *
 */
public abstract class AbstractRecording {

	private Type type;
	private String elementTag;
	private Object elementValue;
	private String elemtnXpath;
	private String desc;

	private Long delay;

	private TestResult testResult;
	private String failedReason;

	private Integer order;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getElementTag() {
		return elementTag;
	}

	public void setElementTag(String elementTag) {
		this.elementTag = elementTag;
	}

	public Object getElementValue() {
		return elementValue;
	}

	public void setElementValue(Object elementValue) {
		this.elementValue = elementValue;
	}

	public String getElementXpath() {
		return elemtnXpath;
	}

	public void setElementXpath(String elemtnXpath) {
		this.elemtnXpath = elemtnXpath;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (order != null)
			sb.append("#").append(order).append("\n");

		sb.append(RecordingConstant.KEY_TYPE).append(getType()).append("\n");
		if (getElementTag() != null)
			sb.append(RecordingConstant.KEY_TAG).append(StringUtil.removeMultiLines(getElementTag())).append("\n");
		if (getElementXpath() != null)
			sb.append(RecordingConstant.KEY_XPATH).append(getElementXpath()).append("\n");
		if (getElementValue() != null)
			sb.append(RecordingConstant.KEY_VALUE)
					.append(StringUtil.removeMultiLines(String.valueOf(getElementValue()))).append("\n");
		sb.append(RecordingConstant.KEY_DELAY).append(SystemTimeFormatter.format(getDelay()));
		if (testResult != null)
			sb.append("\n").append(RecordingConstant.KEY_TEST_RESULT).append(testResult);
		if (failedReason != null)
			sb.append("\n").append(RecordingConstant.KEY_FAILED_REASON).append(failedReason);

		return sb.toString();
	}

	// public String toHTML() {
	// StringBuilder sb = new StringBuilder("<ul>");
	//
	// if (order != null) {
	// sb.append("<li>");
	// sb.append("#").append(order).append("\n");
	// sb.append("</li>");
	// }
	//
	// sb.append("<li>");
	// sb.append(RecordingConstant.KEY_TYPE).append(getType()).append("\n");
	// sb.append("</li>");
	//
	// if (getElementTag() != null) {
	// sb.append("<li>");
	// sb.append(RecordingConstant.KEY_TAG).append(StringUtil.removeMultiLines(getElementTag())).append("\n");
	// sb.append("</li>");
	// }
	//
	// if (getElementXpath() != null) {
	// sb.append("<li>");
	// sb.append(RecordingConstant.KEY_XPATH).append(getElementXpath()).append("\n");
	// sb.append("</li>");
	// }
	//
	// if (getElementValue() != null) {
	// sb.append("<li>");
	// sb.append(RecordingConstant.KEY_VALUE)
	// .append(StringUtil.removeMultiLines(String.valueOf(getElementValue()))).append("\n");
	// sb.append("</li>");
	// }
	//
	// sb.append("<li>");
	// sb.append(RecordingConstant.KEY_DELAY).append(SystemTimeFormatter.format(getDelay()));
	// sb.append("</li>");
	//
	// if (testResult != null) {
	// sb.append("<li>");
	// sb.append("\n").append(RecordingConstant.KEY_TEST_RESULT).append(testResult);
	// sb.append("</li>");
	// }
	//
	// if (failedReason != null) {
	// sb.append("<li>");
	// sb.append("\n").append(RecordingConstant.KEY_FAILED_REASON).append(failedReason);
	// sb.append("</li>");
	// }
	//
	// sb.append("</ul>");
	//
	// return sb.toString();
	// }

	public void outputResult(Node htmlBody) {

		Document doc = htmlBody.getOwnerDocument();

		Node tableNode = doc.createElement("table");

		htmlBody.appendChild(tableNode);

		if (order != null) {
			Node trNode = doc.createElement("tr");
			Node tdNode = doc.createElement("td");
			tdNode.setTextContent("#" + order);
			trNode.appendChild(tdNode);
			tableNode.appendChild(trNode);
		}

		Node typeTrNode = doc.createElement("tr");
		Node typeTdNode = doc.createElement("td");
		Node typeValueNode = doc.createElement("td");
		typeTdNode.setTextContent(RecordingConstant.KEY_TYPE_HTML);
		typeValueNode.setTextContent(getType().toString());
		typeTrNode.appendChild(typeTdNode);
		typeTrNode.appendChild(typeValueNode);
		tableNode.appendChild(typeTrNode);

		if (getElementTag() != null) {
			Node trNode = doc.createElement("tr");
			Node tdNode = doc.createElement("td");
			Node valueNode = doc.createElement("td");
			tdNode.setTextContent(RecordingConstant.KEY_TAG_HTML);
			valueNode.setTextContent(StringUtil.removeMultiLines(getElementTag()));
			trNode.appendChild(tdNode);
			trNode.appendChild(valueNode);
			tableNode.appendChild(trNode);
		}

		if (getElementXpath() != null) {
			Node trNode = doc.createElement("tr");
			Node tdNode = doc.createElement("td");
			Node valueNode = doc.createElement("td");
			tdNode.setTextContent(RecordingConstant.KEY_XPATH_HTML);
			valueNode.setTextContent(getElementXpath());
			trNode.appendChild(tdNode);
			trNode.appendChild(valueNode);
			tableNode.appendChild(trNode);
		}

		if (getElementValue() != null) {

			Node trNode = doc.createElement("tr");
			Node tdNode = doc.createElement("td");
			Node valueNode = doc.createElement("td");
			tdNode.setTextContent(RecordingConstant.KEY_VALUE_HTML);
			valueNode.setTextContent(StringUtil.removeMultiLines(String.valueOf(getElementValue())));
			trNode.appendChild(tdNode);
			trNode.appendChild(valueNode);
			tableNode.appendChild(trNode);

		}

		Node delayNode = doc.createElement("tr");
		Node delayTdNode = doc.createElement("td");
		Node delayValueNode = doc.createElement("td");
		delayTdNode.setTextContent(RecordingConstant.KEY_DELAY_HTML);
		delayValueNode.setTextContent(SystemTimeFormatter.format(getDelay()));
		delayNode.appendChild(delayTdNode);
		delayNode.appendChild(delayValueNode);
		tableNode.appendChild(delayNode);

		if (testResult != null) {
			Node trNode = doc.createElement("tr");
			Node tdNode = doc.createElement("td");
			Node valueNode = doc.createElement("td");
			tdNode.setTextContent(RecordingConstant.KEY_TEST_RESULT_HTML);
			valueNode.setTextContent(testResult.toString());

			switch (testResult) {
			case FAILED:
				((Element) valueNode).setAttribute("class", "result-failed");
				break;
			case PASS:
				((Element) valueNode).setAttribute("class", "result-pass");
				break;
			case WAITING:
				((Element) valueNode).setAttribute("class", "result-waiting");
				break;

			}
			trNode.appendChild(tdNode);
			trNode.appendChild(valueNode);
			tableNode.appendChild(trNode);
		}

		if (failedReason != null) {

			Node trNode = doc.createElement("tr");
			Node tdNode = doc.createElement("td");
			Node valueNode = doc.createElement("td");
			tdNode.setTextContent(RecordingConstant.KEY_FAILED_REASON_HTML.toString());
			valueNode.setTextContent(failedReason);
			trNode.appendChild(tdNode);
			trNode.appendChild(valueNode);
			tableNode.appendChild(trNode);

		}

	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	abstract public void playback() throws Exception;

	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

}
