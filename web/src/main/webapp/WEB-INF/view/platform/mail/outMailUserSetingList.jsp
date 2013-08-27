
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>邮箱管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	function test(id){
		$.ligerDialog.waitting('正在测试连接,请耐心等候...');
		var param={id:id};
		$.post("test.xht",param,function(data){
			$.ligerDialog.closeWaitting();
			var obj=new org.sz.form.ResultMessage(data);
			if(obj.isSuccess()){//成功
				$.ligerDialog.success("连接成功！");
		    }else{//失败
		    	$.ligerDialog.error(obj.getMessage());
		    }
		});
	}
</script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">邮箱管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="edit.xht?">添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link update" id="btnUpd" action="edit.xht">修改</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del "  action="del.xht">删除</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
												<span class="label">账号名称:</span><input type="text" name="Q_userName_S"  class="inputText" />
											
												<span class="label">邮箱地址:</span><input type="text" name="Q_mailAddress_S"  class="inputText" />
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="outMailUserSetingList" id="outMailUserSetingItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${outMailUserSetingItem.id}">
							</display:column>
							<display:column property="userName" title="账号名称" sortable="true" sortName="userName"></display:column>
							<display:column property="mailAddress" title="邮箱地址" sortable="true" sortName="mailAddress"></display:column>
							<display:column title="是否默认" sortable="true" sortName="isDefault">
								<c:choose>
								 	<c:when test="${outMailUserSetingItem.isDefault==1}"><font color="green"><b>是</b></font></c:when>
								 	<c:when test="${outMailUserSetingItem.isDefault==0}"><font color="red"><b>否</b></font></c:when>
								 	<c:otherwise>否</c:otherwise>
								</c:choose>
							</display:column>
						
							<display:column title="管理" media="html" style="width:280px">
								<c:choose>
									<c:when test="${outMailUserSetingItem.isDefault==1}">
										<div style="float: left"><a class="link setting " style="color:gray;">设置默认</a></div>
									</c:when>
									<c:otherwise>
									<a style="float: left" class="link setting" href="setDefault.xht?id=${outMailUserSetingItem.id}"><span >设置默认</span></a>
									</c:otherwise>
								</c:choose>
								<a style="float: left" href="del.xht?id=${outMailUserSetingItem.id}" class="link del">删除</a>
								<a style="float: left" href="edit.xht?id=${outMailUserSetingItem.id}" class="link edit">编辑</a>
								<a style="float: left" href="get.xht?id=${outMailUserSetingItem.id}" class="link detail">明细</a>
								<a style="float: left" href="#" onclick="test(${outMailUserSetingItem.id})" class="link test">测试</a>
								<input type="hidden" id="outMailUserSetingId"value="${outMailUserSetingItem.id}">
							</display:column>
							
						</display:table>
						<sz:paging tableId="outMailUserSetingItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


