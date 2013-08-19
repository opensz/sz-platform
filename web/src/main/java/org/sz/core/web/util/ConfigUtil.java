package org.sz.core.web.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.StringUtil;

public class ConfigUtil {
	private Document doc = null;
	private Map<String, String> mJspExist = new HashMap<String, String>();
	private static ConfigUtil config = null;

	private static Lock lock = new ReentrantLock();

	private ConfigUtil() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"conf/viewconfig.xml");
		this.doc = Dom4jUtil.loadXml(is);
	}

	public static ConfigUtil getInstance() {
		if (config == null) {
			lock.lock();
			try {
				if (config == null)
					config = new ConfigUtil();
			} finally {
				lock.unlock();
			}
		}
		return config;
	}

	public String getValue(String category, String id) {
		String template = "category[@id='%s']/view[@name='%s']";
		String filter = String.format(template, new Object[] { category, id });
		Element root = this.doc.getRootElement();
		Element el = (Element) root.selectSingleNode(filter);
		if (el != null)
			return el.attributeValue("value");
		return "";
	}

	public String uriToJspPath(String requestURI, String businessType) {

		requestURI = requestURI.replace(".xht", "");
		
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		String contextPath = request.getContextPath();
		if(contextPath!=null){
			int cxtIndex = requestURI.indexOf(contextPath);
			if (cxtIndex != -1) {
				requestURI = requestURI.substring(cxtIndex + contextPath.length());
			}
		}

		String[] paths = requestURI.split("[/]");
		String jspPath = null;
		// pattern:[/子系统/包名/表对应实体名/实体操作方法名.xht]
		if ((paths != null) && (paths.length == 5)) {
			jspPath = "/" + paths[1] + "/" + paths[2] + "/" + paths[3]
					+ StringUtil.makeFirstLetterUpperCase(paths[4]) + ".jsp";
		} else if ((paths != null) && (paths.length == 4)) {
			jspPath = "/" + paths[1] + "/" + paths[2]
					+ StringUtil.makeFirstLetterUpperCase(paths[3]) + ".jsp";
		}

		// 检查是否存在系统分类，如有检查是否有定制jsp页面，有则使用系统分类对应的jsp文件
		if (businessType != null && !"".equals(businessType) && jspPath != null) {
			String alterPath = jspPath.replace("/" + paths[1] + "/", "/"
					+ businessType.toLowerCase() + "/");
			if (checkJspExist(alterPath)) {
				jspPath = alterPath;
			}
		}
		if (jspPath == null) {
			jspPath = requestURI + ".jsp";
		}

		return jspPath;
	}

	public String getJspPathPrefix() {
		return "/WEB-INF/view";
	}

	private boolean checkJspExist(String jspPath) {
		boolean bExist = true;
		if (mJspExist.containsKey(jspPath)) {
			bExist = "1".equals(mJspExist.get(jspPath));
		} else {
			String realPath = AppUtil.getRealPath(getJspPathPrefix() + jspPath);
			if ((new File(realPath)).exists()) {
				bExist = true;
				mJspExist.put(jspPath, "1");
			} else {
				bExist = false;
				mJspExist.put(jspPath, "0");
			}
		}
		return bExist;
	}

	public static String getVal(String category, String id) {
		return getInstance().getValue(category, id);
	}
	
//	public static  String getSysCatalog(){
//		return getInstance().getSystemCatalog();
//	}
	
	public static String getJspPath(String requestURI){
		return getJspPath(requestURI, null,false);
	}
	
	public static String getJspPath(String requestURI, String businessType){
		return getJspPath(requestURI, businessType, false);
	}
	
	public static String getJspPath(String requestURI, boolean absolute){
		return getJspPath(requestURI, null, absolute);
	}
	
	public static String getJspPath(String requestURI, String businessType, boolean absolute){
		String jspPath = getInstance().uriToJspPath(requestURI, businessType);
		if(absolute){
			jspPath = getInstance().getJspPathPrefix()+jspPath;
		}
		return jspPath;
	}
	
	
	
	
}
