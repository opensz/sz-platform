<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <%@include file="/commons/include/form.jsp" %>
    <link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>    
    <script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerMsg.js"></script>

    <style type="text/css">
        .l-case-title{font-weight:bold; margin-top:20px;margin-bottom:20px;}
    </style>
    <script type="text/javascript">
      
       function msg_info()
       {
           $.ligerMsg.info('操作提示操作提示操作提示操作提示');
       }
       function msg_info_bottom()
       {
           $.ligerMsg.info('操作提示操作提示操作提示操作提示', true);
       }
       
       function msg_warn()
       {
            $.ligerMsg.warn('操作警告操作警告操作警告操作警告');
       }
       function msg_warn_bottom()
       {
            $.ligerMsg.warn('操作警告操作警告操作警告操作警告', true);
       }
       
       function msg_correct()
       {
            $.ligerMsg.correct('操作成功操作成功操作成功操作成功');
       }
       function msg_correct_bottom()
       {
            $.ligerMsg.correct('操作成功操作成功操作成功操作成功', true);
       }
       
       function msg_error()
       {
            $.ligerMsg.error('操作错误操作错误操作错误操作错误');
       }
       function msg_error_bottom()
       {
            $.ligerMsg.error('操作错误操作错误操作错误操作错误', true);
       }
      
       function msg_confirm()
       {
       			var callback = function() {
       				alert('你点了确定');
       			};
            $.ligerMsg.confirm('请确定请确定请确定请确定请确定', callback);
       }
       function msg_confirm_bottom()
       {
       			var callback = function() {
       				alert('你点了确定');
       			};
            $.ligerMsg.confirm('请确定请确定请确定请确定请确定', callback, true);
       }
    </script>
</head>
<body style="height:100%;width:100%;padding:0px;">
 
  <span class="l-case-title">实例1.1：$.ligerMsg.info('操作提示操作提示操作提示操作提示');</span>
  <input type="button" onclick="msg_info()" value=" info top" />
  <br>
  <span class="l-case-title">实例1.2：$.ligerMsg.info('操作提示操作提示操作提示操作提示', true);</span>
  <input type="button" onclick="msg_info_bottom()" value=" info bottom " />
  <br><br>
  <span class="l-case-title">实例2.1：$.ligerMsg.warn('操作警告操作警告操作警告操作警告');</span>
  <input type="button" onclick="msg_warn()" value=" warn top " />
  <br>
  <span class="l-case-title">实例2.1：$.ligerMsg.warn('操作警告操作警告操作警告操作警告', true);</span>
  <input type="button" onclick="msg_warn_bottom()" value=" warn bottom " />
  <br><br>
  <span class="l-case-title">实例3.1：$.ligerMsg.correct('操作成功操作成功操作成功操作成功');</span>
  <input type="button" onclick="msg_correct()" value=" correct top " />
  <br>
  <span class="l-case-title">实例3.1：$.ligerMsg.correct('操作成功操作成功操作成功操作成功', true);</span>
  <input type="button" onclick="msg_correct_bottom()" value=" correct bottom " />
  <br><br>
  <span class="l-case-title">实例4.1：$.ligerMsg.error('操作错误操作错误操作错误操作错误');</span>
  <input type="button" onclick="msg_error()" value=" error top " />
  <br>
  <span class="l-case-title">实例4.1：$.ligerMsg.error('操作错误操作错误操作错误操作错误', true);</span>
  <input type="button" onclick="msg_error_bottom()" value=" error bottom " />
  <br><br>
  <span class="l-case-title">实例5.1：$.ligerMsg.confirm('请确定请确定请确定请确定请确定', callback);</span>
  <input type="button" onclick="msg_confirm()" value=" confirm top " />
  <br>
  <span class="l-case-title">实例5.1：$.ligerMsg.confirm('请确定请确定请确定请确定请确定', callback, true);</span>
  <input type="button" onclick="msg_confirm_bottom()" value=" confirm bottom " />
  <br>
  
  <br>
  <br>
  <br>
  <br>


</body>
</html>
    