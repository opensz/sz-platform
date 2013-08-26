<%--
	time:2011-11-28 10:17:09
	desc:edit the 用户表
--%>
<%@page language="java" pageEncoding="UTF-8" import="org.sz.platform.system.model.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 用户表</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysUser"></script>
	<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script> 
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTab.js" ></script>
	<script type="text/javascript" src="${ctx}/js/sz/displaytag.js" ></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerWindow.js" ></script>
   <script type="text/javascript"  src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
   <script type="text/javascript" src="${ctx}/js/sz/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript">
	var orgTree;    //组织树
	var posTree;    //岗位树
	var rolTree;    //角色树
	var deptTree;   //部门树
	var orgId="${orgId}";
	var h;
    var rowid;      //行id
    $(function ()
    { 
    	h=$('body').height();
    	$("#tabMyInfo").ligerTab({         	
            	//height:h-80
          });
    	function showRequest(formData, jqForm, options)
    	{ 
			return true;
		} 					
		valid(showRequest,showResponse);
		$("a.save").click(function() {
		
			 var orgIds = $("input[name='orgId']");
			 var len=orgIds.length;
			 if(len==0){
				 $.ligerMessageBox.warn("请至少选择一个组织 !");
				 return;
			 }
			 else{			
				 $('#sysUserForm').submit(); 
			 }
			
		});
		$("#orgAdd").click(function(){
			btnAddRow('orgTree');
		});
		$("#orgDel").click(function(){
			btnDelRow();
		});
		
		$("#deptAdd").click(function(){
			btnAddRow('deptTree');
		});
		$("#deptDel").click(function(){
			btnDelRow();
		});
		
		$("#posAdd").click(function(){
			btnAddRow('posTree');
		});
		$("#posDel").click(function(){
			btnDelRow();
		});
		$("#rolAdd").click(function(){
			btnAddRow('rolTree');
		});
		$("#rolDel").click(function(){
			btnDelRow();
		});
    	loadorgTree();
    	loadDeptTree();
    	loadposTree();
    	loadrolTree();
    });
    
	function dlgCallBack(userIds,fullnames)
	{
		$("#"+rowid).find(":hidden[name='ownUser']").val(userIds);
		$("#"+rowid).find(":text[ownName='ownUserName']").val(fullnames);	
	
	};
	function addClick(id){
		rowid=id;
		UserDialog({callback:dlgCallBack,isSingle:false});
	};
    
	//清空
	function reSet(rowid)
	{
		$("#"+rowid).find(":hidden[name='ownUser']").val("");
		$("#"+rowid).find(":text[ownName='ownUserName']").val("");
	}
	
	
	//添加个人照片
	function picCallBack(fileIds,fileNames,filePaths)
	{
		var arrPath;
		if(filePaths!="")
		{
			arrPath=filePaths.split(",");
			var url="${ctx}/"+arrPath[0];
			$("#picture").val(arrPath[0]);
			$("#personPic").attr("src",url);			
		}		
	};
	function addPic(){
		FlexUploadDialog({isSingle:true,callback:picCallBack});
	};
	
	//生成组织树      		
   function loadorgTree()
   {
		var setting = {       				    					
			data: {
				key : {
					
					name: "orgName",
					title: "orgName"
				},
			
				simpleData: {
					enable: true,
					idKey: "orgId",
					pIdKey: "orgSupId",
					rootPId: -1
				}
			},
			view: {
				selectedMulti: true
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }
			},
			callback:{onDblClick: orgTreeOnDblClick}
		 	
		   };
		   var url="${ctx}/platform/system/sysOrg/getTreeData.xht?demId=1&type=serviceCenter";
			//一次性加载
			$.post(url,function(result){
					orgTree=$.fn.zTree.init($("#orgTree"), setting,result);
					orgTree.expandAll(true);
				}
			);		
	};	
	
	//生成部门树      		
   function loadDeptTree()
   {
		var setting = {       				    					
			data: {
				key : {
					
					name: "orgName",
					title: "orgName"
				},
			
				simpleData: {
					enable: true,
					idKey: "orgId",
					pIdKey: "orgSupId",
					rootPId: -1
				}
			},
			view: {
				selectedMulti: true
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }
			},
			callback:{onDblClick: deptTreeOnDblClick}
		 	
		   };
		   var url="${ctx}/platform/system/sysOrg/getTreeData.xht?demId=1&type=dept&orgId="+orgId;
			//一次性加载
			$.post(url,function(result){
					deptTree=$.fn.zTree.init($("#deptTree"), setting,result);
					deptTree.expandAll(true);
				}
			);		
	};	
    	
	 //生成岗位树      		
	  function loadposTree()
	  {
		var setting = {       				    					
			data: {
				key : {
					
					name: "posName",
					title: "posName"
				},
			
				simpleData: {
					enable: true,
					idKey: "posId",
					pIdKey: "parentId",
					rootPId: -1
				}
			},
			view: {
				selectedMulti: true
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }
			},
			callback:{onDblClick: posTreeOnDblClick}
		 	
		   };
			   
			//一次性加载
			$.ajax({
				type: 'POST',
				url:"${ctx}/platform/system/position/getTreeData.xht",
				autoParam:["posId","parentId"],
				success: function(result){
					posTree=$.fn.zTree.init($("#posTree"), setting,eval(result));
					posTree.expandAll(true);
				}
			});		
		
	};	
    
	
	 //生成角色树      		
	  function loadrolTree()
	  {
		var setting = {       				    					
			data: {
				key : {
					
					name: "roleName",
					title: "roleName"
				},
			
				simpleData: {
					enable: true,
					idKey: "roleId",
					pIdKey: "systemId",
					rootPId: -1
				}
			},
			view: {
				selectedMulti: true
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }
			},
			callback:{onDblClick: rolTreeOnDblClick}
		 	
		   };
			   
			//一次性加载
			$.ajax({
				type: 'POST',
				url:"${ctx}/platform/system/sysRole/getTreeData.xht",
				autoParam:["roleId","systemId"],
				success: function(result){
					rolTree=$.fn.zTree.init($("#rolTree"), setting,eval(result));
					rolTree.expandAll(true);
				}
			});		
		
	};	
	
	function SearchIdInTable(tablename,tablerow, findId){  
	    var tablerownum=tablerow.length;  
	    for (var i=1; i<tablerownum; i++) {
    	    if ($("#"+tablename+" tr:eq(" + i + ")").attr("id") == findId)  {
    	    	return true
            }
	    }
	    return false;  
	 } 
	
	 function btnDelRow() {
		 var $aryId = $("input[type='checkbox'][class='pk']:checked");
		 var len=$aryId.length;
		 if(len==0){
			 $.ligerMessageBox.warn("你还没选择任何记录!");
			 return;
		 }
		 else{			
			 $aryId.each(function(i){
					var obj=$(this);
					delrow(obj.val());
			 });
		 }
	 }
	 
	 function delrow(id)//删除行,用于删除暂时选择的行
	 {
		 $("#"+id).remove();
	 }

	
	
	
	//树按添加按钮
	function btnAddRow(treeName)
    {
		var treeObj = $.fn.zTree.getZTreeObj(treeName);
        var nodes = treeObj.getCheckedNodes(true);
        if(nodes==null||nodes=="")
        {
        	 $.ligerMessageBox.warn("你还没选择任何节点!");
			 return;
        }
		if(treeName.indexOf("org")!=-1) {
			var orgId="";
			var orgSupId="";
			var orgName="";	
			var userName="";
	        $.each(nodes,function(i,n){	
	        	orgId=n.orgId;
	        	orgSupId=n.orgSupId;
	        	orgName=n.orgName;
	        	//if(n.isRoot==0){
	        		orgAddHtml(orgId,orgSupId,orgName);
	        	//}
	        	
			});
	    }
	    else if(treeName.indexOf("dept")!=-1) {
			var orgId="";
			var orgSupId="";
			var orgName="";	
			var userName="";
	        $.each(nodes,function(i,n){	
	        	orgId=n.orgId;
	        	orgSupId=n.orgSupId;
	        	orgName=n.orgName;
	        	//if(n.isRoot==0){
	        		deptAddHtml(orgId,orgSupId,orgName);
	        	//}
	        	
			});
	    }
	    else if(treeName.indexOf("pos")!=-1){
	    	 var posId="";
			 var posName="";
			 var posDesc="";
			 var parentId="";
		     $.each(nodes,function(i,n){
		    	  posId=n.posId;
				  posName=n.posName;
				  parentId=n.parentId;
			 	  posAddHtml(posId,posName,parentId);
		     });
	    }
	    else if(treeName.indexOf("rol")!=-1){
	    	 $.each(nodes,function(i,n){
				  var roleId=n.roleId;
				  if(roleId>0){
					  roleId=n.roleId;
					  roleName=n.roleName;
					  sysName=n.subSystem.sysName;
					  systemId=n.systemId;
					  rolAddHtml(roleId,roleName,systemId,sysName);
				  }
	    	 });
	    }
    }
	 //组织树左键双击
	 function orgTreeOnDblClick(event, treeId, treeNode)
	 {   
		 var orgId=treeNode.orgId;
		 var orgSupId=treeNode.orgSupId;
		 var orgName=treeNode.orgName;
		 if(treeNode.isRoot==0){
			 orgAddHtml(orgId,orgSupId,orgName);
		 }
	 };
	 
	 
	 function orgAddHtml(orgId,orgSupId,orgName){
		
		// if(orgSupId==-1) return;
		 var obj=$("#" +orgId); 
         if(obj.length>0) return;
         
		 var tr="<tr  id='"+orgId+"' style='cursor:pointer'>";
		 tr+="<td style='text-align: center;'>";
		 tr+=""+orgName+"<input  type='hidden' name='orgId' value='"+orgId+"'><input  type='hidden' name='isDept' value='0'>";
		 tr+="</td>";		
		 
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='radio' name='orgIdPrimary' value='"+orgId+"' onclick='setChecked(this.value,this.checked);'/>";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='checkbox' name='chargeOrgId' value='"+orgId+"' />";
		 tr+="</td>";
	
		 
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='#' onclick='delrow(\""+orgId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 
		 tr+="</tr>";
	     
		 $("#orgItem").append(tr);
		 
	 }
	 
	 //部门树左键双击
	 function deptTreeOnDblClick(event, treeId, treeNode)
	 {   
		 var orgId=treeNode.orgId;
		 var orgSupId=treeNode.orgSupId;
		 var orgName=treeNode.orgName;
		 //if(treeNode.isRoot==0){
			 deptAddHtml(orgId,orgSupId,orgName);
		 //}
	 };
	 
	 
	 function deptAddHtml(orgId,orgSupId,orgName){
		
		// if(orgSupId==-1) return;
		 var obj=$("#" +orgId+"_dept"); 
		 
         if(obj.length>0) return;
        
		 var tr="<tr  id='"+orgId+"_dept' style='cursor:pointer'>";
		 tr+="<td style='text-align: center;'>";
		 tr+=""+orgName+"<input  type='hidden' name='orgId' value='"+orgId+"'><input  type='hidden' name='isDept' value='1'>";
		 tr+="</td>";		
		 
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='radio' name='deptIdPrimary' value='"+orgId+"' />";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='checkbox' name='chargeOrgId' value='"+orgId+"' />";
		 tr+="</td>";
	
		 
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='#' onclick='delrow(\""+orgId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 
		 tr+="</tr>";
	    
		 $("#deptItem").append(tr);
		 
	 }
	 
	//岗位树左键双击
	 function posTreeOnDblClick(event, treeId, treeNode)
	 {   
		 var posId=treeNode.posId;
		 var posName=treeNode.posName;
		 var posDesc=treeNode.posDesc;
		 var parentId=treeNode.parentId;
		 posAddHtml(posId,posName,parentId);
		 
	 };
	 
	 function posAddHtml(posId,posName,parentId){
		 if(parentId==-1) return;
		 var obj=$("#" +posId);
		 if(obj.length>0) return;
		 var tr="<tr  id='"+posId+"' style='cursor:pointer'>";
		 tr+="<td style='text-align: center;'>";
		 tr+=posName +"<input type='hidden' name='posId' value='"+posId+"'>";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='radio' name='posIdPrimary' value='"+posId+"' />";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='#' onclick='delrow(\""+posId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 tr+="</tr>";
	    
		 $("#posItem").append(tr);
		 
	 }
	//角色树左键双击
	 function rolTreeOnDblClick(event, treeId, treeNode){   
		 var roleId=treeNode.roleId;
		 var roleName=treeNode.roleName;
		 var sysName=treeNode.subSystem.sysName;
		 var systemId=treeNode.systemId;
		 rolAddHtml(roleId,roleName,systemId,sysName);
	 };
	 
	 function rolAddHtml(roleId,roleName,systemId,sysName){
		 if( systemId==0) return;
		 var obj=$("#" +roleId);
		 if(obj.length>0) return;
		
		 var tr="<tr id='"+roleId+"' style='cursor:pointer'>";
		 tr+="<td style='text-align: center;'>";
		 tr+=""+roleName+"<input type='hidden' name='roleId' value='"+ roleId +"'>";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+=""+sysName;
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='#' onclick='delrow(\""+roleId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 tr+="</tr>";
	   
		 $("#rolItem").append(tr);
		
	 }	 
	
	 function setChecked(value,checked){
	 	orgId=value;
	 	//alert(value);
	 	loadDeptTree();
	 }
	</script>
