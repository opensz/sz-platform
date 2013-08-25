<%@page pageEncoding="UTF-8" import="org.sz.platform.system.model.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
	<%@ taglib prefix="sz" uri="http://www.servicezon.com/paging" %>
<html>
<head>
<title>选择 </title>
	<%@include file="/commons/include/form.jsp" %>

	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	

</head>
<body>
	<div class="panel">
		<div class="panel-body">
			<div class="panel-data">
				
				<table id="sysOrgTable" class="table-grid table-list" id="0"
					cellpadding="1" cellspacing="1">
					<thead>
						<th width="30px"><input type="radio" id="chkall"></th>
						<th>名称</th>
						
					</thead>
					<tbody>

						<c:forEach items="${supplierList}" var="model">
							<tr id="${model.orgId }" parentId="${model.orgId }"
								class="${status.index%2==0?'odd':'even'}">
								<td>
									<input type="radio" class="pk" name="supplierId" value="${model.orgId }">
									<input type="hidden" name="supplierName" value="${model.orgName}"> 
										 
								</td>

								<td nowrap="nowrap">${model.orgName}
								</td>

							</tr>
						</c:forEach>

					</tbody>
				</table>
				<sz:paging tableId="supplier"/>
			</div>
		</div>
		</div>
		
</body>
</html>


