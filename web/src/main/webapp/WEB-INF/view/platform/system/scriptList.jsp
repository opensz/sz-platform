
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>脚本管理管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerComboBox.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/htDicCombo.js"></script>
	<script type="text/javascript">
	function handlerDelSelect()
	{
		//单击删除超链接的事件处理
		$("div.group > a.link.download").click(function()
		{	
			if($(this).hasClass('disabled')) return false;
			
			var action=$(this).attr("action");
			var $aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
			
			if($aryId.length == 0){
				$.ligerMessageBox.warn("请选择记录！");
				return false;
			}
			
			//提交到后台服务器进行日志删除批处理的日志编号字符串
			var delId="";
			var keyName="";
			var len=$aryId.length;
			$aryId.each(function(i){
				var obj=$(this);
				if(i<len-1){
					delId+=obj.val() +",";
				}
				else{
					keyName=obj.attr("name");
					delId+=obj.val();
				}
			});
			var url=action +"?" +keyName +"=" +delId ;
			
			$.ligerMessageBox.confirm('提示信息','确认导出吗？',function(rtn) {
				if(rtn) {
					var form=new org.sz.form.Form();
					form.creatForm("form", action);
					form.addFormEl(keyName, delId);
					form.submit();
				}
			});
			return false;
		
		});
	}
	
	function ImportDefTableWindow(conf)
	{
		if(!conf) conf={};
		var url=__ctx + "/platform/system/script/import.xht";
		conf.url=url;
		var dialogWidth=550;
		var dialogHeight=250;
		conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
		

		var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
			+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
		
		var rtn=window.showModalDialog(url,"",winArgs);
	}
	
	
	</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">脚本管理管理列表</span>
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
					<div class="group"><a class="link download"  action="export.xht">导出</a></div>
					<div class="group"><a class="link upload"  href="#"  onclick="ImportDefTableWindow()">导入</a></div>
				</div>	
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-search">
					<form id="searchForm" method="post" action="list.xht" >
							<div class="row">
								<span class="label">脚本分类:</span>
								<select name="Q_category_S">
									<option value="">全部</option>
									<c:forEach items="${categoryList }" var="catName">
										<option value="${catName}">${catName}</option>
									</c:forEach>
								</select>
								
							</div>
					</form>
					
			</div>
		
			<div class="panel-data">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="scriptList" id="scriptItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" class="table-grid" >
					<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${scriptItem.id}">
					</display:column>
					<display:column property="name" title="名称" sortable="true" sortName="name" style="text-align:left"></display:column>
						
					<display:column property="category" title="脚本分类" sortable="true" sortName="category" ></display:column>
					
					<display:column title="管理" media="html" style="width:180px">
						<a href="del.xht?id=${scriptItem.id}" class="link del">删除</a>
						<a href="edit.xht?id=${scriptItem.id}" class="link edit">编辑</a>
						
					</display:column>
				</display:table>
				<sz:paging tableId="scriptItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