<style type="text/css">
	html{height:100%}

    body {overflow:auto;}

</style>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				<c:if test="${sysUser.userId==null }">添加用户信息</c:if>
				<c:if test="${sysUser.userId!=null }">编辑【${sysUser.fullname}】用户信息</c:if>  
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="${returnUrl}">返回</a></div>
				</div>
			</div>
		</div>
	   <form id="sysUserForm" method="post" action="save.xht">
						
            <div  id="tabMyInfo" style="overflow:hidden; position:relative;">  
	           
	           <div title="基本信息" tabid="userdetail" icon="${ctx}/themes/img/icon/user.gif">
	           <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td rowspan="<c:if test="${not empty sysUser.userId}">10</c:if><c:if test="${empty sysUser.userId}">11</c:if>" align="center" width="26%">
							<div style="float:top !important;background: none;height: 24px;padding:0px 6px 0px 112px;"><a class="link uploadPhoto" href="#" onclick="addPic();">上传照片</a></div>
							<div style="float:bottom;"><img id="personPic" src="${ctx}/${pictureLoad}"  alt="个人相片" style="width:230px;height:378px;" /></div>
							</td>
							<th width="18%">帐   号: <span class="required">*</span></th>
							<td ><c:if test="${bySelf==1}"><input type="hidden" name="bySelf" value="1"></c:if><input type="text" <c:if test="${bySelf==1}">disabled="disabled"</c:if> id="account" name="account" value="${sysUser.account}" style="width:240px !important" class="inputText"/></td>
						</tr>						
																		
						<tr style="<c:if test="${not empty sysUser.userId}">display:none</c:if>">
							<th>密   码: <span class="required">*</span></th>
							<td><input type="password" id="password" name="password" value="${sysUser.password}" style="width:240px !important" class="inputText"/></td>
						</tr>
						
						<tr>
						    <th>用户姓名: </th>
							<td ><input type="text" id="fullname" name="fullname" value="${sysUser.fullname}" style="width:240px !important" class="inputText"/></td>
						</tr>
						
						<tr>
						    <th>工号: </th>
							<td ><input type="text" id="empId" name="empId" value="${sysUser.empId}" style="width:240px !important" class="inputText"/></td>
						</tr>
						
						<tr>
							<th>用户性别: </th>
							<td>
							<select name="sex" class="select" style="width:245px !important">
									<option value="1" <c:if test="${sysUser.sex==1}">selected</c:if> >男</option>
									<option value="0" <c:if test="${sysUser.sex==0}">selected</c:if> >女</option>
							</select>						
							</td>
						</tr>						
						<tr>
							<th>是否锁定: </th>
							<td >								
								<select name="isLock" class="select" style="width:245px !important" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
									<option value="<%=SysUser.UN_LOCKED %>" <c:if test="${sysUser.isLock==0}">selected</c:if> >未锁定</option>
									<option value="<%=SysUser.LOCKED %>" <c:if test="${sysUser.isLock==1}">selected</c:if> >已锁定</option>
								</select>	
							</td>				  
						</tr>
						
						<tr>
						    <th>是否过期: </th>
							<td >
								<select name="isExpired" class="select" style="width:245px !important" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
									<option value="<%=SysUser.UN_EXPIRED %>" <c:if test="${sysUser.isExpired==0}">selected</c:if> >未过期</option>
									<option value="<%=SysUser.EXPIRED %>" <c:if test="${sysUser.isExpired==1}">selected</c:if> >已过期</option>
								</select>
							</td>
						</tr>
						
						<tr>
						   <th>当前状态: </th>
							<td>
								<select name="status"  class="select" style="width:245px !important" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
									<option value="<%=SysUser.STATUS_OK %>" <c:if test="${sysUser.status==1}">selected</c:if> >激活</option>
									<option value="<%=SysUser.STATUS_NO %>" <c:if test="${sysUser.status==0}">selected</c:if> >禁用</option>
									<option value="<%=SysUser.STATUS_Del %>" <c:if test="${sysUser.status==-1}">selected</c:if>>删除</option>
								</select>
							</td>								
						</tr>						
						<tr>
						   <th>邮箱地址: </th>
						   <td ><input type="text" id="email" name="email" value="${sysUser.email}" style="width:240px !important" class="inputText"/></td>
						</tr>
						
						<tr>
							<th>手   机: </th>
							<td ><input type="text" id="mobile" name="mobile" value="${sysUser.mobile}" style="width:240px !important" class="inputText"/></td>						   
						</tr>
						
						<tr>
						    <th>电   话: </th>
							<td ><input type="text" id="phone" name="phone" value="${sysUser.phone}"  style="width:240px !important" class="inputText"/></td>
						
						    
						</tr>
						<!--
						<th>昵   称: </th>
							<td ><input type="text" id="nickName" name="nickName" value="${hwsmUserParam.nickName}"  style="width:240px !important" class="inputText"/></td>
						<tr>
						    <th>是否VIP: </th>
							<td>
							<select name="isVip" class="select" style="width:245px !important">
									<option value="1" <c:if test="${hwsmUserParam.isVip==1}">selected</c:if> >是</option>
									<option value="0" <c:if test="${hwsmUserParam.isVip==0}">selected</c:if> >否</option>
							</select>	
										
							</td>
						    <th>职   务: </th>
							<td ><input type="text" id="title" name="title" value="${hwsmUserParam.title}"  style="width:240px !important" class="inputText"/></td>
						</tr>	
						<tr>
						    <th>服务级别: </th>
							<td>
							<select name="levelId" class="select" style="width:245px !important">
							        <option value="">--请选择--</option>
									<c:forEach items="${staffLevelList}" var="model">
										<option value="${model[0]}" <c:if test="${hwsmUserParam.levelId==model[0]}">selected</c:if> >${model[1]}</option>
									</c:forEach>
							</select>	
										
							</td>
						    <th>办公地点: </th>
							<td>
								<select name="siteId" class="select" style="width:245px !important">
								     <option value="">--请选择--</option>
									<c:forEach items="${siteList}" var="model">
										<option value="${model[0]}" <c:if test="${hwsmUserParam.siteId==model[0]}">selected</c:if>>${model[1]}</option>
									</c:forEach>
							    </select>	
							</td>
						</tr>	
						<input type="hidden" name="userParamId" value="${hwsmUserParam.userParamId}" />	
											
					--></table>
					
					<input type="hidden" name="userId" value="${sysUser.userId}" />
					<input type="hidden" id="picture" name="picture" value="${sysUser.picture}" />
					
				       	        
	           </div>
	           <c:if test="${bySelf!=1}">
	           <div title="组织选择" tabid="orgdetail" icon="${ctx}/themes/img/icon/home.png">		         	         
		          <table align="center"  cellpadding="0" cellspacing="0" class="table-grid table-list">
				    <tr>
			        <td width="28%" valign="top"  style="padding-left:2px !important;">
					<div class="tbar-title">
							<span class="tbar-label">所有组织</span> 
			        </div>
					<div class="panel-body" style="height:520px;overflow-y:auto;border:1px solid #6F8DC6;">	  		  
			            <ul id="orgTree" class="ztree" style="width:200px;margin:-2; padding:-2;" >         
			            </ul>    
					</div>
					</td>
					<td width="3%" valign="middle"  style="padding-left:2px !important;">
					<input type="button" id="orgAdd" value="添加>>" />
					<br/>
					<br/>
					<br/>
					</td>
				    <td valign="top" style="padding-left:2px !important;">
		            <div class="tbar-title">
							<span class="tbar-label">已选组织</span>
			         </div>
					<div style="overflow-y:auto;border:1px solid #6F8DC6;">
					      <table id="orgItem" class="table-grid table-list"  cellpadding="1" cellspacing="1">
					   		<thead>
					   			
					   			<th style="text-align:center !important;">组织名称</th>
					    		<th style="text-align:center !important;">是否主组织</th>
					    		<th style="text-align:center !important;">主要负责人</th>
					    		
					    		<th style="text-align:center !important;">操作</th>
					    	</thead>
					    	<c:forEach items="${orgList}" var="orgItem">
					    		<tr trName="${orgItem.orgName}"  id="${orgItem.orgId}" style='cursor:pointer'>
						    		
						    		<td style="text-align: center;">
					    				${orgItem.orgName}
					    				<input type="hidden" name="orgId" value="${orgItem.orgId}">	
					    				<input type="hidden" name="isDept" value="0">			    				
					    			</td>
					    			<td style="text-align: center;">					    			
					    			 <input type="radio" name="orgIdPrimary" value="${orgItem.orgId}"  <c:if test='${orgItem.isPrimary==1}'>checked</c:if> onclick="setChecked(this.value,this.checked);"/>
					    			</td>
					    			<td style="text-align: center;">					    			
					    			 	<input type="checkbox" name="chargeOrgId" value="${orgItem.orgId}" <c:if test='${orgItem.isCharge==1}'>checked</c:if>> 
					    			</td>
					    			
					    			<td style="text-align: center;">
					    				<c:if test="${sessionScope.sysUser.userOrgId!=orgItem.orgId}">
					    					<a href="#" onclick="delrow('${orgItem.orgId}')" class="link del">移除</a>
					    				</c:if>
					    			 	<c:if test="${sessionScope.sysUser.userOrgId==orgItem.orgId}">
					    					<a href="#">移除</a>
					    				</c:if>
					    					
					    				
					    			</td>
					    		</tr>
					    	</c:forEach>
					   	 </table>
					</div>
		            </td>
		            </tr>
				 </table>
	        </div>
	        <div title="岗位选择" tabid="posdetail" icon="${ctx}/themes/img/icon/nav-sales.png">
		         <table align="center"  cellpadding="0" cellspacing="0" class="table-grid">
				   <tr>
			       <td width="28%" valign="top" style="padding-left:2px !important;">
			        <div class="tbar-title">
							<span class="tbar-label">所有岗位</span>
					</div>				         
					<div class="panel-body" style="height:520px;overflow-y:auto;border:1px solid #6F8DC6;">	 
						<div id="posTree" class="ztree" style="width:200px;margin:-2; padding:-2;" >         
			            </div>
					</div>
					</td>
					<td width="3%" valign="middle"  style="padding-left:2px !important;">
					<input type="button" id="posAdd" value="添加&gt;&gt;" />
					<br/>
					<br/>
					<br/>
					</td>
				    <td valign="top" style="padding-left:2px !important;">
				   <div class="tbar-title">
						<span class="tbar-label">已选岗位</span>
					</div>	
					<div style="overflow-y:auto;border:1px solid #6F8DC6;">
					<table id="posItem" class="table-grid"  cellpadding="1" cellspacing="1">
					   		<thead>					   			
					   			<th style="text-align:center !important;">岗位名称</th>
					   			<th style="text-align:center !important;">是否主岗位</th>
					    		<th style="text-align:center !important;">操作</th>
					    	</thead>
					    	<c:forEach items="${posList}" var="posItem">
					    		<tr  id="${posItem.posId}" style='cursor:pointer'>						    		
						    		<td style="text-align: center;">
					    				${posItem.posName}<input type="hidden" name="posId" value="${posItem.posId}">
					    			</td>
					    			<td style="text-align: center;">					    			
					    			 <input type="radio" name="posIdPrimary" value="${posItem.posId}" <c:if test='${posItem.isPrimary==1}'>checked</c:if> />
					    			</td>
					    			
					    			<td style="text-align: center;">
					    			 <a href="#" onclick="delrow('${posItem.posId}')" class="link del">移除</a>
					    			</td>
					    		</tr>
					    	</c:forEach>
					   	 </table>
					</div>
		            </td>
		            </tr>
				 </table>
	         </div>
	         <div  title="角色选择" tabid="roldetail" icon="${ctx}/themes/img/icon/customer.png">
		        <table align="center"  cellpadding="0" cellspacing="0" class="table-grid">
				   <tr>
			       <td width="28%" valign="top" style="padding-left:2px !important;">
			        <div class="tbar-title">
						 <span class="tbar-label">所有角色</span>
					</div>	
					<div class="panel-body" style="height:520px;overflow-y:auto;border:1px solid #6F8DC6;">	
			    	    <div id="rolTree" class="ztree" style="width:200px;margin:-2; padding:-2;" >         
			            </div>
			        </div>
					</td>
					<td width="3%" valign="middle"  style="padding-left:2px !important;">
					<input type="button" id="rolAdd" value="添加>>" />
					<br/>
					<br/>
					<br/>
					</td>
				    <td valign="top" style="padding-left:2px !important;">
				   <div class="tbar-title">
						 <span class="tbar-label">已选角色</span>
					</div>	
					<div style="overflow-y:auto;border:1px solid #6F8DC6;">
					  <table id="rolItem" class="table-grid"  cellpadding="1" cellspacing="1">
					   		<thead>					   			
					   			<th style="text-align:center !important;">角色名称</th>
					    		<th style="text-align:center !important;">子系统名称</th>
					    		<th style="text-align:center !important;">操作</th>
					    	</thead>
					    	<c:forEach items="${roleList}" var="rolItem">
					    		<tr trName="${rolItem.roleName}" id="${rolItem.roleId}" style="cursor:pointer">
						    		<td style="text-align: center;">
					    				${rolItem.roleName}<input type="hidden" name="roleId" value="${rolItem.roleId}">
					    			</td>
					    			<td style="text-align: center;">
					    			    ${rolItem.systemName}
					    			</td>
					    			
					    			<td style="text-align: center;">
					    			 <a href="#" onclick="delrow('${rolItem.roleId}')" class="link del">移除</a>
					    			</td>
					    		</tr>
					    	</c:forEach>
					   	 </table>
					</div>
		            </td>
		            </tr>
				 </table>
			</div>						
	      	</c:if>
	      </div>      
	  </form>
</div>
</body>
</html>
