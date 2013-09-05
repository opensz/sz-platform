package org.sz.platform.bpm.util;

import org.sz.core.bpm.graph.DivShape;
import org.sz.core.bpm.graph.ShapeMeta;
import org.sz.core.bpm.model.ForkNode;
import org.sz.core.bpm.model.NodeCondition;
import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class BpmUtil {
	
	protected static final Log logger = LogFactory.getLog(BpmUtil.class);
	protected static final String VAR_PRE_NAME = "v_";

	public static Object getValue(String type, String paramValue) {
		Object value = null;

		if ("S".equals(type)) {
			value = paramValue;
		} else if ("L".equals(type)) {
			value = new Long(paramValue);
		} else if ("I".equals(type)) {
			value = new Integer(paramValue);
		} else if ("DB".equals(type)) {
			value = new Double(paramValue);
		} else if ("BD".equals(type)) {
			value = new BigDecimal(paramValue);
		} else if ("F".equals(type)) {
			value = new Float(paramValue);
		} else if ("SH".equals(type)) {
			value = new Short(paramValue);
		} else if ("D".equals(type))
			try {
				value = DateUtils.parseDate(paramValue, new String[] {
						"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
			} catch (Exception ex) {
			}
		else {
			value = paramValue;
		}
		return value;
	}

	public static Map<String, Map<String, String>> getTaskActivitys(
			String defXml) {
		Map rtnMap = new HashMap();

		defXml = defXml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");

		Document doc = Dom4jUtil.loadXml(defXml);
		Element root = doc.getRootElement();
		List list = root.selectNodes("./process//userTask");
		Map taskMap = new HashMap();
		addToMap(list, taskMap);
		rtnMap.put("任务节点", taskMap);

		Map gateWayMap = new HashMap();

		List parallelGatewayList = root
				.selectNodes("./process//parallelGateway");
		if (parallelGatewayList.size() > 0) {
			addToMap(parallelGatewayList, gateWayMap);
		}

		List inclusiveGatewayList = root
				.selectNodes("./process//inclusiveGateway");
		if (inclusiveGatewayList.size() > 0) {
			addToMap(inclusiveGatewayList, gateWayMap);
		}

		List exclusiveGatewayGatewayList = root
				.selectNodes("./process//exclusiveGateway");
		if (exclusiveGatewayGatewayList.size() > 0) {
			addToMap(exclusiveGatewayGatewayList, gateWayMap);
		}

		if (gateWayMap.size() > 0) {
			rtnMap.put("网关节点", gateWayMap);
		}

		List endList = root.selectNodes("./process//endEvent");
		Map endMap = new HashMap();
		addToMap(endList, endMap);
		rtnMap.put("结束节点", endMap);

		List serviceTask = root.selectNodes("./process//serviceTask");
		if (serviceTask.size() > 0) {
			Map serviceMap = new HashMap();
			addToMap(serviceTask, serviceMap);
			rtnMap.put("自动任务", serviceMap);
		}

		List subProcess = root.selectNodes("./process//subProcess");
		if (subProcess.size() > 0) {
			Map subProcessMap = new HashMap();
			addToMap(subProcess, subProcessMap);
			rtnMap.put("子流程", subProcessMap);
		}

		return rtnMap;
	}

	private static void addToMap(List<Node> list, Map<String, String> map) {
		for (Node node : list) {
			Element el = (Element) node;
			String id = el.attributeValue("id");
			String name = el.attributeValue("name");
			map.put(id, name);
		}
	}

	public static String getFirstTaskNode(String defXml) {
		defXml = defXml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(defXml);
		Element root = doc.getRootElement();
		Element startNode = (Element) root
				.selectSingleNode("/definitions/process/startEvent");
		if (startNode == null)
			return "";
		String startId = startNode.attributeValue("id");
		Element sequenceFlow = (Element) root
				.selectSingleNode("/definitions/process/sequenceFlow[@sourceRef='"
						+ startId + "']");
		if (sequenceFlow == null)
			return "";
		String taskId = sequenceFlow.attributeValue("targetRef");
		return taskId;
	}

	public static Map<String, Map<String, String>> getTranstoActivitys(
			String defXml, List<String> nodes) {
		Map actMap = getTaskActivitys(defXml);
		Collection<Map> values = actMap.values();
		for (Iterator<String> iterator = nodes.iterator(); iterator.hasNext();) {
			String node = iterator.next();
			for (Map map : values) {
				map.remove(node);
			}
		}
		return actMap;
	}

	public static boolean isTaskListener(String className) {
		try {
			Class cls = Class.forName(className);
			return BeanUtils.isInherit(cls, TaskListener.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int isHandlerValid(String handler) {
		if (handler.indexOf(".") == -1)
			return -1;
		String[] aryHandler = handler.split("[.]");
		String beanId = aryHandler[0];
		String method = aryHandler[1];
		Object serviceBean = null;
		try {
			serviceBean = ContextUtil.getBean(beanId);
		} catch (Exception ex) {
			return -2;
		}
		if (serviceBean == null)
			return -2;
		try {
			Method invokeMethod = serviceBean.getClass().getDeclaredMethod(
					method, new Class[] { ProcessCmd.class });
			return 0;
		} catch (NoSuchMethodException e) {
			return -3;
		} catch (Exception e) {
		}
		return -4;
	}

	public static String transform(String id, String name, String xml)
			throws TransformerFactoryConfigurationError, Exception {
		Map map = new HashMap();
		map.put("id", id);
		map.put("name", name);

		String xlstPath = FileUtil.getClassesPath()
				+ "org/sz/core/bpm/graph/transform.xsl".replace("/",
						File.separator);

		logger.debug("xlslPath:" + xlstPath);

		xml = xml.trim();

		String str = Dom4jUtil.transXmlByXslt(xml, xlstPath, map);

		logger.debug("xml:" + str);

		str = str.replace("&lt;", "<").replace("&gt;", ">")
				.replace("xmlns=\"\"", "").replace("&amp;", "&");

		// str = str.replace("UTF-8", "utf8").replace("utf-8", "utf8");
		return str;
	}

	public static ShapeMeta transGraph(String xml) throws Exception {
		xml = xml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		List list = root.selectNodes("//bpmndi:BPMNShape");
		int minx = 100000;
		int miny = 100000;
		int maxw = 0;
		int maxh = 0;

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			Element el = (Element) list.get(i);
			Element tmp = (Element) el.selectSingleNode("omgdc:Bounds");
			int x = (int) Float.parseFloat(tmp.attributeValue("x"));
			int y = (int) Float.parseFloat(tmp.attributeValue("y"));

			int w = x + (int) Float.parseFloat(tmp.attributeValue("width"));
			int h = y + (int) Float.parseFloat(tmp.attributeValue("height"));

			minx = Math.min(x, minx);
			miny = Math.min(y, miny);

			maxw = Math.max(w, maxw);
			maxh = Math.max(h, maxh);
		}

		List pointList = root.selectNodes("//omgdi:waypoint");
		for (int i = 0; i < pointList.size(); i++) {
			Element tmp = (Element) pointList.get(i);
			int x = (int) Float.parseFloat(tmp.attributeValue("x"));
			int y = (int) Float.parseFloat(tmp.attributeValue("y"));
			minx = Math.min(x, minx);
			miny = Math.min(y, miny);
		}

		for (int i = 0; i < list.size(); i++) {
			Element el = (Element) list.get(i);
			Element tmp = (Element) el.selectSingleNode("omgdc:Bounds");
			int x = (int) Float.parseFloat(tmp.attributeValue("x"));
			int y = (int) Float.parseFloat(tmp.attributeValue("y"));

			int w = (int) Float.parseFloat(tmp.attributeValue("width"));
			int h = (int) Float.parseFloat(tmp.attributeValue("height"));
			x = x - minx + 5;
			y = y - miny + 5;

			String id = el.attributeValue("bpmnElement");

			Element procEl = (Element) root
					.selectSingleNode("//process/descendant::*[@id='" + id
							+ "']");
			String type = procEl.getName();
			if (type.equals("serviceTask")) {
				String attribute = procEl.attributeValue("class");

				if (attribute != null) {
					if (attribute
							.equals("org.sz.platform.service.bpm.MessageTask")) {
						type = "email";
					} else if (attribute
							.equals("org.sz.platform.service.bpm.ScriptTask")) {
						type = "script";
					}
				}
			}
			Element multiObj = procEl
					.element("multiInstanceLoopCharacteristics");
			if (multiObj != null)
				type = "multiUserTask";
			Element parent = procEl.getParent();

			String name = procEl.attributeValue("name");

			int zIndex = 10;

			String parentName = parent.getName();
			if (parentName.equals("subProcess")) {
				zIndex = 11;
			}

			DivShape shape = new DivShape(name, x, y, w, h, zIndex, id, type);
			sb.append(shape);
		}
		ShapeMeta shapeMeta = new ShapeMeta(maxw, maxh, sb.toString());
		return shapeMeta;
	}

	public static ForkNode getForkNode(String forkNode, String xml)
			throws IOException {
		xml = xml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"",
				"xmlns:bpm='sz'");
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		List preNodes = root.selectNodes("//sequenceFlow[@targetRef='"
				+ forkNode + "']");
		ForkNode model = new ForkNode();
		model.setForkNodeId(forkNode);

		if (preNodes.size() == 1) {
			Element preLine = (Element) preNodes.get(0);
			String sourceId = preLine.attributeValue("sourceRef");

			Element soureNode = (Element) root
					.selectSingleNode("//userTask[@id='" + sourceId + "']");
			if (soureNode != null) {
				model.setPreNodeId(sourceId);
				Element multiNode = soureNode
						.element("multiInstanceLoopCharacteristics");
				if (multiNode != null) {
					model.setMulti(true);
				}
			}
		}

		List<Element> nodes = root.selectNodes("//sequenceFlow[@sourceRef='"
				+ forkNode + "']");
		for (Element el : nodes) {
			String id = el.attributeValue("targetRef");
			String condition = "";
			Element conditionNode = el.element("conditionExpression");
			if (conditionNode != null) {
				condition = conditionNode.getText().trim();
				condition = StringUtil.trimPrefix(condition, "${");
				condition = StringUtil.trimSufffix(condition, "}");
			}

			Element targetNode = (Element) root.selectSingleNode("//*[@id='"
					+ id + "']");
			String nodeName = targetNode.attributeValue("name");

			NodeCondition nodeCondition = new NodeCondition(nodeName, id,
					condition);
			model.addNode(nodeCondition);
		}
		return model;
	}

	public static Map<String, String> getDecisionConditions(String processXml,
			String decisionNodeId) {
		Map map = new HashMap();
		processXml = processXml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"",
				"xmlns:bpm='sz'");
		Document doc = Dom4jUtil.loadXml(processXml);
		Element root = doc.getRootElement();

		List<Element> nodes = root.selectNodes("//sequenceFlow[@sourceRef='"
				+ decisionNodeId + "']");
		for (Element el : nodes) {
			String id = el.attributeValue("targetRef");
			String condition = "";
			Element conditionNode = el.element("conditionExpression");
			if (conditionNode != null) {
				condition = conditionNode.getText().trim();
				condition = StringUtil.trimPrefix(condition, "${");
				condition = StringUtil.trimSufffix(condition, "}");
			}
			map.put(id, condition);
		}
		return map;
	}

	public static String setCondition(String sourceNode,
			Map<String, String> map, String xml) throws IOException {
		xml = xml.replace(
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"",
				"xmlns:bpm=\"sz\"");
		Document doc = Dom4jUtil.loadXml(xml, "utf-8");
		Element root = doc.getRootElement();
		List<Element> nodes = root.selectNodes("//sequenceFlow[@sourceRef='"
				+ sourceNode + "']");
		for (Element el : nodes) {
			String id = el.attributeValue("targetRef");
			String condition = (String) map.get(id);

			Element conditionEl = el.element("conditionExpression");
			if (conditionEl != null)
				el.remove(conditionEl);
			if (StringUtil.isNotEmpty(condition)) {
				Element elAdd = el.addElement("conditionExpression");
				elAdd.addAttribute("xsi:type", "tFormalExpression");
				elAdd.addCDATA("${" + condition + "}");
			}
		}
		String outXml = doc.asXML();
		outXml = outXml.replace("xmlns:bpm=\"sz\"",
				"xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"");
		return outXml;
	}

	public static String setGraphXml(String sourceNode,
			Map<String, String> map, String xml) throws IOException {
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();

		Element node = (Element) root.selectSingleNode("//bg:Gateway[@id='"
				+ sourceNode + "']");
		Element portsEl = node.element("ports");
		List portList = portsEl.elements();

		for (int i = 0; i < portList.size(); i++) {
			Element portEl = (Element) portList.get(i);
			if ((portEl.attribute("x") == null)
					&& (portEl.attribute("y") == null))
				continue;
			String id = portEl.attributeValue("id");
			Element outNode = (Element) root
					.selectSingleNode("//bg:SequenceFlow[@startPort='" + id
							+ "']");
			if (outNode != null) {
				String outPort = outNode.attributeValue("endPort");
				Element tmpNode = (Element) root
						.selectSingleNode("//ciied:Port[@id='" + outPort + "']");
				Element taskNode = tmpNode.getParent().getParent();
				String taskId = taskNode.attributeValue("id");

				Element conditionEl = outNode.element("Condition");
				if (conditionEl != null) {
					outNode.remove(conditionEl);
				}
				if (map.containsKey(taskId)) {
					String condition = (String) map.get(taskId);
					Element elAdd = outNode.addElement("Condition");
					elAdd.addText(condition);
				}
			}
		}

		return doc.asXML();
	}

	public static String getStrByRule(String rule, Map<String, Object> map) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}", 98);
		Matcher matcher = regex.matcher(rule);
		while (matcher.find()) {
			String tag = matcher.group(0);
			String name = matcher.group(1);

			Object value = map.get(name);
			if (BeanUtils.isEmpty(value)) {
				rule = rule.replace(tag, "");
			} else {
				rule = rule.replace(tag, value.toString());
			}
		}
		return rule;
	}

	public static String getTitleByRule(String titleRule,
			Map<String, Object> map) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}", 98);
		Matcher matcher = regex.matcher(titleRule);
		while (matcher.find()) {
			String tag = matcher.group(0);
			String rule = matcher.group(1);
			String[] aryRule = rule.split(":");
			String name = "";
			if (aryRule.length == 1) {
				name = rule;
			} else {
				name = aryRule[1];
			}
			String value = (String) map.get(name);
			if (StringUtil.isEmpty(value)) {
				titleRule = titleRule.replace(tag, "");
			} else {
				titleRule = titleRule.replace(tag, value);
			}
		}
		return titleRule;
	}
}
