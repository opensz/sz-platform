
<%--
	time:2011-11-16 16:34:16
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑系统数据源</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysDataSource"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#sysDataSourceForm').submit(); 
			});
			
			$('#testConnect').click(function() {
				var rtn=$('#sysDataSourceForm').valid(); 
			
				if(!rtn){
					return;
				}
				var data = {};
				$('#sysDataSourceForm input').each(function(i, d) {
					data[$(d).attr('name')] = $(d).val();
				});
				$.ligerDialog.waitting("正在连接请稍后...")
				$.post('testConnectByForm.xht', data, function(data) {
					$.ligerDialog.closeWaitting();
					var d = data[0];
					if(d.success) {
						$.ligerDialog.success('<p><font color="green">连接成功!</font></p>');
					} else {
						$.ligerDialog.error('<p><font color="red">连接失败!<br>原因：' + d.msg + '</font></p>');
					}
				});
				
			});
			
			$("#selDbType").change(function(){
				var type=$(this).val();
				var driver="";
				var jdbcUrl="";
				switch(type){
					case "oracle":
						driver="oracle.jdbc.OracleDriver";
						jdbcUrl="jdbc:oracle:thin:@主机:1521:数据库实例";
						break;
					case "sql2005":
						driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
						jdbcUrl="jdbc:sqlserver://主机:1433;databaseName=数据库名;";
						break;
					case "mysql":
						driver="com.mysql.jdbc.Driver";
						jdbcUrl="jdbc:mysql://主机:3306/数据库名?useUnicode=true&amp;characterEncoding=utf-8";
						break;
					default:
						driver="";
						jdbcUrl="";
						break;
				}
				$("#driverName").val(driver);
				$("#url").val(jdbcUrl);
			});
		});
		
		
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
			    <c:choose>
			        <c:when test="${sysDataSource.id !=null }">
			            <span class="tbar-label">编辑系统数据源</span>
			        </c:when>
			        <c:otherwise>
			            <span class="tbar-label">添加系统数据源</span>
			        </c:otherwise>
			    </c:choose>				
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link test" id="testConnect">测试连接</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">

				<form id="sysDataSourceForm" method="post" action="save.xht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">数据源名称:<span class="required">*</span> </th>
							<td ><input type="text" id="name" name="name" value="${sysDataSource.name}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">别名:<span class="required">*</span> </th>
							<td >
								<c:choose>
									<c:when test="${sysDataSource.id==null}">
										<input type="text" id="alias" name="alias" value=""  class="inputText"/></td>
									</c:when>
									<c:otherwise>
										${sysDataSource.alias}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th width="20%">数据库类型:<span class="required">*</span> </th>
							<td >
								
								<select id="selDbType"  name="dbType">
									<option value="">请选择</option>
									<option value="oracle" <c:if test="${sysDataSource.dbType=='oracle' }">selected</c:if>  >ORACLE</option>
									<option value="sql2005" <c:if test="${sysDataSource.dbType=='sql2005' }">selected</c:if>>MSSQL2005</option>
									<option value="mysql" <c:if test="${sysDataSource.dbType=='mysql' }">selected</c:if>>MYSQL</option>
								</select>
							</td>
						</tr>
						<tr>
							<th width="20%">驱动名称:<span class="required">*</span> </th>
							<td ><input type="text" id="driverName" name="driverName" value="${sysDataSource.driverName}" size="60"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">数据库URL:<span class="required">*</span> </th>
							<td ><input type="text" id="url" name="url" value="${sysDataSource.url}"  class="inputText" size="100"/></td>
						</tr>
						<tr>
							<th width="20%">用户名: </th>
							<td ><input type="text" id="userName" name="userName" value="${sysDataSource.userName}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">密码: </th>
							<td ><input type="password" id="password" name="password" value="${sysDataSource.password}"  class="inputText"/></td>
						</tr>
					</table>
					<input type="hidden" id="returnUrl" value="${returnUrl}" />
					<input type="hidden" name="id" value="${sysDataSource.id}" />
				</form>
		</div>
</div>
</body>
</html>

