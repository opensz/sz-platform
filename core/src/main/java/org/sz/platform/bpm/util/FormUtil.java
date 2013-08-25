package org.sz.platform.bpm.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.id.IdentityGenerator;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.sz.core.engine.GroovyScriptEngine;
import org.sz.core.keygenerator.IKeyGenerator;
import org.sz.core.keygenerator.impl.GuidGenerator;
import org.sz.core.keygenerator.impl.TimeGenerator;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.FileUtil;

public class FormUtil {
	private static Log logger = LogFactory.getLog(FormUtil.class);

	private static final Pattern regex = Pattern.compile("\\[(.*?)\\]", 98);

	public static Object calcuteField(String script, Map<String, Object> map,
			String colPreFix) {
		GroovyScriptEngine engine = (GroovyScriptEngine) ContextUtil
				.getBean(GroovyScriptEngine.class);
		script = parseScript(script, map, colPreFix);
		return engine.executeObject(script, null);
	}

	private static String parseScript(String script, Map<String, Object> map,
			String colPreFix) {
		if ((map == null) || (map.size() == 0))
			return script;

		Matcher regexMatcher = regex.matcher(script);
		while (regexMatcher.find()) {
			String tag = regexMatcher.group(0);

			String key = colPreFix + regexMatcher.group(1);
			String value = map.get(key).toString();
			script = script.replace(tag, value);
		}
		return script;
	}

	public static Object getKey(int keyType, String alias) throws Exception {
		IKeyGenerator generator = null;
		switch (keyType) {
		case 1:
			generator = new GuidGenerator();
			break;
		//TODO
		//case 2:
			//generator = new IdentityGenerator();
			//break;
		case 3:
			generator = new TimeGenerator();
			break;
		default:
			generator = new TimeGenerator();
		}

		if (generator == null)
			return "-1";
		generator.setAlias(alias);

		return generator.nextId();
	}

