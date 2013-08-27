<%--
	desc:edit the 通用表单对话框
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>显示通用对话框</title>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<f:link href="web.css" ></f:link>
	<link href="${ctx}/jslib/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
	<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/displaytag.js" ></script>
	<script type="text/javascript" src="${ctx}/jslib/calendar/My97DatePicker/WdatePicker.js"></script>
	<c:choose>
		<c:when test="${bpmFormDialog.style==1}">
			<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
			<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
			<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script> 
			<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script>
			<script type="text/javascript">
				var json=${bpmFormDialog.displayfield};
				var isMuliti=${bpmFormDialog.issingle==0};
				var dialogTree;
				var returnField="${bpmFormDialog.resultfield}";
				$(function(){
					var setting = {
						data: {
							key : {name: json.displayName},
							simpleData: {
								enable: true,
								idKey: json.id,
								pIdKey: json.pid,
							}
						},
						check: {
							enable: isMuliti,
							chkboxType:  { "Y" : "", "N" : "p" }
						}
						
					};
					//一次性加载
					var url="${ctx}/platform/form/bpmFormDialog/getTreeData.xht?alias=${bpmFormDialog.alias}";
					
					$.post(url,function(result){
						dialogTree=$.fn.zTree.init($("#dialogTree"), setting,result);
						dialogTree.expandAll(true);
					})
				});
				
				function getResult(){
				
					var aryField=returnField.split(",");
					if(isMuliti){
						var aryRtn=new Array();
						var nodes = dialogTree.getCheckedNodes(true);
						if(nodes.length<1){
							return -1;	
						}
						
						for(var i=0;i<nodes.length;i++){
							var obj=new Object();
							var node=nodes[i];
							for(var j=0;j<aryField.length;j++)	{
								var field=aryField[j];
								obj[field]=node[field];
							}
							aryRtn.push(obj);
						}
						return aryRtn;
					}
					else{
						var nodes = dialogTree.getSelectedNodes();
						if(nodes.length<1){
							return -1;
						}
						var obj=new Object();
						var node=nodes[0];
						for(var i=0;i<aryField.length;i++)	{
							var field=aryField[i];
							obj[field]=node[field];
						}
						return obj;
					}
				}
			</script>
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				var isSingle=${bpmFormDialog.issingle};
				function getResult(){
					var tableObj=$("#bpmFormDialogTable");
					if(isSingle==1){ //单选
						var obj=$("input.pk:checked",tableObj);
						if(obj.length<1){
							return -1;
						}
						var json=eval("(" + obj.next("textarea").val() +")");
						return json;
					}
					else{  //多选
						var obj=$("input.pk:checked",tableObj);
						if(obj.length<1){
							return -1;
						}
						else{
							var jsonArray=new Array();
							obj.each(function(){
								var chkObj=$(this);
								var json=eval("(" + chkObj.next("textarea").val() +")");
								jsonArray.push(json);
							})
							return jsonArray;
						}
					}
				}
			
			</script>
		</c:otherwise>
	</c:choose>
	<style type="text/css">
		body{margin: 2 px 2px 2px 2px;}
	</style>
</head>
<body>
	<c:choose>
		<c:when test="${bpmFormDialog.style==1}">
			<div id="dialogTree" class="ztree"></div>
		</c:when>
		<c:otherwise>
			<div class="panel">
				<div class="panel-top">
					<c:if test="${fn:length(bpmFormDialog.conditionList)>0}">
						<div class="panel-toolbar">
							<div class="toolBar">
								<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							</div>	
						</div>
						<div class="panel-search">
								<form id="searchForm" method="post" action="show.xht">
										<div class="row">
											<c:forEach items="${bpmFormDialog.conditionList}" var="col" >
												<span class="label">${col.comment }:</span>
												<c:choose>
													<c:when test="${col.fieldType=='date'}">
														<input type="text" name="${col.conditionField }"  class="inputText date" />
													</c:when>
													<c:otherwise>
														<input type="text" name="${col.conditionField }"  class="inputText" />
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</div>
										<input type="hidden" name="dialog_alias_" value="${bpmFormDialog.alias}">
								</form>
						</div>
					</c:if>
				</div>
				<div class="panel-body">
					
					<div class="panel-data">
						<div class='panel-table'>
						<table id="bpmFormDialogTable" cellpadding="1" class="table-grid table-list" cellspacing="1">
							<thead>
							<tr>
							<th></th>
							<c:forEach items="${bpmFormDialog.displayList}" var="field">
								<th>${field.comment }</th>	
							</c:forEach>
							</thead>
							<tbody>
							    
								<c:forEach items="${bpmFormDialog.list}" var="row" varStatus="status">
									<c:set var="clsName" ><c:choose><c:when test="${status.index %2==0 }">even</c:when><c:otherwise>odd</c:otherwise></c:choose></c:set>
									<tr class="${clsName}">
										<td>
											<c:choose>
												<c:when test="${bpmFormDialog.issingle==1}">
													<input type="radio" name="rtn" class="pk" />
													<textarea style="display:none">{<c:forEach items="${bpmFormDialog.returnList}" var="field" varStatus="status"><c:choose><c:when test="${status.last}">"${field.fieldName}":"${row[field.fieldName] }"</c:when><c:otherwise>"${field.fieldName}":"${row[field.fieldName] }",</c:otherwise></c:choose></c:forEach>}</textarea>
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="rtn" class="pk"  />
													<textarea style="display:none">{<c:forEach items="${bpmFormDialog.returnList}" var="field" varStatus="status"><c:choose><c:when test="${status.last}">"${field.fieldName}":"${row[field.fieldName] }"</c:when><c:otherwise>"${field.fieldName}":"${row[field.fieldName] }",</c:otherwise></c:choose></c:forEach>}</textarea>
												</c:otherwise>
											</c:choose>
										</td>
										<c:forEach items="${bpmFormDialog.displayList}" var="field">
											<td>${row[field.fieldName] }</td>	
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
						<c:if test="${bpmFormDialog.needpage==1 }">
						${pageHtml }
						</c:if>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
		</c:otherwise>
	</c:choose>	
       	 
</body>
</html>
