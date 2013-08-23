<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>TAB控件使用</title>
    <%@include file="/commons/include/form.jsp" %>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerTab.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerMenu.js" ></script>

    <script type="text/javascript">
            var tab = null;
  
            $(function (){
              
            	var height=$("body").height();
            	
               //Tab
                $("#tabMyInfo").ligerTab({height:height});
				
            });
			
     </script> 
<style type="text/css"> 
	
    body{ padding:0px; margin:0;overflow:hidden;border:1px solid red;}  

 </style>
</head>
<body>  

        <div  id="tabMyInfo" style="overflow:hidden; border:1px solid #A3C0E8; ">
        
           <div tabid="home" title="我的主页" icon="${ctx}/themes/img/icon/home.png" >
              		 我的主页 <br/>
              		 注意每个选项卡需要设置tabId。<br/>
              		 <br/>
              		 设置全屏 <br/>
              		 var height=$("body").height();<br/>
            		
		               //Tab <br/>
		                $("#tabMyInfo").ligerTab({height:height});
              		 
            </div> 
             <div  title="我的任务"  tabid="task"  >
               		我的任务
            </div> 
             <div  title="我的代办任务" tabid="delegatetask"  >
                	我的代办任务
            </div> 
        </div> 
       
 
      
</body>
</html>