	private static String parseNode(String html) throws ParserException {
		Parser parser = Parser.createParser(html, "utf-8");

		NodeFilter inputFilter = new AndFilter(new NodeClassFilter(
				InputTag.class), new HasAttributeFilter("name"));

		NodeFilter selectFilter = new AndFilter(new NodeClassFilter(
				SelectTag.class), new HasAttributeFilter("name"));

		NodeFilter textareaFilter = new AndFilter(new NodeClassFilter(
				TextareaTag.class), new HasAttributeFilter("name"));

		NodeFilter subTableFilter = new AndFilter(
				new NodeClassFilter(Div.class), new HasAttributeFilter("type"));

		NodeFilter selectDivFilter = new AndFilter(
				new NodeClassFilter(Div.class),new HasAttributeFilter("name"));

		NodeFilter subFieldFilter = new HasAttributeFilter("fieldname");

		NodeFilter filter = new OrFilter(new NodeFilter[] { inputFilter,
				selectFilter, textareaFilter, subTableFilter, subFieldFilter, selectDivFilter });

		Node[] nodes = parser.parse(filter).toNodeArray();
		String pNodeHtml = "div_node_html";
		for (Node node : nodes) {
			TagNode tag = (TagNode) node;

			String nodeHtml = tag.toHtml();

			if ((tag.getAttribute("name") != null)
					&& (tag.getAttribute("name").matches("^m:.*"))) {
				String fieldName = tag.getAttribute("name").replaceAll("^.*:",
						"");

				TagNode tagParent = tag;
				String controltype = tag.getAttribute("controltype");
				String type = tag.getAttribute("type");

				if ((controltype != null) && ("attachment".equals(controltype))) {
					tagParent = getContainer(tag, "div_attachment_container");

					String parentHtml = tagParent.toHtml();

					TextNode textNode = new TextNode(
							"${service.getFieldValue('" + fieldName
									+ "',model)}");
					tag.setChildren(new NodeList(textNode));

					tagParent.setAttribute("right", "${service.getFieldRight('"
							+ fieldName + "',  permission)}");

					html = html.replace(parentHtml, tagParent.toHtml());
				} else if ((controltype != null)
						&& ("office".equals(controltype))) {
					tag.setAttribute("value", "${service.getFieldValue('"
							+ fieldName + "',model)}", '"');

					tag.setAttribute("right", "${service.getFieldRight('"
							+ fieldName + "',  permission)}");

					html = html.replace(nodeHtml, tag.toHtml());
				} else if (((tag instanceof InputTag))
						&& (type != null)
						&& ((type.toLowerCase().equals("radio")) || (type
								.toLowerCase().equals("checkbox")))) {
					String value = tag.getAttribute("value");
					tag.setAttribute("chk", "1");
					tag.setAttribute("disabled", "disabled");
					html = html.replace(nodeHtml,
							"${service.getRdoChkBox('"
									+ fieldName
									+ "', '"
									+ tag.toHtml().replaceAll("\\n", "")
											.replaceAll("\\s*/\\s*", "")
									+ "','" + value + "', model, permission)}");
				} else if ((tag instanceof TextareaTag)) {
					TextNode textNode = new TextNode("#value");
					tag.setChildren(new NodeList(textNode));

					html = html.replace(nodeHtml,
							"${service.getField('" + fieldName + "', '"
									+ tag.toHtml().replaceAll("\\n", "")
									+ "', model, permission)}");
				} else if ((tag instanceof InputTag)) {
					
					if(tag.getParent() instanceof Div){  //判断为div 
						if(tag.getAttribute("type")!=null && !tag.getAttribute("type").equals("hidden")){
							tag.setAttribute("value", "#value", '"');
							tagParent=(TagNode)tag.getParent();//div
							
							String pHtml=tag.getParent().toHtml();
							html = html.replace(pNodeHtml, pHtml);
						/*	html = html.replace(pNodeHtml,
									"${service.getField('" + fieldName + "', '"
											+ pNodeHtml.replaceAll("\\n", "")
											+ "', model, permission)}");*/
							
						}else{
							
							tag.setAttribute("value", "#valueId", '"');
							tagParent=(TagNode)tag.getParent();//div
							
							
							
						}
					}
					else{
						tag.setAttribute("value", "#value", '"');
						html = html.replace(nodeHtml,
								"${service.getField('" + fieldName + "', '"
										+ tag.toHtml().replaceAll("\\n", "")
										+ "', model, permission)}");
					}
					
				} else if ((tag instanceof SelectTag)) {
					tag.setAttribute("val", "#value", '"');

					html = html.replace(nodeHtml,
							"${service.getField('" + fieldName + "', '"
									+ tag.toHtml().replaceAll("\\n", "")
									+ "', model, permission)}");
				}else if ((tag instanceof Div)) {
					tag.setAttribute("val", "#value", '"');
					pNodeHtml=tag.toHtml();
					html = html.replace(nodeHtml,
							"${service.getField('" + fieldName + "', '"
									+ tag.toHtml().replaceAll("\\n", "")
									+ "', model, permission)}");
				}

			} else if ((tag.getAttribute("name") != null)
					&& (tag.getAttribute("name").matches("^s:.*"))) {
				String fieldName = tag.getAttribute("name")
						.replaceAll("^.*:", "").toLowerCase();
				String type = tag.getAttribute("type");

				if ((tag instanceof TextareaTag)) {
					TextNode textNode = new TextNode("${table." + fieldName
							+ "}");
					tag.setChildren(new NodeList(textNode));
					html = html.replace(nodeHtml, tag.toHtml());
				} else if (((tag instanceof InputTag))
						&& (type != null)
						&& ((type.toLowerCase().equals("radio")) || (type
								.toLowerCase().equals("checkbox")))) {
					tag.setAttribute("chk", "1");
					
					String value = tag.getAttribute("value");
					html = html.replace(nodeHtml,
							"${service.getRdoChkBox('"
									+ fieldName
									+ "', '"
									+ tag.toHtml().replaceAll("\\n", "")
											.replaceAll("\\s*/\\s*", "")
									+ "','" + value + "', table)}");
				} else if (((tag instanceof InputTag))
						|| ((tag instanceof SelectTag))) {
					tag.setAttribute("value", "${table." + fieldName + "}", '"');
					html = html.replace(nodeHtml, tag.toHtml());
				}

			} else if ((tag.getAttribute("name") != null)
					&& (tag.getAttribute("name").matches("^opinion:.*"))) {
				String optionName = tag.getAttribute("name").replaceAll("^.*:",
						"");
				html = html.replace(nodeHtml,
						"${service.getOpinion('" + optionName + "', '"
								+ tag.toHtml().replaceAll("\\n", "")
								+ "', model, permission)}");
			} else if ((tag.getAttribute("type") != null)
					&& (tag.getAttribute("type").matches("subtable"))) {
				String tableName = tag.getAttribute("tableName").toLowerCase();

				tag.setAttribute("right", "${permission.table." + tableName
						+ "}");
				html = html.replace(nodeHtml, tag.toHtml());
			} else if (tag.getAttribute("fieldname") != null) {
				String fieldName = tag.getAttribute("fieldname")
						.replaceAll("^.*:", "").toLowerCase();

				TextNode textNode = new TextNode("${table." + fieldName + "}");
				tag.setChildren(new NodeList(textNode));
				html = html.replace(nodeHtml, tag.toHtml());
			}
		}

		return html;
	}

