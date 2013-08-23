<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title></title>
        <%@include file="/commons/include/form.jsp" %>
        <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	    <link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	    
	       
	    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerLayout.js" ></script>
        <style type="text/css"> 
			
            body{ padding:0px; margin:0; }
            #layout1{  width:100%;margin:0; padding:0;  }  
           
           </style>
        <script type="text/javascript">

                $(function (){
                    $("#layout1").ligerLayout({ leftWidth: 200,height: '100%'});
                });
                
         </script> 
    </head>
    <body >  
      
    	<div id="layout1">
            <div position="left" title="组织机构管理"></div>
            <div position="center">
            <div class="l-layout-header">标题</div>
            <h4>$("#layout1").ligerLayout({ leftWidth: 200});</h4> 
            
                
            </div>  
    	</div> 
    </body>
    </html>
