<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>BUTTON控件使用</title>
    <%@include file="/commons/include/form.jsp" %>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/htButtons.js" ></script>
	<script type="text/javascript" src="${ctx}/js/ligerui/plugins/ligerDrag.js" ></script>
	<script type="text/javascript" src="${ctx}/js/ligerui/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js" ></script>
	<script type="text/javascript">
            var tab = null;
  
            $(function (){
       
                $("span.htbtn").htButtons();
				
            });
			
            function test()
            {
            	alert("aaaa");
            }
            
            var __ctx='${ctx}';
            // 角色选择器
            function showRoleDlg()
            {
               RoleDialog({
                  	callback:function(roleIds,roleNames){
                     alert('return: roleIds:'+roleIds + ' roleNames'+ roleNames);
                  }
               });
            }
			
            // 组织选择器
            function showOrgDlg()
            {
               OrgDialog({
                  callback:function(orgIds,orgNames){
                     alert('return: orgIds:'+orgIds + ' orgNames'+ orgNames);
                  }
               });
            }
            
            // 岗位选择器
            function showPosDlg()
            {
               PosDialog({
                  callback:function(posIds,posNames){
                     alert('return: posIds:'+posIds + ' posNames'+ posNames);
                  }
               });
            }
            
     </script> 
     
     
<style type="text/css"> 
	
    body{ padding:0px; margin:0;overflow:hidden;}  

 </style>
</head>
<body>  

        <span class="htbtn" enable="true" onclick="showRoleDlg()">角色选择器</span>
        <span class="htbtn" onclick="showOrgDlg()">组织选择器</span>
        <span class="htbtn" onclick="showPosDlg()">岗位选择器</span>
        <span class="htbtn">按钮测试</span>
        <span class="htbtn">按钮测试</span>
       
 
      
</body>
</html>
