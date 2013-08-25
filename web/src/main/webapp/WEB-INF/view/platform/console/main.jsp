<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8" import="org.sz.platform.model.system.Resources"%>
<!DOCTYPE>
<html>
<head>
    <title>SZ Platform</title>
    <%@include file="/commons/include/form.jsp" %>
    <link rel="shortcut icon" href="${ctx}/commons/image/szplatform.ico">
    <script type="text/javascript" src="${ctx}/js/topCommon.js"></script>
    <link href="${ctx}/styles/${styleName}/css/Aqua/css/ligerui-all.css" type="text/css" rel="stylesheet" name="styleTag" title="index">
	<link href="${ctx}/styles/${styleName}/css/web.css" type="text/css" rel="stylesheet" name="styleTag" title="index">
	<link href="${ctx}/styles/${styleName}/css/index.css" type="text/css" rel="stylesheet" name="styleTag" title="index">
	<link href="${ctx}/styles/${styleName}/css/select.css" type="text/css" rel="stylesheet" name="styleTag" title="index">
	<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />    
   <style type="text/css">
   	.index_top{
		height:60px;
		
	}
	div.index_menu{
		top:27px;	
	}
   </style>
    <script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js" ></script>
    <script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTab.js" ></script>
    <script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMenu.js" ></script>
    <script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"  ></script>
    <script type="text/javascript">
            var tab = null;
            var tree = null;
            var ctxPath=__ctx;
          
            var setting = {
            		view: {showLine: true,nameIsHTML: true},
            		data: {
						key : {name: "resName"},
						simpleData: {enable: true,idKey: "resId",pIdKey: "parentId"}
					},
            		callback: {onClick: zTreeOnClick}
            };

            $(function (){
                //布局
                $("#layoutMain").ligerLayout({topHeight :80, leftWidth: 180, height: '100%', onHeightChanged: heightChanged });
				//取得layout的高度
                var height = $(".l-layout-center").height();
                $("#leftTree").height(height-35);
               //Tab
                $("#framecenter").ligerTab({ height: height});
				//获取tab的引用
                tab = $("#framecenter").ligerGetTabManager();
				//加载菜单
				loadMenu();
                //隐藏加载对话框
                $("#pageloading").hide();
 
                $("#menuPanel").delegate("a.menuItem", "click", function(){
                    var id=$(this).attr("id");
                    loadTree(id);
                    $(this).siblings().removeClass("menuItem_hover").end().addClass("menuItem_hover");
                    jQuery.setCookie("selectTab",id);
                });
                
                //更多操作的初始化
                var button = $('#loginButton');
        	    var box = $('#loginBox');
        	    var shade=$('#shadeEm');
        	    button.mouseup(function(login) {
        	        box.toggle();
        	        shade.toggle();
        	        button.toggleClass('active');        	  
        		});
        		$(this).mouseup(function(login) {
        			if ($(login.target).hasClass("more")){
        				var prehref=$(login.target).attr('prehref');
        				if(prehref)
       						addToTab(prehref,$(login.target).text(),$(login.target).attr('resid'));
        				else
        					location.href=$(login.target).attr('href');
        				button.removeClass('active');
        				box.hide();
        				shade.hide();
        			}
        			else if(!($(login.target).parent('#loginButton').length > 0)){
        				button.removeClass('active');
        				box.hide();
        				shade.hide();
        			}
        		});                
            });

			//布局大小改变的时候通知tab，面板改变大小
            function heightChanged(options){
                if (tab){
                	 tab.addHeight(options.diff);
                	 var height=$("#leftTree").height +options.diff;
                	 $("#leftTree").height(height);
                }
            }
		      
            var aryTreeData=null;
            //返回根节点
            function getRootNodes(){
            	var nodes=new Array();
            	for(var i=0;i<aryTreeData.length;i++){
            		var node=aryTreeData[i];
            		if(node.parentId==0){
            			nodes.push(node);
            		}
            	}
            	return nodes;
            };
            //初始化菜单滚动按钮
            function initRollButton(){	
        		var pWidth = $("div.menuParent").width(),sWidth = $("div.menuPanel").width();
        		if(sWidth<=0)return;		
        		var left = pWidth - sWidth;        		
        		if (left <= 0) {        			
        			$(".nav_left").css("display", "block");
        			$(".nav_right").css("display", "block");
        		}
        	};
            //加载菜单面板
            function loadMenu(){
                $("#leftTree").empty();
            	//一次性加载
				$.post("${ctx}/platform/console/getSysRolResTreeData.xht",
					 function(result){
						aryTreeData=result;
						for(var i=0;i<result.length;i++){
            				var node=result[i];
            				//node.resName="<span class='treeFont'>" + node.resName +"</span>";
            			}
						var headers=getRootNodes();
						var len=headers.length;
						var menuContainer=$("#menuPanel");
						for(var i=0;i<len;i++){
	            			var head=headers[i];
	            			var menuItemHtml=getMenuItem(head);
	            			menuContainer.append($(menuItemHtml));
	            		}
						initRollButton();
						if(len>0){
							var selectTab=jQuery.getCookie("selectTab");
							var obj= $("#" +selectTab);
							if(selectTab && obj.length>0){
								$("#" +selectTab).addClass("menuItem_hover");
								loadTree(selectTab);
							}
							else{
								var head=headers[0];
								var resId=head.resId;
								$("#" +resId).addClass("menuItem_hover");
								loadTree(resId);
							}
						}
					});
            }
            
            function loadTree(resId){
            	var nodes=new Array();
    			getChildByParentId(resId,nodes);
    			$.fn.zTree.init($("#leftTree"), setting, nodes);
            }
            
            //加载菜单项
            function getMenuItem(node){
            	var str='<a class="menuItem" id="'+node.resId+'">';
           		if(node.icon!="null" && node.icon!=""){
           			str+='<img src="'+node.icon+'" />';
           		}
           		str+='<span >'+node.resName+'</span></a>';
           		return str;
			}
            
            function getChildByParentId(parentId,nodes){
            	for(var i=0;i<aryTreeData.length;i++){
            		var node=aryTreeData[i];
            		if(node.parentId==parentId){
            			nodes.push(node);
            			getChildByParentId(node.resId,nodes);
            		}
            	}
            };
            
            //处理点击事件
            function zTreeOnClick(event, treeId, treeNode) {
            	var url= treeNode.defaultUrl;
            	if(!url.startWith("http",false)) url=ctxPath +url;
            	//扩展了tab方法。
            	addToTab(url,treeNode.resName,treeNode.resId,treeNode.icon);
            };
            
          	//切换系统
          	function saveCurrentSys(){
            	var systemId=$("#setSubSystem").val();
        		var form=new org.sz.form.Form();
        		form.creatForm("form", "${ctx}/platform/console/saveCurrSys.xht");
        		form.addFormEl("systemId", systemId);
        		form.submit();
			}
    		
          	// firefox下切换tab的高度处置
          	$(window).resize(function() {
          		if(navigator.userAgent.indexOf("Firefox")>0){
	       	    	setTimeout( 
	       	    	function(){
	                     $.each($(".l-tab-content-item"),function(idx,obj) {
	                     	if($(this).attr("style").indexOf("hidden")>0){
	                     		$(this).css({"height":"0px"});
	                     	}
	                     });
	       	    	}, 500);
          		}
          	});
         	// firefox下切换tab的高度处置
          	
          	function changeSkin(styleName){
				var linktitles=['index'];
				alertIframeSkin(linktitles,styleName);
						for(var i=0;i<linktitles.length;i++){
								setLink(linktitles[i],styleName);
						}
				$.post(__ctx + "/login/changeStyle.xht",{'styleName':styleName}, function(response) {
					var json=response;
					//var json=eval('('+response+')');
					if(json.success){
						alertIframeSkin(linktitles,styleName);
						for(var i=0;i<linktitles.length;i++){
								setLink(linktitles[i],styleName);
						}
					}
					
				
				});
			}
			function alertIframeSkin(linktitles,styleName,obj){
					$('iframe',obj).each(function(){			
								var subobj=	$(this).contents();
								
								for(var i=0;i<linktitles.length;i++){
										setLink(linktitles[i],styleName,subobj);
										if($('iframe',subobj).length>0)
											alertIframeSkin(linktitles,styleName,subobj);
								}
					});
			}
			//更改指定皮肤分风格
			function setLink(linktitle,styleName,obj){
				var updatelogo=true;
				$('link[rel=stylesheet]:[title='+linktitle+']',obj).each(
					function(){
						var curhref=this.href;
						var startIndex=curhref.indexOf("/styles/");
						var endIndex=curhref.indexOf("/css/");
						var substr=curhref.substring((startIndex+8),endIndex);
						this.href=curhref.replace(substr,styleName) ;
						if(updatelogo){
							var newlogo=$('#logoImg').attr("src").replace(substr,styleName);
							//$('#logoImg').attr("src",function(){ return newlogo});
							updatelogo=false;
						}
					}		
				);
			}
     </script> 
