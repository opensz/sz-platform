package org.sz.platform.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.encrypt.EncryptUtil;
import org.sz.core.query.QueryFilter;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.servlet.ValidCode;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.SubSystemService;
import org.sz.platform.service.system.SysOrgService;
import org.sz.platform.service.system.SysUserService;


@Controller
@RequestMapping({"/login/"})
public class IndexController extends BaseController{
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;

	@Resource
	private Properties configproperties;
	
	 @RequestMapping({"index"})
	  public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    List list = this.subSystemService.getActiveSystem();//.getAll(new WebQueryFilter(request, "subSystemItem"));
	    ModelAndView mv = getAutoView().addObject("loginList", list);
	    //return new ModelAndView("/login/index.jsp").addObject("loginList", list);
	    return mv;
	  }
	 

	 
	 @RequestMapping({ "checkUser" })
	 @ResponseBody
	 public Map checkUser(HttpServletRequest request,HttpServletResponse response,
			 @RequestParam("username") String username,@RequestParam("password") String password) throws Exception {
		 Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId",20000L));
		 String validCodeEnabled = this.configproperties.getProperty("validCodeEnabled");
		 boolean error = false;
		 String str="";
		 Map hashMap=new HashMap();
				if ((validCodeEnabled != null) && ("true".equals(validCodeEnabled))) {
					String validCode = (String) request.getSession().getAttribute(
							ValidCode.SessionName_Randcode);
					String code = request.getParameter("validCode");
					if ((validCode == null)
							|| (org.apache.commons.lang.StringUtils.isEmpty(code))
							|| (!validCode.equals(code))) {
							str="验证码不正确！";
							error = true;
					}
				}
				if ((org.apache.commons.lang.StringUtils.isEmpty(username))
						|| (org.apache.commons.lang.StringUtils.isEmpty(password))) {
							str="用户名密码为空!";
							error = true;
				}
				else{
					SysUser sysUser = this.sysUserService.getByAccount(username,orgId);
					String encrptPassword = EncryptUtil.encryptSha256(password);
					if ((sysUser == null)
							|| (!encrptPassword.equals(sysUser.getPassword()))) {
						str="用户名密码输入错误!";
						error = true;
					}
					
				}
				
		
			hashMap.put("msg", str);
			hashMap.put("error", error);
			hashMap.put("success", true);
			return hashMap;
	 }
	 
	 
	 @RequestMapping({ "changeStyle" })
	 @ResponseBody
	 public Map changeStyle(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 String validCodeEnabled = this.configproperties.getProperty("validCodeEnabled");
		 boolean error = false;
		 String str="";
		 Map hashMap=new HashMap();
		 request.getParameter("styleName");
		 request.getSession().setAttribute("styleName", request.getParameter("styleName"));
		 hashMap.put("msg", "更新成功");
		 //hashMap.put("error", error);
		 hashMap.put("success", true);
		 return hashMap;
	 }
}
