
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程节点邮件管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">流程节点邮件管理列表</span>
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
												<span class="label">节点设置ID:</span><input type="text" name="Q_setId_S"  class="inputText" />
											
												<span class="label">主题:</span><input type="text" name="Q_subject_S"  class="inputText" />
											
												<span class="label">收件人:</span><input type="text" name="Q_receiver_S"  class="inputText" />
											
												<span class="label">抄送:</span><input type="text" name="Q_copyTo_S"  class="inputText" />
											
												<span class="label">流程定义ID:</span><input type="text" name="Q_actDefId_S"  class="inputText" />
											
												<span class="label">流程节点ID:</span><input type="text" name="Q_nodeId_S"  class="inputText" />
											
												<span class="label">内容模版:</span><input type="text" name="Q_templateId_S"  class="inputText" />
											
												<span class="label">秘密抄送:</span><input type="text" name="Q_bcc_S"  class="inputText" />
											
												<span class="label">发件人:</span><input type="text" name="Q_fromUser_S"  class="inputText" />
											
												<span class="label">messageType:</span><input type="text" name="Q_messageType_S"  class="inputText" />
											
									</div>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmNodeMessageList" id="bpmNodeMessageItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${bpmNodeMessageItem.id}">
							</display:column>
							<display:column property="subject" title="主题" sortable="true" sortName="subject"></display:column>
								<display:column property="receiver" title="收件人" sortable="true" sortName="receiver" maxLength="80"></display:column>
								<display:column property="copyTo" title="抄送" sortable="true" sortName="copyTo" maxLength="80"></display:column>
							<display:column property="actDefId" title="流程定义ID" sortable="true" sortName="actDefId"></display:column>
							<display:column property="nodeId" title="流程节点ID" sortable="true" sortName="nodeId"></display:column>
							<display:column property="templateId" title="内容模版" sortable="true" sortName="templateId"></display:column>
								<display:column property="bcc" title="秘密抄送" sortable="true" sortName="bcc" maxLength="80"></display:column>
							<display:column property="fromUser" title="发件人" sortable="true" sortName="fromUser"></display:column>
							<display:column property="messageType" title="messageType" sortable="true" sortName="messageType"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?id=${bpmNodeMessageItem.id}" class="link del">删除</a>
								<a href="edit.xht?id=${bpmNodeMessageItem.id}" class="link edit">编辑</a>
								<a href="get.xht?id=${bpmNodeMessageItem.id}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="bpmNodeMessageItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


