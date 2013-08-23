<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <%@include file="/commons/include/form.jsp" %>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    

   
    <script src="${ctx }/js/ligerui/plugins/ligerEasyTab.js" type="text/javascript"></script>

    <script type="text/javascript"> 
        $(function(){ 
            $("#navtab1").ligerEasyTab();
        });
    </script>
</head>
<body style="padding:0px">
<div id="navtab1" style="width: 600px;height:300px; ">
	<div tabid="home" title="基本信息" lselected="true" >
		基本信息<br/>
		使用方法:<br/>
		$("#navtab1").ligerEasyTab();
	</div>
	<div  title="教育信息"  >
		教育信息
	</div>
	<div  title="培训记录"  >
		培训记录
	</div>
	<div  title="获得奖励" >
		获得奖励
	</div>
</div>
</body>
</html>
