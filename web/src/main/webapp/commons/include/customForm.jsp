<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="css.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<link href="${ctx}/jslib/tree/v30/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/default/css/form.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/default/css/form01.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/additional-methods.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.validate.ext.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.validation_localization_cn.js"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/base.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/htDicCombo.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMenuBar.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMenu.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTextBox.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTip.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTab.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMessageBox.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerDrag.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerResizable.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/calendar/My97DatePicker/WdatePicker.js" ></script>

<script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/rule.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/CustomForm.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/OfficeControl.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/OfficePlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/FormUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/CommonDialog.js"></script>
<style type="text/css">
	.validError {border: 1px solid red}
</style>
<script type="text/javascript">
$(function() {
	jQuery.validator.addMethod("maxIntLen", function(value, element, param) {
		return (value+'').split(".")[0].length <= param;
	}, "整数位最长为 {0} 位");
	
	jQuery.validator.addMethod("maxDecimalLen", function(value, element, param) {
		return (value+'').replace(/^[^.]*[.]*/, '').length <= param;
	}, "小数位最长为 {0} 位");
});



</script>