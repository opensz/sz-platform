<%@page import="org.sz.core.util.SpringContextHolder"%>
<%@page import="java.util.Properties"%>
<%@ page pageEncoding="UTF-8" %>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SZ BPM业务管理平台--登录</title>
<link type="text/css" rel="stylesheet" href="${ctx}/styles/default/css/login.css" />
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.form.js"></script>
	<script type="text/javascript">
			
			if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
				  top.location='<%=request.getContextPath()%>/login/login_oa.xht';
			}
			<%Properties configProperties=(Properties)SpringContextHolder.getBean("configproperties");
			String validCodeEnabled=configProperties.getProperty("validCodeEnabled");%>
			
</script>
<script>
	function checkUser(){
				$("#form-login").ajaxSubmit({
					url:'<%=request.getContextPath()%>/login/checkUser.xht',
					//target:        '#msg',
					//resetForm:true,
				    success: function(data) {
				    	var msg=data['msg'];
				     	if(msg!=""){
				     		$("#msg").html(msg);
				     	}
				     	else{
				     		$("#form-login").action="<%=request.getContextPath()%>/login.xht";
				     		$("#form-login").submit();
				     	}
				    }
				    
				});
	}
</script>
</head>
<body>
        <div class="second_body02">
        	<form id="form-login" action="<%=request.getContextPath()%>/login.xht" method="post">
        			<input type="hidden" name="systemId" value="${param['systemId']}" />
        			<table width="300" border="0">
                          <tr>
                            <td width="55"><span>用户名：</span></td>
                            <td colspan="2"><input type="text" name="username" class="login" value="" /></td>
                          </tr>
                          <tr>
                            <td style="letter-spacing:0.5em;">密码：</td>
                            <td colspan="2"><input type="password" name="password" class="login" value="" /></td>
                          </tr>
                          <%
                          if(validCodeEnabled!=null && "true".equals(validCodeEnabled)){
                          %>
                          <tr>
                            <td>验证码：</td>
                            <td width="128"><input type="text" name="validCode" class="login2" /></td>
                            <td width="103"><img src="${ctx}/servlet/ValidCode" /></td>
                          </tr>
                          <%
                          }
                          %>
                          <tr>
                            <td>&nbsp;</td>
                            <td colspan="2"><input type="checkbox" name="rememberMe" value="1"/><span>系统记住我</span></td>
                          </tr>
                          <tr align="center">
                            <td colspan="3"><input type="button" value="登录" class="login_button" onclick="checkUser();"/><input type="button" value="重置" class="reset_botton" onclick="this.form.reset();"></td>
                          </tr>
                        	 <%
							String loginError=(String)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
							
							if(loginError!=null && !loginError.equals(""))
							{
							%>
							<%
								}
							%>
							<tr>
								<td align="center" colspan="3">
										<font color="red"><div id="msg"></div></font>
								</td>
							</tr>
							
                    </table>
				</form>
        </div>
</body>
</html>
