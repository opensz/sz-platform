
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看版本</title>
<%@include file="/commons/include/get.jsp" %>

<style type="text/css">
	.panel-data {
		margin-top: 5px ;
		margin-bottom: 5px ;
	}
	
	.floatBtn {
		font-weight: bold;
		color: red !important;
	}
	
</style>
<script type="text/javascript">
	$(function(){
		$("a.link.del").unbind("click");
		handPublish();
		delFormDef();
	});
	
	function handPublish(){
		$("#btnPublish").click(function(){
			var ele=this;
			$.ligerMessageBox.confirm('提示信息','确认发布吗？',function(rtn) {
				if(rtn){
					location.href=ele.href;
				}
			});
			return false;
		});
	}
	
	function delFormDef(){
		
		$("a.link.del").click(function(){
			if($(this).hasClass('disabled')) return false;
			var ele = this;
			$.ligerMessageBox.confirm('提示信息','确定删除该自定义表单吗？',function(rtn) {
				if(rtn) {
					if(ele.click) {
						$(ele).unbind('click');
						ele.click();
					} else {
						location.href=ele.href;
					}
				}
			});
			return false;
		});
	}
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-body">
			<div class="panel-data">
				<table class="table-grid">
					 <c:forEach items="${versions}" var="version">
					 	<tr class="even">
					 		<td class="row">
						 		<span class="label">
							 		版本:${version.versionNo }<c:if test="${version.isDefault == 1}"><font color="red">[默认]</font></c:if>&nbsp;
							 		发布时间:<fmt:formatDate value="${version.publishTime }" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
							 		发布人:${version.publishedBy }
							 	</span>
							 	<c:if test="${version.isDefault != 1 && version.isPublished==1 }">
								 	<a href="setDefaultVersion.xht?formDefId=${version.formDefId }&formKey=${version.formKey}" class="floatBtn" >
								 		[设为默认]
								 	</a>
							 	</c:if>
							 	<c:if test="${version.isPublished==0 }">
								 	<a  id="btnPublish" href="publish.xht?formDefId=${version.formDefId }" class="floatBtn" >
								 		[发布]
								 	</a>
							 	</c:if>
							</td>
						</tr>
						<tr>
							<td>
								
							 	<table class="table-grid">
									<tr>
										<td width="40%">${version.subject}</td>
				
										<td width="20%">
											<a href="get.xht?formDefId=${version.formDefId}"  class="link detail">查看</a>
											
											<c:if test="${version.isPublished==0 }">
												<a href="#" onclick="javascript:jQuery.openFullWindow('edit.xht?formDefId=${version.formDefId}');" class="link edit">编辑</a>
							 				</c:if>
											
											<a target="_blank" href="${ctx}/platform/form/bpmFormHandler/edit.xht?formDefId=${version.formDefId}" class="link preview">预览</a>
										</td>
									</tr>
								</table>
								
							</td>
						</tr>
				     </c:forEach>
				</table>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


