<%@page pageEncoding="UTF-8" import="org.sz.platform.system.model.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>选择合同 </title>
	<%@include file="/commons/include/form.jsp" %>
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" 	src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
	<script type="text/javascript" 	src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerAccordion.js"></script>
	<script type="text/javascript" 	src="${ctx }/jslib/lg/plugins/ligerComboBox.js"></script>
	<f:link href="web.css"></f:link>
	<script type="text/javascript">
		var isSingle=${isSingle};
		var rol_Tree=null; 
		var org_Tree=null; 
		var pos_Tree=null; 
		var onl_Tree=null; 
		var accordion = null;
		var orgType = "1,2,3";
		var orgId = "${orgId}";
		var setting = {
		    		data: {
						key : {
							name: "orgName",
							title: "orgName"
						},
						simpleData: {
							enable: true,
							idKey: "orgId",
							pIdKey : "orgSupId",
							rootPId: -1
						}
					},
		    		callback: {
						onClick: function(event, treeId, treeNode){
							var url="${ctx}/itsm/contract/contractMgt/selector.xht";
							var orgId=treeNode.orgId;
							orgId=orgId==null?"":orgId;
							var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ORG%>&orgA="+orgId;
							$("#userListFrame").attr("src",url+p);
							setCenterTitle("按组织查找:"+treeNode.orgName);
						}
					}
		    };
		$(function(){
			//布局
			$("#defLayout").ligerLayout({
				 leftWidth: 200,
				 rightWidth: 140,
				 bottomHeight :40,
				 height: '90%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 onHeightChanged: heightChanged,
				 minLeftWidth:200,
				 allowLeftResize:false
			});
			
			var height = $(".l-layout-center").height();
			$("#SEARCH_BY_ORG").height(height-30);
			//$("#leftMemu").ligerAccordion({ height: height-28, speed: null });
		    accordion = $("#leftMemu").ligerGetAccordionManager();
		   
        		
		
			$("#dem").ligerComboBox({
				width:"130"
			}); 
		    $("#systemId").ligerComboBox({
				width:"130"
			}); 
		    
			//load_Rol_Tree();
		    load_Org_Tree();
		    //load_Pos_Tree();
		    //load_Onl_Tree();
		    
		    //heightChanged();
		    
		    $('#keyword').keydown(function(e){
					if(e.keyCode==13){
					   var value=$('#keyword').val();
					   //if(value!=""){
					    $.ajax({
							type : 'POST',
							url : "${ctx}/platform/system/sysOrg/getCustomerTreeData.xht",
							data :{'orgName':value},
							success : function(result) {
								var org_Tree = $.fn.zTree.init($("#SEARCH_BY_ORG"), setting,
										eval(result));
								org_Tree.expandAll(true);
							}
						});
					   //}
					}  
				});
		});
		function heightChanged(options){
			if(options){   
			    if (accordion && options.middleHeight - 28 > 0){
			    	$("#SEARCH_BY_ROL").height(options.middleHeight - 183);
				    $("#SEARCH_BY_ORG").height(options.middleHeight - 163);
				    $("#SEARCH_BY_POS").height(options.middleHeight - 140);
				    $("#SEARCH_BY_ONL").height(options.middleHeight - 120);
			        accordion.setHeight(options.middleHeight - 28);
			    }
			}else{
			    var height = $(".l-layout-center").height();
				$("#SEARCH_BY_ROL").height(height - 183);
			    $("#SEARCH_BY_ORG").height(height - 163);
			    $("#SEARCH_BY_POS").height(height - 140);
			    $("#SEARCH_BY_ONL").height(height - 120);
		    }
		}
		
		function setCenterTitle(title){
			
			$("#centerTitle").empty();
			$("#centerTitle").append(title);
			
		};
		
		function load_Pos_Tree(){
			
			var setting = {
		    		data: {
						key : {
							name: "posName",
							title: "posName"
						},
						simpleData: {
							enable: true,
							idKey: "posId",
							pIdKey : "parentId",
							rootPId: -1
						}
					},
		    		callback: {
						onClick: function(event, treeId, treeNode){
							var url="${ctx}/platform/bpm/contractMgt/selector.xht";
							var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ORG%>&orgA="+treeNode.orgId;
							$("#userListFrame").attr("src",url+p);
							setCenterTitle("按组织查找:"+treeNode.orgName);
						}
					}
		    };
			$.ajax({
				type : 'POST',
				url : "${ctx}/platform/system/position/getTreeData.xht",
				data:{demId : 1,type:orgType,orgId:orgId},
				success : function(result) {
					pos_Tree = $.fn.zTree.init($("#SEARCH_BY_POS"), setting,
							eval(result));
					pos_Tree.expandAll(true);
				}
			});
		};
		
		function load_Org_Tree(){
			var value=$("#dem").val();
			var setting = {
		    		data: {
						key : {
							name: "orgName",
							title: "orgName"
						},
						simpleData: {
							enable: true,
							idKey: "orgId",
							pIdKey : "orgSupId",
							rootPId: -1
						}
					},
		    		callback: {
						onClick: function(event, treeId, treeNode){
							var url="${ctx}/itsm/contract/contractMgt/selector.xht";
							var orgId=treeNode.orgId;
							orgId=orgId==null?"":orgId;
							var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ORG%>&orgA="+orgId;
							$("#userListFrame").attr("src",url+p);
							setCenterTitle("按组织查找:"+treeNode.orgName);
						}
					}
		    };
			$.ajax({
				type : 'POST',
				url : "${ctx}/platform/system/sysOrg/getCustomerTreeData.xht",
				//data:{demId :1,type:'6'},
				success : function(result) {
					org_Tree = $.fn.zTree.init($("#SEARCH_BY_ORG"), setting,
							eval(result));
					org_Tree.expandAll(true);
				}
			});
		};
		
		function load_Rol_Tree(){
			var systemId=$("#systemId").val();
			var roleName=$("#roleName").val();
			var setting = {
		    		data: {
						key : {
							name: "roleName",
							title: "roleName"
						},
						simpleData: {
							enable: true,
							idKey: "roleId",
							rootPId: -1
						}
					},
		    		callback: {
						onClick: function(event, treeId, treeNode){
						var url="${ctx}/platform/system/sysUser/selector.xht";
						var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ROL%>&roleId=" + treeNode.roleId;
							$("#userListFrame").attr("src", url + p);
							setCenterTitle("按角色查找:" + treeNode.roleName);
						}
					}
				};
				$.ajax({
					type : 'POST',
					url : "${ctx}/platform/system/sysRole/getAll.xht",
					data : {
						systemId : systemId,
						roleName : roleName
					},
					success : function(result) {
						rol_Tree = $.fn.zTree.init($("#SEARCH_BY_ROL"), setting,
								eval(result));
					}
				});
			};

			
			function load_Onl_Tree(){
				var value=$("#onl").val();
				var setting = {
			    		data: {
							key : {
								name: "orgName",
								title: "orgName"
							},
							simpleData: {
								enable: true,
								idKey: "orgId",
								pIdKey : "orgSupId",
								rootPId: -1
							}
						},
			    		callback: {
							onClick: function(event, treeId, treeNode){
								var url="${ctx}/platform/system/sysUser/selector.xht";
								var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ONL%>&path="+treeNode.path;
								$("#userListFrame").attr("src",url+p);
								setCenterTitle("按组织查找:"+treeNode.orgName);
							}
						}
			    };
				$.ajax({
					type : 'POST',
					url : "${ctx}/platform/system/sysOrg/getTreeOnlineData.xht",
					data : {
						demId : value
					},
					success : function(result) {
						org_Tree = $.fn.zTree.init($("#SEARCH_BY_ONL"), setting,
								result);
						org_Tree.expandAll(true);
					}
				});
			};
				
				
			function dellAll() {
				$("#sysUserList").empty();
			};
			function del(obj) {
				var tr = $(obj).parents("tr");
				$(tr).remove();
			};
			function add(ch) {
				//所选
				var userId = $(ch).val();
				var fullname = $(ch).siblings("input[name='fullname']").val();
				var email = $(ch).siblings("input[name='email']").val();
				var mobile = $(ch).siblings("input[name='mobile']").val();
				var retype = $(ch).siblings("input[name='retype']").val();
				
				var add = true;
				$("#sysUserList").find(":input[name='userId']").each(function() {
					if (userId == $(this).val()) {
						add = false;
					}
				});
				if(!add)  return;
				var tr = '';
				tr += '<tr>';
				tr += '<td>';
				tr += '	<input type="hidden" class="pk" name="userId" value="'+userId +'"> ';
				tr += '	<input type="hidden" class="pk" name="email" value="'+email+'"> ';
				tr += '	<input type="hidden" class="pk" name="mobile" value="'+mobile+'"> ';
				tr += '	<input type="hidden" class="pk" name="retype" value="'+retype+'"> ';
				tr += '	<input size="12" type="text" name="fullname"  value="'+fullname+'" style="border: none;" readonly="readonly"/>';
				tr += '</td>';
				tr += '<td><a onclick="javascript:del(this);" class="link del" ></a> </td>';
				tr += '</tr>';
		
				$("#sysUserList").append(tr);
				
			};
		
			function selectMulti(obj) {
				if ($(obj).attr("checked") == "checked")
					add(obj);
			};
		
			function selectAll(obj) {
		
				var state = $(obj).attr("checked");
				if (state == undefined)
					checkAll(false);
				else
					checkAll(true);
		
			};
		
			function checkAll(checked) {
		
				$("#userListFrame").contents().find(
						"input[type='checkbox'][class='pk']").each(function() {
					$(this).attr("checked", checked);
					if (checked) {
						add(this);
					}
				});
			};
			
			function selectContract(){
				var chIds;
				if(isSingle==true){
					chIds = $('#userListFrame').contents().find(":input[name='contractId'][checked]");
				
				}else{
					chIds = $("#userListFrame").contents().find(":input[name='contractId'][checked]");
				}
			
				var aryIds=new Array();
				var aryNames=new Array();
				var aryCode=new Array();
				var arySignedTime=new Array();
				var aryExpirationDate=new Array();
				$.each(chIds,function(i,ch){
					aryIds.push($(ch).val());
					aryNames.push($(ch).siblings("input[name='contractName']").val());
					aryCode.push($(ch).siblings("input[name='cIId']").val());
					arySignedTime.push($(ch).siblings("input[name='signedTime']").val());
					aryExpirationDate.push($(ch).siblings("input[name='expirationDate']").val());
				});
				
			
				var obj={ids:aryIds.join(","),names:aryNames.join(","),
						codes:aryCode.join(","),signedTimes:arySignedTime.join(","),expirationDates:aryExpirationDate.join(",")};
				window.returnValue=obj;
				window.close();
				
			}
	</script>

