<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <%@include file="/commons/include/form.jsp" %>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerMessageBox.js"></script>
    <script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerDrag.js" ></script>

    <style type="text/css">
        .l-case-title{font-weight:bold; margin-top:20px;margin-bottom:20px;}
    </style>
    <script type="text/javascript">
      
       function msg_success()
       {
           $.ligerMessageBox.success('操作成功','操作提示操作提示操作提示操作提示');
       }
       function msg_info_bottom()
       {
           $.ligerMsg.info('操作提示操作提示操作提示操作提示', true);
       }
       
       function msg_warn()
       {
            $.ligerMessageBox.warn('提示信息','操作警告操作警告操作警告操作警告');
       }
       function msg_question()
       {
    	   $.ligerMessageBox.question('提示信息','确定吗?')
       }
       
    
       
       function msg_error()
       {
    	   $.ligerMessageBox.error('出错了','操作出错');
       }
     
       function msg_confirm()
       {
    	   var callback = function(rtn) {
  				if(rtn)
  					alert('你点了确定');
  				else
  					alert('你点了取消');
  			};
  			$.ligerMessageBox.confirm('提示信息','操作成功,继续修改吗?',callback)
       }
     
    </script>
</head>
<body style="height:100%;width:100%;padding:0px;">
 
  <span class="l-case-title">实例1.1：$.ligerMessageBox.success('提示信息','操作成功请返回');</span>
  <input type="button" onclick="msg_success()" value=" msg_success" />
  <br>
  <span class="l-case-title">实例1.2：$.ligerMessageBox.error('出错了','操作出错');</span>
  <input type="button" onclick="msg_error()" value=" msg_error " />
  <br><br>
  <span class="l-case-title">实例2.1：$.ligerMessageBox.warn('操作警告操作警告操作警告操作警告');</span>
  <input type="button" onclick="msg_warn()" value=" warn " />
  <br>
  <span class="l-case-title">实例2.1：ligerMessageBox.question('提示信息','确定吗?');</span>
  <input type="button" onclick="msg_question()" value="msg_question" />
  <br><br>
  <span class="l-case-title">实例3.1：$.ligerMessageBox.confirm('提示信息','操作成功,继续修改吗?',callback);</span>
  <input type="button" onclick="msg_confirm()" value="msg_confirm" />
  <br>
 



</body>
</html>
  