<style type="text/css"> 
    html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;}  
    #pageloading{position:absolute; left:0px; top:0px; background:white url('${ctx}/themes/img/ligericons/loading.gif') no-repeat center; width:100%; height:100%; height:700px; z-index:99999;}
    #top{color:White;height: 80px;}
	#top a{color:white;}
	
 </style>
</head>
<body style="padding:0px;">  
<div id="pageloading"></div>
	<%@include file="main_top.jspf" %>
  <div id="layoutMain" style="width:100%">
        <div position="left" title="<img src='${ctx}/themes/img/icon/home.png' >  ${currentSystem.sysName }"> 
	      	<ul id='leftTree' class='ztree' style="overflow:auto;height: 100%" ></ul>
        </div>	 
        
        <div position="center" id="framecenter"> 
        
            <div tabid="home" title="我的主页" style="height:300px" >
            	<c:if test="${not empty currentSystem.homePage }">
            		<c:choose>
            			<c:when test="${currentSystem.isLocal==1}">
            				<iframe frameborder="0" name="home" src="${ctx}${currentSystem.homePage}"></iframe>
            			</c:when>
            			<c:otherwise>
            				<iframe frameborder="0" name="home" src="${currentSystem.defaultUrl}${currentSystem.homePage}"></iframe>
            			</c:otherwise>
            		</c:choose>
            	</c:if>
            </div> 
        </div> 
    </div>    
</body>
</html>
