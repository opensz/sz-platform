<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>TAB控件使用</title>
    <%@include file="/commons/include/form.jsp" %>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerWindow.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerDrag.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerResizable.js" ></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerDialog.js" ></script>

    <script type="text/javascript">
           
    		var winIframe;
    		var winDiv;
  
            $(function (){
            	
            	var buttons=[{text:"确定",handler:okHandler},{text:"选择",handler:selHandler}];

            	$("#btnOpen").click(function(){
            		var w=400;
            		var h=200;
            		var left=($(window).width()-w)/2;
            		var top=($(window).height()-h)/2;

            		var p={url:"http://www.baidu.com",width:w,height:200,left:left,top:top,title:"修改设置",showMax:true,showToggle:true,modal:true,
            				onClose:closeHandler,showButton:true,buttons:buttons,frameid:"frmSetting"};

            		winIframe=$("body").ligerWindow(p);
            	});
            	
            	
            	
            	$("#btnOpenDiv").click(function(){
            		var w=400;
            		var h=200;
            		var left=($(window).width()-w)/2;
            		var top=($(window).height()-h)/2;
            		var p={width:w,height:200,left:left,top:top,title:"修改设置",showMax:true,showToggle:true,modal:true,
            				onClose:true,showButton:true,buttons:buttons};
            		if(!winDiv)
            			winDiv=$("#divContent").ligerWindow(p);
            		winDiv.show();
            		
            	});
            	
            	
            	$("#btnCloseDiv").click(function(){
         
            		winDiv.close();
            	});
            	
            	$("#btnCloseIframe").click(function(){
            		
            		winIframe.close();
            	});
            	
				
            });
            
            function selHandler(obj)
            {
            	alert("selHandler");
            	obj.close();
            }
            
            function okHandler(obj)
            {
            	alert("okHandler");
            	obj.close();
            }
            
            function closeHandler(obj)
            {
            	$.ligerDialog.confirm('确认关闭吗?', function (rtn)
                {
                        if( rtn){
                        	obj.remove();
                        }
                });

            }
			
     </script> 
<style type="text/css"> 
	#tbMemo tr{height:25px;}
   
 </style>
</head>
<body>  

	<div id="divContent" style="display:none;">
	测试
	</div>
	
	

        <input id="btnOpen" value="打开" type="button"/>
        
         <input id="btnOpenDiv" value="div打开" type="button"/>
         
          <input id="btnCloseDiv" value="div关闭" type="button"/>
          
          <input id="btnCloseIframe" value="iframe关闭" type="button"/>
        
        打开窗口参数定义说明:
        <table border="1" width="500" cellspacing="2" bgcolor="silver" id="tbMemo">
        	<tr>
        		<td>参数名</td>
        		<td>参数解释</td>
        		
        	</tr>
        	<tr>
        		<td>url</td>
        		<td>窗口指定要打开的URL</td>
        	</tr>
        	<tr>
        		<td>content</td>
        		<td>window需要显示的内容，用html字符串传入。</td>
        	</tr>
        	<tr>
        		<td>target</td>
        		<td>window需要显示的内容。使用方法。
        			<br/>
        			例如页面有一个DIV。
        			<div id="content"></div>
        		</td>
        	</tr>
        	<tr>
        		<td>width</td>
        		<td>window宽度</td>
        	</tr>
        	<tr>
        		<td>height</td>
        		<td>window高度</td>
        	</tr>
        	<tr>
        		<td>top</td>
        		<td>上边距</td>
        	</tr>
        	<tr>
        		<td>left</td>
        		<td>左边距</td>
        	</tr>
        	<tr>
        		<td>title</td>
        		<td>窗口标题</td>
        	</tr>
        	<tr>
        		<td>showMax</td>
        		<td>显示最大化按钮</td>
        	</tr>
        	<tr>
        		<td>showToggle</td>
        		<td>显示toggle按钮</td>
        	</tr>
        	<tr>
        		<td>showButton</td>
        		<td>显示带确定和取消按钮窗口</td>
        	</tr>
        	<tr>
        		<td>buttons</td>
        		<td>var buttons=[{text:"确定",handler:okHandler},{text:"选择",handler:selHandler}];</td>
        	</tr>
        	
        	<tr>
        		<td>frameid</td>
        		<td>如果使用url方式打开窗口，将在window中添加一个iframe。这个属性就是iframe的ID。</td>
        	</tr>
        	
        </table>
 
      
</body>
</html>
