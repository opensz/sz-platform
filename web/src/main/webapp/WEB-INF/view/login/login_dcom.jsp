<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page
	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>

<%@page import="org.sz.core.util.SpringContextHolder"%>
<%@page import="java.util.Properties"%>

<%@page import="org.springframework.security.web.WebAttributes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统登录</title>
		<%@ include file="/WEB-INF/common/meta.jsp"%>
		<link href="${ctx}/styles/css/main_login.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/styles/css/itsm-s3.css" type="text/css" rel="stylesheet" />
		<script type="${ctx}/scripts/initCss.js"></script>		
		<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.form.js"></script>
		<style type="text/css">
.error{
	color:red;
	padding-left:80px;
}
</style>
		<script type="text/javascript">
					
			if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
				  top.location='<%=request.getContextPath()%>/login/login_dcom.xht';
			}
			<%Properties configProperties=(PropertiesSpringContextHolderl.getBean("configproperties");
			String validCodeEnabled=configProperties.getProperty("validCodeEnabled");%>

	//
	//判断是否在 iframe 里面进入
	if (!(window == top)) {
		if (self == top) {
			window.location.href = "${ctx}/login";
		} else if (window.parent == top) {
			window.parent.location.href = "${ctx}/login";
		} else if (window.parent.parent == top) {
			window.parent.parent.location.href = "${ctx}/login";
		}
	}
	//utility function called by getCookie()
	function getCookieVal(offset)
	{
	    var endstr = document.cookie.indexOf(";", offset);
	    if(endstr == -1)
	    {
	        endstr = document.cookie.length;
	    }
	    return unescape(document.cookie.substring(offset, endstr));
	}
	
	
	function getCookie(name)
	{
	    var arg = name + "=";
	    var alen = arg.length;
	    var clen = document.cookie.length;
	    var i = 0;
	    while(i < clen)
	    {
	        var j = i + alen;
	        if (document.cookie.substring(i, j) == arg)
	        {
	            return getCookieVal(j);
	        }
	        i = document.cookie.indexOf(" ", i) + 1;
	        if(i == 0) break;
	    }
	    return;
	}
	
	// store cookie value with optional details as needed
	function setCookie(name, value, expires, path, domain, secure)
	{
	    document.cookie = name + "=" + escape(value) +
	        ((expires) ? "; expires=" + expires : "") +
	        ((path) ? "; path=" + path : "") +
	        ((domain) ? "; domain=" + domain : "") +
	        ((secure) ? "; secure" : "");
	}
	$(document).ready(function ()
    {
	if (getCookie("username")) {
			var username=getCookie("username");
			username=username.substr(0,username.indexOf("@"));
	    	$("#j_username_").val(username);
	   		$("#j_password").focus();
	    } else {
	    	$("#j_username_").focus();
	}    
	
	if(getCookie("dcId")){
		//设置数据中心默认选择
		$("#dcId").val(getCookie("dcId")+"");
	}

	});
	//绑定回车登录	
	function whenenter(event){
		if(!event) event = window.event;
	    if((event.keyCode || event.which) == 13){
			checkUser();
		}
	}
	
	function saveUsername() {
	    var expires = new Date();
	    expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
	    setCookie("username",$("#j_username_").val() + "@" + $("#dcId").val(),expires,"<c:url value="/"/>");
	    setCookie("dcId",$("#dcId").val(),expires,"<c:url value="/"/>");
	}
		
	function resetForm(){
			$("#form-login")[0].reset();
			$("#msg").html("");
	}
	
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
							saveUsername();
				     		$("#form-login").action="<%=request.getContextPath()%>/login.xht";
				     		$("#form-login").submit();
				     	}
				    }
				    
				});
	}
</script>

	</head>
	<body class="login_bg">
		<div class="login_div">
		     <form id="form-login" name="loginForm" action="<%=request.getContextPath()%>/login.xht" method="post">
				<input type="hidden" name="systemId" value="1370622070759" />	
			<ul class="login">
				<li>
					<label>数据中心：</label>
					<select name="dcId" id="dcId"><option value="dcom">张江数据中心</option></select>
				</li>			
				<li>
					<label>登录帐号：</label>
					<input type="text" id='j_username_' name="username" class="login" value="" />
				</li>
				<li>
					<label>密码：</label>
					<input type="password" id='j_password' name="password" onkeydown="whenenter(event)" class="login" value="" />
				</li>
				<li style="padding-left:75px;">					
					<div class="but" onclick="checkUser();" style="margin-right:12px;">
						<span>确定</span>
					</div>
					<div class="but" onclick='resetForm()'>
						<span>重置</span>
					</div>
					
				</li>
				<li>
							<font color="red">  <div id="msg"></div></font>
				</li>
			</ul>
			</form>
		</div>

	</body>
</html>
