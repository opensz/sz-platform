
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>消息设置管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">消息设置管理列表</span>
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
												<span class="label">主题:</span><input type="text" name="Q_subject_S"  class="inputText" />
											
												<span class="label">收件人:</span><input type="text" name="Q_receiver_S"  class="inputText" />
											
												<span class="label">抄送:</span><input type="text" name="Q_copyTo_S"  class="inputText" />
											
												<span class="label">秘密抄送:</span><input type="text" name="Q_bcc_S"  class="inputText" />
											
												<span class="label">发件人:</span><input type="text" name="Q_fromUser_S"  class="inputText" />
											
												<span class="label">内容模版:</span><input type="text" name="Q_templateId_S"  class="inputText" />
											
												<span class="label">消息类型:</span><input type="text" name="Q_messageType_S"  class="inputText" />
											
									</div>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="messageList" id="messageItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="messageId" value="${messageItem.messageId}">
							</display:column>
							<display:column property="subject" title="主题" sortable="true" sortName="subject"></display:column>
								<display:column property="receiver" title="收件人" sortable="true" sortName="receiver" maxLength="80"></display:column>
								<display:column property="copyTo" title="抄送" sortable="true" sortName="copyTo" maxLength="80"></display:column>
								<display:column property="bcc" title="秘密抄送" sortable="true" sortName="bcc" maxLength="80"></display:column>
							<display:column property="fromUser" title="发件人" sortable="true" sortName="fromUser"></display:column>
							<display:column property="templateId" title="内容模版" sortable="true" sortName="templateId"></display:column>
							<display:column property="messageType" title="消息类型" sortable="true" sortName="messageType"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?messageId=${messageItem.messageId}" class="link del">删除</a>
								<a href="edit.xht?messageId=${messageItem.messageId}" class="link edit">编辑<</a>
								<a href="get.xht?messageId=${messageItem.messageId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="messageItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


