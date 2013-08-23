
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下属管理管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx }/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript">
	function dlgCallBack(userIds, fullnames) {
		if (userIds.length > 0) {
			$.post("addUnderUser.xht",
				   {userIds: userIds, userNames:fullnames },
					function(){
					   window.location.reload();
					 });			
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
						<span class="tbar-label">下属管理管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="javascript:add();">添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">									
												<span class="label">下属用户名:</span><input type="text" name="Q_underusername_S"  class="inputText" />
											
									</div>
							</form>
					</div>
					
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="userUnderList" id="userUnderItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${userUnderItem.id}">
							</display:column>
							
							<display:column property="underusername" title="下属用户名" sortable="true" sortName="underusername"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?id=${userUnderItem.id}" class="link del">删除</a>
								
							</display:column>
						</display:table>
						<sz:paging tableId="userUnderItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


