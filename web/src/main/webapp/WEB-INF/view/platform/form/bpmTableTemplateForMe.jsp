<%@page import="org.sz.platform.system.model.GlobalType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.sz.platform.bpm.model.form.BpmTableTemprights"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
        <title>业务数据模板管理</title>
		<%@include file="/commons/include/form.jsp" %>
		<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
		<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/form/GlobalType.js"></script>
		<style type="text/css">
			.tree-title{overflow:hidden;width:100%;}
			body{overflow: hidden;}
		</style>	
        <script type="text/javascript">
        		var catKey="<%=GlobalType.CAT_FORM%>";
        		var globalType=new GlobalType(catKey,"glTypeTree",
        				{
        					onClick:onClick,        					
        					url:'${ctx}/platform/system/globalType/getByCatKeyForForm.xht'
        				});        		
                $(function (){
                	//布局
                    $("#defLayout").ligerLayout({ leftWidth:210,height: '100%',allowLeftResize:false});
                	//加载菜单 
                    globalType.loadGlobalTree();                	
                });
            	
            	//左击
            	function onClick(treeNode){
            		var typeId=treeNode.typeId;            		
            		var url="${ctx}/platform/form/bpmTableTemplate/myList.xht?typeId="+typeId;
            		$("#defFrame").attr("src",url);
            	};
            	//展开收起
            	function treeExpandAll(type){
            		globalType.treeExpandAll(type);
            	};
            	
         </script> 
    </head>
    <body>
      	<div id="defLayout" >
            <div position="left" title="表单类型分类管理" style="overflow: auto;float:left;width:100%">
            	<div class="tree-toolbar">
					<span class="toolBar">
						<div class="group"><a class="link reload" id="treeFresh" href="javascript:globalType.loadGlobalTree();;">刷新</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
					</span>
				</div>
				<ul id="glTypeTree" class="ztree"></ul>
            </div>
            <div position="center" title="业务数据模板管理">
          		<iframe id="defFrame" height="100%" width="100%" frameborder="0" src="${ctx}/platform/form/bpmTableTemplate/myList.xht"></iframe>
            </div>
        </div>
	
		
    </body>
    </html>
