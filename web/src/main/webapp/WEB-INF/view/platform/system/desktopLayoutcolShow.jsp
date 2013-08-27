<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>设置栏目</title>
	<%@include file="/commons/include/get.jsp" %>
	<%@include file="/commons/include/getById.jsp" %>
    <link href="${ctx}/js/desktop/inettuts.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/js/desktop/inettuts.js.css" rel="stylesheet" type="text/css" /> 
    <script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMessageBox.js"></script>  
    <script type="text/javascript" src="${ctx}/jslib/lg/plugins/htButtons.js" ></script>
    <script type="text/javascript" src="${ctx}/js/desktop/jquery-ui-personalized.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/desktop/inettuts.js"></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/desktop/desktopShow.js" ></script>    
	<script type="text/javascript">		
		var nonactivated = 'nonactivated';
		var active = 'active';
        $(function () {        	
        	$("#colshtml table").attr("class", "wtMonth");//设置表格显示边框
        	$(".more").attr("class", "more-hide");//设置不显示更多按钮
        	$("#colshtml").delegate("td", "click", function() {
    			changeClass(this);//注册选择列的事件
    		});       
        	iNettuts.init();//初始化（设置可拖动，可删除等）
        });
	</script>
</head>
<body>		
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">设置栏目</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="save" href="#" onclick="saveMycolumn('/platform/system/desktopLayoutcol/saveCol.xht')" >保存栏目</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="../desktopLayout/list.xht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr><td>
			<div class="row" style="margin-top: 10px; margin-left: 0px;">
				<ul>
					<li style="float: left;"><span class="label">请先选择下面栏目区域，再选择栏目:</span></li>
					<li style="float: left;">
					<select id="columnName" name="columnName">
						<c:forEach items="${desktopColumnList}" var="t">
							<option value="${t.id}">
								${t.name}
							</option>
						</c:forEach>
					</select>
					</li>
					<li style="float: left;">
						<a class="button" id="addThisColumn"  onclick="addThisColumn('news')"><span>添加到此列</span></a>						
					</li>
				</ul>
			</div></td></tr></table>
		<div id="colshtml">${f:unescapeXml(html)}</div>
</body>
</html>
