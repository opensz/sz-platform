<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
    <head>
        <title>代理任务列表</title>
        <%@include file="/commons/include/form.jsp" %>
        <link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	    <script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js" ></script>
		<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
        <script type="text/javascript">
        		//左分类树
        		var bpmAgentTree=null;
        		var globalTypeWin=null;
        		var typeRightWin=null;
        		
                $(function (){
                	//布局
                    $("#defLayout").ligerLayout({ leftWidth: 220,height: '100%',allowLeftResize:false});
                	//加载菜单 
                    loadTree();
                    //menu();
                    
            		$(document).click(function ()
         			{
         				$(".l-menu,.l-menu-shadow").hide();
         			});
                    
                });
                
                //加载树
            	function loadTree(){
            		hideRMenu();
            		var setting = {
            				data: {key: {children: "nodes"}},
            				view: {
            					selectedMulti: false
            				},
            			onRightClick: false,
            			callback:{onClick: zTreeOnLeftClick}
            		};
            		$.ajax({
            			type: 'POST',
            			url:"${ctx}/platform/bpm/bpmAgent/tree.xht",
            			success: function(result){
            				bpmAgentTree=$.fn.zTree.init($("#bpmAgentTree"), setting,eval(result));
            				bpmAgentTree.expandAll(true);
            			}
            		});
            	};
            	
            	//左击
            	function zTreeOnLeftClick(event, treeId, treeNode){            		
            		var url="${ctx}/platform/bpm/task/forAgent.xht?userId="+treeNode.id;
            		$("#defFrame").attr("src",url);
            	};
            	//展开收起
            	function treeExpandAll(type){
            		bpmAgentTree = $.fn.zTree.getZTreeObj("bpmAgentTree");
            		bpmAgentTree.expandAll(type);
            	};
            	/**
            	 * 菜单隐藏
            	 */
            	hideRMenu=function hideRMenu() {
            		$("#rMenu").css("visibility","hidden");
            	};
         </script> 
    </head>
    <body>
      	<div id="defLayout" >
      		<div position="left" title="代理授权人" style="overflow: auto;float:left;width:220px">
            	<div class="tree-toolbar">
					<span class="toolBar">
						<div class="group"><a class="link reload" id="treeFresh" href="javascript:loadTree();">刷新</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
					</span>
				</div>
				<ul id="bpmAgentTree" class="ztree"></ul>
            </div>
      	    <div position="center">
          		<iframe id="defFrame" height="100%" width="100%" frameborder="0" src="${ctx}/platform/bpm/task/forAgent.xht"></iframe>
            </div>
        </div>
    </body>
    </html>
