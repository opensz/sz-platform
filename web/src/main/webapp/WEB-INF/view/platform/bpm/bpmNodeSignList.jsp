
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会签任务投票规则管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">会签任务投票规则管理列表</span>
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
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
											
											
												<span class="label">流程节点ID:</span><input type="text" name="Q_nodeId_S"  class="inputText" />
											
												<span class="label">票数:</span><input type="text" name="Q_voteAmount_S"  class="inputText" />
											
												<span class="label">决策方式:</span><input type="text" name="Q_decideType_SN"  class="inputText" />
											
												<span class="label">1=百分比:</span><input type="text" name="Q_voteType_SN"  class="inputText" />
											
												<span class="label">Act流程发布ID:</span><input type="text" name="Q_actDeployId_S"  class="inputText" />
											
									</div>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmNodeSignList" id="bpmNodeSignItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="signId" value="${bpmNodeSignItem.signId}">
							</display:column>
							
							<display:column property="nodeId" title="流程节点ID" sortable="true" sortName="nodeId"></display:column>
							<display:column property="voteAmount" title="票数" sortable="true" sortName="voteAmount"></display:column>
							<display:column property="decideType" title="决策方式" sortable="true" sortName="decideType"></display:column>
							<display:column property="voteType" title="1=百分比" sortable="true" sortName="voteType"></display:column>
							<display:column property="actDeployId" title="Act流程发布ID" sortable="true" sortName="actDeployId"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?signId=${bpmNodeSignItem.signId}" class="link del">删除</a>
								<a href="edit.xht?signId=${bpmNodeSignItem.signId}" class="link edit">编辑</a>
								<a href="get.xht?signId=${bpmNodeSignItem.signId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="bpmNodeSignItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


