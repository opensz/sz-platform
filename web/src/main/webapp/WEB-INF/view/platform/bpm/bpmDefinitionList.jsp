<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.sz.platform.model.bpm.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义扩展管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerWindow.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/ImportXmlWindow.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/tabOperator.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowRightDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowUtil.js" ></script>
<script type="text/javascript">
	  
	  
	function ExportXml(){
		var actDeployId="";
		//提交到后台服务器进行日志删除批处理的日志编号字符串
		var $aryId = $("input[type='checkbox'][class='pk']:checked");
		var len=$aryId.length;
		if(len==0){
			$.ligerMessageBox.warn("还没有选择,请选择一项流程定义!");
			return;
		}
		$aryId.each(function(i){
			var obj=$(this);
			if(i<len-1){
				actDeployId+=obj.val() +",";
			}
			else{
				actDeployId+=obj.val();
			}
		});
		var value=actDeployId!=""?actDeployId:"";
		location.href="exportXml.xht?actDeployId="+value;
	}
     
	var copyDesign = function(){
		var $aryId = $("input[type='checkbox'][class='pk']:checked");
		var len=$aryId.length;
		if(len==0){
			$.ligerMessageBox.warn("还没有选择,请选择一项流程定义!");
			return;
		}
		if(len > 1){
			$.ligerMessageBox.warn("必须选择一个流程定义,您选择了"+len+"个!");
			return;
		}
		var actDeployId = "";
		$aryId.each(function(i){
			var obj=$(this);
			if(i<len-1){
				actDeployId+=obj.val() +",";
			}
			else{
				actDeployId+=obj.val();
			}
		});
		if(actDeployId){
			top.showMask("正在拷贝，请稍候...");
			location.href = 'copyBpmDef.xht?copyId=' + actDeployId;
		}
	}
</script>
</head>
<body>      
			<div class="panel">
				<div class="panel-top">
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link flowDesign" onclick="window.open('design.xht')">在线流程设计</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link flowDesign" onclick="copyDesign();">拷贝在线流程设计</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link download"  href="#" onclick="ExportXml()">导出</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link upload"  href="#" onclick="ImportXmlWindow()">导入</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
						<form id="searchForm" method="post" action="list.xht">
							<div class="row">
								<span class="label">&nbsp;&nbsp;&nbsp;标题:</span><input type="text" name="Q_subject_S"  class="inputText" style="width:13%;"/>
								<!-- 
								<span class="label">流程分类:</span><input type="text" name="Q_typeName_S" class="inputText" style="width:13%;"/>
								 -->
								<span class="label">流程定义Key:</span><input type="text" name="Q_defKey_S"  class="inputText" style="width:13%;"/>
								<span class="label">描述:</span><input type="text" name="Q_descp_S" class="inputText" maxlength="125" style="width:13%;"/>
								<span class="label">状态:</span>
								<select name="Q_status_L" class="select" style="width:13%;">
									<option value="">--全部--</option>
									<option value="0">未发布</option>
									<option value="1">已发布</option>
									<option value="2">禁用</option>
								</select>
								<br>
								<span class="label">创建时间:</span><input type="text" name="Q_createtime_DL"  class="inputText date" style="width:13%;"/>
								<span class="label">至</span><input name="Q_endcreatetime_DG" class="inputText date" style="width:13%;"/>
							</div>
						</form>
					</div>
				
					<div class="panel-data">						
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmDefinitionList" id="bpmDefinitionItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="defId" value="${bpmDefinitionItem.defId}">
							</display:column>
							<display:column property="subject" title="标题" sortable="true" sortName="subject" ></display:column>
							
							<display:column title="分类" sortable="true" sortName="typeName">
								<c:out value="${bpmDefinitionItem.typeName}"></c:out>
							</display:column>
							<display:column property="versionNo" title="版本" sortable="true" sortName="versionNo" style="width:60px"></display:column>
							<display:column title="状态" sortable="true" sortName="status" style="width:60px">
								<c:choose>
									<c:when test="${bpmDefinitionItem.status eq 0}">未发布</c:when>
									<c:when test="${bpmDefinitionItem.status eq 1}">已发布</c:when>
									<c:when test="${bpmDefinitionItem.status eq 2}">禁用</c:when>
									<c:otherwise>未选择</c:otherwise>
								</c:choose>
							</display:column>
							<display:column title="创建时间" sortable="true" sortName="createtime" >
								<fmt:formatDate value="${bpmDefinitionItem.createtime}" pattern="yyyy-MM-dd"/>
							</display:column>
							
							<display:column title="管理" media="html" style="width:280px;">
								<a href="del.xht?defId=${bpmDefinitionItem.defId}" class="link del" title="删除">删除</a>
								<a href="design.xht?defId=${bpmDefinitionItem.defId}" target="_blank" class="link flowDesign" title="在线流程设计">设计</a>
								<c:if test="${bpmDefinitionItem.status==1}">
									<a href="detail.xht?defId=${bpmDefinitionItem.defId}" class="link setting" title="设置">设置</a>
									<a href="#" onclick="FlowUtil.startFlow(${bpmDefinitionItem.defId})" class="link run" title="启动流程">启动</a>	
								</c:if>
								<a href="javascript:FlowRightDialog(${bpmDefinitionItem.defId},0)" class="link grant" title="授权">授权</a>
								<c:if test="${bpmDefinitionItem.status==0}">
									<a href="deploy.xht?defId=${bpmDefinitionItem.defId}" class="link deploy" title="发布">发布	</a>
								</c:if>
							</display:column>
						</display:table>
						<sz:paging tableId="bpmDefinitionItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


