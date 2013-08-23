
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>自定义表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
<script type="text/javascript" src="${ctx }/js/sz/platform/bpm/SetRelationWindow.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/ImportDefTableWindow.js" ></script>
<script type="text/javascript">
	var win;
	function showRelation(tableId,dsName) {
		var conf={tableId:tableId,dsName:dsName};
		SetRelationWindow(conf);
	}
	
	function assignMainTable(subTableId){
		var url=__ctx + "/platform/form/bpmFormTable/assignMainTable.xht?subTableId="+subTableId;
		var winArgs="dialogWidth=400px;dialogHeight=200px;help=0;status=0;scroll=0;center=1;resizable=1";
		url=url.getNewUrl();
		var rtn=window.showModalDialog(url,"",winArgs);
	}
	
	function newTableTemp(e){		
		var url=__ctx + '/platform/form/bpmTableTemplate/edit.xht?tableId='+e;
		win= $.ligerDialog.open({ url: url, height: 400,width:600 ,isResize: false });		
	}
	function closeWin(){
		if(win){
			win.close();
		}		
	}	
	function generator(tableId) {
		$.ligerMessageBox.confirm('提示信息','将连同子表一起生成,是否继续?',function(rtn){
			if(!rtn) return;

			$.post('generateTable.xht', {'tableId': tableId}, function(data) {
				var obj=new org.sz.form.ResultMessage(data);
				if(obj.isSuccess()){//成功
					location.reload();
			    }else{//失败
			    	$.ligerMessageBox.error('出错了',obj.getMessage());
			    }
			});

		});
	}
	
	// 导出自定义表
	function ExportXml(){
		var tableId="";
		var $aryId = $("input[type='checkbox'][class='pk']:checked");
		var len = $aryId.length;
		$aryId.each(function(i){
			var obj=$(this);
			if(obj.val()==1){
				tableId+= obj.attr('id')+",";
			}
		});
		
		if(tableId.length==0){
			$.ligerMessageBox.warn("请选择主表进行导出!");
			return;
		}else{
			value=tableId.substring(0,tableId.length-1);
		}

		location.href="exportXml.xht?tableId="+value;
	}
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">自定义表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.xht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link download"  href="#" onclick="ExportXml()">导出</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link redo"  href="#" onclick="ImportDefTableWindow()">导入</a></div>
				</div>	
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.xht">
					<div class="row">
						<span class="label">表名:</span><input type="text" name="Q_tableName_S"  class="inputText" />
					
						<span class="label">描述:</span><input type="text" name="Q_tableDesc_S"  class="inputText" />
					
						<span class="label">是否主表:</span>
						<select name="Q_isMain_SN">
							<option value="">全部</option>
							<option value="1">主表</option>
							<option value="0">子表</option>
						</select>
						
						<span class="label">外部表:</span>
						<select name="Q_isExternal_SN">
							<option value="">全部</option>
							<option value="1">外部表</option>
							<option value="0">本地表</option>
						</select>
						
						<span class="label">数据源:</span><input type="text" name="Q_dsAlias_S"  class="inputText" />
					</div>
				</form>
			</div>
			
			<div class="panel-data">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="bpmFormTableList" id="bpmFormTableItem" requestURI="list.xht" 
			    	sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input type="checkbox" class="pk" name="tableId" id="${bpmFormTableItem.tableId}" 
							value="${bpmFormTableItem.isMain}">
					</display:column>
					<display:column property="tableName" title="表名" style="text-align:left"></display:column>
					
					<display:column property="tableDesc" title="描述" style="text-align:left"></display:column>
					<display:column title="属性" style="text-align:left">
						<c:choose>
							<c:when test="${bpmFormTableItem.isMain == 1}">
								主表
							</c:when>
							<c:otherwise>
								子表
								<c:if test="${bpmFormTableItem.mainTableId == 0}">
									<a href="#" onclick="assignMainTable(${bpmFormTableItem.tableId})" >分配主表</a>
								</c:if>
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="状态"  style="text-align:center">
						<c:choose>
							<c:when test="${bpmFormTableItem.isPublished==0}">
								<span class="red">未生成</span>
							</c:when>
							<c:otherwise>
								<span class="green">已生成</span>
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="数据源">
							<c:if test="${bpmFormTableItem.isExternal == 1 }">
								${bpmFormTableItem.dsName }		
							</c:if>
					</display:column>
					<display:column title="管理" media="html" style="text-align:left;">
						<c:choose>
							<c:when test="${bpmFormTableItem.isExternal == 0 }">
								<a href="edit.xht?tableId=${bpmFormTableItem.tableId}" class="link edit">编辑</a>
								
								<a href="delByTableId.xht?tableId=${bpmFormTableItem.tableId}" class="link del">删除</a>
								
								<c:if test="${bpmFormTableItem.isMain == 1  && bpmFormTableItem.isPublished==0}">
									<a href="#" class="link table" onclick="generator('${bpmFormTableItem.tableId}')">生成表</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<a href="editExt.xht?tableId=${bpmFormTableItem.tableId}" class="link edit">编辑</a>
								<a href="delExtTableById.xht?tableId=${bpmFormTableItem.tableId}" class="link del">删除</a>
								<a href="#" onclick="showRelation('${bpmFormTableItem.tableId}','${bpmFormTableItem.dsName}')">表关系</a>
							</c:otherwise>
						</c:choose>
						<a href="get.xht?tableId=${bpmFormTableItem.tableId}" class="link detail">明细</a>
					
						<c:if test="${bpmFormTableItem.isMain == 1 &&  bpmFormTableItem.isPublished==1}">
							<a href="javascript:;" class="link table" onclick="newTableTemp(${bpmFormTableItem.tableId})">生成数据模板</a>
						</c:if>
					</display:column>
				</display:table>
				<sz:paging tableId="bpmFormTableItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


