<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>桌面栏目管理表明细</title>
<%@include file="/commons/include/get.jsp"%>
<link href="${ctx}/js/desktop/inettuts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/desktop/inettuts.js.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMessageBox.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/htButtons.js"></script>
<script type="text/javascript" src="${ctx}/js/desktop/jquery-ui-personalized.min.js"></script>
<script type="text/javascript" src="${ctx}/js/desktop/inettuts.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/desktop/desktopShow.js"></script>
<script type="text/javascript">	
	var nonactivated = 'nonactivated';
	var active = 'active';	
	$(function() {
		$("#colshtml").delegate("td", "click", function() {
			changeClass(this);
		});
		$("#colshtml table").attr("class", "wtMonth");//设置表格显示边框
		$(".more").attr("class", "more-hide");//设置不显示更多按钮
		iNettuts.init();
	});	
	//更换布局列数
	function changeThisLayout() {
		var cols = $("#colsSelect").find("option:selected").attr("cols");
		var width = $("#colsSelect").find("option:selected").attr("widthStr");
		var layoutId=$("#colsSelect").find("option:selected").val();
		var html = getColumnTemp(cols, width, 1,layoutId);
		$("#colshtml").html(html);//栏目模板的html文件
	}
</script>
</head>
<body>
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">桌面布局设置</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group">
					<a class="link save" id="save" href="#" onclick="saveMycolumn('/platform/system/desktopMycolumn/saveCol.xht')">保存布局</a>
				</div>
			</div>
		</div>
	</div>
	<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td>
				<div class="row" style="margin-top: 10px; margin-left: 0px;">
					<ul>
						<li style="float: left;">&nbsp;&nbsp;<span class="label">请先选择下面栏目区域，再选择栏目:</span></li>
						<li style="float: left;"><select id="columnName"
							name="columnName">
								<c:forEach items="${desktopColumnList}" var="t">
									<option value="${t.id}">${t.name}</option>
								</c:forEach>
						</select>&nbsp;&nbsp;</li> 
						<li style="float: left;"><a class="button" onclick="addThisColumn()"><span>添加到此列</span></a></li>
						<li style="float: left;">&nbsp;&nbsp;<span class="label">选择布局:</span>
							&nbsp;&nbsp;
						</li>
						<li style="float: left;"><select id="colsSelect" name="cols">
								<c:forEach items="${desktopLayout}" var="t">
									<option value="${t.id}" cols="${t.cols}" widthStr="${t.width}">${t.name}</option>
								</c:forEach>
						</select>&nbsp;&nbsp;</li>
						<li style="float: left;">&nbsp;&nbsp;</li>
						<li style="float: left;"><a class="button"
							onclick="changeThisLayout()"><span>更换布局</span></a></li>
					</ul>
				</div>
			</td>
		</tr>
	</table>
	<div id="colshtml">
	${html}	
	</div>
</body>
</html>