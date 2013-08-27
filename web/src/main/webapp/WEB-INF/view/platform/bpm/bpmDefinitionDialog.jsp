<%
	//我的流程定义外面窗口
%>
<%@page import="org.sz.platform.system.model.GlobalType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.sz.platform.bpm.model.flow.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
        <title>流程定义选择器</title>
		<%@include file="/commons/include/form.jsp" %>
		<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	    <script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMenu.js"></script>
		<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
		<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/form/GlobalType.js"></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/system/GlobalMenu.js"></script>
		<style type="text/css">
			
			body{overflow: hidden;}
		</style>
        <script type="text/javascript">
        		var catKey="<%=GlobalType.CAT_FLOW%>";
        		var globalType=new GlobalType(catKey,"glTypeTree",
       				{
        			onClick:onClick,
        			url:'${ctx}/platform/system/globalType/getByCatKeyForBpm.xht'
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
            		var url="${ctx}/platform/bpm/bpmDefinition/selector.xht?typeId="+typeId;
            		$("#bpmDefinitionListFrame").attr("src",url);
            	};
            	//展开收起
            	function treeExpandAll(type){
            		globalType.treeExpandAll(type);
            	};
            	
            	//返回选择的流程定义
            	function selectFlow(){
            		var chIds = $('#bpmDefinitionListFrame').contents().find(":input[name='defId'][checked]");
            		
            		var defIdArr=new Array();
            		var subjectArr=new Array();
            		$.each(chIds,function(i,ch){
            			defIdArr.push($(ch).val());
            			subjectArr.push($(ch).siblings("input[name='subject']").val());
            		});
            		
            		defIds=defIdArr.join(',');
            		subjects=subjectArr.join(',');
            		
            		var obj={defIds:defIds,subjects:subjects};
            		window.returnValue=obj;
            		window.close();
            	};
            	
            	function selectMulti(obj){
            		if($(obj).attr("checked")=="checked"){
            			add(obj);
            		}
            	};
            	
         </script> 
    </head>
    <body>
      	<div id="defLayout" >            
            <div position="left" title="流程分类" style="overflow: auto;float:left;width:100%">
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
            <div position="center" title="流程定义">
          		<iframe id="bpmDefinitionListFrame" name="bpmDefinitionListFrame" height="100%" width="100%" frameborder="0" src="${ctx}/platform/bpm/bpmDefinition/selector.xht"></iframe>
            </div>
            <c:if test="${isSingle==false}">
					<div   position="right" title="   <a onclick='javascript:dellAll();' class='link del'>&ensp;&ensp;&ensp;清空 </a> " style="overflow-y: auto;">
						<table width="145" id="sysUserList" class="table-grid table-list" id="0" cellpadding="1" cellspacing="1">
						</table>
					</div>
			</c:if>
            <div position="bottom" class="bottom" style='margin-top:10px;'>
			  	<a href='#' class='button'  onclick="selectFlow()" ><span >选择</span></a>
			    <a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span>取消</span></a>
			</div>
			
		  <div position="bottom"  class="bottom" style='margin-top:10px;'>
		  	<a href='#' class='button'  onclick="selectFlow()" ><span >选择</span></a>
		    <a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span>取消</span></a>
		</div>
</body>
       
</html>
