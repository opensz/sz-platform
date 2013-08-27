<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>设置图标</title>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	<link href="${ctx}/jslib/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<f:link href="web.css" ></f:link>
	<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
	<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/base.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerDrag.js"></script> 
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerDialog.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMessageBox.js"></script>
	<script type="text/javascript" 	src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
	<style type="text/css">
		html { overflow-x: hidden; overflow-y: auto; }
		#imgList{
			overflow: auto;
		}
		#imgList img {
			cursor: pointer;
			border-width: 1px; 
			border-style: solid;
			border-color: transparent; 
			padding: 1px;
		}
		#imgList .selected {
			border-color:  #86a9d1; 
			background-color:#c3dcfc; 
		}
	</style>
	<script type="text/javascript">
		//选中的图标
		var selected;
		
		var imgSrc="";
		$(function() {
			$('#imgList img').click(function() {
				if(selected) {
					$(selected).removeClass('selected');
				}
				$(this).addClass('selected');
				selected = this;
		
				var src=$(this).attr('src');
				imgSrc=src;
			});
			
			$("#defLayout").ligerLayout({ leftWidth: 200,height: '95%',
				bottomHeight :50,
				 
				 allowBottomResize:false});
			
		});
		function selectIcon(){
			if(imgSrc==""){
				$.ligerMessageBox.success("提示信息","没有选择图标!");
				return ;
			}
			
			var obj={srcValue:imgSrc};
			window.returnValue=obj;
			window.close();
		}
	</script>
</head>
<body style="margin:0 0 0 0;padding: 0 0 0 0;">
	<div id="defLayout">
	<input type="hidden" id="src">	
	<div  position="center"   id="imgList">
		<c:forEach items="${iconList}" var="d" varStatus="status">
			<img src="${iconPath}/${d}" title="${d}"/>
		</c:forEach>
	</div>
		<div position="bottom"  class="bottom" style='margin-top:10px;'>
			<a href='#' class='button' onclick="selectIcon()" ><span>选择</span></a>
			<a href='#' class='button' style='margin-left:10px;'  onclick="window.close()"><span>取消</span></a>
		</div>
	</div>
</body>
</html>