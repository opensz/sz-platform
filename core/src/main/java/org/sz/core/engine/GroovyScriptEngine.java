package org.sz.core.engine;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.sz.core.service.BaseService;
import org.sz.core.util.BeanUtils;

public class GroovyScriptEngine implements BeanPostProcessor {
	private Log logger = LogFactory.getLog(GroovyScriptEngine.class);
	private Binding binding = new Binding();
	private Map<String, String> errors;
	private GroovyShell shell = null;
	public static final String NoProperty = "NoProperty";
	public static final String NoMethod = "NoMethod";
	public static final String CompileError = "CompileError";
	public static final String OtherError = "OtherError";

	public void execute(String script, Map<String, Object> vars) {
		executeObject(script, vars);
	}

	private void setParameters(GroovyShell shell, Map<String, Object> vars) {
		if (vars == null)
			return;
		Set set = vars.entrySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			shell.setVariable((String) entry.getKey(), entry.getValue());
		}
	}

	public boolean executeBoolean(String script, Map<String, Object> vars) {
		Boolean rtn = (Boolean) executeObject(script, vars);
		return rtn.booleanValue();
	}

	public String executeString(String script, Map<String, Object> vars) {
		String str = (String) executeObject(script, vars);
		return str;
	}

	public int executeInt(String script, Map<String, Object> vars) {
		Integer rtn = (Integer) executeObject(script, vars);
		return rtn.intValue();
	}

	public float executeFloat(String script, Map<String, Object> vars) {
		Float rtn = (Float) executeObject(script, vars);
		return rtn.floatValue();
	}

	public Object executeObject(String script, Map<String, Object> vars) {
		this.errors = new HashMap();
		try {
			this.logger.debug("执行:" + script);
			this.shell = new GroovyShell(this.binding);
			setParameters(this.shell, vars);
			Object rtn = this.shell.evaluate(script);
			return rtn;
		} catch (MissingPropertyException ex) {
			this.errors.put("NoProperty", ex.getProperty());
			this.logger.debug(ex.getProperty() + ",变量不存在，请检查脚本！");
		} catch (MissingMethodException ex) {
			this.errors.put("NoMethod", ex.getMethod() + "方法不存在,请检查脚本！");
			this.logger.debug("执行:" + script + ",原因:" + ex.getMessage());
		} catch (MultipleCompilationErrorsException ex) {
			this.errors.put("CompileError", "编译出错:" + ex.getMessage()
					+ ",请检查代码!");
			this.logger.debug("执行:" + script + ",编译出错:" + ex.getMessage()
					+ ",请检查代码!");
		} catch (Exception ex) {
			this.errors
					.put("OtherError", "其他错误:" + ex.getMessage() + ",请检查代码!");
			this.logger.debug("执行:" + script + ",其他错误:" + ex.getMessage()
					+ ",请检查代码!");
		}
		return null;
	}

	public Map<String, String> getErrors() {
		return this.errors;
	}

	public String getErrorMessage() {
		if (this.errors == null)
			return "";
		String tmp = "";
		Set set = this.errors.entrySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			tmp = tmp + (String) entry.getValue() + "#";
		}
		return tmp;
	}

	public boolean hasErrors() {
		if (this.errors != null)
			return this.errors.size() > 0;
		return false;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		boolean rtn = BeanUtils.isInherit(bean.getClass(), BaseService.class);
		boolean isImplScript = BeanUtils.isInherit(bean.getClass(),
				IScript.class);
		if ((rtn) || (isImplScript)) {
			this.binding.setProperty(beanName, bean);
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
}
