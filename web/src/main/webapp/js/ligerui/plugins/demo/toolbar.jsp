<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>BUTTON控件使用</title>
    <%@include file="/commons/include/form.jsp" %>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    
   
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerToolBar.js" ></script>
    

    <script type="text/javascript">
         
            $(function (){
       
            	/*
            	$("#toptoolbar").ligerToolBar({ items: [
            	                                        { text: '增加', click: itemclick },
            	                                        { text: '修改', click: itemclick },
            	                                        { text: '删除', click: itemclick }
            	                                    ]
            	                                    });
            	*/
            	
            	$("#toptoolbar").ligerToolBar();
            	
            	function itemclick(item)
                {
                    alert(item.text);
                }


				
            });
			
     </script> 
<style type="text/css"> 
	
    body{ padding:0px; margin:0;overflow:hidden;}  

 </style>
</head>
<body>  


       
 <div id="toptoolbar" float="right" >
 <div icon="${ctx}/themes/img/ligericons/customers.gif" disable="true"  >添加</div>
 <div icon="${ctx}/themes/img/ligericons/customers.gif" >删除</div>
 <div icon="${ctx}/themes/img/ligericons/customers.gif" >更新</div>
 </div> 
 
  float="right"<br/>
 toolbar: 自定义float属性：<br/>
 left:按钮向左排。<br/>
 right:按钮想右排。<br/>
 <br/>
 按钮：<br/>
自定义属性：<br/>
icon ：按钮图标<br/>
disable：灰显，禁止按钮。<br/>
</body>
</html>
