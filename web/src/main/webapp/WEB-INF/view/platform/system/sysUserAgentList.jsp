<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>代理授权管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">代理授权管理列表</span>
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
												<span class="label">代理人:</span><input type="text" name="Q_tofullname_S"  class="inputText" style="width:9%"/>
											
												<span class="label">开始时间 :</span> <input  name="Q_beginstarttime_DL"  class="inputText date" style="width:9%"/>
			
												<span class="label">结束时间 :</span><input  name="Q_endendtime_DG" class="inputText date" style="width:9%"/>
											
												<span class="label">是否全权代理:</span>
												<select name="Q_isall_SN"  style="margin-left:9px;width:100px;">
														<option value="">--全部--</option>
														<option value="1">是</option>
														<option value="0">否</option>
												</select>
												<span class="label">是否有效:</span>
												<select name="Q_isvalid_SN"  style="margin-left:9px;width:100px;">
														<option value="">--全部--</option>
														<option value="1">是</option>
														<option value="0">否</option>
												</select>
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysUserAgentList" id="sysUserAgentItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="agentid" value="${sysUserAgentItem.agentid}">
							</display:column>
							<display:column property="tofullname" title="代理给" sortable="true" sortName="tofullname"></display:column>
							<display:column  title="开始时间" sortable="true" sortName="starttime">
								<fmt:formatDate value="${sysUserAgentItem.starttime}" pattern="yyyy-MM-dd"/>
							</display:column>
							<display:column  title="结束时间" sortable="true" sortName="endtime">
								<fmt:formatDate value="${sysUserAgentItem.endtime}" pattern="yyyy-MM-dd"/>
							</display:column>
							<display:column title="是否全权代理" sortable="true" sortName="isall">
							<c:if test="${sysUserAgentItem.isall==1 }">是</c:if>
							<c:if test="${sysUserAgentItem.isall==0 }">否</c:if>
							</display:column>
							<display:column title="是否有效" sortable="true" sortName="isvalid">
							<c:if test="${sysUserAgentItem.isvalid==1 }">是</c:if>
							<c:if test="${sysUserAgentItem.isvalid==0 }">否</c:if>
							</display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?agentid=${sysUserAgentItem.agentid}" class="link del">删除</a>
								<a href="edit.xht?agentid=${sysUserAgentItem.agentid}" class="link edit">编辑</a>
								<a href="get.xht?agentid=${sysUserAgentItem.agentid}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysUserAgentItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


