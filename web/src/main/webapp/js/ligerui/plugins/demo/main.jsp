<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>SZ BPM 基础平台</title>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerLayout.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerTab.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerMenu.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerAccordion.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerMsg.js"></script>
    <script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"  ></script>
    <script type="text/javascript" src="${ctx }/js/util/util.js"  ></script>
    <link href="common/css/common.css" rel="stylesheet" type="text/css" />  
    <link href="common/css/index.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
            var tab = null;
            var accordion = null;
            var tree = null;
            var ctxPath=__ctx;
            var setting = {
            		data: {key: {childs: "nodes"}},
            		callback: {
        				onClick: zTreeOnClick
        			}
            };

            $(function (){
                //布局
                $("#layoutMain").ligerLayout({ leftWidth: 190, height: '100%', onHeightChanged: f_heightChanged });
				//取得layout的高度
                var height = $(".l-layout-center").height();
               //Tab
                $("#framecenter").ligerTab({ height: height });
				//获取tab的引用
                tab = $("#framecenter").ligerGetTabManager();
                //面板
                $("#leftMemu").ligerAccordion({ height: height-24, speed: null });
				//获取leftMemu的引用
                accordion = $("#leftMemu").ligerGetAccordionManager();
				//加载面板
				loadAccordion();
                //隐藏加载对话框
                $("#pageloading").hide();
                
                
            });
			//布局大小改变的时候通知tab，面板改变大小
            function f_heightChanged(options){
                if (tab)
                    tab.addHeight(options.diff);
                //设置面板的高度
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
            }
			
           
            //添加树容器
            function addTreeContainer(id){
          	  var ul=$("<ul id='tree_" +id +"' class='ztree' />");
          	  $("body").append(ul);
          	  return ul;
            }
            //加载菜单面板
            function loadAccordion(){
            	$.getScript("${ctx }/js/tree/treeData.js", function(){
            		 var len=zNodes.length;
            		 for(var i=0;i<len;i++){
            			  var node=zNodes[i];
            			  var objUl=addTreeContainer(node.id);
            			  //自定义添加面板的方法。
            			  accordion.addPanel(node.name,"",objUl);
            			  $.fn.zTree.init($("#tree_" +node.id), setting, node.nodes);
            		  }
            		  //需要调用addComplete方法。
            		  accordion.addComplete();
            	});
            }
            //处理点击事件
            function zTreeOnClick(event, treeId, treeNode) {
            	var url=treeNode.link;
            	if(!url.startWith("http",false)) url=ctxPath +url;
            	//扩展了tab方法。
            	tab.addTabItem({ tabid : treeNode.id,text: treeNode.name, url: url,icon:'${ctx}/themes/img/ligericons/customers.gif' });
            }
     </script> 
<style type="text/css"> 
    body{ padding:0px; margin:0;   overflow:hidden;}  
    .l-link{ display:block; height:26px; line-height:26px; padding-left:10px; text-decoration:underline; color:#333;}
    .l-layout-top{background:#102A49; color:White;}
    #pageloading{position:absolute; left:0px; top:0px; background:white url('${ctx}/themes/img/ligericons/loading.gif') no-repeat center; width:100%; height:100%; height:700px; z-index:99999;}
    .l-link:hover{ background:#FFEEAC; border:1px solid #DB9F00;}
 </style>
</head>
<body style="padding:0px;">  
<div id="pageloading"></div>
  <div id="layoutMain" style="width:100%">
        <div position="top" style="background:#102A49; color:White; ">
            <div style="margin-top:10px; margin-left:10px">SZ BPM 基础平台</div>
        </div>
        <div position="left"  title="系统管理" id="leftMemu"> 
                   
        </div>
        <div position="center" id="framecenter"> 
            <div tabid="home" title="我的主页" style="height:300px" >
                <iframe frameborder="0" name="home" src=""></iframe>
            </div> 
        </div> 
      
    </div> 
      
</body>
</html>