<style type="text/css">
.tree-title {
	overflow: hidden;
	width: 8000px;
}

.ztree {
	overflow: auto;
}

.label {
	color: #6F8DC6;
	text-align: right;
	padding-right: 6px;
	padding-left: 0px;
	font-weight: bold;
}
html { overflow-x: hidden; }


</style>
</head>
<body>
	<div id="defLayout">
		<div id="leftMemu" position="left" title="客户条件" style="overflow: auto; float: left;width: 100%;">
			<div title="按客户查找" style="overflow: hidden;">
				<!--<div class="tree-title" style="width: 100%;">
					<table border="0" width="100%" class="table-detail">
						<tr >
							<td width="100%" nowrap="nowrap"><span class="label">维度:</span>
							</td>
							<td style="width:100%;">
								<select id="dem" name="dem" onchange="load_Org_Tree()">
									<c:forEach var="demen" items="${demensionList}">
										<option  value="${demen.demId}" <c:if test="${demen.demId==1}">selected</c:if>>${ demen.demName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</div>
				-->
				<p>
			<div>关键字：<input type="text" id="keyword" name="keyword" style="color:#b8b8b8;"
						class="inputText" size="15" onfocus="if(this.value==this.defaultValue){this.value='';this.style.color='#333333';}" 
						onblur="if(this.value==''){this.value=this.defaultValue;this.style.color='#b8b8b8'; }" 
						value="根据名称回车查询"/></div>
				<div id="SEARCH_BY_ORG" class='ztree'></div>
			</div>

		</div>
		<div position="center">
			<div id="centerTitle" class="l-layout-header">全部合同</div>
			
			<div class="panel-search">
				<form action="selector.xht" id="orgSearchForm" method="POST" target="userListFrame">
					<div class="row">
						<span class="label">名称:</span> 

						<input type="text" id="contractName" name="contractName" value="${contractName}"
						class="inputText" size="40" /> &nbsp; 
						<a href="#" onclick="$('#orgSearchForm').submit()" class='button'><span>查询</span></a>
						
					</div>
				</form>
			</div>
			<iframe id="userListFrame" name="userListFrame" height="90%" width="100%" frameborder="0" src="${ctx}/itsm/contract/contractMgt/selector.xht?isSingle=${isSingle}&orgId=${orgId}&searchBy=<%=SysUser.SEARCH_BY_ORG%>"></iframe>
		</div>

		</div>
		<div position="bottom"  class="bottom" style='margin-top:10px'>
			<a href='#' class='button'  onclick="selectContract()" ><span class="icon ok"></span><span >选择</span></a>
			<a href='#' class='button' style='margin-left:10px;'  onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
	</div>
</body>
</html>


