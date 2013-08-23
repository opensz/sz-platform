<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,org.sz.core.util.SpringContextHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/commons/include/get.jsp" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String basePath = request.getContextPath();
	String path = request.getScheme() + "://" + request.getHeader("host") +  basePath+"/messagebroker/amf";
	String calPath=basePath+"/media/swf/calendar";
	// 日程显示模式
	String calendarMode = "3";
%>
<html>
    <head>    
        <title></title>         
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- Include CSS to eliminate any default margins/padding and set the height of the html element and 
		     the body element to 100%, because Firefox, or any Gecko based browser, interprets percentage as 
			 the percentage of the height of its parent container, which has to be set explicitly.  Initially, 
			 don't display flashContent div so it won't show if JavaScript disabled.-->
		
        <style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; text-align:center; 
			       background-color: #ffffff; }   
			#flashContent { visibility: hidden; }
        </style>
		
		<!-- Enable Browser History by replacing useBrowserHistory tokens with two hyphens -->
        <!-- BEGIN Browser History required section >
        <link rel="stylesheet" type="text/css" href="history/history.css" />
        <script type="text/javascript" src="history/history.js"></script>
        <! END Browser History required section -->  
		
		<script type="text/javascript">
			function getMessageBrokerPath(){
				return document.getElementById("flexPath").value;
			}
			
			// 取得日程显示模式
			function getCalendarMode(){
				return document.getElementById("calendarMode").value;
			}
			
			/*
			* planId 任务ID
			*/
			function goToDetail(planId){
				/*var par = this.parent;
				par.tab.addTabItem({ tabid : planId,text: '我的待办事项',
					url: '${ctx}/platform/bpm/task/toStart.xht?taskId='+planId });*/
				 var url="${ctx}/platform/bpm/task/toStart.xht?taskId="+planId;
				 jQuery.openFullWindow(url);
			}
			
		</script>
		
        <script type="text/javascript" src="<%=calPath%>/swfobject.js"></script>
        <script type="text/javascript">
        
            <!-- For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. --> 
            var swfVersionStr = "10.0.0";
            /* To use express install, set to playerProductInstall.swf, otherwise the empty string. */
            var xiSwfUrlStr = "<%=calPath%>/../common/playerProductInstall.swf";
            var flashvars = {};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            params.wmode = "transparent";
            var attributes = {};
            attributes.id = "calendar";
            attributes.name = "calendar";
            attributes.align = "middle";

            swfobject.embedSWF(
                "<%=calPath%>/calendar.swf", "flashContent", 
                "100%", "100%", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
            
			/*JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.*/
			swfobject.createCSS("#flashContent", "visibility:visible;text-align:left;");
        </script>
    </head>
    <body>
        <input type="hidden" id="flexPath" value="<%=path %>" />
    	<input type="hidden" id="calendarMode" value="<%=calendarMode %>" />
        <!-- SWFObject's dynamic embed method replaces this alternative HTML content with Flash content when enough 
			 JavaScript and Flash plug-in support is available. The div is initially hidden so that it doesn't show
			 when JavaScript is disabled.-->
		
        <div id="flashContent">
			<script type="text/javascript"> 
				var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://"); 
				document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
								+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
			</script> 
        </div>
	   	
       	<noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="calendar">
                <param name="movie" value="<%=calPath%>/calendar.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <param name="wmode" value="transparent" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="<%=calPath%>/calendar.swf" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
                	<param name="wmode" value="transparent" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                	<p> 
                		Either scripts and active content are not permitted to run or Adobe Flash Player version
                		10.0.0 or greater is not installed.
                	</p>
                <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                    </a>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
	    </noscript>		
   </body>
</html>