	private static TagNode getContainer(TagNode node, String containerName) {
		TagNode parent = node;
		while ((parent = (TagNode) parent.getParent()) != null) {
			String name = parent.getAttribute("name");
			if ((name != null) && (containerName.equals(name))) {
				return parent;
			}
		}
		return node;
	}

	public static String getFreeMarkerTemplate(String html, Long mainTableId,
			String mainTableName) throws ParserException {
		html = parseNode(html);

		NodeFilter inputFilter = new AndFilter(new NodeClassFilter(
				InputTag.class), new HasAttributeFilter("name"));

		NodeFilter selectFilter = new AndFilter(new NodeClassFilter(
				SelectTag.class), new HasAttributeFilter("name"));

		NodeFilter textareaFilter = new AndFilter(new NodeClassFilter(
				TextareaTag.class), new HasAttributeFilter("name"));

		NodeFilter formFilter = new HasAttributeFilter("formtype", "window");

		Parser parser1 = Parser.createParser(html, "utf-8");

		NodeFilter editFilter = new HasAttributeFilter("formtype");
		Node[] editNodes = parser1.parse(editFilter).toNodeArray();

		for (Node node : editNodes) {
			TagNode tag = (TagNode) node;

			String nodeHtml = tag.toHtml();

			String tableName = getTableName(tag);

			if ("edit".equals(tag.getAttribute("formtype"))) {
				html = addRowHtml(html, nodeHtml, tag, tableName);
			} else if ("form".equals(tag.getAttribute("formtype"))) {
				TagNode tableNode = getTableNode(tag);
				NodeList nodeList = new NodeList();

				tableNode.collectInto(nodeList, formFilter);

				TagNode formNode = null;
				if (nodeList.size() != 1) {
					logger.debug("form表单不存在");
				} else {
					formNode = (TagNode) nodeList.elementAt(0);
					nodeList = new NodeList();
					NodeList children = tag.getChildren();
					formNode.collectInto(nodeList, new OrFilter(
							new NodeFilter[] { inputFilter, selectFilter,
									textareaFilter }));
					List nameList = new ArrayList();
					for (int i = 0; i < nodeList.size(); i++) {
						TagNode tagNode = (TagNode) nodeList.elementAt(i);
						String name = tagNode.getAttribute("name");
						if (!nameList.contains(name)) {
							TagNode hidNode = new TagNode();
							hidNode.setTagName("input");
							hidNode.setAttribute("type", "hidden", '"');
							hidNode.setAttribute("name",
									tagNode.getAttribute("name"), '"');
							if ((tagNode instanceof TextareaTag)) {
								hidNode.setAttribute("value",
										((TextareaTag) tagNode)
												.getChildrenHTML(), '"');
							} else if (((tagNode instanceof InputTag))
									&& ((tagNode.getAttribute("type")
											.toLowerCase().equals("radio")) || (tagNode
											.getAttribute("type").toLowerCase()
											.equals("checkbox")))) {
								String value = "${table."
										+ name.replaceAll("^.*:", "")
												.toLowerCase() + "}";
								hidNode.setAttribute("value", value, '"');
							} else {
								hidNode.setAttribute("value",
										tagNode.getAttribute("value"), '"');
							}
							children.add(hidNode);
							nameList.add(name);
						}
					}
				}
				html = addRowHtml(html, nodeHtml, tag, tableName);
			}
		}
		html = "<#setting number_format=\"#\">\n" + html;
		return html;
	}

	private static String getTableName(TagNode node) {
		TagNode parent = getTableNode(node);
		if (parent == null)
			return "";
		return parent.getAttribute("tablename");
	}

	private static TagNode getTableNode(TagNode node) {
		boolean found = false;
		TagNode parent = node;
		while ((parent = (TagNode) parent.getParent()) != null) {
			String tableName = parent.getAttribute("tablename");
			if (tableName != null) {
				found = true;
				break;
			}
		}
		if (found)
			return parent;
		return null;
	}

	private static String addRowHtml(String html, String tagHtml, TagNode tag,
			String tableName) {
		tag.setAttribute("formtype", "newrow");
		StringBuffer sb = new StringBuffer();
		sb.append("<#if model.sub." + tableName + " != null>\r");
		sb.append("<#list model.sub." + tableName + ".dataList as table>\r");
		sb.append(tag.toHtml());
		sb.append("\r</#list>\r");
		sb.append("</#if>");
		html = html.replace(tagHtml, tagHtml + sb);
		return html;
	}

	public static String getFormTemplatePath() {
		return FileUtil.getClassesPath() + "template" + File.separator + "form"
				+ File.separator;
	}

	public static String getRuleJsPath() {
		return FileUtil.getRootPath() + "js" + File.separator + "sz"
				+ File.separator + "platform" + File.separator + "form"
				+ File.separator + "rule.js";
	}
}
