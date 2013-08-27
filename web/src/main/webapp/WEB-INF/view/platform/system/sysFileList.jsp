<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>附件管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">附件管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
												<span class="label">文件名:</span><input type="text" name="Q_fileName_S"  class="inputText" style="width:13%">
												<span class="label">附件类型:</span>
												<select name="Q_fileType_S" style="margin-left:9px;width:100px;">
												<option value="">--请选择--</option>
												<c:forEach items="${globalTypeList }" var="globalType">
												<option value="${globalType.nodeKey }">
												${globalType.nodeKey }
												</option>
												</c:forEach>
												<option value="others">其它分类</option>
												</select>
												<span class="label">上传者:</span><input type="text" name="Q_creator_S"  class="inputText" style="width:13%"/>
												<span class="label">扩展名:</span><input type="text" name="Q_ext_S"  class="inputText"  style="width:13%"/>
									</div>
									<div class="row">
											<span class="label">创建时间 从:</span> <input type="text"  name="Q_begincreatetime_DL"  class="inputText date" />
												<span class="label">至: </span><input type="text" name="Q_endcreatetime_DG" class="inputText date" />
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysFileList" id="sysFileItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="fileId" value="${sysFileItem.fileId}">
							</display:column>
							<display:column property="fileName" title="文件名" sortable="true" sortName="fileName"></display:column>
							<display:column title="创建时间" sortable="true" sortName="createtime">
								<fmt:formatDate value="${sysFileItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</display:column>
							<display:column property="ext" title="扩展名" sortName="ext" sortable="true"></display:column>
							<display:column property="fileType" title="附件类型" ></display:column>
						    <display:column  title="类型名称" >
						    <c:choose>
									<c:when test="${sysFileItem.typeName eq null}">null</c:when>
									<c:otherwise>${sysFileItem.typeName }</c:otherwise>
								</c:choose>
						    </display:column>
						    <display:column property="note" title="说明" sortable="true" sortName="note" maxLength="80"></display:column>
							<display:column property="creator" title="上传者"  ></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?fileId=${sysFileItem.fileId}" class="link del">删除</a>
								<a href="get.xht?fileId=${sysFileItem.fileId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysFileItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


