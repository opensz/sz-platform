<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>系统数据源管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	$(function() 
	{
		$('#testConnect').click(function() {
			var ids = '';
			$(':checkbox[name=ID][checked]').each(function() {
				ids += ',' + $(this).val();
			});
			if(ids.length == 0) {
				$.ligerMessageBox.warn('请选择记录!');
				return;
			}
			$.ligerDialog.waitting('正在测试连接，请等待...');
			$.post('testConnectById.xht', {id: ids.substring(1)}, function(data) {
				$.ligerDialog.closeWaitting();
				var msg = '';
				var success = true;
				$(data).each(function(i, d) {
					
					if(d.success) {
						msg += '<p>' +  d.name + ': <font color="green">连接成功!</font></p>';
					} else {
						success = false;
						msg += '<p>' +  d.name + ': <font color="red">连接失败!<br>原因：' + d.msg + '</font></p>';
					}
				});
				if(success) {
					$.ligerDialog.success(msg);
				} else {
					$.ligerDialog.error(msg);
				}
			});
			
		});
	});
</script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">系统数据源列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="edit.xht">添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link update" id="btnUpd" action="edit.xht">修改</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link test" id="testConnect">测试连接</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
												<span class="label">数据源名称:</span><input type="text" name="Q_name_S"  class="inputText" />
											
												<span class="label">别名:</span><input type="text" name="Q_alias_S"  class="inputText" />
											
												<span class="label">数据库类型:</span>
												
												<select id="selDbType"  name="dbType">
													<option value="">请选择</option>
													<option value="oracle"  >ORACLE</option>
													<option value="sql2005" >MSSQL2005</option>
													<option value="mysql" >MYSQL</option>
												</select>
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysDataSourceList" id="sysDataSourceItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${sysDataSourceItem.id}">
							</display:column>
							<display:column property="name" style="text-align:left;" title="数据源名称" sortable="true" sortName="name"></display:column>
							<display:column  property="dbType"  title="数据库类型" ></display:column>
							<display:column property="alias" title="别名" sortable="true" sortName="alias"></display:column>
							<display:column title="管理" media="html" style="width:120px;">
								<a href="del.xht?id=${sysDataSourceItem.id}" class="link del">删除</a>
								<a href="edit.xht?id=${sysDataSourceItem.id}" class="link edit">编辑</a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysDataSourceItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


