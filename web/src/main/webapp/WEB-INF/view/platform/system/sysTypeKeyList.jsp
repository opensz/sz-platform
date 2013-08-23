
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>系统分类键定义管理</title>
	<%@include file="/commons/include/get.jsp"%>
	<script type="text/javascript">
		$(function(){
			$("tr.odd,tr.even").unbind("hover");
			$("#sysTypeKeyItem").delegate("tbody>tr", "click", function() {
				$("#sysTypeKeyItem>tbody>tr").removeClass("over");
				$(this).addClass("over");
			});
			var hasChange=false;
			$("#moveupField").click(function(){
				move(true);
				hasChange=true;
			});
			
			$("#movedownField").click(function(){
				move(false);
				hasChange=true;
			});
			
			$("#saveSeqence").click(function(){
				if(!hasChange){
					$.ligerMessageBox.warn('提示信息',"还没有进行排序操作，请选择行进行排序!");
					return;
				}
				var aryTypeId=new Array();
				$("input[name='typeId']").each(function(i,obj){
					aryTypeId.push(obj.value);
				});
				var ids=aryTypeId.join(",");
				$.post("saveSequence.xht",{ids:ids},function(data){
					var obj=new org.sz.form.ResultMessage(data);
					if(obj.isSuccess()){
						$.ligerMessageBox.success('提示信息',obj.getMessage(),function(){
							location.reload();
						});
					}
					else{
						$.ligerMessageBox.warn('提示信息',obj.getMessage());
					}
				});
			});
		});
		
		
		/**
		 * 移动行
		 */
		 function move(direct){
			var objTr=$("#sysTypeKeyItem>tbody .over");
			if(objTr.length==0) return;
			
			if(direct){
				var prevObj=objTr.prev();
				if(prevObj.length>0){
					objTr.insertBefore(prevObj);	
				}
			}
			else{
				var nextObj=objTr.next();
				if(nextObj.length>0){
					objTr.insertAfter(nextObj);
				}
			}
		};
	</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">系统分类键定义管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.xht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="moveupField" class="link moveup">上移</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="movedownField" class="link movedown">下移</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="saveSeqence" class="link save">保存排序</a></div>

				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.xht">
					<div class="row">
						<span class="label">分类名称:</span><input type="text" name="Q_typeName_S" class="inputText" />
						<span class="label">分类键:</span><input type="text"
							name="Q_typeKey_S" class="inputText" />
					</div>
				</form>
			</div>
			<div class="panel-data">
				
				<display:table name="sysTypeKeyList" id="sysTypeKeyItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
					<display:column title="序号" media="html" style="text-align:center;width:30px;" >
						${sysTypeKeyItem_rowNum}
						<input type="hidden" name="typeId" value="${sysTypeKeyItem.typeId}"/>
					</display:column>
					<display:column property="typeName" title="分类名称" sortable="true" sortName="typeName"></display:column>
					<display:column property="typeKey" title="分类Key" sortable="true" sortName="typeKey"></display:column>
					<display:column title="系统" sortable="true" sortName="flag">
						<c:choose>
							<c:when test='${sysTypeKeyItem.flag ==1}'>
						 		<font color="red"><b>系统默认</b></font>
					  	 	</c:when>
						  	 <c:otherwise>
							 	
							 	<font color="green"><b>自定义</b></font>
						  	 </c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="类型" sortable="true" sortName="flag" style="text-align:center;">
						<c:choose>
							<c:when test="${sysTypeKeyItem.type==0}">
								<div title="平铺结构" class="icon-list">&nbsp;</div>
						   	</c:when>
					       	<c:otherwise>
						        <div title="树型结构" class="icon-tree">&nbsp;</div>
						   	</c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="管理" media="html" style="width:180px">
					<c:choose>
					  <c:when test='${sysTypeKeyItem.flag ==0}'>
						 <a href="del.xht?typeId=${sysTypeKeyItem.typeId}" class="link del">删除</a>
					   <a href="edit.xht?typeId=${sysTypeKeyItem.typeId}" class="link edit">编辑</a>
					  </c:when>
					</c:choose>
					</display:column>
				</display:table>
				
			</div>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


