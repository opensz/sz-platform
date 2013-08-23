
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>系统组织管理</title>
	<%@include file="/commons/include/get.jsp"%>
	<script type="text/javascript">
		//树列表的收展
		function treeClick(obj) {
	
			var clazz = $(obj).attr("class");
			var id = $(obj).parents("tr").attr("id");
	
			if (clazz == "tree-list-minus") {
				toggleChild(id, "hide");
			} else if (clazz == "tree-list-plus") {
				toggleChild(id, "show");
			}
	
			//置换加减号
			$(obj).toggleClass("tree-list-minus");
			$(obj).toggleClass("tree-list-plus");
		};
	
		//子结点收展
		function toggleChild(parentId, type) {
			var child = $("tr[parentId='" + parentId + "']");
			$.each(child, function(i, c) {
				if (type == "hide") {
					$(c).hide();
				} else if (type == "show") {
					$(c).find("a[name='tree_a']").removeClass("tree-list-plus");
					$(c).find("a[name='tree_a']").addClass("tree-list-minus");
					$(c).show();
				}
	
				var id = $(c).attr("id");
				toggleChild(id, type);
			});
	
		};
	</script>
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
						<th width="30px"><input type="checkbox" id="chkall"></th>
						<th>名称</th>
						<th>合同编号</th>
					</thead>
					<tbody>

						<c:forEach items="${contractList}" var="model">
							<tr id="${model.id }" parentId="${model.id }"
								class="${status.index%2==0?'odd':'even'}">
								<td>
									<input type="radio" class="pk" name="contractId" value="${model.id }">
									<input type="hidden" name="contractName" value="${model.contractName}"> 
										<input type="hidden" name="cIId" value="${model.cIId}"> 
									<input type="hidden" name="signedTime" value="<fmt:formatDate value="${model.signedTime}" pattern="yyyy-MM-dd" />"> 
									
									<input type="hidden" name="expirationDate" value="<fmt:formatDate value="${model.expirationDate}" pattern="yyyy-MM-dd" />"> 
								</td>

								<td nowrap="nowrap">${model.contractName}
								</td>

								<td>${model.cIId }</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
				<sz:paging tableId="contractItem"/>
			</div>
		</div>
	</div>
</body>
</html>