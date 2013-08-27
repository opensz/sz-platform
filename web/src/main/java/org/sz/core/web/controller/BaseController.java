package org.sz.core.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.ConfigurableBeanValidator;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.util.ConfigUtil;

public class BaseController extends GenericController {
	public static final String Message = "message";

	@Resource
	protected ConfigurableBeanValidator confValidator;

	public void addMessage(ResultMessage message, HttpServletRequest request) {
		HttpSession session = request.getSession();

		session.setAttribute("message", message);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		this.logger.debug("init binder ....");
		binder.registerCustomEditor(Integer.class, null,
				new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
				Long.class, null, true));
		// binder.registerCustomEditor(Boolean.class, new
		// ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, null,
				new CustomBooleanEditor(true));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
				dateFormat, true));
	}

	protected ResultMessage validForm(String form, Object obj,
			BindingResult result, HttpServletRequest request) {
		ResultMessage resObj = new ResultMessage(1, "");
		this.confValidator.setFormName(form);
		this.confValidator.validate(obj, result);
		if (result.hasErrors()) {
			resObj.setResult(0);
			List<FieldError> list = result.getFieldErrors();
			String errMsg = "";
			for (FieldError err : list) {
				String msg = getText(err.getDefaultMessage(),
						err.getArguments(), request);
				errMsg = errMsg + msg + "\r\n";
			}
			resObj.setMessage(errMsg);
		}
		return resObj;
	}

	public ModelAndView getView(String category, String id) {
		String view = ConfigUtil.getVal(category, id);
		return new ModelAndView(view);
	}

	public static byte[] getByte(File file) throws Exception {
		if (file == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
