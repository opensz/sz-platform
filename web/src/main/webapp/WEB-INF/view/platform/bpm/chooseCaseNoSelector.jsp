<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
   <%@include file="/commons/include/get.jsp"%>
	<style type="text/css">
		html { overflow-x: hidden; }
	</style>
</head>
<body>
	
			<div class="panel">
				<div class="panel-body">
					<div class="panel-data">
						<c:set var="checkAll">
							<input type="checkbox" id="chkall" />
						</c:set>
		
						<table id="sysOrgTable" class="table-grid table-list" id="0"
							cellpadding="1" cellspacing="1">
							<thead>
								<th width="30px"><input type="radio" id="chkall"></th>
								<th>单号</th>		
							</thead>
							<tbody>
								<c:forEach items="${list}" var="m">
									<tr id="${m.id}" 
										class="${status.index%2==0?'odd':'even'}">
										<td>
											<input type="radio" class="pk" name="id" value="${m.id }">
											<input type="hidden" name="name" value="${m.case_no}"> 
										</td>
										<td nowrap="nowrap">
											${m.case_no}
										</td>
									</tr>
								</c:forEach>
		
							</tbody>
						</table>
						<sz:paging tableId="caseList"/>
					</div>
				</div>
		
	</div>
	
</body>
</html>