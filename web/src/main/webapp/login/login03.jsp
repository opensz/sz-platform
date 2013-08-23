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
				  top.location='<%=request.getContextPath()%>/login.jsp';
			}
			<%Properties configProperties=(Properties)SpringContextHolder.getBean("configproperties");
			String validCodeEnabled=configProperties.getProperty("validCodeEnabled");%>
			
</script>
<script>
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			checkUser();
		}
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
				     		$("#form-login").action="<%=request.getContextPath()%>/login.xht";
				     		$("#form-login").submit();
				     	}
				    }
				    
				});
	}
</script>
</head>





<link href="${pageContext.request.contextPath}/styles/login/gk-login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

	$(function(){	
		div_position();
	});		
	$(window).resize(function(){
		div_position();
	});

	var div_position = function(){
		var win_width = $(window).width();
		var win_height = $(window).height();	
		
		//alert(win_width);
		//文字
		var text_div_left = win_width/2-$("#text_div").width();
		$("#text_div").css('left',text_div_left);
		var text_div_top = win_height/2-$("#text_div").height();
		$("#text_div").css('top',text_div_top);

		//登陆框
		var login_div_left = win_width/2+260;
		$("#login_div").css('left',login_div_left);		
		var login_div_top = win_height/2+15;
		$("#login_div").css('top',login_div_top);		
		//alert(login_div_left);
	}	
	
	
	var oldNavId = '';
	function setClassName(loginType, cssName) {
		var navId = "li_" + loginType;
		oldNavId = navId;
		var obj = document.getElementById(navId);
		if (obj != null) {
			obj.className = cssName;
		}
	}
	function replaceCss(oldNavId) {
		if (oldNavId != '') {
			var oldObj = document.getElementById(oldNavId);
			if (oldObj != null) {
				oldObj.className = "topic";
			}
		}
	}
	
	//显示隐藏
	function showCenter() {
		replaceCss(oldNavId);
		
		document.getElementById('org_select').style.display = '';
		
	}
	
	function hiddenCenter() {
		replaceCss(oldNavId);
		
		document.getElementById('org_select').style.display = 'none';
		
	}
	
</script>	
	
	<body>
	
		<div class="logo">
			<EMBED align="right" src="${pageContext.request.contextPath}/styles/login/images/logo.swf" width="316" height="56" type="application/x-shockwave-flash" wmode="transparent" quality="high">				
		</div>
		<div class="working_hours"></div>
		
		<div class="flash_div" style="width:100%;height:245px;left:0%;z-index:-1;">
			<EMBED align="right" src="${pageContext.request.contextPath}/styles/login/images/c9.swf" width="100%;" height="245" type="application/x-shockwave-flash" wmode="transparent" quality="high">
		</div>		
				
		<div class="flash_div" style="z-index:-3;" id="text_div">
			<EMBED align="right" src="${pageContext.request.contextPath}/styles/login/images/text01.swf" width="430" height="120" type="application/x-shockwave-flash" wmode="transparent" quality="high">
		</div>
			
  		<div class="login" id="login_div">
					<div class="left" >
						<ul>
							
								<li id="li_20" onclick="hiddenCenter()">
									<a href="javascript:void(0);">公司服务台</a>
								</li>
							
									<li id="li_26" onclick="showCenter()">
										<a href="javascript:void(0);">中心服务台</a>
									</li>
								
						</ul>
					</div>	
					<div class="line"></div>
				
					<div class="biao">
					
					
				<form id="form-login" action="<%=request.getContextPath()%>/login.xht" method="post" style="margin-left: 60px">
        			<input type="hidden" name="systemId" value="${param['systemId']}" />
        			<table width="300" border="0">
        				  <tr id="org_select">
        				  	<td >中&nbsp;&nbsp;&nbsp;心:</td>
        				  	<td ><select name="orgId" style="width:150px;"
										id="j_orgname_suborg2">
										
								 </select>
							</td>
        				  </tr>
        				  
                          <tr>
                            <td width="55"><span>用户名：</span></td>
                            <td ><input type="text" name="username" value="" /></td>
                          </tr>
                          <tr>
                            <td>密&nbsp;&nbsp;&nbsp;码：</td>
                            <td ><input type="password" name="password" value="" /></td>
                          </tr>
                          <%
                          if(validCodeEnabled!=null && "true".equals(validCodeEnabled)){
                          %>
                          <tr>
                            <td>验证码：</td>
                            <td width="128"><input type="text" name="validCode" /></td>
                            <td width="103"><img src="${ctx}/servlet/ValidCode" /></td>
                          </tr>
                          <%
                          }
                          %>
                          <tr>
                            <td>&nbsp;</td>
                            <td colspan="2"><input type="checkbox" name="rememberMe" value="1"/><span>让系统记住我的信息</span></td>
                          </tr>
                          <tr align="left">
                            <td colspan="2"><input type="button" value="登录" class="login_button" onclick="checkUser();"/><input type="button" value="重置" class="reset_botton" onclick="this.form.reset();"></td>
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
								<td align="left" colspan="2">
										<font color="red"><div id="msg"></div></font>
								</td>
							</tr>
							
                    </table>
				</form>
			</div>
		</div>
		
	

	
	</body>
	

</html>
