<%
	//我的流程定义外面窗口
%>
<%@page import="org.sz.platform.system.model.GlobalType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.sz.platform.bpm.model.flow.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
        <title>我的流程</title>
		<%@include file="/commons/include/get.jsp" %>
		<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
		<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/form/GlobalType.js"></script>
		<style type="text/css">
			.tree-title{overflow:hidden;width:100%;}
			body{overflow: hidden;}
		</style>	
        <script type="text/javascript">
        		var catKey="<%=GlobalType.CAT_FLOW%>";
        		var globalType=new GlobalType(catKey,"glTypeTree",{onClick:onClick,url:'${ctx}/platform/system/globalType/getByCatKeyForBpm.xht'});
                $(function (){
                	//布局
                    $("#defLayout").ligerLayout({ leftWidth:210,height: '100%',allowLeftResize:false});
                	//加载菜单 
                    globalType.loadGlobalTree();
                	
                });
              	//左击
            	function onClick(treeNode){
            		var typeId=treeNode.typeId;
            		var url="${ctx}/platform/bpm/bpmDefinition/myList.xht?typeId="+typeId;
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
            <div position="left" title="流程分类" style="overflow: auto;float:left;width:100%">
            	<div class="tree-toolbar">
					<span class="toolBar">
						<div class="group"><a class="tref bar-button" id="treeFresh" href="javascript:globalType.loadGlobalTree();">刷新</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="texp bar-button" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="tcol bar-button" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
					</span>
				</div>
				<ul id="glTypeTree" class="ztree"></ul>
            </div>
            <div position="center" title="流程定义">
          		<iframe id="defFrame" height="100%" width="100%" frameborder="0" src="${ctx}/platform/bpm/bpmDefinition/myList.xht"></iframe>
            </div>
        </div>
    </body>
</html>
