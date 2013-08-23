<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="org.sz.core.util.StringUtil"%>
<%@tag import="org.sz.platform.model.system.SysUser"%>
<%@tag import="org.sz.core.util.AppUtil"%>
<%@tag import="org.sz.platform.service.system.SysUserService"%>
<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="userId" type="java.lang.String" required="true" rtexprvalue="true"%>
<%
	if(StringUtil.isNumberic(userId)){
		//通过用户Id获取用户名称
		SysUserService sysUserService=(SysUserService)AppUtil.getBean("sysUserService");
		SysUser user=sysUserService.getById(new Long(userId));
		if(user!=null){
			if(StringUtils.isEmpty(user.getFullname())){
				out.println("<img src='" + request.getContextPath() + "/themes/img/commons/user-16.png'>&nbsp;"+user.getAccount());
			}else{
				out.println("<img src='" + request.getContextPath() + "/themes/img/commons/user-16.png'>&nbsp;"+user.getFullname());
			}
		}
	}else if(StringUtil.isNotEmpty(userId)){
		out.println(userId);
	}else{
		out.println("暂无");
	}
%>