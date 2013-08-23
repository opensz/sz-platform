<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>用户角色映射表管理</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx }/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript">
	function dlgCallBack(userIds, fullnames) {
		if (userIds.length > 0) {
			var form = new org.sz.form.Form();
			form.creatForm("form", "${ctx}/platform/system/userRole/add.xht");
			form.addFormEl("roleId", "${roleId}");
			form.addFormEl("userIds", userIds);
 	 		form.submit();
		}
	};

	function add() {
		UserDialog({
			callback : dlgCallBack,
			isSingle : false
		});
	}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">用户角色映射表管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link add" href="javascript:add();">加入用户</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link del" action="del.xht">删除</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back"
							href="${ctx }/platform/system/sysRole/list.xht">返回</a>
					</div>
				</div>
			</div>
		</div>


		<div class="panel-body">
			<div class="panel-search">
				<form id="searchForm" method="post" action="edit.xht?roleId=${roleId}">
						<div class="row">
							<span class="label">姓名:</span><input type="text" name="Q_fullname_S"  class="inputText" />
							<span class="label">帐号:</span><input type="text" name="Q_account_S"  class="inputText" />					
						</div>
						
				</form>
			</div>
			<div class="panel-data">
				<c:set var="checkAll">
					<input type="checkbox" id="chkall" />
				</c:set>
				<display:table name="userRoleList" id="userRoleItem"
					requestURI="edit.xht" sort="external" cellpadding="1"
					cellspacing="1" export="false" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input type="checkbox" class="pk" name="userRoleId" value="${userRoleItem.userRoleId}">
					</display:column>

					<display:column property="fullname" title="用户名称" sortable="true" sortName="fullname"></display:column>
					<display:column property="account" title="帐号" sortable="true" sortName="account"></display:column>

					<display:column title="管理" media="html" style="width:180px">
						<a href="del.xht?userRoleId=${userRoleItem.userRoleId}" class="link del">删除</a>
					</display:column>
				</display:table>
				<sz:paging tableId="userRoleItem"/>
			</div>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


